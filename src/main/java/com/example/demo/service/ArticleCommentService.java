package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.ArticleComment;
import com.example.demo.domain.UserAccount;
import com.example.demo.dto.ArticleCommentDto;
import com.example.demo.repository.ArticleCommentRepository;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.getArticleId());
            System.out.println(article.getContent());

            ArticleComment articleComment = dto.toEntity(article);
            System.out.println(articleComment.getChildComments());


            // 부모가 있을 때
            if (dto.getParentCommentId() != null) {
                ArticleComment parentComment = articleCommentRepository.getReferenceById(dto.getParentCommentId());
                System.out.println("222222222"+parentComment.getChildComments());
                parentComment.addChildComment(articleComment);
                System.out.println("333333333333333"+parentComment.getChildComments());

            } else { //부모가 없을 때
                articleCommentRepository.save(articleComment);
            }

        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다");
        }
    }

    public void deleteArticleComment(Long commentId, String userId){
        articleCommentRepository.deleteByIdAndUserAccount_UserId(commentId,userId);
    }
}
