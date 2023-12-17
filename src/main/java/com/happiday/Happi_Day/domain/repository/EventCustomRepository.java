package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventCustomRepository {

    Page<Event> findEventsByFilterAndKeyword(Pageable pageable, String filter, String keyword);

    Page<Event> findEventsByFilterAndKeywordAndOngoing(Pageable pageable, String filter, String keyword);

    Page<Event> findEventsByFilterAndKeywordAndSubscribedArtists(Pageable pageable, String filter, String keyword, User loginUser);

    Page<Event> findEventsByFilterAndKeywordAndOngoingAndSubscribedArtists(Pageable pageable, String filter, String keyword, User loginUser);

}