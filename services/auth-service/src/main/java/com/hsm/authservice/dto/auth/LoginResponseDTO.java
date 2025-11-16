package com.hsm.authservice.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private final String token;
    private LocalDateTime generatedAt;
}
