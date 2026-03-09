package com.digitalcommerce.orderservice.dto.requestdtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record OrderItem (
        @Min(value = 1, message = "Product Id can not be zero")
        @Max(value = 190, message = "Product Id should be < 190")
        Long productId,
        @Min(value = 1, message = "Quantity can not be zero")
        Integer quantity
){ }
