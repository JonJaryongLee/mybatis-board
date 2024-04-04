package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Article;

@Mapper
public interface ArticleMapper {

	/**
	 * 모든 게시글을 조회합니다.
	 * 
	 * @return article list
	 */
	List<Article> findAll();
	
	/**
	 * 아이디에 해당하는 게시글을 조회합니다.
	 * 
	 * @param id 게시글 아이디
	 * @return Optional 리턴
	 */
	Optional<Article> findById(@Param("id") Long id);
	
	/**
	 * 회원이 작성한 게시글을 조회합니다.
	 * 
	 * @param memberId 조회 기준이 되는 회원 아이디
	 * @return article list
	 */
	List<Article> findByMemberId(@Param("memberId") String memberId);
	
	/**
	 * 게시글을 저장합니다.
	 * 
	 * @param article 게시글 객체
	 * @return 성공했을 경우 1L, 실패했을 경우 0L
	 */
	Long save(Article article);
	
	/**
	 * 게시글을 수정합니다.
	 * 
	 * @param article 수정하고자 하는 게시글 객체
	 * @return 성공했을 경우 1L, 실패했을 경우 0L
	 */
	Long update(Article article);
	
	/**
	 * 아이디에 해당하는 게시글을 삭제합니다.
	 * 
	 * @param id 게시글 아이디
	 * @return 성공했을 경우 1L, 실패했을 경우 0L
	 */
	Long deleteById(@Param("id") Long id);
	
	/**
	 * 모든 게시글 삭제
	 * 
	 * @return 삭제된 게시글 갯수를 반환합니다.
	 */
	Long deleteAll();
}
