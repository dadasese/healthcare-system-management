package com.hsm.patientservice.dto;


import com.hsm.patientservice.dto.validators.ModelValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDTO {
    @NotNull
    @NotBlank(message = "name field is required")
    @Size(max = 100, message = "name field cannot exceed 100 characters")
    private String name;

    @NotNull
    @NotBlank(message = "lastName field is required")
    private String lastName;

    @NotNull
    @NotBlank(message = "phoneNumber field is required")
    @Size(max = 15, message = "phoneNumber field cannot exceed 15 characters")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "address field is required")
    private String address;

    @NotNull
    @Email
    @NotBlank(message = "email field is required")
    private String email;

    @NotBlank(groups = ModelValidationGroup.class, message = "birthDate field is required")
    private String birthDate;
}
