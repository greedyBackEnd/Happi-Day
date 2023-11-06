package com.happiday.Happi_Day.domain.entity.product;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CONFIRM("입금확인"),
    ORDER_COMPLETED("주문완료"),
    PREPARE("발송준비중"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    ORDER_CANCEL("주문취소");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
