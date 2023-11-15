package com.happiday.Happi_Day.domain.entity.product;

import com.happiday.Happi_Day.domain.entity.BaseEntity;
import com.happiday.Happi_Day.domain.entity.product.dto.CreateProductDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted_at = now() WHERE id =?")
@Where(clause = "deleted_at IS NULL")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매글 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product")
    private List<OrderedProduct> orderedProducts = new ArrayList<>();

    public static Product createProduct(Sales newSales, CreateProductDto dto) {
        Product newProduct = Product.builder()
                .sales(newSales)
                .productStatus(ProductStatus.ON_SALE)
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
        return newProduct;
    }

    public void update(Product product) {
        if (product.getName() != null) this.name = product.getName();
        if (product.getPrice() != null) this.price = product.getPrice();
//        if (product.getProductStatus() != null) this.productStatus = product.getProductStatus();
        if (product.getStock() != null) this.stock = product.getStock();
    }

    public void updateStatus(ProductStatus productStatus){
        this.productStatus = productStatus;
    }

    public void updateStock(Integer stock) {
        this.stock = stock;
    }
}
