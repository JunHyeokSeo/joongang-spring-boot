package com.example.demo.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestControlle
@Controller
public class SampleController {
	//브라우저에서 요청을 보내고 응답을 수신하는 형태
	@RequestMapping("/hi")
	@ResponseBody
	public void hello(HttpServletResponse response) throws IOException {
		response.getWriter().print("Hello world~!!!");
	}

	@RequestMapping("/abc")
	@ResponseBody
	public String abc() {
		return "hi abc";
	}

	//JSP 파일 사용
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/gugu")
	public String gugu(Model model) {
		Random r = new Random();
		int dan = r.nextInt(8) + 2;        // 2 ~ 9단
		model.addAttribute("dan", dan);
		return "gugu";
	}

}
