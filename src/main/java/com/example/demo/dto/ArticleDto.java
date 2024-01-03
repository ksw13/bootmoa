package com.example.demo.dto;

import com.example.demo.domain.Article;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String category;
    private UserAccountDto userAccountDto;

    public ArticleDto(Long id, String title, String content, String category, UserAccountDto userAccountDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.userAccountDto = userAccountDto;
    }

    public ArticleDto(String title, String content, String category, UserAccountDto userAccountDto) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userAccountDto = userAccountDto;
    }

    public static ArticleDto from(Article entity){
        return new ArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCategory(),
                UserAccountDto.from(entity.getUserAccount())
        );
    }

    public Article toEntity() {
        return new Article(
                title,
                content,
                category,
                userAccountDto.toEntity()
        );
    }
}
