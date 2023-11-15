package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByUser(User user, Pageable pageable);

    Page<Event> findAllByLikesContains(User user, Pageable pageable);

    Page<Event> findAllByJoinListContains(User user, Pageable pageable);
}
