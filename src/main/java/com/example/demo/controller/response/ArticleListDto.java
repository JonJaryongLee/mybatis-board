package com.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleListDto {

	private Long articleId;
	private String title;
	private String memberId;
}
