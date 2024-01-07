package com.example.demo.dto.request;


import com.example.demo.domain.ArticleComment;
import com.example.demo.dto.ArticleCommentDto;
import com.example.demo.dto.UserAccountDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCommentRequest {
    private Long articleId;
    private Long parentCommentId;
    private String content;

    public ArticleCommentRequest() {
    }

    public ArticleCommentRequest(Long articleId, String content) {
        this.articleId = articleId;
        this.parentCommentId = null;
        this.content = content;
    }

    public ArticleCommentRequest(Long articleId, Long parentCommentId, String content) {
        this.articleId = articleId;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto){
        return new ArticleCommentDto(
                articleId,
                parentCommentId,
                userAccountDto,
                content
        );
    }
}
