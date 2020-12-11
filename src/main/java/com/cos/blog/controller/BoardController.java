package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping({"", "/"})
	public String index() {
		// /WEB-INF/views/index.jsp
		//위 내용이 yml파일에 설정되어있다. prefix, suffix
		return "index";
	}
}
