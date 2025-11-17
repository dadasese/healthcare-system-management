package com.hsm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hsm.analyticsservice.model.PatientEventRecord;
import com.hsm.analyticsservice.repository.PatientAnalyticsRepository;
import com.hsm.analyticsservice.shared.EventType;
import com.hsm.protodefinitions.patient.PatientEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final PatientAnalyticsRepository patientAnalyticsRepository;

    @KafkaListener(topics="patient", groupId="analytics-service")
    public void consumeEvent( @Payload byte[] event,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                              @Header(KafkaHeaders.OFFSET) long offset,
                              Acknowledgment acknowledgment) {

        log.info("=== MESSAGE RECEIVED ===");
        log.info("Received message from topic: {}, partition: {}, offset: {}", topic, partition, offset);
        log.info("Message size: {} bytes", event.length);

        try{
            // Deserialize protobuf
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            // Determine event type
            EventType eventType = mapEventType(patientEvent);

            // Persist to database
            PatientEventRecord record = PatientEventRecord.builder()
                    .patientId(patientEvent.getPatientId())
                    .name(patientEvent.getName())
                    .email(patientEvent.getEmail())
                    .eventType(eventType)
                    .processedAt(LocalDateTime.now())
                    .kafkaTopic(topic)
                    .kafkaPartition(partition)
                    .kafkaOffset(offset)
                    .build();

            patientAnalyticsRepository.save(record);

            // ... perform any business related to analytics here
            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

            if (acknowledgment != null){
                acknowledgment.acknowledge();
            }

        } catch(InvalidProtocolBufferException e){
            log.error("Error deserializing event at offset {}: {}", offset, e.getMessage());
            handlePoisonMessage(topic, partition, offset, event, e);

        } catch(Exception e){
            log.error("Unexpected error processing event from offset {}: {}", offset, e.getMessage(), e);
            throw new RuntimeException("Failed to process message", e);
        }
    }

    private EventType mapEventType(PatientEvent patientEvent){

        if (patientEvent.hasEventType()){
            return switch (patientEvent.getEventType()){
                case CREATED -> EventType.CREATED;
                case UPDATED -> EventType.UPDATED;
                case DELETED -> EventType.DELETED;
                default -> EventType.UNKNOWN;
            };
        }

        return EventType.UNKNOWN;
    }

    private void handlePoisonMessage(String topic, int partition, long offset, byte[] event, Exception e){
        log.error("POISON MESSAGE detected - Topic: {}, Partition: {}, Offset: {}, Size: {}, bytes : {}",
                topic, partition, offset, event.length, e.getMessage());
    }
}
