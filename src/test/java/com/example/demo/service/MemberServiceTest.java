package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Member;

@SpringBootTest
@Transactional
class MemberServiceTest {
	
	@Autowired MemberService memberService;
	@Autowired MemberMapper memberMapper;

	Member member1;
	Member member2;
	
	@BeforeEach
	void setUp() {
		member1 = new Member();
		member1.setId("testid1");
		member1.setPassword("testpassword1");
		member1.setNickname("testnickname1");
		member1.setAge(11L);
		
		member2 = new Member();
		member2.setId("testid2");
		member2.setPassword("testpassword2");
		member2.setNickname("testnickname2");
		member2.setAge(22L);
	}

	@AfterEach
	void tearDown() {
		memberMapper.deleteAll();
	}

	@Test
	void testFindMembers() {
		// given
		memberMapper.save(member1);
		memberMapper.save(member2);
		
		// when
		List<Member> foundMembers = memberService.findMembers();
		
		// then
		assertThat(foundMembers.size()).isEqualTo(2L);
	}

	@Test
	void testFindMember() {
		// given
		memberMapper.save(member1);
		
		// when
		Member foundMember = memberService.findMember(member1.getId());
		
		// then
		assertThat(foundMember.getId()).isEqualTo(member1.getId());
		assertThat(foundMember.getPassword()).isEqualTo(member1.getPassword());
		assertThat(foundMember.getNickname()).isEqualTo(member1.getNickname());
		assertThat(foundMember.getAge()).isEqualTo(member1.getAge());
	}
	
	@Test
	void testFindMemberVerifyMemberId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> memberService.findMember("invalidid"));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 회원이 존재하지 않습니다.");
	}

	@Test
	void testCreateMember() {
		// given
		
		// when
		String memberId = memberService.createMember("testid", "testpassword", "testnickname", null);
		Member foundMember = memberMapper.findById(memberId).get();
		
		// then
		assertThat(foundMember.getId()).isEqualTo(memberId);
		assertThat(foundMember.getPassword()).isEqualTo("testpassword");
		assertThat(foundMember.getNickname()).isEqualTo("testnickname");
		assertThat(foundMember.getAge()).isEqualTo(null);
	}
	
	@Test
	void testCreateMemberVerifyDuplicateMemberId() {
		// given
		memberMapper.save(member1);
		
		// when
		DuplicateKeyException e = assertThrows(DuplicateKeyException.class, ()
				-> memberService.createMember(member1.getId(), "testpassword", "testnickname", null));
		
		// then
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testCreateMemberIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}

	@Test
	void testUpdateMember() {
		// given
		memberMapper.save(member1);
		
		// when
		String memberId = memberService.updateMember(member1.getId(), member1.getPassword(), "fixednickname", member1.getAge());
		Member foundMember = memberMapper.findById(memberId).get();
		
		// then
		assertThat(foundMember.getId()).isEqualTo(memberId);
		assertThat(foundMember.getPassword()).isEqualTo(member1.getPassword());
		assertThat(foundMember.getNickname()).isEqualTo("fixednickname");
		assertThat(foundMember.getAge()).isEqualTo(member1.getAge());
	}
	
	@Test
	void testUpdateMemberVerifyMemberId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> memberService.updateMember("invalidid", "ddd", "ddd", null));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 회원이 존재하지 않습니다.");
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testUpdateMemberIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}

	@Test
	void testDeleteMember() {
		// given
		memberMapper.save(member1);
		
		// when
		String memberId = memberService.deleteMember(member1.getId());
		Optional<Member> foundMember = memberMapper.findById(memberId);
		
		// then
		assertThat(memberId).isEqualTo(member1.getId());
		assertThat(foundMember).isEqualTo(Optional.empty());
	}
	
	@Test
	void testDeleteMemberVerifyMemberId() {
		// given
		
		// when
		NoSuchElementException e = assertThrows(NoSuchElementException.class, ()
				-> memberService.deleteMember("invalidid"));
		
		// then
		assertThat(e.getMessage()).isEqualTo("아이디에 해당하는 회원이 존재하지 않습니다.");
	}
	
	// Mockito 를 배워야 구현 가능
//	@Test
//	void testDeleteMemberIllegalStateException() {
//		// given
//		
//		// when
//		
//		// then
//	}
}
