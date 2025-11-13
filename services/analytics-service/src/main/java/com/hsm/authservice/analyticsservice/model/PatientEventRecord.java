package com.hsm.authservice.analyticsservice.model;


import com.hsm.authservice.analyticsservice.shared.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEventRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private Long patientId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EventType eventType;

    @Column(nullable = false)
    private LocalDateTime processedAt;

    @Column(nullable = false)
    private String kafkaTopic;

    @Column(nullable = false)
    private Integer kafkaPartition;

    @Column(nullable = false)
    private Long kafkaOffset;

}
