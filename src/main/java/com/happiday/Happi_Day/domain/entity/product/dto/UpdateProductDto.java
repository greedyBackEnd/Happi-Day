package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.ProductStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductDto {
    private String name;
    private Integer price;
    private String status;
    private Integer stock;

    public Product toEntity(){
        return Product.builder()
                .name(name)
                .price(price)
                .productStatus(ProductStatus.valueOf(status))
                .stock(stock)
                .build();
    }
}
