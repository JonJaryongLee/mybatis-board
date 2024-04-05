package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberMapper memberMapper;
	
	/**
	 * 모든 회원을 조회합니다.
	 * 
	 * @return 조회된 회원 리스트
	 */
	public List<Member> findMembers() {
		return memberMapper.findAll();
	}
	
	/**
	 * 아이디에 해당하는 회원을 조회합니다.
	 * 
	 * @param memberId 조회하고자 하는 회원 ID
	 * @return 찾은 회원 객체
	 * @throws NoSuchElementException 아이디에 해당하는 회원이 존재하지 않을 때, 예외를 발생시킵니다.
	 */
	public Member findMember(String memberId) {
		return verifyMemberId(memberId);
	}
	
	/**
	 * 회원을 생성합니다.
	 * 
	 * @param memberId 회원 아이디
	 * @param password 회원 비밀번호
	 * @param nickname 회원 닉네임
	 * @param age 회원 나이
	 * @return 회원 아이디
	 * @throws DuplicateKeyException 중복된 아이디가 존재할 경우, 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 회원가입에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public String createMember(String memberId, String password, String nickname, Long age) {
		verifyDuplicateMemberId(memberId);
		Member member = new Member();
		member.setId(memberId);
		member.setPassword(password);
		member.setNickname(nickname);
		member.setAge(age);
		Long isSuccess = memberMapper.save(member);
		return verifySuccess(isSuccess, memberId);
	}
	
	/**
	 * 회원 정보를 수정합니다.
	 * 
	 * @param memberId 회원 아이디
	 * @param password 회원 비밀번호
	 * @param nickname 회원 닉네임
	 * @param age 회원 나이
	 * @return 회원 아이디
	 * @throws NoSuchElementException 아이디에 해당하는 회원이 존재하지 않을 때, 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 회원수정에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public String updateMember(String memberId, String password, String nickname, Long age) {
		Member foundMember = verifyMemberId(memberId);
		foundMember.setPassword(password);
		foundMember.setNickname(nickname);
		foundMember.setAge(age);
		Long isSuccess = memberMapper.update(foundMember);
		return verifySuccess(isSuccess, memberId);
	}
	
	/**
	 * 아이디에 해당하는 회원을 삭제합니다.
	 * 
	 * @param memberId 회원 아이디
	 * @return 회원 아이디
	 * @throws NoSuchElementException 아이디에 해당하는 회원이 존재하지 않을 때, 예외를 발생시킵니다.
	 * @throws IllegalStateException 서버 내부의 문제로 회원삭제에 실패할 경우, 예외를 발생시킵니다.
	 */
	@Transactional
	public String deleteMember(String memberId) {
		verifyMemberId(memberId);
		Long isSuccess = memberMapper.deleteById(memberId);
		return verifySuccess(isSuccess, memberId);
	}
	
	private void verifyDuplicateMemberId(String memberId) {
		memberMapper.findById(memberId).ifPresent(m -> {
			throw new DuplicateKeyException("이미 존재하는 아이디입니다.");
		});
	}
	
	private Member verifyMemberId(String memberId) {
		return memberMapper.findById(memberId).orElseThrow(()
				-> new NoSuchElementException("아이디에 해당하는 회원이 존재하지 않습니다."));
	}
	
	private String verifySuccess(Long isSuccess, String memberId) {
		if (isSuccess.equals(1L)) {
			return memberId;
		} else {
			throw new IllegalStateException("요청 실패");
		}
	}
}
