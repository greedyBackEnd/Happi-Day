package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.board.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
    boolean existsByName(String name);
}
