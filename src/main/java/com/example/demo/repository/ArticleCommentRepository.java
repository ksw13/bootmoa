package com.example.demo.repository;

import com.example.demo.domain.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
    Page<ArticleComment> findByContentContaining(String keyword, Pageable pageable);
    void deleteByIdAndUserAccount_UserId(Long commentId, String userId);
}
