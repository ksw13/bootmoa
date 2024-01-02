package com.example.demo.dto;


import com.example.demo.domain.Article;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;


// 기존 ArticleDto와 다르게 Article+Comment까지 한번에 확인할 수 있는 Dto
@Setter
@Getter
public class ArticleWithCommentDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private UserAccountDto userAccountDto;
    private Set<ArticleCommentDto> articleCommentDtos;

    public ArticleWithCommentDto(Long id, String title, String content, String category, UserAccountDto userAccountDto,
                                 Set<ArticleCommentDto> articleCommentDtos) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.userAccountDto = userAccountDto;
        this.articleCommentDtos = articleCommentDtos;
    }

    public static ArticleWithCommentDto from(Article entity){
        return new ArticleWithCommentDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCategory(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
