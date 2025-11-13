package com.hsm.authservice.billingservice.service;

import com.hsm.authservice.billingservice.exception.EmailException;
import com.hsm.authservice.billingservice.model.Billing;
import com.hsm.authservice.billingservice.repository.BillingRepository;
import com.hsm.authservice.billingservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;

    @Transactional
    public Billing createBillingAccount(Long patientId, String name, String email){
        if (billingRepository.existsByEmail(email)){
            throw new EmailException("Email address already exists");
        }

        Billing billing = Billing.builder()
                .patientId(patientId)
                .name(name)
                .email(email)
                .status(Status.ACTIVE)
                .build();
        return billingRepository.save(billing);
    }

    @Transactional
    public Billing updateBillingAccount(Long patientId, String name, String email){
        Billing billing = billingRepository.findByPatientId(patientId).orElseThrow(
                () -> new IllegalArgumentException("Patient not found with ID:" + patientId));

        if(billingRepository.existsByEmailAndIdNot(email, billing.getId())){
            throw new EmailException("Email already in use by another account");
        }

        if(name != null && !name.isEmpty()){
            billing.setName(name);
        }
        if(email != null && !email.isEmpty()){
            billing.setEmail(email);
        }

        return billingRepository.save(billing);
    }

    @Transactional
    public void deleteBillingAccount(Long patientId){
        Billing billing = billingRepository.findByPatientId(patientId).orElseThrow(
                () -> new IllegalArgumentException("Patient not found with ID:" + patientId));
        billingRepository.delete(billing);
    }
}
