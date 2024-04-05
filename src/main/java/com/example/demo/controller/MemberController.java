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

import com.example.demo.controller.request.CreateMemberRequest;
import com.example.demo.controller.request.UpdateMemberRequest;
import com.example.demo.controller.response.ArticleListDto;
import com.example.demo.controller.response.CreateMemberResponse;
import com.example.demo.controller.response.DeleteMemberResponse;
import com.example.demo.controller.response.MemberDetailDto;
import com.example.demo.controller.response.MemberListDto;
import com.example.demo.controller.response.UpdateMemberResponse;
import com.example.demo.model.Member;
import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final ArticleService articleService;
	
	@GetMapping
	public List<MemberListDto> list() {
		return memberService.findMembers().stream()
			.map(m -> new MemberListDto(
					m.getId(),
					m.getNickname()
			)).collect(Collectors.toList());
	}
	
	@GetMapping("/{memberId}")
	public MemberDetailDto detail(@PathVariable(name = "memberId") String memberId) {
		Member foundMember = memberService.findMember(memberId);
		return new MemberDetailDto(
				foundMember.getId(),
				foundMember.getNickname(),
				foundMember.getAge());
	}
	
	@GetMapping("/{memberId}/articles")
	public List<ArticleListDto> listByMemberId(@PathVariable(name = "memberId") String memberId) {
		return articleService.findArticleByMemberId(memberId).stream()
				.map(a -> new ArticleListDto(
						a.getId(),
						a.getTitle(),
						a.getMemberId()
				)).collect(Collectors.toList());
	}
	
	@PostMapping
	public CreateMemberResponse create(@RequestBody CreateMemberRequest request) {
		String memberId = memberService.createMember(
				request.getMemberId(),
				request.getPassword(),
				request.getNickname(),
				request.getAge());
		return new CreateMemberResponse(memberId);
	}
	
	@PatchMapping("/{memberId}")
	public UpdateMemberResponse update(@PathVariable(name = "memberId") String memberId, @RequestBody UpdateMemberRequest request) {
		memberService.updateMember(
				memberId,
				request.getPassword(),
				request.getNickname(),
				request.getAge());
		return new UpdateMemberResponse(memberId);
	}
	
	@DeleteMapping("/{memberId}")
	public DeleteMemberResponse delete(@PathVariable(name = "memberId") String memberId) {
		memberService.deleteMember(memberId);
		return new DeleteMemberResponse(memberId);
	}
}
