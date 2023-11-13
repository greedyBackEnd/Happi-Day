package com.happiday.Happi_Day.domain.entity.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderDto {
    private String orderStatus;
    private String trackingNum;
}
