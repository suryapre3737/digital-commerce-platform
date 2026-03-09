package com.digitalcommerce.orderservice.dto.requestdtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateOrderRequest(
        Long userId,//retrieve user from jwt
        List<OrderItem>OrderItems,
        @NotBlank(message = "PaymentMethod is required")
        String paymentMethod,
        CustomerShippingAddressRq shippingAddress) { }