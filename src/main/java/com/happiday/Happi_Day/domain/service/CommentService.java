package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.article.Comment;
import com.happiday.Happi_Day.domain.entity.article.dto.ReadCommentDto;
import com.happiday.Happi_Day.domain.entity.article.dto.WriteCommentDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ArticleRepository;
import com.happiday.Happi_Day.domain.repository.CommentRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReadCommentDto writeComment(Long articleId, WriteCommentDto dto, String username) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Comment newComment = Comment.builder()
                .content(dto.getContent())
                .article(article)
                .user(user)
                .build();
        commentRepository.save(newComment);

        ReadCommentDto response = ReadCommentDto.fromEntity(newComment);
        return response;
    }

    public Page<ReadCommentDto> readComment(Long articleId, Pageable pageable) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Page<Comment> comments = commentRepository.findAllByArticle(article, pageable);
        return comments.map(ReadCommentDto::fromEntity);
    }

    @Transactional
    public ReadCommentDto updateComment(Long articleId, Long commentId, WriteCommentDto requestDto) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        comment.update(requestDto.toEntity());
        commentRepository.save(comment);

        ReadCommentDto response = ReadCommentDto.fromEntity(comment);
        return response;
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        commentRepository.deleteById(commentId);
    }


}
