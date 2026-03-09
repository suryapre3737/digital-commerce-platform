package com.digitalcommerce.userservice.dto;

public record UserAddressInfoRq(
        Long addressId,
        String username,
        String addressType,
        String line1,
        String line2,
        String city,
        String state,
        int postal_code,
        String country
) {}


