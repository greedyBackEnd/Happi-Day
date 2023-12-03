package com.happiday.Happi_Day.domain.entity.article.dto;

import com.happiday.Happi_Day.domain.entity.article.Article;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class ReadListArticleDto {
    private String title;
    private String nickname;
    private String date;
    private String thumbnailUrl;
    private Integer commentNum;

    public static ReadListArticleDto fromEntity(Article article) {
        return ReadListArticleDto.builder()
                .nickname(article.getUser().getNickname())
                .title(article.getTitle())
                .date(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .thumbnailUrl(article.getThumbnailUrl())
                .commentNum(article.getArticleComments().size())
                .build();
    }
}
