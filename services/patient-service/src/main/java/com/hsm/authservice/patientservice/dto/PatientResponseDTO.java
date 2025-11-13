package com.hsm.authservice.patientservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDTO {

    private Long id;
    private String name;
    private String lastName;
    private String createdAt;

}
