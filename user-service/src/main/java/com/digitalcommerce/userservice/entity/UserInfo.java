package com.digitalcommerce.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String roles;

    @OneToMany(
            mappedBy = "userInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Getter
    private List<UserAddressInfo> userAddressInfoList;

    public void addAddressInfo(UserAddressInfo userAddressInfo){
        userAddressInfoList.add(userAddressInfo);
        userAddressInfo.setUserInfo(this);
    }

    public void removeAddressInfo(UserAddressInfo userAddressInfo){
        userAddressInfoList.remove(userAddressInfo);
        userAddressInfo.setUserInfo(null);
    }
}

