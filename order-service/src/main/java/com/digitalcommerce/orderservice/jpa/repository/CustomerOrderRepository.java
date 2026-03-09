package com.digitalcommerce.orderservice.jpa.repository;

import com.digitalcommerce.orderservice.jpa.entities.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrders, Long> {
     Optional<CustomerOrders> getCustomerOrderById(Long id);

     Optional<List<CustomerOrders>> getAllCustomerOrdersByUserId(Long userId);
}
