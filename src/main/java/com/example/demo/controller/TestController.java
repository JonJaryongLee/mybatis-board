package com.example.demo.controller;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TestController {
	
	@GetMapping("/test")
	public void test() throws HttpException, IOException {
		log.info("work");
		
		String url = "https://qaif.multicampus.com/api/v1/basic/ext/getPublicKey";
		PostMethod post = new PostMethod(url);
		HttpClient client = new HttpClient();
		post.addParameter("mdCd", "STUDENT");
		post.addParameter("bsnCpScCd", "CP");
		post.addParameter("linkInstId", "MINCODING");
		// 3. URL Http
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		int statusCode = client.executeMethod(post); // http request Status valus (200, 404, 500 등)
		String resposeMessage = post.getResponseBodyAsString().replaceAll("\r\n", "");
		
		log.info(post.getParameter("mdCd").toString());
		log.info(post.getParameter("bsnCpScCd").toString());
		log.info(post.getParameter("linkInstId").toString());
		log.info(resposeMessage);
		
		org.json.JSONObject rtnJson_01 = new org.json.JSONObject(resposeMessage);
		String result = rtnJson_01.getString("result");
		String msg = rtnJson_01.getString("msg");
		String ts = rtnJson_01.getString("ts");
		String sno = rtnJson_01.getString("sno");
		String publicKey = rtnJson_01.getString("publicKey");
	}
}
