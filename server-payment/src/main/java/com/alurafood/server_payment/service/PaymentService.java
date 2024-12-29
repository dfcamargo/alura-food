package com.alurafood.server_payment.service;

import com.alurafood.server_payment.dto.PaymentDTO;
import com.alurafood.server_payment.http.OrderClient;
import com.alurafood.server_payment.model.Payment;
import com.alurafood.server_payment.model.Status;
import com.alurafood.server_payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient orderClient;

    public Page<PaymentDTO> index(Pageable pagination) {
        return paymentRepository
                .findAll(pagination)
                .map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO show(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO create(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO update(UUID id, PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void updateStatusPendingIntegration(UUID id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty())
            throw new EntityNotFoundException();

        payment.get().setStatus(Status.PENDING_INTEGRATION);
        paymentRepository.save(payment.get());
    }

    public void updateStatusPaid(UUID id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty())
            throw new EntityNotFoundException();

        payment.get().setStatus(Status.CONFIRMED);
        paymentRepository.save(payment.get());
        orderClient.updateStatusPaid(payment.get().getOrderId());
    }

    public void delete(UUID id) {
        paymentRepository.deleteById(id);
    }
}
