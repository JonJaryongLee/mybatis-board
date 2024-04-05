package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.request.CreateArticleRequest;
import com.example.demo.controller.request.UpdateArticleRequest;
import com.example.demo.controller.response.ArticleDetailDto;
import com.example.demo.controller.response.ArticleListDto;
import com.example.demo.controller.response.CreateArticleResponse;
import com.example.demo.controller.response.DeleteArticleResponse;
import com.example.demo.controller.response.UpdateArticleResponse;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;
	
	@GetMapping
	public List<ArticleListDto> list() {
		return articleService.findArticles().stream()
				.map(a -> new ArticleListDto(
						a.getId(),
						a.getTitle(),
						a.getMemberId()
				)).collect(Collectors.toList());
	}
	
	@GetMapping("/{articleId}")
	public ArticleDetailDto detail(@PathVariable(name = "articleId") Long articleId) {
		Article foundArticle = articleService.findArticleById(articleId);
		return new ArticleDetailDto(
				foundArticle.getId(),
				foundArticle.getTitle(),
				foundArticle.getContent(),
				foundArticle.getCreatedAt(),
				foundArticle.getUpdatedAt(),
				foundArticle.getMemberId());
	}
	
	@PostMapping
	public CreateArticleResponse create(@RequestBody CreateArticleRequest request) {
		Long articleId = articleService.createArticle(
				request.getTitle(),
				request.getContent(),
				request.getMemberId());
		return new CreateArticleResponse(articleId);
	}
	
	@PatchMapping("/{articleId}")
	public UpdateArticleResponse update(@PathVariable(name = "articleId") Long articleId, @RequestBody UpdateArticleRequest request) {
		articleService.updateArticle(
				articleId,
				request.getTitle(),
				request.getContent());
		return new UpdateArticleResponse(articleId);
	}
	
	@DeleteMapping("/{articleId}")
	public DeleteArticleResponse delete(@PathVariable(name = "articleId") Long articleId) {
		articleService.deleteArticle(articleId);
		return new DeleteArticleResponse(articleId);
	}
}
