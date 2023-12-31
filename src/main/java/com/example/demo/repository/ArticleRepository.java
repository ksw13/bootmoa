package com.example.demo.repository;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource가 붙은 Repository 만
// Repository 인터페이스를 기반으로 RESTful API를 자동으로 생성합니다.
// 예를 들어, UserRepository라는 인터페이스가 있다면 /users 엔드포인트가 자동으로 생성됩니다.
@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
