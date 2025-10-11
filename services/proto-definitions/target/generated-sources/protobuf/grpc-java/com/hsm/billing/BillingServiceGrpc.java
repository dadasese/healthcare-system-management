package com.hsm.billing;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: billing/billing_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BillingServiceGrpc {

  private BillingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "BillingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.hsm.billing.BillingRequest,
      com.hsm.billing.BillingResponse> getCreateBillingAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateBillingAccount",
      requestType = com.hsm.billing.BillingRequest.class,
      responseType = com.hsm.billing.BillingResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hsm.billing.BillingRequest,
      com.hsm.billing.BillingResponse> getCreateBillingAccountMethod() {
    io.grpc.MethodDescriptor<com.hsm.billing.BillingRequest, com.hsm.billing.BillingResponse> getCreateBillingAccountMethod;
    if ((getCreateBillingAccountMethod = BillingServiceGrpc.getCreateBillingAccountMethod) == null) {
      synchronized (BillingServiceGrpc.class) {
        if ((getCreateBillingAccountMethod = BillingServiceGrpc.getCreateBillingAccountMethod) == null) {
          BillingServiceGrpc.getCreateBillingAccountMethod = getCreateBillingAccountMethod =
              io.grpc.MethodDescriptor.<com.hsm.billing.BillingRequest, com.hsm.billing.BillingResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateBillingAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BillingServiceMethodDescriptorSupplier("CreateBillingAccount"))
              .build();
        }
      }
    }
    return getCreateBillingAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hsm.billing.BillingUpdateRequest,
      com.hsm.billing.BillingUpdateResponse> getUpdateBillingAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateBillingAccount",
      requestType = com.hsm.billing.BillingUpdateRequest.class,
      responseType = com.hsm.billing.BillingUpdateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hsm.billing.BillingUpdateRequest,
      com.hsm.billing.BillingUpdateResponse> getUpdateBillingAccountMethod() {
    io.grpc.MethodDescriptor<com.hsm.billing.BillingUpdateRequest, com.hsm.billing.BillingUpdateResponse> getUpdateBillingAccountMethod;
    if ((getUpdateBillingAccountMethod = BillingServiceGrpc.getUpdateBillingAccountMethod) == null) {
      synchronized (BillingServiceGrpc.class) {
        if ((getUpdateBillingAccountMethod = BillingServiceGrpc.getUpdateBillingAccountMethod) == null) {
          BillingServiceGrpc.getUpdateBillingAccountMethod = getUpdateBillingAccountMethod =
              io.grpc.MethodDescriptor.<com.hsm.billing.BillingUpdateRequest, com.hsm.billing.BillingUpdateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateBillingAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingUpdateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingUpdateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BillingServiceMethodDescriptorSupplier("UpdateBillingAccount"))
              .build();
        }
      }
    }
    return getUpdateBillingAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.hsm.billing.BillingDeleteRequest,
      com.hsm.billing.BillingDeleteResponse> getDeleteBillingAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteBillingAccount",
      requestType = com.hsm.billing.BillingDeleteRequest.class,
      responseType = com.hsm.billing.BillingDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.hsm.billing.BillingDeleteRequest,
      com.hsm.billing.BillingDeleteResponse> getDeleteBillingAccountMethod() {
    io.grpc.MethodDescriptor<com.hsm.billing.BillingDeleteRequest, com.hsm.billing.BillingDeleteResponse> getDeleteBillingAccountMethod;
    if ((getDeleteBillingAccountMethod = BillingServiceGrpc.getDeleteBillingAccountMethod) == null) {
      synchronized (BillingServiceGrpc.class) {
        if ((getDeleteBillingAccountMethod = BillingServiceGrpc.getDeleteBillingAccountMethod) == null) {
          BillingServiceGrpc.getDeleteBillingAccountMethod = getDeleteBillingAccountMethod =
              io.grpc.MethodDescriptor.<com.hsm.billing.BillingDeleteRequest, com.hsm.billing.BillingDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteBillingAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.hsm.billing.BillingDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BillingServiceMethodDescriptorSupplier("DeleteBillingAccount"))
              .build();
        }
      }
    }
    return getDeleteBillingAccountMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BillingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceStub>() {
        @java.lang.Override
        public BillingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceStub(channel, callOptions);
        }
      };
    return BillingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BillingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceBlockingStub>() {
        @java.lang.Override
        public BillingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceBlockingStub(channel, callOptions);
        }
      };
    return BillingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BillingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BillingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BillingServiceFutureStub>() {
        @java.lang.Override
        public BillingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BillingServiceFutureStub(channel, callOptions);
        }
      };
    return BillingServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createBillingAccount(com.hsm.billing.BillingRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateBillingAccountMethod(), responseObserver);
    }

    /**
     */
    default void updateBillingAccount(com.hsm.billing.BillingUpdateRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingUpdateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateBillingAccountMethod(), responseObserver);
    }

    /**
     */
    default void deleteBillingAccount(com.hsm.billing.BillingDeleteRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingDeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteBillingAccountMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BillingService.
   */
  public static abstract class BillingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BillingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BillingService.
   */
  public static final class BillingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<BillingServiceStub> {
    private BillingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceStub(channel, callOptions);
    }

    /**
     */
    public void createBillingAccount(com.hsm.billing.BillingRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateBillingAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateBillingAccount(com.hsm.billing.BillingUpdateRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingUpdateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateBillingAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteBillingAccount(com.hsm.billing.BillingDeleteRequest request,
        io.grpc.stub.StreamObserver<com.hsm.billing.BillingDeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteBillingAccountMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BillingService.
   */
  public static final class BillingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BillingServiceBlockingStub> {
    private BillingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.hsm.billing.BillingResponse createBillingAccount(com.hsm.billing.BillingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateBillingAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.hsm.billing.BillingUpdateResponse updateBillingAccount(com.hsm.billing.BillingUpdateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateBillingAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.hsm.billing.BillingDeleteResponse deleteBillingAccount(com.hsm.billing.BillingDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteBillingAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BillingService.
   */
  public static final class BillingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<BillingServiceFutureStub> {
    private BillingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BillingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BillingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hsm.billing.BillingResponse> createBillingAccount(
        com.hsm.billing.BillingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateBillingAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hsm.billing.BillingUpdateResponse> updateBillingAccount(
        com.hsm.billing.BillingUpdateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateBillingAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hsm.billing.BillingDeleteResponse> deleteBillingAccount(
        com.hsm.billing.BillingDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteBillingAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_BILLING_ACCOUNT = 0;
  private static final int METHODID_UPDATE_BILLING_ACCOUNT = 1;
  private static final int METHODID_DELETE_BILLING_ACCOUNT = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_BILLING_ACCOUNT:
          serviceImpl.createBillingAccount((com.hsm.billing.BillingRequest) request,
              (io.grpc.stub.StreamObserver<com.hsm.billing.BillingResponse>) responseObserver);
          break;
        case METHODID_UPDATE_BILLING_ACCOUNT:
          serviceImpl.updateBillingAccount((com.hsm.billing.BillingUpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.hsm.billing.BillingUpdateResponse>) responseObserver);
          break;
        case METHODID_DELETE_BILLING_ACCOUNT:
          serviceImpl.deleteBillingAccount((com.hsm.billing.BillingDeleteRequest) request,
              (io.grpc.stub.StreamObserver<com.hsm.billing.BillingDeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateBillingAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hsm.billing.BillingRequest,
              com.hsm.billing.BillingResponse>(
                service, METHODID_CREATE_BILLING_ACCOUNT)))
        .addMethod(
          getUpdateBillingAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hsm.billing.BillingUpdateRequest,
              com.hsm.billing.BillingUpdateResponse>(
                service, METHODID_UPDATE_BILLING_ACCOUNT)))
        .addMethod(
          getDeleteBillingAccountMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.hsm.billing.BillingDeleteRequest,
              com.hsm.billing.BillingDeleteResponse>(
                service, METHODID_DELETE_BILLING_ACCOUNT)))
        .build();
  }

  private static abstract class BillingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BillingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hsm.billing.BillingServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BillingService");
    }
  }

  private static final class BillingServiceFileDescriptorSupplier
      extends BillingServiceBaseDescriptorSupplier {
    BillingServiceFileDescriptorSupplier() {}
  }

  private static final class BillingServiceMethodDescriptorSupplier
      extends BillingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BillingServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BillingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BillingServiceFileDescriptorSupplier())
              .addMethod(getCreateBillingAccountMethod())
              .addMethod(getUpdateBillingAccountMethod())
              .addMethod(getDeleteBillingAccountMethod())
              .build();
        }
      }
    }
    return result;
  }
}
