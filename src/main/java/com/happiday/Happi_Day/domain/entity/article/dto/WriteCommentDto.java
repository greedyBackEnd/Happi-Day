package com.happiday.Happi_Day.domain.entity.article.dto;

import com.happiday.Happi_Day.domain.entity.article.ArticleComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WriteCommentDto {
    private String content;

    public ArticleComment toEntity(){
        return ArticleComment.builder()
                .content(content)
                .build();
    }
}
