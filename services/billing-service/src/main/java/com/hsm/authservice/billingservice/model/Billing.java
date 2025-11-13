package com.hsm.authservice.billingservice.model;

import com.hsm.authservice.billingservice.shared.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "billing")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
}

