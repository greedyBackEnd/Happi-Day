package com.happiday.Happi_Day.domain.entity.artist.dto;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArtistDetailResponseDto {
    private Long id;
    private String name;
    private String description;
    private String profileUrl;
    private boolean isSubscribed;
    private List<TeamListResponseDto> teams;

    public static ArtistDetailResponseDto of(Artist artist, boolean isSubscribed) {
        return ArtistDetailResponseDto.builder()
                .id(artist.getId())
                .name(artist.getName())
                .description(artist.getDescription())
                .profileUrl(artist.getProfileUrl())
                .isSubscribed(isSubscribed)
                .build();
    }

    public static ArtistDetailResponseDto of(Artist artist, boolean isSubscribed, List<TeamListResponseDto> teams) {
        return ArtistDetailResponseDto.builder()
                .id(artist.getId())
                .name(artist.getName())
                .description(artist.getDescription())
                .profileUrl(artist.getProfileUrl())
                .isSubscribed(isSubscribed)
                .teams(teams)
                .build();
    }
}
