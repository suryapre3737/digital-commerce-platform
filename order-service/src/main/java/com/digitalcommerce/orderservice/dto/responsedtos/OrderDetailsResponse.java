package com.digitalcommerce.orderservice.dto.responsedtos;

import com.digitalcommerce.orderservice.util.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class OrderDetailsResponse {
    Long userId;
    BigDecimal totalAmount;
    OrderStatus orderStatus;
    List<OrderItemDetails> orderItemDetailsList;

    LocalDateTime orderCreatedAt;
    LocalDateTime orderLastUpdatedAt;
}

