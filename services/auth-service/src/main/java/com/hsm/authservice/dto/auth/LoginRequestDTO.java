package com.hsm.authservice.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "email is required")
    @Email(message = "Email should be a valid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 0, message = "Password must be at least 8 characters long")
    private String password;
}
