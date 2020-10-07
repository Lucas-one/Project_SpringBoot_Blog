package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//모든 알규먼트(변수) 다쓰는 생성자 만들고 싶으면
//@AllArgsConstructor

//final 붙은애들에대한(걔네만) constructor만들어준다.
//@RequiredArgsConstructor

//@Getter
//@Setter
//두개다 하고 싶을 때는 Data
//@Data

//빈 생성자
//@NoArgsConstructor

@Data
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	//요즘에 DB에서 가져온 데이터 변경할 일 없을 때 저렇게 final 붙힌다.
	//	private final int id;
}
