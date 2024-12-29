package com.alurafood.server_order.controller;

import com.alurafood.server_order.dto.OrderDTO;
import com.alurafood.server_order.dto.StatusDTO;
import com.alurafood.server_order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public List<OrderDTO> index() {
        return orderService.index();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> show(@PathVariable @NotNull UUID id) {
        OrderDTO orderDTO = orderService.show(id);
        return  ResponseEntity.ok(orderDTO);
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO orderDTO, UriComponentsBuilder uriBuilder) {
        OrderDTO created = orderService.create(orderDTO);
        URI link = uriBuilder.path("/orders/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(link).body(created);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestBody StatusDTO statusDTO){
        orderService.updateStatus(id, statusDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status-paid")
    public ResponseEntity<Void> updateStatusPaid(@PathVariable @NotNull UUID id) {
        orderService.updateStatusPaid(id);
        return ResponseEntity.noContent().build();
    }
}
