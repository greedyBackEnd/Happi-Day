package com.happiday.Happi_Day.domain.entity.product;

import com.happiday.Happi_Day.domain.entity.BaseEntity;
import com.happiday.Happi_Day.domain.entity.product.dto.CreateDeliveryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "delivery")
@SQLDelete(sql = "UPDATE delivery SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id")
    private Sales sales;

    @OneToMany(mappedBy = "delivery")
    private List<Order> orders = new ArrayList<>();

    public static Delivery createDelivery(Sales sales, CreateDeliveryDto dto){
        Delivery newDelivery = Delivery.builder()
                .sales(sales)
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
        return newDelivery;
    }
}
