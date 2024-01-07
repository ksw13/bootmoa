package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.type.SearchType;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleWithCommentDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;


    // Page 인터페이스안에 map이 이미 있음
    public Page<ArticleDto> searchArticles(SearchType searchType, String keyword, Pageable pageable){
        if(keyword == null || keyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(keyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(keyword,pageable).map(ArticleDto::from);
            case CATEGORY -> articleRepository.findByCategory(keyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다"));
    }

    public ArticleWithCommentDto getArticle(Long articleId){
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
    }

    public void saveArticle(ArticleDto articleDto){
        articleRepository.save(articleDto.toEntity());
    }

    public void updateArticle(ArticleDto articleDto){
        try {
            Article article = articleRepository.getReferenceById(articleDto.getId());
            if (articleDto.getTitle() != null)
                article.setTitle(articleDto.getTitle());
            if (articleDto.getContent() != null)
                article.setContent(articleDto.getContent());
            articleDto.setCategory(articleDto.getCategory());
        }
        catch (EntityNotFoundException e){
            log.warn("게시글을 찾을 수 없습니다.");
        }
    }

    public void deleteArticle(long articleId, String userId){
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }
}
