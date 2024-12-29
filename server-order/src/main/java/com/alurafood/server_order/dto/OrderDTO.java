package com.alurafood.server_order.dto;

import com.alurafood.server_order.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private LocalDateTime orderDate;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();
}
