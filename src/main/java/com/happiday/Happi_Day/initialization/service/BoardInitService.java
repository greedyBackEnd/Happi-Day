package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.board.BoardCategory;
import com.happiday.Happi_Day.domain.repository.BoardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInitService {

    private final BoardCategoryRepository boardCategoryRepository;

    public void initBoards() {
        List<BoardCategory> boards = List.of(
                BoardCategory.builder().name("자유").description("자유글 카테고리입니다.").build(),
                BoardCategory.builder().name("홍보").description("홍보글 카테고리입니다.").build(),
                BoardCategory.builder().name("거래").description("거래글 카테고리입니다.").build(),
                BoardCategory.builder().name("친목").description("친목글 카테고리입니다.").build(),
                BoardCategory.builder().name("주최").description("주최글 카테고리입니다.").build()
        );
        boards.forEach(category -> {
            if (!boardCategoryRepository.existsByName(category.getName())) {
                boardCategoryRepository.save(category);
            }
        });
    }
}
