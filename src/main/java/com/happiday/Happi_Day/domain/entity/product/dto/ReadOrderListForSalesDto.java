package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Order;
import com.happiday.Happi_Day.domain.entity.product.OrderStatus;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class ReadOrderListForSalesDto {
    private Long orderId;
    private String username;
    private String orderAt;
    private OrderStatus orderStatus;

    public static ReadOrderListForSalesDto fromEntity(Order order, User user){
        return ReadOrderListForSalesDto.builder()
                .orderId(order.getId())
                .username(user.getUsername())
                .orderAt(order.getOrderedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
