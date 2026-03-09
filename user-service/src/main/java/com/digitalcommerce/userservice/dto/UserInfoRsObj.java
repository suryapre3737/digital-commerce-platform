package com.digitalcommerce.userservice.dto;

import com.digitalcommerce.userservice.entity.UserAddressInfo;

import java.util.List;

public record UserInfoRsObj (int id, String name, String email, String roles, List<UserAddressInfo> userAddressInfoList) {}
