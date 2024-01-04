package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.dto.ArticleCommentDto;
import com.example.demo.repository.ArticleCommentRepository;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
        System.out.println(articleCommentDto.getArticleId());

        Article article = articleRepository.getReferenceById(articleCommentDto.getArticleId());

        articleCommentRepository.save(articleCommentDto.toEntity(article));
    }

    public void deleteArticleComment(Long commentId, String userId){
        articleCommentRepository.deleteByIdAndUserAccount_UserId(commentId,userId);
    }
}
