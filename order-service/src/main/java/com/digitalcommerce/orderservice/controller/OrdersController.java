package com.digitalcommerce.orderservice.controller;

import com.digitalcommerce.orderservice.dto.requestdtos.CreateOrderRequest;
import com.digitalcommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrderDetails(@RequestParam Long orderId){
        return ResponseEntity.ok(orderService.getOrderDetailsByOrderId(orderId));
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getAllOrderDetails(@RequestParam Long userId){
        return ResponseEntity.ok(orderService.getAllOrderDetailsByUserId(userId));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> cancelOrder(@RequestParam Long orderId){
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }


    @DeleteMapping
    public ResponseEntity<?> deleteOrder(){
        return ResponseEntity.ok(orderService.deleteOrder());
    }
}
