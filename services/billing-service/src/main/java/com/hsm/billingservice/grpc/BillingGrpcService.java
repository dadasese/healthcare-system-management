package com.hsm.billingservice.grpc;

import com.hsm.billing.*;
import com.hsm.billingservice.exception.EmailException;
import com.hsm.billingservice.model.Billing;
import com.hsm.billingservice.service.BillingService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import com.hsm.billing.BillingServiceGrpc.BillingServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class BillingGrpcService extends BillingServiceImplBase{

    private final BillingService billingService;

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver) {

        try {
            log.info("Creating billing account {}", billingRequest.toString());

            if (billingRequest.getName() == null ||
                    billingRequest.getName().isEmpty()) {
                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription("Name cannot be empty")
                                .asRuntimeException()
                );
                return;
            }

            if (billingRequest.getEmail() == null ||
                    billingRequest.getEmail().isEmpty()) {
                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription("Email cannot be empty")
                                .asRuntimeException()
                );
                return;
            }

            Billing billing = billingService.createBillingAccount(
                    billingRequest.getPatientId(),
                    billingRequest.getName(),
                    billingRequest.getEmail()
            );

            long accountId = billing.getId() == null ? 0L : billing.getId(); // guard if null
            BillingResponse response = BillingResponse.newBuilder()
                    .setAccountId(accountId)
                    .setStatus(billing.getStatus() == null ? "" : billing.getStatus().name())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

            log.info("Billing account created successfully with ID: {}", billing.getId());

        } catch (EmailException e) {
            log.warn("Email already exists: {}", e.getMessage());
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (DataIntegrityViolationException e) {
            log.warn("DB constraint violation creating billing account", e);
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription("Email already exists (database constraint)").asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            log.warn("Invalid argument: {}", e.getMessage());
            responseObserver.onError(
                    Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (Exception e) {
            log.error("Unexpected error creating billing account", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException()
            );
        }
    }

    @Override
    public void updateBillingAccount(BillingUpdateRequest billingUpdateRequest,
                                     StreamObserver<BillingUpdateResponse> responseObserver) {
        try{
            log.info("Updating account with PatientId: {}", billingUpdateRequest.getPatientId());

            if(billingUpdateRequest.getPatientId() <=0 ){
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("PatientId cannot be null/0")
                        .asRuntimeException());
                return;
            }

            if(billingUpdateRequest.getName() == null ||
                billingUpdateRequest.getName().isEmpty()){
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Name cannot be null")
                        .asRuntimeException());
                return;
            }

            if(billingUpdateRequest.getEmail() == null ||
                billingUpdateRequest.getEmail().isEmpty()){
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Email cannot be null")
                        .asRuntimeException());
                return;
            }

            Billing updateBillingAccount = billingService.updateBillingAccount(
                    billingUpdateRequest.getPatientId(),
                    billingUpdateRequest.getName(),
                    billingUpdateRequest.getEmail());

            BillingUpdateResponse billingUpdateResponse = BillingUpdateResponse.newBuilder()
                    .setAccountId(updateBillingAccount.getId())
                    .setName(updateBillingAccount.getName())
                    .setEmail(updateBillingAccount.getEmail())
                    .setStatus(String.valueOf(updateBillingAccount.getStatus()))
                    .build();

            responseObserver.onNext(billingUpdateResponse);
            responseObserver.onCompleted();


        } catch (EmailException e) {
        log.warn("Email already exists: {}", e.getMessage());
        responseObserver.onError(
                Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException()
        );
        } catch (DataIntegrityViolationException e) {
            log.warn("DB constraint violation creating billing account", e);
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription("Email already exists (database constraint)").asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            log.warn("Invalid argument: {}", e.getMessage());
            responseObserver.onError(
                    Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (Exception e) {
            log.error("Unexpected error updating billing account", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException()
            );
        }
    }

    @Override
    public void deleteBillingAccount(BillingDeleteRequest billingDeleteRequest,
                                                      StreamObserver<BillingDeleteResponse> responseObserver){
        try{
            log.info("Deleting billing account with patientId {}", billingDeleteRequest.getPatientId());

            if(billingDeleteRequest.getPatientId() <= 0){
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("PatientId cannot be null/0")
                        .asRuntimeException());
                return;
            }

            billingService.deleteBillingAccount(billingDeleteRequest.getPatientId());

            BillingDeleteResponse billingDeleteResponse = BillingDeleteResponse
                    .newBuilder()
                    .setMessage("Success deleted billing account!").build();

            responseObserver.onNext(billingDeleteResponse);
            responseObserver.onCompleted();


        } catch (DataIntegrityViolationException e) {
            log.warn("DB constraint violation creating billing account", e);
            responseObserver.onError(
                    Status.ALREADY_EXISTS.withDescription("Email already exists (database constraint)").asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            log.warn("Invalid argument: {}", e.getMessage());
            responseObserver.onError(
                    Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException()
            );
        } catch (Exception e) {
            log.error("Unexpected error deleting billing account", e);
            responseObserver.onError(
                    Status.INTERNAL.withDescription("Internal server error").withCause(e).asRuntimeException()
            );
        }
    }


}