package com.digitalcommerce.userservice.service;

import com.digitalcommerce.userservice.dto.ApiResponse;
import com.digitalcommerce.userservice.dto.UserAddressInfoRq;
import com.digitalcommerce.userservice.dto.UserInfoDetails;
import com.digitalcommerce.userservice.dto.UserInfoRsObj;
import com.digitalcommerce.userservice.entity.UserAddressInfo;
import com.digitalcommerce.userservice.entity.UserInfo;
import com.digitalcommerce.userservice.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService{

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(UserInfo userInfo){
        // Encrypt password before saving
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User added successfully!";
    }

    // Method to load user details by username (userEmailId)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Convert UserInfo to UserDetails (UserInfoDetails)
        UserInfoDetails userInfoDetails = new UserInfoDetails(userInfo.get());
        return new User(userInfoDetails.getUsername(), userInfoDetails.getPassword(), userInfoDetails.getAuthorities());
    }

    public UserInfoRsObj loadUserInfo(String email){
        return userInfoRepository.findByEmail(email)
                .map(e -> new UserInfoRsObj(e.getId(), e.getName(), e.getEmail(), e.getRoles(), e.getUserAddressInfoList()))
                .orElseThrow( () -> new RuntimeException("User not found!"));
    }

    public List<UserInfoRsObj> loadAllUserInfo(){
        return userInfoRepository.findAll()
                .stream()
                .map(e -> new UserInfoRsObj(e.getId(), e.getName(), e.getEmail(), e.getRoles(), e.getUserAddressInfoList()))
                .toList();
    }


    public ApiResponse<UserInfo> addUserAddressInfo(UserAddressInfoRq userAddressInfoRq) {
        UserInfo user = userInfoRepository.findByEmail(userAddressInfoRq.username()).orElseThrow();

        UserAddressInfo address = UserAddressInfo.builder()
                .addressType(userAddressInfoRq.addressType())
                .line1(userAddressInfoRq.line1())
                .line2(userAddressInfoRq.line2())
                .city(userAddressInfoRq.city())
                .state(userAddressInfoRq.state())
                .postal_code(userAddressInfoRq.postal_code())
                .country(userAddressInfoRq.country())
                .build();

        //check if the user already has the same address, if yes then do nothing, else add the new address
        Optional<UserAddressInfo> existingAddress = user.getUserAddressInfoList().stream()
                .filter(a -> a.equals(address))
                .findFirst();

        if(existingAddress.isEmpty()) {
            user.addAddressInfo(address);
            userInfoRepository.save(user);
            return ApiResponse.<UserInfo>builder()
                    .statusCode(201)
                    .message("Address added successfully")
                    .data(user)
                    .build();
        } else {
            return ApiResponse.<UserInfo>builder()
                    .statusCode(400)
                    .message("This address already exists for the user")
                    .data(user)
                    .build();
        }
    }

    public ApiResponse<UserInfo> removeUserAddressInfo(String username, Long addressId) {
        UserInfo user = userInfoRepository.findByEmail(username).orElseThrow();

        //Find address to remove
        Optional<UserAddressInfo> existingAddress = user.getUserAddressInfoList().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst();
        if(existingAddress.isPresent()) {
            user.removeAddressInfo(existingAddress.get());
            userInfoRepository.save(user);
            return ApiResponse.<UserInfo>builder()
                    .statusCode(201)
                    .message("Address removed successfully")
                    .data(user)
                    .build();
        }else {
            return ApiResponse.<UserInfo>builder()
                    .statusCode(400)
                    .message("Unable to delete address, address not found for the user")
                    .data(user)
                    .build();
        }
    }

    public ApiResponse<UserInfo> updateUserAddressInfo(UserAddressInfoRq userAddressInfoRq) {
        UserInfo user = userInfoRepository.findByEmail(userAddressInfoRq.username()).orElseThrow();

        //check if the user already has the same address, if yes then update the existing address, else add the new address
        Optional<UserAddressInfo> existingAddress = user.getUserAddressInfoList().stream()
                .filter(a -> userAddressInfoRq.addressId().equals(a.getId()))
                .findFirst();

        //update the existing address with the new details
        if(existingAddress.isPresent()){
            existingAddress.get().setAddressType(userAddressInfoRq.addressType());
            existingAddress.get().setLine1(userAddressInfoRq.line1());
            existingAddress.get().setLine2(userAddressInfoRq.line2());
            existingAddress.get().setCity(userAddressInfoRq.city());
            existingAddress.get().setState(userAddressInfoRq.state());
            existingAddress.get().setPostal_code(userAddressInfoRq.postal_code());
            existingAddress.get().setCountry(userAddressInfoRq.country());
            userInfoRepository.save(user);
        }

        return ApiResponse.<UserInfo>builder()
                .statusCode(204)
                .message("Address updated successfully")
                .data(user)
                .build();
    }
}
