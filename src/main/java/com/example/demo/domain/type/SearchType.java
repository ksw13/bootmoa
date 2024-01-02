package com.example.demo.domain.type;

import lombok.Getter;

public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    CATEGORY("카테고리");

    @Getter private final String description;
    SearchType(String description) {
        this.description = description;
    }
}
