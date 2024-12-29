package com.alurafood.server_order.dto;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
public class PaymentDTO {
    private UUID id;
    private BigDecimal amount;
    private String name;
    private String registration;
    private String expiration;
    private String code;
    private UUID orderId;
    private UUID paymentMethodId;
}
