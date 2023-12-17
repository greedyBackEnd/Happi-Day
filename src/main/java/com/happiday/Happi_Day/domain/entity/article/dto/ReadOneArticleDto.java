package com.happiday.Happi_Day.domain.entity.article.dto;

import com.happiday.Happi_Day.domain.entity.article.Article;
import com.happiday.Happi_Day.domain.entity.article.Hashtag;
import com.happiday.Happi_Day.domain.entity.artist.Artist;
import com.happiday.Happi_Day.domain.entity.team.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ReadOneArticleDto {
    private String title;
    private String content;
    private List<String> hashtags;
    private String user;
    private String updatedAt;
    private List<ReadCommentDto> comments;
    private int likeUsersNum;
    private List<String> imageUrl;

    public static ReadOneArticleDto fromEntity(Article article) {
        List<String> keywords = new ArrayList<>();

        keywords.addAll(article.getArtists().stream().map(Artist::getName).collect(Collectors.toList()));
        keywords.addAll(article.getTeams().stream().map(Team::getName).collect(Collectors.toList()));
        keywords.addAll(article.getHashtags().stream().map(Hashtag::getTag).collect(Collectors.toList()));

        return ReadOneArticleDto.builder()
                .user(article.getUser().getNickname())
                .title(article.getTitle())
                .content(article.getContent())
                .comments(ReadCommentDto.toReadCommentDto(article.getArticleComments()))
                .hashtags(keywords)
                .likeUsersNum(article.getLikeUsers().size())
                .imageUrl(article.getImageUrl())
                .updatedAt(article.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
