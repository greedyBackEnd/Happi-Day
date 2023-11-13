package com.happiday.Happi_Day.domain.entity.subscription.dto;

import com.happiday.Happi_Day.domain.entity.artist.dto.ArtistListResponseDto;
import com.happiday.Happi_Day.domain.entity.team.dto.TeamListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UnsubscriptionResponseDto {
    private List<ArtistListResponseDto> subscribedArtists;
    private List<TeamListResponseDto> subscribedTeams;
}
