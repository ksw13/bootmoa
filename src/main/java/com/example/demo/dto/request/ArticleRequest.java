package com.example.demo.dto.request;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.UserAccountDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleRequest {
    private String title;
    private String content;
    private String category;

    public ArticleRequest(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public ArticleDto toDto(UserAccountDto userAccountDto){
        return new ArticleDto(
                title,
                content,
                category,
                userAccountDto
        );
    }

}
