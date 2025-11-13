package com.hsm.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "email is required")
    @Email(message = "Email should be a valid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 0, message = "Password must be at least 8 characters long")
    private String password;
}
