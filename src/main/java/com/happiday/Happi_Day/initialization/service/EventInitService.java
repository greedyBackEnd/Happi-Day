package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ArtistRepository;
import com.happiday.Happi_Day.domain.repository.EventRepository;
import com.happiday.Happi_Day.domain.repository.TeamRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventInitService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;

    public void initEvents() {
        User writer = userRepository.findById(2L).orElse(null);
        Artist artist1 = artistRepository.findById(1L).orElse(null);
        Artist artist2 = artistRepository.findById(2L).orElse(null);
        Artist artist3 = artistRepository.findById(3L).orElse(null);
        Artist artist4 = artistRepository.findById(4L).orElse(null);
        Team team1 = teamRepository.findById(1L).orElse(null);
        Team team2 = teamRepository.findById(1L).orElse(null);

        List<Event> events = List.of(
                Event.builder()
                        .user(writer)
                        .title("동방신기 팬미팅")
                        .description("동방신기 팬미팅입니다.")
                        .startTime(LocalDateTime.now().plusDays(10))
                        .endTime(LocalDateTime.now().plusDays(10).plusHours(4))
                        .location("서울특별시 송파구")
                        .address("올림픽로 424")
                        .artists(List.of(artist1, artist2))
                        .teams(List.of(team1))
                        .build(),
                Event.builder()
                        .user(writer)
                        .title("god 사인회")
                        .description("god 사인회입니다")
                        .startTime(LocalDateTime.now().plusMonths(1))
                        .endTime(LocalDateTime.now().plusMonths(1).plusHours(6))
                        .location("대구 북구")
                        .address("엑스코로 10")
                        .artists(List.of(artist3, artist4))
                        .teams(List.of(team2))
                        .build()
        );

        events.forEach(event -> {
            if (!eventRepository.existsByTitle(event.getTitle())) {
                eventRepository.save(event);
            }
        });
    }
}
