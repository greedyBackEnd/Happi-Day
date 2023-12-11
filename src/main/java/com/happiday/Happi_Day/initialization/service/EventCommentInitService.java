package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.event.EventComment;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.EventCommentRepository;
import com.happiday.Happi_Day.domain.repository.EventRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventCommentInitService {
    private final EventCommentRepository eventCommentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public void initEventComments() {
        User writer = userRepository.findById(2L).orElse(null);
        Event sampleEvent = eventRepository.findById(1L).orElse(null);

        List<EventComment> comments = List.of(
                EventComment.builder()
                        .user(writer)
                        .event(sampleEvent)
                        .content("너무 재밌어요!")
                        .build(),
                EventComment.builder()
                        .user(writer)
                        .event(sampleEvent)
                        .content("똑똑한 청년.")
                        .build()
        );

        try {
            eventCommentRepository.saveAll(comments);
        } catch (Exception e) {
            log.error("DB Seeder 이벤트 댓글 저장 중 예외 발생", e);
            throw new CustomException(ErrorCode.DB_SEEDER_EVENT_COMMENT_SAVE_ERROR);
        }
    }
}
