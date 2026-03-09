package com.digitalcommerce.orderservice.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_order_address_info")
@Getter
@Setter
public class CustomerOrderAddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_address_info_seq")
    @SequenceGenerator(
            name = "customer_order_address_info_seq",
            sequenceName = "customer_order_address_info_seq",
            allocationSize = 1
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private CustomerOrders customerAddOrders;

    @Column(name = "address_type")
    private String addressType;
    @Column(nullable = false)
    private String line1;
    private String line2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Long postal_code;
    @Column(nullable = false)
    private String country;

    @Column(name = "insert_timestamp", updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

}