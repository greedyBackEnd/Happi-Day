package com.happiday.Happi_Day.initialization.service;

import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.board.BoardCategory;
import com.happiday.Happi_Day.domain.entity.team.Team;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleInitService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final ArtistRepository artistRepository;
    private final TeamRepository teamRepository;

    public void initArticles() {
        User writer = userRepository.findById(2L).orElse(null);
        BoardCategory category = boardCategoryRepository.findById(1L).orElse(null);
        Artist artist1 = artistRepository.findById(1L).orElse(null);
        Artist artist2 = artistRepository.findById(1L).orElse(null);
        Artist artist3 = artistRepository.findById(2L).orElse(null);
        Artist artist4 = artistRepository.findById(2L).orElse(null);
        Team team1 = teamRepository.findById(1L).orElse(null);
        Team team2 = teamRepository.findById(2L).orElse(null);

        List<Article> articles = List.of(
                Article.builder()
                        .user(writer)
                        .title("동방신기 제목")
                        .content("동방신기 게시글 내용...")
                        .category(category)
                        .artists(List.of(artist1, artist2))
                        .teams(List.of(team1))
                        .build(),
                Article.builder()
                        .user(writer)
                        .title("god 제목")
                        .content("god 게시글 내용...")
                        .category(category)
                        .artists(List.of(artist3, artist4))
                        .teams(List.of(team2))
                        .build()
        );

        articles.forEach(article -> {
            if (!articleRepository.existsByTitle(article.getTitle())) {
                articleRepository.save(article);
            }
        });
    }
}
