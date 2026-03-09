package com.digitalcommerce.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_address_info_seq")
    @SequenceGenerator(
            name = "user_address_info_seq",
            sequenceName = "user_address_info_seq",
            allocationSize = 1
    )
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserInfo userInfo;

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
    private int postal_code;
    @Column(nullable = false)
    private String country;

    @Column(name = "insert_timestamp", updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimestamp;

    @Column(name = "update_timestamp")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAddressInfo that = (UserAddressInfo) o;
        return postal_code == that.postal_code && Objects.equals(addressType, that.addressType) && Objects.equals(line1, that.line1) && Objects.equals(line2, that.line2) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInfo, addressType, line1, line2, city, state, postal_code, country);
    }
}

