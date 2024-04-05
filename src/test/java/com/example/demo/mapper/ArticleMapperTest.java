package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Article;
import com.example.demo.model.Member;

@SpringBootTest
@Transactional
class ArticleMapperTest {
	
	@Autowired ArticleMapper articleMapper;
	@Autowired MemberMapper memberMapper;
	
	Member member;
	Article article1;
	Article article2;

	@BeforeEach
	void setUp() {
		member = new Member();
		member.setId("jony123");
		member.setPassword("qwer1234");
		member.setNickname("조니");
		memberMapper.save(member);
		
		article1 = new Article();
		article1.setTitle("title1");
		article1.setContent("testcontent1");
		article1.setCreatedAt(LocalDateTime.now());
		article1.setUpdatedAt(LocalDateTime.now());
		article1.setMemberId(member.getId());
		
		article2 = new Article();
		article2.setTitle("title2");
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
	void testFindAll() {
		// given
		articleMapper.save(article1);
		articleMapper.save(article2);
		
		// when
		List<Article> foundArticles = articleMapper.findAll();
		
		// then
		assertThat(foundArticles.size()).isEqualTo(2L);
	}

	@Test
	void testFindById() {
		// given
		articleMapper.save(article1);
		
		// when
		Article foundArticle = articleMapper.findById(article1.getId()).get();
		
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
	void testFindByIdInvalidId() {
		// given
		
		// when
		Optional<Article> foundArticle = articleMapper.findById(99L);
		
		// then
		assertThat(foundArticle).isEqualTo(Optional.empty());
	}

	@Test
	void testFindByMemberId() {
		// given
		articleMapper.save(article1);
		articleMapper.save(article2);
		
		// when
		List<Article> foundArticles = articleMapper.findByMemberId(member.getId());
		
		// then
		assertThat(foundArticles.size()).isEqualTo(2L);
	}
	
	@Test
	void testFindByMemberIdInvalidMemberId() {
		// given
		
		// when
		List<Article> foundArticles = articleMapper.findByMemberId(member.getId());
		
		// then
		assertThat(foundArticles.size()).isEqualTo(0L);
	}

	@Test
	void testSave() {
		// given
		
		// when
		Long isSuccess = articleMapper.save(article1);
		Article foundArticle = articleMapper.findById(article1.getId()).get();
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
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
	void testUpdate() {
		// given
		articleMapper.save(article1);
		article1.setTitle("changedtitle");
		
		// when
		Long isSuccess = articleMapper.update(article1);
		Article foundArticle = articleMapper.findById(article1.getId()).get();
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
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
	void testUpdateInvalidId() {
		// given
		
		// when
		Long isSuccess = articleMapper.update(article1);
		
		// then
		assertThat(isSuccess).isEqualTo(0L);
	}

	@Test
	void testDeleteById() {
		// given
		articleMapper.save(article1);
		
		// when
		Long isSuccess = articleMapper.deleteById(article1.getId());
		Optional<Article> foundArticle = articleMapper.findById(article1.getId());
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
		assertThat(foundArticle).isEqualTo(Optional.empty());
	}
	
	@Test
	void testDeleteByIdInvalidId() {
		// given
		
		// when
		Long isSuccess = articleMapper.deleteById(99L);
		
		// then
		assertThat(isSuccess).isEqualTo(0L);
	}

	@Test
	void testDeleteAll() {
		// given
		articleMapper.save(article1);
		articleMapper.save(article2);
		List<Article> foundArticles1 = articleMapper.findAll();
		
		// when
		Long affectedRows = articleMapper.deleteAll();
		List<Article> foundArticles2 = articleMapper.findAll();
		
		// then
		assertThat(foundArticles1.size()).isEqualTo(2L);
		assertThat(affectedRows).isEqualTo(2L);
		assertThat(foundArticles2.size()).isEqualTo(0L);
	}

}
