package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Order;
import com.happiday.Happi_Day.domain.entity.product.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ReadListOrderDto {
    private Long id;
    private String name;
    private Integer price;
    private OrderStatus orderStatus;
    private String orderAt;

    public static ReadListOrderDto fromEntity(Order order) {
        return ReadListOrderDto.builder()
                .id(order.getId())
                .name(order.getSales().getName())
                .price(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .orderAt(order.getOrderedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
