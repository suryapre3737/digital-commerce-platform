package com.digitalcommerce.userservice.service;

import com.digitalcommerce.userservice.dto.UserInfoDetails;
import com.digitalcommerce.userservice.dto.UserInfoRsObj;
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
                .map(e -> new UserInfoRsObj(e.getId(), e.getName(), e.getEmail(), e.getRoles()))
                .orElseThrow( () -> new RuntimeException("User not found!"));
    }

    public List<UserInfoRsObj> loadAllUserInfo(){
        return userInfoRepository.findAll()
                .stream()
                .map(e -> new UserInfoRsObj(e.getId(), e.getName(), e.getEmail(), e.getRoles()))
                .toList();
    }
    
    
}
