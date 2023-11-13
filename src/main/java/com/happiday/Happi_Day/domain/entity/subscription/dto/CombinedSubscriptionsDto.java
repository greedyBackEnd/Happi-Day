package com.happiday.Happi_Day.domain.entity.subscription.dto;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CombinedSubscriptionsDto {
    private List<ArtistListResponseDto> subscribedArtists;
    private List<TeamListResponseDto> subscribedTeams;
    private List<ArtistListResponseDto> unsubscribedArtists;
    private List<TeamListResponseDto> unsubscribedTeas;

    public CombinedSubscriptionsDto(
            List<ArtistListResponseDto> subscribedArtists,
            List<TeamListResponseDto> subscribedTeams,
            List<ArtistListResponseDto> unsubscribedArtists,
            List<TeamListResponseDto> unsubscribedTeas) {
        this.subscribedArtists = subscribedArtists;
        this.subscribedTeams = subscribedTeams;
        this.unsubscribedArtists = unsubscribedArtists;
        this.unsubscribedTeas = unsubscribedTeas;
    }
}
