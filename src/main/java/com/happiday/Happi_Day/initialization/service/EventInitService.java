package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.artist.ArtistEvent;
import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.*;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.utils.DefaultImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventInitService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;
    private final ArtistEventRepository artistEventRepository;
    private final DefaultImageUtils defaultImageUtils;

    public void initEvents() {
        User writer = userRepository.findById(2L).orElse(null);
        String imageUrl = defaultImageUtils.getDefaultImageUrlEventThumbnail();

        Map<Event, List<Long>> eventArtistMap = new LinkedHashMap<>();
        eventArtistMap.put(createEvent(writer, "동방신기 팬미팅", "동방신기 팬미팅입니다.", imageUrl, List.of(1L, 2L), List.of(1L)), List.of(1L, 2L));
        eventArtistMap.put(createEvent(writer, "god 사인회", "god 사인회입니다", imageUrl, List.of(3L, 4L), List.of(2L)), List.of(3L, 4L));
        eventArtistMap.put(createEvent(writer, "god 사인회", "현재 진행중인 god 사인회", imageUrl, List.of(3L, 4L), List.of(2L)), List.of(3L, 4L));

        eventArtistMap.forEach((event, artistIds) -> {
            try {
                if (!eventRepository.existsByTitle(event.getTitle())) {
                    Event savedEvent = eventRepository.save(event);
                    linkArtistsToEvent(savedEvent, artistIds);
                }
            } catch (Exception e) {
                log.error("DB Seeder 이벤트 저장 중 예외 발생 - 이벤트명: {}", event.getTitle(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_EVENT_SAVE_ERROR);
            }
        });
    }

    private Event createEvent(User writer, String title, String description, String imageUrl, List<Long> artistIds, List<Long> teamIds) {
        return Event.builder()
                .user(writer)
                .title(title)
                .description(description)
                .startTime(LocalDateTime.now().plusDays(10))
                .endTime(LocalDateTime.now().plusDays(10).plusHours(4))
                .location("서울특별시 송파구")
                .address("올림픽로 424")
                .thumbnailUrl(imageUrl)
                .build();
    }

    private void linkArtistsToEvent(Event event, List<Long> artistIds) {
        artistIds.forEach(artistId -> {
            artistRepository.findById(artistId).ifPresent(artist -> {
                ArtistEvent artistEvent = ArtistEvent.builder()
                        .event(event)
                        .artist(artist)
                        .build();
                artistEventRepository.save(artistEvent); // ArtistEvent 저장
            });
        });
    }
}
