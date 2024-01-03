package com.example.demo.dto;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleComment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCommentDto {
    private Long id;
    private Long articleId;
    private UserAccountDto userAccountDto;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;

    public ArticleCommentDto(Long id, Long articleId, UserAccountDto userAccountDto, String content,
                             LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        this.id = id;
        this.articleId = articleId;
        this.userAccountDto = userAccountDto;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public ArticleCommentDto(Long articleId, UserAccountDto userAccountDto, String content) {
        this.id=null;
        this.articleId = articleId;
        this.userAccountDto = userAccountDto;
        this.content = content;
        this.createdAt = null;
        this.createdBy = null;
        this.modifiedAt = null;
        this.modifiedBy = null;
    }

    public static ArticleCommentDto from(ArticleComment entity){
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public ArticleComment toEntity(Article article){
        return new ArticleComment(
                article,
                content,
                userAccountDto.toEntity()
        );
    }
}
