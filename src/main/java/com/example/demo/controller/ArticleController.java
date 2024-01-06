package com.example.demo.controller;

import com.example.demo.domain.type.SearchType;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleWithCommentDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.dto.request.ArticleRequest;
import com.example.demo.dto.response.ArticleCommentResponse;
import com.example.demo.dto.response.ArticleWithCommentsResponse;
import com.example.demo.dto.security.BootPrincipal;
import com.example.demo.service.ArticleService;
import com.example.demo.service.PaginationService;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    // 게시판 리스트 출력
    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String keyword,
            // pageable에 대한 옵션
            @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
            ModelMap map
    ){
        Page<ArticleDto> articles = articleService.searchArticles(searchType, keyword, pageable);
        List<Integer> paginationNumber = paginationService.getPaginationNumber(pageable.getPageNumber(),
                articles.getTotalPages());
        map.addAttribute("articles", articles);
        map.addAttribute("paginationNumber", paginationNumber);
        map.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map){
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.getArticleCommentResponses());

        return "articles/detail";
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId, @AuthenticationPrincipal BootPrincipal bootPrincipal){
        articleService.deleteArticle(articleId, bootPrincipal.getUsername());
        return "redirect:/articles/";
    }

    @GetMapping("/form")
    public String articleForm(){
        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest, @AuthenticationPrincipal BootPrincipal bootPrincipal){
        articleService.saveArticle(articleRequest.toDto(bootPrincipal.toDto()));

        return "redirect:/articles";
    }
}
