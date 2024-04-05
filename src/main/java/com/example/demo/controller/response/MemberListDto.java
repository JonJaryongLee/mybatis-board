package com.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberListDto {
	
	private String memberId;
	private String nickname;
}
