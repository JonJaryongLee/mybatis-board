package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateArticleRequest {
	
	private String title;
	private String content;
	private String memberId;
}
