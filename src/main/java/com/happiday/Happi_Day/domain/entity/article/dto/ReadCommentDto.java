package com.happiday.Happi_Day.domain.entity.article.dto;

import com.happiday.Happi_Day.domain.entity.article.ArticleComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ReadCommentDto {
    private Long id;
    private String user;
    private String content;
    private LocalDateTime createdAt;

    public static ReadCommentDto fromEntity(ArticleComment comment) {
        return ReadCommentDto.builder()
                .id(comment.getId())
                .user(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<ReadCommentDto> toReadCommentDto(List<ArticleComment> commentList){
        List<ReadCommentDto> newList = new ArrayList<>();
        for (ArticleComment comment: commentList) {
            newList.add(ReadCommentDto.fromEntity(comment));
        }
        return newList;
    }

}
