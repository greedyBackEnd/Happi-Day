package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadProductDto {
    private Long id;
    private String name;
    private int price;
    private String productStatus;
    private Integer stock;

    public static ReadProductDto fromEntity(Product product){
        ReadProductDto dto = ReadProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productStatus(product.getProductStatus().getValue())
                .stock(product.getStock())
                .build();
        return dto;
    }
}
