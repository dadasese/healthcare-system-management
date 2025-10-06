package com.hsm.patientservice.mapper;


import com.hsm.patientservice.dto.PatientRequestDTO;
import com.hsm.patientservice.dto.PatientResponseDTO;
import com.hsm.patientservice.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientResponseDTO toPatientDTO(Patient patient);

    Patient toEntityPatient(PatientRequestDTO patientDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updatePatientFromDTO(PatientRequestDTO dto, @MappingTarget Patient patient);

    default LocalDate map(String date){
        if (date == null || date.isBlank()) return null;
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
