package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Order;
import com.happiday.Happi_Day.domain.entity.product.OrderStatus;
import com.happiday.Happi_Day.domain.entity.product.Product;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ReadOneOrderDto {
    private Long id;
    private Long userId;
    private Long salesId;
    private List<String> products;
    private Integer price;
    private OrderStatus orderStatus;

    public static ReadOneOrderDto fromEntity(Sales sales, Order order, User user){
        List<String> productList = new ArrayList<>();
        for (Product product: order.getProducts()) {
            productList.add(product.getName());
        }

        return ReadOneOrderDto.builder()
                .id(order.getId())
                .userId(user.getId())
                .salesId(sales.getId())
                .products(productList)
                .price(order.getPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
