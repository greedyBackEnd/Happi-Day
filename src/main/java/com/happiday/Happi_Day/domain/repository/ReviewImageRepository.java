package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.event.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}