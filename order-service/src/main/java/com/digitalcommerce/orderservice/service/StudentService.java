package com.digitalcommerce.orderservice.service;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class StudentService {
    String beanType;

    public StudentService() {
        System.out.println("Default constructor called");
        beanType = "@component";
    }

    public StudentService(String name) {
        System.out.println("Parameterized constructor called");
        this.beanType = "@Bean";
    }
}
