package com.alurafood.server_order.repository;

import com.alurafood.server_order.model.Order;
import com.alurafood.server_order.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Order o SET o.status = :status where o = :order")
    void updateStatus(Status status, Order order);

    @Query(value = "SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id")
    Order findByIdWithItems(UUID id);
}
