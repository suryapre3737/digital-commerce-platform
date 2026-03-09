package com.digitalcommerce.orderservice.service;

import com.digitalcommerce.orderservice.dto.ProductResponseDto;
import com.digitalcommerce.orderservice.dto.requestdtos.CreateOrderRequest;
import com.digitalcommerce.orderservice.dto.requestdtos.CustomerShippingAddressRq;
import com.digitalcommerce.orderservice.jpa.entities.CustomerOrderAddressInfo;
import com.digitalcommerce.orderservice.jpa.entities.CustomerOrderDetails;
import com.digitalcommerce.orderservice.jpa.entities.CustomerOrders;
import com.digitalcommerce.orderservice.jpa.repository.CustomerOrderRepository;
import com.digitalcommerce.orderservice.util.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final ProductClient productClient;

    public Map<String, Long> createOrder(CreateOrderRequest request){

        //map createOrderRequest to CustomerOrder and CustomerOrderDetails
        CustomerOrders customerOrders = new CustomerOrders();
        customerOrders.setUserId(request.userId());
        BigDecimal totalAmount = request.OrderItems().stream()
                .map(e -> {
                    ProductResponseDto productDetails = productClient.getProduct(e.productId());
                    BigDecimal lineTotal = productDetails.getPrice().multiply(BigDecimal.valueOf(e.quantity()));

                    CustomerOrderDetails details = new CustomerOrderDetails();
                    details.setProductId(e.productId());
                    details.setOrderQuantity(e.quantity());
                    details.setPrice(productDetails.getPrice());
                    customerOrders.addOrderDetails(details);
                    return lineTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        customerOrders.setTotalAmount(totalAmount);
        customerOrders.setOrderStatus(OrderStatus.CREATED);

        //order-address mapping is not implemented yet, so skipping for now
        CustomerShippingAddressRq shippingAddress = request.shippingAddress();
        CustomerOrderAddressInfo addressInfo = new CustomerOrderAddressInfo();
        addressInfo.setAddressType(shippingAddress.addressType());
        addressInfo.setLine1(shippingAddress.line1());
        addressInfo.setLine2(shippingAddress.line2());
        addressInfo.setCity(shippingAddress.city());
        addressInfo.setState(shippingAddress.state());
        addressInfo.setPostal_code(shippingAddress.postal_code());
        addressInfo.setCountry(shippingAddress.country());
        customerOrders.setCustomerOrderAddressInfo(addressInfo);

        //save customerOrder
        CustomerOrders orders = customerOrderRepository.save(customerOrders);

        //map customerOrders to OrderDetailsResponse and return
        /*return OrderDetailsResponse.builder()
                .userId(orders.getUserId())
                .totalAmount(orders.getTotalAmount())
                .orderStatus(orders.getOrderStatus())
                .orderCreatedAt(orders.getInsertTimestamp())
                .orderLastUpdatedAt(orders.getUpdateTimestamp())
                .orderItemDetailsList(
                    orders.getCustomerOrderDetailsList().stream().map(e -> OrderItemDetails.builder()
                            .productId(e.getProductId())
                            .orderQuantity(e.getOrderQuantity())
                            .price(e.getPrice())
                            .build()).toList()
                )
                .build();*/
        return Map.of("order_id", orders.getId());
    }

    public CustomerOrders deleteOrder() {
        CustomerOrders aa = customerOrderRepository.getCustomerOrderById(1L).get();

        CustomerOrderDetails mac = new CustomerOrderDetails();
        mac.setOrderQuantity(5);
        mac.setPrice(BigDecimal.valueOf(34000));
        mac.setProductId(3L);

        aa.removeOrderDetails(aa.getCustomerOrderDetailsList().stream().findFirst().get());

        aa.addOrderDetails(mac);

        return customerOrderRepository.save(aa);
    }

    public Optional<CustomerOrders> getOrderDetailsByOrderId(Long orderId) {
        return customerOrderRepository.getCustomerOrderById(orderId);
    }

    public Optional<CustomerOrders> cancelOrder(Long orderId) {
        Optional<CustomerOrders> order = customerOrderRepository.getCustomerOrderById(orderId);
        order.ifPresent(o -> {
            o.setOrderStatus(OrderStatus.CANCELLED);
            customerOrderRepository.save(o);
        });
        return order;
    }

    public Optional<List<CustomerOrders>> getAllOrderDetailsByUserId(Long userId) {
        return customerOrderRepository.getAllCustomerOrdersByUserId(userId);
    }
}
