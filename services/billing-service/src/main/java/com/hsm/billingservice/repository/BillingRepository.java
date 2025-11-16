package com.hsm.billingservice.repository;

import com.hsm.billingservice.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Billing> findByPatientId(Long patientId);
}
