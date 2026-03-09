package com.digitalcommerce.orderservice.jpa.entities;

import com.digitalcommerce.orderservice.util.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CustomerOrders {
    //to-do
    //@Version
    //private Long version;
    //equals and hashcode

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_seq")
    @SequenceGenerator(
            name = "customer_order_seq",
            sequenceName = "customer_order_seq",
            allocationSize = 1
    )
    private Long id;

    @Setter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Setter
    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(
            mappedBy = "customerOrders",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CustomerOrderDetails> customerOrderDetailsList = new ArrayList<>();

    @OneToOne(
            mappedBy = "customerAddOrders",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private CustomerOrderAddressInfo customerOrderAddressInfo;

    @Column(name = "insert_timestamp", updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

    //helper method in parent to maintain both sides.
    public void addOrderDetails(CustomerOrderDetails customerOrderDetails){
        customerOrderDetailsList.add(customerOrderDetails);
        //If you only set: details.setCustomerOrder(order); then order.getCustomerOrderDetailsList() will NOT contain details in memory.
        customerOrderDetails.setCustomerOrders(this);
    }

    public void removeOrderDetails(CustomerOrderDetails customerOrderDetails){
        customerOrderDetailsList.remove(customerOrderDetails);
        customerOrderDetails.setCustomerOrders(null); //remove FK reference from order_details table
    }

    public void setCustomerOrderAddressInfo(CustomerOrderAddressInfo customerOrderAddressInfo){
        this.customerOrderAddressInfo = customerOrderAddressInfo;
        customerOrderAddressInfo.setCustomerAddOrders(this);
    }

}
