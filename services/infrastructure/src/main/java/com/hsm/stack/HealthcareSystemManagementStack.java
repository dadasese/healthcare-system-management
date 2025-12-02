package com.hsm.stack;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.msk.CfnCluster;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.secretsmanager.Secret;
import software.amazon.awscdk.services.secretsmanager.SecretStringGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class HealthcareSystemManagementStack extends Stack {

    private final Vpc vpc;
    private final Cluster ecsCluster;

    private DatabaseInstance authServiceDb;
    private DatabaseInstance patientServiceDb;
    private DatabaseInstance analyticsServiceDb;

    public HealthcareSystemManagementStack(final App scope, final String id, final StackProps props){
        super(scope, id, props);

        // 1. Network Layer - Simple VPC for LocalStack
        this.vpc = createVpc();

        // 2. Data Layer
        // Auth Service: Own database (MySQL)
        this.authServiceDb = createMySQLDatabase(
                "AuthServiceDB",
                "authservicedb",
                "Database for Authentication Service"
        );

        // Patient Service: Primary transactional database (PostgreSQL)
        this.patientServiceDb = createPostgresDatabase(
                "PatientServiceDB",
                "patientservicedb",
                "Database for Patient Service"
        );

        // Analytics Service: Separate analytical database (PostgreSQL)
        this.analyticsServiceDb = createPostgresDatabase(
                "AnalyticsServiceDB",
                "analyticsservicedb",
                "Database for Analytics Service"
        );

        // 5. Compute Layer - Create cluster first
        this.ecsCluster = createEcsCluster();

        // 6. Application Services - All depend on cluster
        FargateService authService = createFargateService(
                "AuthService",
                "auth-service",
                List.of(4005),
                authServiceDb,
                Map.of("JWT_SECRET", "d1282dbc0a771c5d93b0f2398c5efcb7cd103cf2e0c2dd1a3316123f9102f9b9")
        );
        authService.getNode().addDependency(ecsCluster);
        authService.getNode().addDependency(authServiceDb);

        FargateService billingService = createFargateService(
                "BillingService",
                "billing-service",
                List.of(4001, 9001),
                patientServiceDb,
                null
        );
        billingService.getNode().addDependency(ecsCluster);
        billingService.getNode().addDependency(patientServiceDb);

        FargateService analyticsService = createFargateService(
                "AnalyticsService",
                "analytics-service",
                List.of(4002),
                analyticsServiceDb,
                null
        );
        analyticsService.getNode().addDependency(ecsCluster);
        analyticsService.getNode().addDependency(analyticsServiceDb);

        FargateService patientService = createFargateService(
                "PatientService",
                "patient-service",
                List.of(4000),
                patientServiceDb,
                Map.of(
                        "BILLING_SERVICE_ADDRESS", "localhost",
                        "BILLING_SERVICE_GRPC_PORT", "9001"
                )
        );
        patientService.getNode().addDependency(ecsCluster);
        patientService.getNode().addDependency(patientServiceDb);
        patientService.getNode().addDependency(billingService);

        FargateService apiGateway = createApiGatewayService();
        apiGateway.getNode().addDependency(ecsCluster);
        apiGateway.getNode().addDependency(authService);
        apiGateway.getNode().addDependency(patientService);
    }

    private Vpc createVpc(){
        // LocalStack free tier: simplified VPC without NAT gateways
        return Vpc.Builder
                .create(this, "HealthcareSystemManagementVPC")
                .vpcName("HealthcareSystemManagementVPC")
                .maxAzs(2) // LocalStack works better with single AZ
                .natGateways(0) // NAT gateways not needed in LocalStack
                .subnetConfiguration(List.of(
                        SubnetConfiguration.builder()
                                .name("Public")
                                .subnetType(SubnetType.PUBLIC)
                                .cidrMask(24)
                                .build()
                ))
                .build();
    }

    /**
     * Creates a PostgreSQL database instance - simplified for LocalStack
     */
    private DatabaseInstance createPostgresDatabase(String id, String name, String description) {
        // LocalStack free tier: simplified secret without complex generation
        Secret dbSecret = Secret.Builder.create(this, id + "Secret")
                .secretName(id.toLowerCase() + "-creds")
                .description(description + " credentials")
                .secretStringValue(SecretValue.unsafePlainText(
                        "{\"username\":\"admin_user\",\"password\":\"admin_password\"}"
                ))
                .build();

        return DatabaseInstance.Builder.create(this, id)
                .engine(DatabaseInstanceEngine.postgres(
                        PostgresInstanceEngineProps.builder()
                                .version(PostgresEngineVersion.VER_16) // Use older version for LocalStack
                                .build()
                ))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC) // Public subnet for LocalStack
                        .build())
                .databaseName(name)
                .credentials(Credentials.fromPassword("admin_user", SecretValue.unsafePlainText("admin_password")))
                .allocatedStorage(20)
                .storageType(StorageType.GP2) // GP2 instead of GP3 for LocalStack
                .multiAz(false)
                .publiclyAccessible(true) // LocalStack needs this
                .deletionProtection(false)
                .removalPolicy(RemovalPolicy.DESTROY)
                .backupRetention(Duration.days(0))
                .build();
    }

    /**
     * Creates a MySQL database instance - simplified for LocalStack
     */
    private DatabaseInstance createMySQLDatabase(String id, String dbName, String description) {
        Secret dbSecret = Secret.Builder.create(this, id + "Secret")
                .secretName(id.toLowerCase() + "-creds")
                .description(description + " credentials")
                .secretStringValue(SecretValue.unsafePlainText(
                        "{\"username\":\"admin_user\",\"password\":\"admin_password\"}"
                ))
                .build();

        return DatabaseInstance.Builder.create(this, id)
                .engine(DatabaseInstanceEngine.mysql(
                        MySqlInstanceEngineProps.builder()
                                .version(MysqlEngineVersion.VER_8_0) // Use version without patch number
                                .build()
                ))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO))
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .databaseName(dbName)
                .credentials(Credentials.fromPassword("admin_user", SecretValue.unsafePlainText("admin_password")))
                .allocatedStorage(20)
                .storageType(StorageType.GP2)
                .multiAz(false)
                .publiclyAccessible(true)
                .deletionProtection(false)
                .removalPolicy(RemovalPolicy.DESTROY)
                .backupRetention(Duration.days(0))
                .build();
    }

    private Cluster createEcsCluster(){
        // LocalStack: simplified cluster without CloudMap (not supported in free tier)
        return Cluster.Builder.create(this, "HealthcareSystemManagementCluster")
                .vpc(vpc)
                .clusterName("healthcare-cluster")
                .build();
    }

    private FargateService createFargateService(
            String id,
            String imageName,
            List<Integer> ports,
            DatabaseInstance db,
            Map<String, String> additionalEnvVars
    ) {
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
                .create(this, id + "Task")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        Map<String, String> envVars = new HashMap<>();

        // LocalStack Kafka endpoints
        envVars.put("SPRING_KAFKA_BOOTSTRAP_SERVERS", "localhost.localstack.cloud:4510,localhost.localstack.cloud:4511,localhost.localstack.cloud:4512");

        if(additionalEnvVars != null){
            envVars.putAll(additionalEnvVars);
        }

        if (db != null){
            if (db.equals(authServiceDb)){
                // MySQL configuration
                envVars.put("SPRING_DATASOURCE_URL", String.format(
                        "jdbc:mysql://%s:%s/%s",
                        db.getDbInstanceEndpointAddress(),
                        db.getDbInstanceEndpointPort(),
                        "authservicedb"
                ));
                envVars.put("SPRING_DATASOURCE_USERNAME", "admin_user");
                envVars.put("SPRING_DATASOURCE_PASSWORD", "admin_password");
                envVars.put("SPRING_DATASOURCE_DRIVER_CLASS_NAME", "com.mysql.cj.jdbc.Driver");
                envVars.put("SPRING_JPA_DATABASE_PLATFORM", "org.hibernate.dialect.MySQLDialect");
            } else {
                // PostgreSQL configuration
                String dbName = db.equals(patientServiceDb) ? "patientservicedb" : "analyticsservicedb";
                envVars.put("SPRING_DATASOURCE_URL", String.format(
                        "jdbc:postgresql://%s:%s/%s",
                        db.getDbInstanceEndpointAddress(),
                        db.getDbInstanceEndpointPort(),
                        dbName
                ));
                envVars.put("SPRING_DATASOURCE_USERNAME", "admin_user");
                envVars.put("SPRING_DATASOURCE_PASSWORD", "admin_password");
                envVars.put("SPRING_DATASOURCE_DRIVER_CLASS_NAME", "org.postgresql.Driver");
                envVars.put("SPRING_JPA_DATABASE_PLATFORM", "org.hibernate.dialect.PostgreSQLDialect");
            }

            // Common settings
            envVars.put("SPRING_JPA_HIBERNATE_DDL_AUTO", "update");
            envVars.put("SPRING_SQL_INIT_MODE", "always");
            envVars.put("SPRING_DATASOURCE_HIKARI_INITIALIZATION_FAIL_TIMEOUT", "120000");
            envVars.put("SPRING_DATASOURCE_HIKARI_CONNECTION_TIMEOUT", "60000");
        }

        ContainerDefinitionOptions containerDefinitionOptions =
                ContainerDefinitionOptions.builder()
                        .image(ContainerImage.fromRegistry(imageName))
                        .environment(envVars)
                        .portMappings(ports.stream()
                                .map(port -> PortMapping.builder()
                                        .containerPort(port)
                                        .protocol(Protocol.TCP)
                                        .build())
                                .toList())
                        .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                .logGroup(LogGroup.Builder.create(this, id + "LogGroup")
                                        .logGroupName("/ecs/" + imageName)
                                        .removalPolicy(RemovalPolicy.DESTROY)
                                        .retention(RetentionDays.ONE_DAY)
                                        .build())
                                .streamPrefix(imageName)
                                .build()))
                        .build();

        taskDefinition.addContainer(imageName + "Container", containerDefinitionOptions);

        return FargateService.Builder.create(this, id)
                .cluster(ecsCluster)
                .taskDefinition(taskDefinition)
                .assignPublicIp(true) // LocalStack needs public IP
                .serviceName(imageName)
                .desiredCount(1)
                .build();
    }

    private FargateService createApiGatewayService(){
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder
                .create(this, "ApiGatewayTaskDefinition")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();

        ContainerDefinitionOptions containerOptions =
                ContainerDefinitionOptions.builder()
                        .image(ContainerImage.fromRegistry("api-gateway"))
                        .environment(Map.of(
                                "SPRING_PROFILES_ACTIVE", "prod",
                                "AUTH_SERVICE_URL", "http://localhost:4005",
                                "PATIENT_SERVICE_URL", "http://localhost:4000"
                        ))
                        .portMappings(Stream.of(4004)
                                .map(port -> PortMapping.builder()
                                        .containerPort(port)
                                        .protocol(Protocol.TCP)
                                        .build())
                                .toList())
                        .logging(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                .logGroup(LogGroup.Builder.create(this, "ApiGatewayLogGroup")
                                        .logGroupName("/ecs/api-gateway")
                                        .removalPolicy(RemovalPolicy.DESTROY)
                                        .retention(RetentionDays.ONE_DAY)
                                        .build())
                                .streamPrefix("api-gateway")
                                .build()))
                        .build();

        taskDefinition.addContainer("APIGatewayContainer", containerOptions);

        // LocalStack free tier: Use simple FargateService instead of ApplicationLoadBalancedFargateService
        return FargateService.Builder.create(this, "APIGatewayService")
                .cluster(ecsCluster)
                .serviceName("api-gateway")
                .taskDefinition(taskDefinition)
                .desiredCount(1)
                .assignPublicIp(true)
                .build();
    }

    public static void main(final String[] args){
        App app = new App(AppProps.builder().outdir("./cdk.out").build());

        StackProps props = StackProps.builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build();

        new HealthcareSystemManagementStack(app, "HealthcareSystemManagementStack", props);
        app.synth();
        System.out.println("App synthesizing completed.");
    }
}