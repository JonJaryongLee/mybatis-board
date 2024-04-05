package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Article;
import com.example.demo.model.Member;

@SpringBootTest
@Transactional
class ArticleServiceTest {
	
	@Autowired ArticleService articleService;
	@Autowired ArticleMapper articleMapper;
	@Autowired MemberMapper memberMapper;

	Member member;
	Article article1;
	Article article2;
	
	@BeforeEach
	void setUp() {
		member = new Member();
		member.setId("testid");
		member.setPassword("testpassword");
		member.setNickname("testnickname");
		member.setAge(null);
		memberMapper.save(member);
		
		article1 = new Article();
		article1.setTitle("testtitle1");
		article1.setContent("testcontent1");
		article1.setCreatedAt(LocalDateTime.now());
		article1.setUpdatedAt(LocalDateTime.now());
		article1.setMemberId(member.getId());
		
		article2 = new Article();
		article2.setTitle("testtitle2");
		article2.setContent("testcontent2");
		article2.setCreatedAt(LocalDateTime.now());
		article2.setUpdatedAt(LocalDateTime.now());
		article2.setMemberId(member.getId());
	}

	@AfterEach
	void tearDown() {
		articleMapper.deleteAll();
		memberMapper.deleteAll();
	}

	@Test
	void testFindArticles() {
		// given
		articleMapper.save(article1);
		articleMapper.save(article2);
		
		// when
		List<Article> foundArticles = articleService.findArticles();
		
		// then
		assertThat(foundArticles.size()).isEqualTo(2L);
	}

	@Test
	void testFindArticleById() {
		// given
		articleMapper.save(article1);
		
		// when
		Article foundArticle = articleService.findArticleById(article1.getId());
		
		// then
		assertThat(foundArticle.getId()).isEqualTo(article1.getId());
		assertThat(foundArticle.getTitle()).isEqualTo(article1.getTitle());
		assertThat(foundArticle.getContent()).isEqualTo(article1.getContent());
		assertThat(foundArticle.getMemberId()).isEqualTo(article1.getMemberId());
		
		int foundArticleCreatedAtNano = foundArticle.getCreatedAt().getNano() / 1000000;
		int foundArticleUpdatedAtNano = foundArticle.getUpdatedAt().getNano() / 1000000;
		int article1CreatedAtNano = article1.getCreatedAt().getNano() / 1000000;
		int article1UpdatedAtNano = article1.getUpdatedAt().getNano() / 1000000;
		
		assertThat(foundArticleCreatedAtNano).isEqualTo(article1CreatedAtNano);
		assertThat(foundArticleUpdatedAtNano).isEqualTo(article1UpdatedAtNano);
	}
	
	@Test
	void testFindArticleByIdVerifyArticleId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> articleService.findArticleById(99L));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 게시글이 존재하지 않습니다.");
	}

	@Test
	void testFindArticleByMemberId() {
		// given
		articleMapper.save(article1);
		articleMapper.save(article2);
		
		// when
		List<Article> foundArticles = articleService.findArticleByMemberId(member.getId());
		
		// then
		assertThat(foundArticles.size()).isEqualTo(2L);
	}
	
	@Test
	void testFindArticleByMemberIdVerifyMemberId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> articleService.findArticleByMemberId("invalidid"));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 회원이 존재하지 않습니다.");
	}

	@Test
	void testCreateArticle() {
		// given
		
		// when
		Long articleId = articleService.createArticle("testtitle", "testcontent", member.getId());
		Article foundArticle = articleMapper.findById(articleId).get();
		
		// then
		assertThat(foundArticle.getId()).isEqualTo(articleId);
		assertThat(foundArticle.getTitle()).isEqualTo("testtitle");
		assertThat(foundArticle.getContent()).isEqualTo("testcontent");
		assertThat(foundArticle.getMemberId()).isEqualTo(member.getId());
//		assertThat(foundArticle.getCreatedAt()).isEqualTo(LocalDateTime.now());
//		assertThat(foundArticle.getUpdatedAt()).isEqualTo(LocalDateTime.now());
	}
	
	@Test
	void testCreateArticleVerifyMemberId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> articleService.createArticle("testtitle", "testcontent", "invalidmemberid"));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 회원이 존재하지 않습니다.");
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testCreateArticleIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}

	@Test
	void testUpdateArticle() {
		// given
		articleMapper.save(article1);
		
		// when
		Long articleId = articleService.updateArticle(article1.getId(), "fixedtitle", "fixedcontent");
		Article updatedArticle = articleMapper.findById(articleId).get();
		
		// then
		assertThat(updatedArticle.getId()).isEqualTo(articleId);
		assertThat(updatedArticle.getTitle()).isEqualTo("fixedtitle");
		assertThat(updatedArticle.getContent()).isEqualTo("fixedcontent");
		assertThat(updatedArticle.getMemberId()).isEqualTo(member.getId());
//		assertThat(updatedArticle.getCreatedAt()).isEqualTo(LocalDateTime.now());
//		assertThat(updatedArticle.getUpdatedAt()).isEqualTo(LocalDateTime.now());
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testUpdateArticleIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}

	@Test
	void testDeleteArticle() {
		// given
		articleMapper.save(article1);
		
		// when
		Long articleId = articleService.deleteArticle(article1.getId());
		Optional<Article> foundArticle = articleMapper.findById(articleId);
		
		// then
		assertThat(articleId).isEqualTo(article1.getId());
		assertThat(foundArticle).isEqualTo(Optional.empty());
	}
	
	@Test
	void testDeleteArticleVerifyArticleId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> articleService.deleteArticle(99L));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 게시글이 존재하지 않습니다.");
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testDeleteArticleIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}
}
