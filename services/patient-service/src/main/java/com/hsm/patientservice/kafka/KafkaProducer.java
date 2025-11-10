package com.hsm.patientservice.kafka;

import com.hsm.patientservice.model.Patient;
import com.hsm.protodefinitions.patient.PatientEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void sendEvent(Patient patient, PatientEvent.EventType patientEvent){
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType(patientEvent)
                .build();
        try{
            log.info("Attempting to send event to topic 'patient' for patient ID: {}", patient.getId());
            kafkaTemplate.send("patient", event.toByteArray())
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send event: {}", ex.getMessage(), ex);
                        } else {
                            log.info("Successfully sent event. Topic: {}, Partition: {}, Offset: {}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        } catch(Exception ex){
            log.error("Error sending PatientCreated event: {}", event, ex);
        }
    }

}
