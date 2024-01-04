package com.example.demo.controller;

import com.example.demo.dto.UserAccountDto;
import com.example.demo.dto.request.ArticleCommentRequest;
import com.example.demo.dto.security.BootPrincipal;
import com.example.demo.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BootPrincipal bootPrincipal){
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(bootPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.getArticleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId
            , @AuthenticationPrincipal BootPrincipal bootPrincipal){
        articleCommentService.deleteArticleComment(commentId, bootPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }
}
