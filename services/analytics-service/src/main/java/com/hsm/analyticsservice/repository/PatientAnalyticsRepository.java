package com.hsm.analyticsservice.repository;

import com.hsm.analyticsservice.model.PatientEventRecord;
import com.hsm.analyticsservice.shared.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientAnalyticsRepository extends JpaRepository<PatientEventRecord, Long> {

    long countByEventType(EventType eventType);

}
