package com.digitalcommerce.orderservice.jpa.repository;

import com.digitalcommerce.orderservice.jpa.entities.CustomerOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderDetailsRepository extends JpaRepository<CustomerOrderDetails, Long> {
}
