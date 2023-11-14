package com.example.demo.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
	@RequestMapping("/hi")
	@ResponseBody
	//void 타입이라 뷰를 반환하지는 않지만, 메소드 내부에서 응답 객체의 출력 스트림으로 문자열을 전송하기 때문에 브라우저에서 수신 가능
	public void hello(HttpServletResponse response) throws IOException {
		response.getWriter().print("Hello world~!!!");
	}

	@RequestMapping("/abc")
	@ResponseBody
	//문자열을 응답 패킷의 body에 반환
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
