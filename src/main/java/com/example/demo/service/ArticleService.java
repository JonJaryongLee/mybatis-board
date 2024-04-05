package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Article;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
	
	private final ArticleMapper articleMapper;
	private final MemberMapper memberMapper;
	
	/**
	 * 전체 게시글을 조회합니다.
	 * 
	 * @return 게시글 리스트
	 */
	public List<Article> findArticles() {
		return articleMapper.findAll();
	}
	
	/**
	 * 게시글 아이디에 해당하는 게시글을 조회합니다.
	 * 
	 * @param articleId 게시글 아이디
	 * @return 게시글 객체
	 * @throws NoSuchElementException 아이디에 해당하는 게시글이 존재하지 않을 경우 예외를 발생시킵니다.
	 */
	public Article findArticleById(Long articleId) {
		return verifyArticleId(articleId);
	}
	
	/**
	 * 회원이 작성한 게시글 리스트를 조회합니다.
	 * 
	 * @param memberId 회원 아이디
	 * @return 게시글 리스트
	 * @throws NoSuchElementException 아이디에 해당하는 회원이 존재하지 않을 경우 예외를 발생시킵니다.
	 */
	public List<Article> findArticleByMemberId(String memberId) {
		verifyMemberId(memberId);
		return articleMapper.findByMemberId(memberId);
	}
	
	/**
	 * 게시글을 등록합니다.
	 * 
	 * @param title 게시글 제목
	 * @param content 게시글 내용
	 * @param memberId 작성자
	 * @return 생성된 게시글 아이디
	 * @throws NoSuchElementException 아이디에 해당하는 회원이 존재하지 않을 경우 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 게시글 등록에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public Long createArticle(String title, String content, String memberId) {
		verifyMemberId(memberId);
		Article article = new Article();
		article.setTitle(title);
		article.setContent(content);
		article.setCreatedAt(LocalDateTime.now());
		article.setUpdatedAt(LocalDateTime.now());
		article.setMemberId(memberId);
		Long isSuccess = articleMapper.save(article);
		return verifySuccess(isSuccess, article.getId());
	}
	
	/**
	 * 게시글을 수정합니다.
	 * 
	 * @param articleId 게시글 아이디
	 * @param title 게시글 제목
	 * @param content 게시글 내용
	 * @return 생성된 게시글 아이디
	 * @throws NoSuchElementException 아이디에 해당하는 게시글이 존재하지 않을 경우 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 게시글 수정에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public Long updateArticle(Long articleId, String title, String content) {
		Article foundArticle = verifyArticleId(articleId);
		foundArticle.setTitle(title);
		foundArticle.setContent(content);
		foundArticle.setUpdatedAt(LocalDateTime.now());
		Long isSuccess = articleMapper.update(foundArticle);
		return verifySuccess(isSuccess, foundArticle.getId());
	}
	
	/**
	 * 게시글을 삭제합니다.
	 * 
	 * @param id 게시글 아이디
	 * @return 삭제된 게시글 아이디
	 * @throws NoSuchElementException 아이디에 해당하는 게시글이 존재하지 않을 경우 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 게시글 삭제에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public Long deleteArticle(Long articleId) {
		verifyArticleId(articleId);
		Long isSuccess = articleMapper.deleteById(articleId);
		return verifySuccess(isSuccess, articleId);
	}
	
	private void verifyMemberId(String memberId) {
		memberMapper.findById(memberId).orElseThrow(()
				-> new NoSuchElementException("아이디에 해당하는 회원이 존재하지 않습니다."));
	}
	
	private Article verifyArticleId(Long articleId) {
		return articleMapper.findById(articleId).orElseThrow(()
				-> new NoSuchElementException("아이디에 해당하는 게시글이 존재하지 않습니다."));
	}
	
	private Long verifySuccess(Long isSuccess, Long articleId) {
		if (isSuccess.equals(1L)) {
			return articleId;
		} else {
			throw new IllegalStateException("요청 실패");
		}
	}
}
