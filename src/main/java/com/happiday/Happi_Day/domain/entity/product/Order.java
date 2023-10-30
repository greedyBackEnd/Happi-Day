package com.happiday.Happi_Day.domain.entity.product;

import com.happiday.Happi_Day.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // 판매글 id
    @OneToOne
    @JoinColumn(name="sales_id")
    private Sales sales;

    @Column(nullable = false)
    private String address;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Product> products = new ArrayList<>();

    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    public String updateStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
        return orderStatus.getValue();
    }
}
