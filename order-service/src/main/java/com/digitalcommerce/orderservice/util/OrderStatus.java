package com.digitalcommerce.orderservice.util;

public enum OrderStatus {
    CREATED,
    PAYMENT_PENDING,
    PAYMENT_FAILED,
    PAYMENT_SUCCESSFUL,
    CANCELLED,
    SHIPPED,
    DELIVERED
}