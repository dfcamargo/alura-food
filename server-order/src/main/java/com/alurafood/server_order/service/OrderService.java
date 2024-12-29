package com.alurafood.server_order.service;

import com.alurafood.server_order.dto.OrderDTO;
import com.alurafood.server_order.dto.StatusDTO;
import com.alurafood.server_order.model.Order;
import com.alurafood.server_order.model.Status;
import com.alurafood.server_order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<OrderDTO> index() {
        return orderRepository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO show(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.CONFIRMED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order created = orderRepository.save(order);

        return modelMapper.map(created, OrderDTO.class);
    }

    public OrderDTO updateStatus(UUID id, StatusDTO statusDTO) {
        Order order = orderRepository.findByIdWithItems(id);

        if (order == null)
            throw new EntityNotFoundException();

        order.setStatus(statusDTO.getStatus());
        orderRepository.updateStatus(statusDTO.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void updateStatusPaid(UUID id) {
        Order order = orderRepository.findByIdWithItems(id);

        if (order == null)
            throw new EntityNotFoundException();

        order.setStatus(Status.PAID);
        orderRepository.updateStatus(Status.PAID, order);
    }
}
