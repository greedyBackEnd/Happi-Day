package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Delivery;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadDeliveryDto {
    private Long id;
    private String name;
    private int price;

    public static ReadDeliveryDto fromEntity(Delivery delivery){
        ReadDeliveryDto dto = ReadDeliveryDto.builder()
                .id(delivery.getId())
                .name(delivery.getName())
                .price(delivery.getPrice())
                .build();
        return dto;
    }

}
