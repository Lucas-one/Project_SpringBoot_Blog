package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice	//Exception들어오면 모두 이곳으로 오게하는 어노테이션
@RestController
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(value=IllegalArgumentException.class)
//	public String handleArgumentException(IllegalArgumentException e) {
//		return "<h1>"+ e.getMessage() + "</h1>";
//	}
	
	//전체 에러 처리하기
	
	@ExceptionHandler(value=Exception.class)
	public String handleException(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
}
