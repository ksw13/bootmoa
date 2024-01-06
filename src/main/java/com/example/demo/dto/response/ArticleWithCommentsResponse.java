package com.example.demo.dto.response;

import com.example.demo.dto.ArticleCommentDto;
import com.example.demo.dto.ArticleWithCommentDto;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ArticleWithCommentsResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String userId;
    private Set<ArticleCommentResponse> articleCommentResponses;

    public ArticleWithCommentsResponse(Long id, String title, String content, LocalDateTime createdAt, String userId,
                                       Set<ArticleCommentResponse> articleCommentResponses) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.articleCommentResponses = articleCommentResponses;
    }

    public static ArticleWithCommentsResponse from (ArticleWithCommentDto dto){
        return new ArticleWithCommentsResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getUserId(),
                organizeChildComments(dto.getArticleCommentDtos())
        );
    }

    private static Set<ArticleCommentResponse> organizeChildComments(Set<ArticleCommentDto> dtos) {
        Map<Long, ArticleCommentResponse> map = dtos.stream()
                .map(ArticleCommentResponse::from)
                .collect(Collectors.toMap(ArticleCommentResponse::getId, Function.identity()));

        map.values().stream()
                .filter(ArticleCommentResponse::hasParentComment)
                .forEach(comment -> {
                    ArticleCommentResponse parentComment = map.get(comment.getParentCommentId());
                    parentComment.getChildComments().add(comment);
                });

        return map.values().stream()
                .filter(comment -> !comment.hasParentComment())
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator
                                .comparing(ArticleCommentResponse::getCreatedAt)
                                .reversed()
                                .thenComparingLong(ArticleCommentResponse::getId)
                        )
                ));
    }

}
