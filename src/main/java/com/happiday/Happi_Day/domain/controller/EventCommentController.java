package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.event.dto.comment.EventCommentCreateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.comment.EventCommentResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.comment.EventCommentUpdateDto;
import com.happiday.Happi_Day.domain.service.EventCommentService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
public class EventCommentController {
    private final EventCommentService commentService;

    @PostMapping
    public ResponseEntity<EventCommentResponseDto> createComment(@PathVariable("eventId") Long eventId,
                                                                 @RequestBody EventCommentCreateDto createDto) {
        String username = SecurityUtils.getCurrentUsername();
        EventCommentResponseDto responseDto = commentService.createComment(eventId, createDto, username);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EventCommentResponseDto>> readComments(
            @PathVariable("eventId") Long eventId,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<EventCommentResponseDto> responseDtoList = commentService.readComments(eventId, pageable);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<EventCommentResponseDto> updateComment(@PathVariable("eventId") Long eventId,
                                                                 @PathVariable("commentId") Long commentId,
                                                                 @RequestBody EventCommentUpdateDto updateDto) {
        log.info("수정 테스트");
        String username = SecurityUtils.getCurrentUsername();
        EventCommentResponseDto responseDto = commentService.updateComment(eventId, commentId, updateDto, username);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("eventId") Long eventId,
                                                @PathVariable("commentId") Long commentId) {
        log.info("삭제 테스트");
        String username = SecurityUtils.getCurrentUsername();
        commentService.deleteComment(eventId, commentId, username);
        return ResponseEntity.ok("삭제 성공");
    }
}
