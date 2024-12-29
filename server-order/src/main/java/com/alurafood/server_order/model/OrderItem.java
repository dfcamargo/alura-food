package com.alurafood.server_order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_generator")
    @SequenceGenerator(name = "order_item_generator", sequenceName = "order_items_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    private String description;

    @ManyToOne(optional = false)
    private Order order;
}
