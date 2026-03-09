package com.digitalcommerce.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stock;
}