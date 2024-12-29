package com.alurafood.server_payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 19)
    private String registration;

    @NotBlank
    @Size(max = 7)
    private String expiration;

    @NotBlank
    @Size(min = 3, max = 3)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID paymentMethodId;

}
