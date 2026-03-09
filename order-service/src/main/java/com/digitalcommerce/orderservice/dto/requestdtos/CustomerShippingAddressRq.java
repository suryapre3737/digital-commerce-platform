package com.digitalcommerce.orderservice.dto.requestdtos;

public record CustomerShippingAddressRq(
        String addressType,
        String line1,
        String line2,
        String city,
        String state,
        Long postal_code,
        String country
) { }
