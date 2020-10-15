package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@RestController
public class DummyControllerTest {
	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	//그렇게 하면 지금처럼 @RequstParam 안써도된다. 변수명 일치시 !!
//	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email) {//key=value (약속된 규칙)
//		//참고로
//		//public String join(@RequestParam("username") String u ,    ...    )
//		//이렇게 해서 변수명 따로 바꿔서 받을 수 도 있다. 하지만 주석아닌것처럼 정확히 같게하면
//		//그대로 받을 수 있다.
//		System.out.println("username : " + username);
//		System.out.println("password : " + password);
//		System.out.println("email : " + email);
//		return "회원가입이 완료되었습니다.";A
//	}
	
	@Autowired		//의존성 주입(DI)
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value (약속된 규칙)

		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPssword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
