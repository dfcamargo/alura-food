package com.alurafood.server_order.ampq;

import com.alurafood.server_order.dto.PaymentDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = "payment.confirmed")
    public void finishedPaymento(PaymentDTO paymentDTO) {
        System.out.println("Received new payment: " + paymentDTO.toString());
    }
}
