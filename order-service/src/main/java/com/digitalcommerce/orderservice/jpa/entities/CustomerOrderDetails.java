package com.digitalcommerce.orderservice.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_order_details")
@Getter
public class CustomerOrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_order_details_seq")
    @SequenceGenerator(
            name = "customer_order_details_seq",
            sequenceName = "customer_order_details_seq",
            allocationSize = 1
    )
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private CustomerOrders customerOrders;

    @Setter
    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Setter
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Setter
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "insert_timestamp", updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

}