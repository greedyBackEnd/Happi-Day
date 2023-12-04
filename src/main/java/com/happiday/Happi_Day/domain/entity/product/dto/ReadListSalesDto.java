package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadListSalesDto {
    private Long id;
    private String salesCategory;
    private String name;
    private String user;
    private Integer likeNum;
    private String thumbnailImage;
    private Integer orderNum;

    public static ReadListSalesDto fromEntity(Sales sales){
        return ReadListSalesDto.builder()
                .id(sales.getId())
                .salesCategory(sales.getSalesCategory().getName())
                .name(sales.getName())
                .user(sales.getUsers().getNickname())
                .likeNum(sales.getSalesLikesUsers().size())
                .thumbnailImage(sales.getThumbnailImage())
                .orderNum(sales.getOrders().size())
                .build();
    }
}
