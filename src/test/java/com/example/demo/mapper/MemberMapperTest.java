package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Member;

@SpringBootTest
@Transactional
class MemberMapperTest {

	@Autowired
	MemberMapper memberMapper;

	Member member1;
	Member member2;

	@BeforeEach
	void setUp() {
		member1 = new Member();
		member2 = new Member();

		member1.setId("jony123");
		member1.setPassword("qwer1234");
		member1.setNickname("조니");

		member2.setId("sylvie456");
		member2.setPassword("qwer1234");
		member2.setNickname("실비");
	}

	@AfterEach
	void tearDown() {
		memberMapper.deleteAll();
	}

	@Test
	void testFindAll() {
		// given
		memberMapper.save(member1);
		memberMapper.save(member2);

		// when
		List<Member> foundMembers = memberMapper.findAll();

		// then
		assertThat(foundMembers.size()).isEqualTo(2L);
	}

	@Test
	void testFindById() {
		// given
		memberMapper.save(member1);
		
		// when
		Member foundMember = memberMapper.findById(member1.getId()).get();
		
		// then
		assertThat(foundMember.getId()).isEqualTo("jony123");
		assertThat(foundMember.getPassword()).isEqualTo(member1.getPassword());
		assertThat(foundMember.getNickname()).isEqualTo(member1.getNickname());
		assertThat(foundMember.getAge()).isEqualTo(member1.getAge());
	}
	
	@Test
	void testFindByIdInvalidId() {
		// given
		
		// when
		Optional<Member> foundMember = memberMapper.findById("invalidid");
		
		// then
		assertThat(foundMember).isEqualTo(Optional.empty());
	}

	@Test
	void testSave() {
		// given
		
		// when
		Long isSuccess = memberMapper.save(member1);
		Member foundMember = memberMapper.findById(member1.getId()).get();
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
		assertThat(foundMember.getId()).isEqualTo(member1.getId());
		assertThat(foundMember.getPassword()).isEqualTo(member1.getPassword());
		assertThat(foundMember.getNickname()).isEqualTo(member1.getNickname());
		assertThat(foundMember.getAge()).isEqualTo(member1.getAge());
	}
	
	@Test
	void testSaveDuplicatedId() {
		// given
		memberMapper.save(member1);
		
		Member member3 = new Member();
		member3.setId("jony123");
		member3.setPassword("1456456");
		member3.setNickname("조니투");
		
		// when
		DuplicateKeyException e = assertThrows(DuplicateKeyException.class,
				() -> memberMapper.save(member3));
		
		// then
		assertThat(e.getClass().getName()).isEqualTo("org.springframework.dao.DuplicateKeyException");
	}

	@Test
	void testUpdate() {
		// given
		memberMapper.save(member1);
		member1.setPassword("newpassword");
		
		// when
		Long isSuccess = memberMapper.update(member1);
		Member foundMember = memberMapper.findById(member1.getId()).get();
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
		assertThat(foundMember.getId()).isEqualTo(member1.getId());
		assertThat(foundMember.getPassword()).isEqualTo(member1.getPassword());
		assertThat(foundMember.getNickname()).isEqualTo(member1.getNickname());
		assertThat(foundMember.getAge()).isEqualTo(member1.getAge());
	}
	
	@Test
	void testUpdateInvalidId() {
		// given
		Member invalidMember = new Member();
		invalidMember.setId("invalidId");
		
		// when
		Long isSuccess = memberMapper.update(invalidMember);
		
		// then
		assertThat(isSuccess).isEqualTo(0L);
	}

	@Test
	void testDeleteById() {
		// given
		memberMapper.save(member1);
		
		// when
		Long isSuccess = memberMapper.deleteById(member1.getId());
		Optional<Member> foundMember = memberMapper.findById(member1.getId());
		
		// then
		assertThat(isSuccess).isEqualTo(1L);
		assertThat(foundMember).isEqualTo(Optional.empty());
	}
	
	@Test
	void testDeleteByIdInvalidId() {
		// given
		
		// when
		Long isSuccess = memberMapper.deleteById("invalidId");
		
		// then
		assertThat(isSuccess).isEqualTo(0L);
	}
	
	@Test
	void testDeleteAll() {
		// given
		memberMapper.save(member1);
		memberMapper.save(member2);
		List<Member> foundMembers1 = memberMapper.findAll();
		
		// when
		Long affectedRows = memberMapper.deleteAll();
		List<Member> foundMembers2 = memberMapper.findAll();
		
		// then
		assertThat(foundMembers1.size()).isEqualTo(2L);
		assertThat(affectedRows).isEqualTo(2L);
		assertThat(foundMembers2.size()).isEqualTo(0L);
	}
}
