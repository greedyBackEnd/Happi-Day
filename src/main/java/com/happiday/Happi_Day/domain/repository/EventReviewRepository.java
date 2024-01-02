package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.event.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
}
