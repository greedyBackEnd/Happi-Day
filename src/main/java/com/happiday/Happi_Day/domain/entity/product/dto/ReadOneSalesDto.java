package com.happiday.Happi_Day.domain.entity.product.dto;

import com.happiday.Happi_Day.domain.entity.article.Hashtag;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.product.Sales;
import com.happiday.Happi_Day.domain.entity.team.Team;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> artists;
    private List<String> teams;
    private List<String> hashtag;
    private int likeNum;
    private List<String> imageList;
    private List<ReadDeliveryDto> deliveries;

    public static ReadOneSalesDto fromEntity(Sales sales, List<ReadProductDto> productList){
        List<String> artists = sales.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        List<String> additionalArtists = sales.getArtists() != null ? Arrays.asList(sales.getEctArtists().split(", ")) : Collections.emptyList();
        List<String> allArtists = new ArrayList<>(artists);
        allArtists.addAll(additionalArtists);

        List<String> teams = sales.getTeams().stream().map(Team::getName).collect(Collectors.toList());
        List<String> additionalTeams = sales.getTeams() != null ? Arrays.asList(sales.getEctTeams().split(", ")) : Collections.emptyList();
        List<String> allTeams = new ArrayList<>(teams);
        allTeams.addAll(additionalTeams);

        List<ReadDeliveryDto> deliveries = sales.getDeliveries().stream().map(ReadDeliveryDto::fromEntity).collect(Collectors.toList());

        List<String> hashtagList = new ArrayList<>();
        for (Hashtag hashtag : sales.getHashtags()) {
            hashtagList.add(hashtag.getTag());
        }

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
                .artists(allArtists)
                .teams(allTeams)
                .hashtag(hashtagList)
                .deliveries(deliveries)
                .build();
    }
}
