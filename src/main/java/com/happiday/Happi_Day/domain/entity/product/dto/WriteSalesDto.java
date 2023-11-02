package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class WriteSalesDto {
    private String name;
    private String description;
    private String artists;
    private Map<String, Integer> products;
    private String account;

    // TODO artist 추가예정
    public Sales toEntity(){
        return Sales.builder()
                .name(name)
                .description(description)
                .account(account)
                .build();
    }
}
