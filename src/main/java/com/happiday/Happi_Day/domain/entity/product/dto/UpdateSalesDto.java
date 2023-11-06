package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.product.SalesStatus;
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

    public Sales toEntity(){
        return Sales.builder()
                .name(name)
                .description(description)
                .salesStatus(SalesStatus.valueOf(status))
                .build();
    }
}
