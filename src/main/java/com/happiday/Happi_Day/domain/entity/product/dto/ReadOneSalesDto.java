package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.product.Sales;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadOneSalesDto {
    private Long id;
    private String salesCategory;
    private String user;
    private String name;
    private String description;
    private String salesStatus;
    private List<ReadProductDto> products;
    private int likeNum;
    private List<String> imageList;

    public static ReadOneSalesDto fromEntity(Sales sales, List<ReadProductDto> productList){
        return ReadOneSalesDto.builder()
                .id(sales.getId())
                .salesCategory(sales.getSalesCategory().getName())
                .user(sales.getUsers().getNickname())
                .name(sales.getName())
                .description(sales.getDescription())
                .salesStatus(sales.getSalesStatus().getValue())
                .products(productList)
                .likeNum(sales.getSalesLikesUsers().size())
                .imageList(sales.getImageUrl())
                .build();
    }
}
