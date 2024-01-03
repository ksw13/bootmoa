package com.example.demo.controller;

import com.example.demo.domain.type.SearchType;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ArticleWithCommentDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.dto.request.ArticleRequest;
import com.example.demo.service.ArticleService;
import com.example.demo.service.PaginationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
        ArticleWithCommentDto article = articleService.getArticle(articleId);
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.getArticleCommentDtos());

        return "articles/detail";
    }

    @GetMapping("/form")
    public String articleForm(){
        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest){
        articleService.saveArticle(articleRequest.toDto(new UserAccountDto("swkang","1234")));

        return "redirect:/articles";
    }
}
