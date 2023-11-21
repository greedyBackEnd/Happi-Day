package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.article.ArticleComment;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.ArticleCommentRepository;
import com.happiday.Happi_Day.domain.repository.ArticleRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentInitService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public void initArticleComments() {
        User writer = userRepository.findById(2L).orElse(null);
        Article sampleArticle = articleRepository.findById(1L).orElse(null);

        List<ArticleComment> comments = List.of(
                ArticleComment.builder()
                        .user(writer)
                        .article(sampleArticle)
                        .content("동방신기 게시글 댓글1...")
                        .build(),
                ArticleComment.builder()
                        .user(writer)
                        .article(sampleArticle)
                        .content("동방신기 게시글 댓글2...")
                        .build()
        );

        articleCommentRepository.saveAll(comments);
    }
}
