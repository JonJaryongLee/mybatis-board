package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	
	/**
	 * 모든 회원을 조회합니다.
	 * 
	 * @return member list
	 */
	List<Member> findAll();
	
	/**
	 * 아이디에 해당하는 회원을 조회합니다.
	 * 
	 * @param id String 타입의 회원 아이디
	 * @return 성공 시 Optional 리턴
	 */
	Optional<Member> findById(@Param("id") String id);
	
	/**
	 * 회원을 저장합니다.
	 * 
	 * @param member id 를 포함한 회원 객체
	 * @return 성공했을 경우 1L, 실패했을 경우 0L (단, 이 실패는 중복된 회원 아이디와는 상관없습니다.)
	 * @throws DuplicateKeyException 중복된 회원 아이디일 경우, 예외를 발생시킵니다.
	 */
	Long save(Member member);
	
	/**
	 * 회원 정보를 수정합니다.
	 * 
	 * @param member 수정하고자 하는 회원 객체
	 * @return 성공했을 경우 1L, 실패했을 경우 0L
	 */
	Long update(Member member);
	
	/**
	 * 아이디에 해당하는 회원을 삭제합니다.
	 * 
	 * @param id 회원 아이디
	 * @return 성공했을 경우 1L, 실패했을 경우 0L
	 */
	Long deleteById(@Param("id") String id);
	
	/**
	 * 모든 회원 삭제
	 * 
	 * @return 삭제된 회원 수를 반환합니다.
	 */
	Long deleteAll();
}
