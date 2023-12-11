package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.*;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class ReadOneOrderDto {
    private Long id;
    private String username;
    private Long salesId;
    private Map<String, Integer> orderedProducts;
    private Integer price;
    private OrderStatus orderStatus;
    private String address;
    private String orderAt;
    private String delivery;
    private String trackingNum;
    private String depositor;
    private String refundAccount;

    public static ReadOneOrderDto fromEntity(Sales sales, Order order, User user){
        Map<String, Integer> productList = new HashMap<>();
        for (OrderedProduct orderedProduct: order.getOrderedProducts()) {
            productList.put(orderedProduct.getProduct().getName(),orderedProduct.getQuantity());
        }

        return ReadOneOrderDto.builder()
                .id(order.getId())
                .username(user.getNickname())
                .salesId(sales.getId())
                .orderAt(order.getOrderedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .price(order.getTotalPrice())
                .orderedProducts(productList)
                .orderStatus(order.getOrderStatus())
                .address(order.getAddress())
                .delivery(order.getDelivery().getName())
                .trackingNum(order.getTrackingNum() != null ? order.getTrackingNum(): "등록되지 않음.")
                .depositor(order.getDepositor())
                .refundAccount(order.getRefundAccount())
                .build();
    }
}
