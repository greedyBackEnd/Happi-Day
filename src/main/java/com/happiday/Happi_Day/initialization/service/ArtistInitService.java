package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.repository.ArtistRepository;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistInitService {

    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;

    public void initArtists() {
        Team team1 = teamRepository.findById(1L).orElse(null);
        Team team2 = teamRepository.findById(2L).orElse(null);

        List<Artist> artists = List.of(
                Artist.builder()
                        .name("유노윤호")
                        .description("유노윤호입니다")
                        .teams(List.of(team1))
                        .build(),
                Artist.builder()
                        .name("시아준수")
                        .description("시아준수입니다.")
                        .teams(List.of(team1))
                        .build(),
                Artist.builder()
                        .name("박준형")
                        .description("박준형입니다")
                        .teams(List.of(team2))
                        .build(),
                Artist.builder()
                        .name("윤계상")
                        .description("윤계상입니다.")
                        .teams(List.of(team2))
                        .build()
        );

        artists.forEach(artist -> {
            try {
                if (!artistRepository.existsByName(artist.getName())) {
                    artistRepository.save(artist);
                }
            } catch (Exception e) {
                log.error("DB Seeder 아티스트 저장 중 예외 발생 - 아티스트명: {}", artist.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_ARTIST_SAVE_ERROR);
            }
        });
    }
}
