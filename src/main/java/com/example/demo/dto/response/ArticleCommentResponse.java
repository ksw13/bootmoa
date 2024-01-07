package com.example.demo.dto.response;

import com.example.demo.dto.ArticleCommentDto;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleCommentResponse {
    private Long id;
    private String content;
    private String userId;
    private Long parentCommentId;
    private Set<ArticleCommentResponse> childComments;
    private LocalDateTime createdAt;

    public ArticleCommentResponse(Long id, String content, String userId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public ArticleCommentResponse(Long id, String content, String userId, Long parentCommentId,
                                  LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.parentCommentId = parentCommentId;
        this.createdAt = createdAt;
        Comparator<ArticleCommentResponse> childCommentComparator = Comparator
                .comparing(ArticleCommentResponse::getCreatedAt)
                .thenComparingLong(ArticleCommentResponse::getId);
        this.childComments = new TreeSet<>(childCommentComparator);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto){
        return new ArticleCommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getUserAccountDto().getUserId(),
                dto.getParentCommentId(),
                dto.getCreatedAt()
        );
    }

    public boolean hasParentComment(){
        return parentCommentId != null;
    }
    public boolean isEmpty() { return childComments.isEmpty(); }
}
