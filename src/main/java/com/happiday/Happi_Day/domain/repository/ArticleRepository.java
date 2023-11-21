package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.board.BoardCategory;
import com.happiday.Happi_Day.domain.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByCategory(BoardCategory category, Pageable pageable);

    Page<Article> findAllByUser(User user, Pageable pageable);

    Page<Article> findAllByLikeUsersContains(User user, Pageable pageable);

    boolean existsByTitle(String title);
}
