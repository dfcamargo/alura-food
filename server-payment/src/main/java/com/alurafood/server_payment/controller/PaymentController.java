package com.alurafood.server_payment.controller;

import com.alurafood.server_payment.dto.PaymentDTO;
import com.alurafood.server_payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PaymentDTO> index(@PageableDefault() Pageable pagination) {
        return paymentService.index(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> show(@PathVariable @NotNull UUID id) {
        PaymentDTO paymentDTO = paymentService.show(id);
        return ResponseEntity.ok(paymentDTO);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid PaymentDTO paymentDTO, UriComponentsBuilder uriBuilder) {
        URI link = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDTO.getId()).toUri();
        PaymentDTO created = paymentService.create(paymentDTO);

        rabbitTemplate.convertAndSend("payments.ex", "", created);
        return ResponseEntity.created(link).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable @NotNull UUID id, @RequestBody @Valid PaymentDTO paymentDTO) {
        paymentService.update(id, paymentDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status-paid")
    @CircuitBreaker(name = "updateStatusPaid", fallbackMethod = "paymentPaidWithPendingIntegration")
    public void updateStatusPaid(@PathVariable @NotNull UUID id) {
        paymentService.updateStatusPaid(id);
    }

    public void paymentPaidWithPendingIntegration(UUID id, Exception e) {
        paymentService.updateStatusPendingIntegration(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
