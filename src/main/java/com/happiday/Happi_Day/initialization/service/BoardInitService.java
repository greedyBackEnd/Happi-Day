package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.board.BoardCategory;
import com.happiday.Happi_Day.domain.repository.BoardCategoryRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
            try {
                if (!boardCategoryRepository.existsByName(category.getName())) {
                    boardCategoryRepository.save(category);
                }
            } catch (Exception e) {
                log.error("DB Seeder 게시판 저장 중 예외 발생 - 카테고리명: {}", category.getName(), e);
                throw new CustomException(ErrorCode.DB_SEEDER_BOARD_SAVE_ERROR);
            }
        });
    }
}
