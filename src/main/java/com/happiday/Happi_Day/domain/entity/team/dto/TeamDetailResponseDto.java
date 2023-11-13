package com.happiday.Happi_Day.domain.entity.team.dto;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeamDetailResponseDto {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private boolean isSubscribed;
    private List<ArtistListResponseDto> artists;

    public static TeamDetailResponseDto of(Team team, boolean isSubscribed) {
        return TeamDetailResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .logoUrl(team.getLogoUrl())
                .isSubscribed(isSubscribed)
                .build();
    }

    public static TeamDetailResponseDto of(Team team, boolean isSubscribed, List<ArtistListResponseDto> artists) {
        return TeamDetailResponseDto.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .logoUrl(team.getLogoUrl())
                .isSubscribed(isSubscribed)
                .artists(artists)
                .build();
    }
}
