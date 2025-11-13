package com.hsm.authservice.analyticsservice.repository;

import com.hsm.authservice.analyticsservice.model.PatientEventRecord;
import com.hsm.authservice.analyticsservice.shared.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAnalyticsRepository extends JpaRepository<PatientEventRecord, Long> {

    long countByEventType(EventType eventType);

}
