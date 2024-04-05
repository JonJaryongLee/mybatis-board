package com.example.demo.controller.request;

import lombok.Data;

@Data
public class CreateMemberRequest {
	
	private String memberId;
	private String password;
	private String nickname;
	private Long age;
}
