package com.digitalcommerce.orderservice.jpa.repository;

import com.digitalcommerce.orderservice.jpa.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
