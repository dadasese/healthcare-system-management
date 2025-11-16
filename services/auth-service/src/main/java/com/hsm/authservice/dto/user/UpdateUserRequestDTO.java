package com.hsm.authservice.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO {

    @Email(message = "Email must be valid")
    private String email;

    @NotNull
    @NotBlank(message = "Role is required")
    private String role;
}
