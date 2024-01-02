package com.example.demo.dto;

import com.example.demo.domain.ArticleComment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCommentDto {
    private Long id;
    private Long articleId;
    private UserAccountDto userAccountDto;
    private String content;

    public ArticleCommentDto(Long id, Long articleId, UserAccountDto userAccountDto, String content) {
        this.id = id;
        this.articleId = articleId;
        this.userAccountDto = userAccountDto;
        this.content = content;
    }

    public static ArticleCommentDto from(ArticleComment entity){
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent()
        );
    }
}
