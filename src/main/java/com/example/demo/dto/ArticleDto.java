package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDto {
    private String title;
    private String content;
    private String category;
    private UserAccountDto userAccountDto;

    public ArticleDto(String title, String content, String category, UserAccountDto userAccountDto) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.userAccountDto = userAccountDto;
    }
}
