package com.hsm.patientservice.service;

import com.hsm.patientservice.dto.PatientRequestDTO;
import com.hsm.patientservice.dto.PatientResponseDTO;
import com.hsm.patientservice.exception.EmailException;
import com.hsm.patientservice.exception.ModelNotFoundException;
import com.hsm.patientservice.grpc.BillingServiceGrpcClient;
import com.hsm.patientservice.kafka.KafkaProducer;
import com.hsm.patientservice.mapper.PatientMapper;
import com.hsm.patientservice.model.Patient;
import com.hsm.patientservice.repository.PatientRepository;
import com.hsm.protodefinitions.patient.PatientEvent;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getPatients(){
        log.debug("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        log.info("Retrieved {} patients", patients.size());
        return patients.stream().map(patientMapper::toPatientDTO).toList();
    }

    @Transactional
    public PatientResponseDTO createPatient(@Valid PatientRequestDTO patientRequestDTO){

        validateInputs(patientRequestDTO);

        log.debug("Creating new patient with email {}", patientRequestDTO.getEmail());

        validateEmailUniqueness(patientRequestDTO.getEmail());

        Patient patientEntity = patientMapper.toEntityPatient(patientRequestDTO);
        Patient savedPatient = patientRepository.save(patientEntity);

        billingServiceGrpcClient.createBillingAccount(savedPatient.getId(),
                savedPatient.getName(),
                savedPatient.getEmail());

        kafkaProducer.sendEvent(savedPatient, PatientEvent.EventType.CREATED);

        PatientResponseDTO response = patientMapper.toPatientDTO(savedPatient);

        log.info("Successfully created patient with ID: {}", response.getId());
        return response;

    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO){

        log.debug("Updating patient with ID: {}", id);

        validateInputs(id, patientRequestDTO);

        Patient existingPatient = findPatientById(id);
        validateEmailUniqueness(patientRequestDTO.getEmail(), id);

        patientMapper.updatePatientFromDTO(patientRequestDTO, existingPatient);

        Patient savedPatient = patientRepository.save(existingPatient);

        billingServiceGrpcClient.updateBillingAccount(id, savedPatient.getName(), savedPatient.getEmail());

        log.info("Successfully updated patient with ID: {}", id);
        kafkaProducer.sendEvent(savedPatient, PatientEvent.EventType.UPDATED);

        return patientMapper.toPatientDTO(savedPatient);
    }

    @Transactional
    public void deletePatient(Long id){
        log.debug("Attempting to delete patient with ID: {}", id);
        validatePatientId(id);

        Patient deletedPatient = findPatientById(id);

        kafkaProducer.sendEvent(deletedPatient, PatientEvent.EventType.DELETED);

        patientRepository.deleteById(id);

        billingServiceGrpcClient.deleteBillingAccount(id);

        log.info("Successfully deleted patient with ID: {}", id);
    }

    private void validatePatientId(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("Patient data cannot be null");
        }

        patientRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Patient not found with ID: " + id));

    }

    private void validateInputs(PatientRequestDTO patientRequestDTO){
        if(patientRequestDTO == null){
            throw new IllegalArgumentException("Patient data cannot be null");
        }

    }

    private void validateInputs(Long id, PatientRequestDTO patientRequestDTO){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("Invalid patient ID");
        }

        if(patientRequestDTO == null){
            throw new IllegalArgumentException("Patient data cannot be null");
        }
    }

    private Patient findPatientById(Long id){
        return patientRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Patient not found with ID: " + id));
    }

    private void validateEmailUniqueness(String email, Long excludeId){
        if(patientRepository.existsByEmailAndIdNot(email, excludeId)){
            throw new EmailException("Email address already exists " + email);
        }
    }

    private void validateEmailUniqueness(String email){
        if(patientRepository.existsByEmail(email)){
            throw new EmailException("Email address already exists " + email);
        }
    }


}
