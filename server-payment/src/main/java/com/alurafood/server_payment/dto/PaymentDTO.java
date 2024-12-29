package com.alurafood.server_payment.dto;

import com.alurafood.server_payment.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentDTO {
    private UUID id;
    private BigDecimal amount;
    private String name;
    private String registration;
    private String expiration;
    private String code;
    private Status status;
    private UUID orderId;
    private UUID paymentMethodId;
}
