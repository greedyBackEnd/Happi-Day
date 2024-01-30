package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.*;
import com.happiday.Happi_Day.domain.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ReadOneOrderDto {
    private Long id;
    private Long salesId;
    private String salesName;
    private String sellerAccount;
    private String username;
    private String userPhone;
    private String address;
    private String depositor;
    private String refundAccount;
    private String orderAt;
    private List<OrderedProductDto> orderedProducts;
    private Integer productPrice;
    private DeliveryDto delivery;
    private Integer totalPrice;
    private OrderStatus orderStatus;
    private String trackingNum;

    @Builder
    @Getter
    private static class OrderedProductDto {
        private String productName;
        private int count;
        private int individualProductPrice;

        private OrderedProductDto(String productName, int count, int individualProductPrice){
            this.productName = productName;
            this.count = count;
            this.individualProductPrice = individualProductPrice;
        }
    }

    @Builder
    @Getter
    private static class DeliveryDto{
        private String deliveryWay;
        private int price;

        private DeliveryDto(String deliveryWay, int price){
            this.deliveryWay = deliveryWay;
            this.price = price;
        }
    }


    public static ReadOneOrderDto fromEntity(Sales sales, Order order, User user) {
//        Map<String, Integer> productList = new HashMap<>();
//        Map<String, Integer> priceList = new HashMap<>();
//        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
//            productList.put(orderedProduct.getProduct().getName(), orderedProduct.getQuantity());
//            priceList.put(orderedProduct.getProduct().getName(), orderedProduct.getProduct().getPrice() * orderedProduct.getQuantity());
//        }
//        Map<String, Integer> delivery = new HashMap<>();
//        delivery.put(order.getDelivery().getName(), order.getDelivery().getPrice());

        List<OrderedProductDto> productList = new ArrayList<>();
        for(OrderedProduct orderedProduct : order.getOrderedProducts()){
//            productList.add(new Product(orderedProduct.getProduct().getName(),orderedProduct.getQuantity(), orderedProduct.getProduct().getPrice()));
            OrderedProductDto orderedProductDto = OrderedProductDto.builder()
                    .productName(orderedProduct.getProduct().getName())
                    .count(orderedProduct.getQuantity())
                    .individualProductPrice(orderedProduct.getProduct().getPrice())
                    .build();
            productList.add(orderedProductDto);
        }

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryWay(order.getDelivery().getName())
                .price(order.getDelivery().getPrice())
                .build();

        return ReadOneOrderDto.builder()
                .id(order.getId())
                .salesId(sales.getId())
                .salesName(sales.getName())
                .sellerAccount(sales.getAccount())
                .username(user.getNickname())
                .userPhone(user.getPhone())
                .address(order.getAddress())
                .depositor(order.getDepositor())
                .refundAccount(order.getRefundAccount())
                .orderAt(order.getOrderedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .orderedProducts(productList)
                .productPrice(order.getTotalPrice()-order.getDelivery().getPrice())
                .delivery(deliveryDto)
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .trackingNum(order.getTrackingNum() != null ? order.getTrackingNum() : "등록되지 않음.")
                .build();
    }
}
