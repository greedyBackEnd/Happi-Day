package com.happiday.Happi_Day.domain.entity.product.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SalesSearchFilteringDto {
    private Boolean ongoingCheck;
    private Boolean subscribeCheck;
    private String filter;
    private String keyword;
}
