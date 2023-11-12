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
    private List<String> teams;
    private List<String> artists;
    private List<String> hashtags;
    private String user;
    private String createdAt;
    private List<ReadCommentDto> comments;
    private int likeUsersNum;
    private List<String> imageUrl;


    public static ReadOneArticleDto fromEntity(Article article) {
        List<String> artists = article.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        List<String> additionalArtists = article.getArtists() != null ? Arrays.asList(article.getEctArtists().split(", ")) : Collections.emptyList();
        List<String> allArtists = new ArrayList<>(artists);
        allArtists.addAll(additionalArtists);

        List<String> teams = article.getTeams().stream().map(Team::getName).collect(Collectors.toList());
        List<String> additionalTeams = article.getTeams() != null ? Arrays.asList(article.getEctTeams().split(", ")) : Collections.emptyList();
        List<String> allTeams = new ArrayList<>(teams);
        allTeams.addAll(additionalTeams);

        List<String> hashtagList = new ArrayList<>();
        for (Hashtag hashtag : article.getHashtags()) {
            hashtagList.add(hashtag.getTag());
        }

        return ReadOneArticleDto.builder()
                .user(article.getUser().getNickname())
                .title(article.getTitle())
                .content(article.getContent())
                .teams(allTeams)
                .artists(allArtists)
                .comments(ReadCommentDto.toReadCommentDto(article.getArticleComments()))
                .hashtags(hashtagList)
                .likeUsersNum(article.getLikeUsers().size())
                .imageUrl(article.getImageUrl())
                .createdAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

}
