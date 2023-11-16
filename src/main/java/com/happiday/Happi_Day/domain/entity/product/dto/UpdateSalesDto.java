package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateSalesDto {
    private String name;
    private String description;
    private List<String> artists;
    private List<String> teams;
    private List<String> hashtag;
    private String status;
    private String account;

    public Sales toEntity(){
        return Sales.builder()
                .name(name)
                .description(description)
                .account(account)
                .build();
    }
}
