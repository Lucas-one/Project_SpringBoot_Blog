package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//해동 JpaRepo는 User테이블이 관리하는 Repo야 그리고 User테이블의 PK는 Integer야
//DAO (JSP로 치면)
//Spring레거시에서 Bean으로 등록되는가? => 자동으로 bean등록이 된다.
//즉 @Repository 생략가능하다
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
