package com.digitalcommerce.orderservice.service;

import com.digitalcommerce.orderservice.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-client", url = "https://fakestoreapi.com/")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(@PathVariable Long id);
}