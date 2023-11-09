package com.happiday.Happi_Day.domain.controller;

import com.happiday.Happi_Day.domain.entity.article.dto.ReadCommentDto;
import com.happiday.Happi_Day.domain.entity.article.dto.WriteCommentDto;
import com.happiday.Happi_Day.domain.service.CommentService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments/{articleId}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // TODO 유저 부분 추가예정
    @PostMapping
    public ResponseEntity<ReadCommentDto> writeComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody WriteCommentDto requestDto) {
        String username = SecurityUtils.getCurrentUsername();
        ReadCommentDto responseComment = commentService.writeComment(articleId, requestDto,username);
        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    // 댓글 조회
    @GetMapping()
    public ResponseEntity<List<ReadCommentDto>> readComment(
            @PathVariable("articleId") Long articleId) {
        List<ReadCommentDto> responseComments = commentService.readComment(articleId);
        return new ResponseEntity<>(responseComments, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ReadCommentDto> updateComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId,
            @RequestBody WriteCommentDto requestDto) {
        ReadCommentDto responseComment = commentService.updateComment(articleId, commentId, requestDto);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("articleId") Long articleId,
            @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(articleId, commentId);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }


}
