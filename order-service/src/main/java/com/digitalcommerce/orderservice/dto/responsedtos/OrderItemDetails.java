package com.digitalcommerce.orderservice.dto.responsedtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderItemDetails{
    Long productId;
    Integer orderQuantity;
    BigDecimal price;
}
