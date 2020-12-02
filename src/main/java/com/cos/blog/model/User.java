package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder		//빌더 패턴
//ORM => Java(혹은 다른언어) Object -> 테이블로 매핑해주는 기술
//여기서 내가 설정해놨으므로 id와 Timestamp는 입력 안해줘도 된다. 자동으로 된다.
@Entity	//User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert		//insert 시에 null인 필드 제외시켜준다.
public class User {

	@Id	//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 오라클의 경우 시퀀스, MySQL의 경우 auto_increment
	private int id;	//오라클  : 시퀀스 , MySQL : auto_increment
	
	@Column(nullable = false, length = 30)
	private String username;	//id
	
	@Column(nullable = false, length = 100)	//123456 => 해쉬 (비밀번호암호화)로 바꿀거다 따라서 넉넉히
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//도메인은 범위를 말한다 예를들어 학년이라면 1~6학년 성별이라면 남 여
	//Enum은 도메인을 설정할 수 있다.
	//@ColumnDefault("'user'")	//홑따옴표도 포함하는것에 주목하자 !!
	//즉 "   'user '   " 이렇게 되어 있다.
	//DB는 RoleType이라는 게 없다.	따라서 String Type이라는 것을 알려준다.
	@Enumerated(EnumType.STRING)
	private RoleType role;		//Enum을 쓰는게 좋다.	//admin, user, manager		//String으로 쓰면 오타나도 받아들여지기 때문에 문제된다. 
	
	@CreationTimestamp		//시간이 자동으로 입력된다.
	private Timestamp createDate;
	
}
