package com.hsm.patientservice.grpc;

import com.hsm.protodefinitions.billing.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.hsm.protodefinitions.billing.BillingServiceGrpc.BillingServiceBlockingStub;

@Service
@Slf4j
public class BillingServiceGrpcClient {


    private final BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
    @Value("${billing.service.grpc.port:9001}") int serverPort){
        log.info("Connecting to BillingService GRPC service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public void createBillingAccount(Long patientId, String name, String email){

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing create service via GRPC: {}", response);
    }

    public void updateBillingAccount(Long patientId, String name, String email){

        BillingUpdateRequest request = BillingUpdateRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email).build();


        BillingUpdateResponse response = blockingStub.updateBillingAccount(request);
        log.info("Received response from billing update service via GRPC: {}", response);
    }

    public void deleteBillingAccount(Long patientId){

        BillingDeleteRequest request = BillingDeleteRequest.newBuilder()
                .setPatientId(patientId)
                .build();

        BillingDeleteResponse response = blockingStub.deleteBillingAccount(request);
        log.info("Received response from billing delete service via GRPC: {}", response);
    }
}
