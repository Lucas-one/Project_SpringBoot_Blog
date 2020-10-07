package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob	//대용량 데이터 쓸 때 사용
	private String content;	//	섬머노트 라이브러리 쓴다. <html>태그가 섞여서 디자인이 된다.
	
	@ColumnDefault("0")	//여기선 홑따옴표 필요없다.
	private int count;	//조회수

	//이게 핵심이다. Database는 Object저장할 수 없어서 FK 사용한다. ORM 사용하면 그대로 Object 사용할 수 있다. 자동적으로 FK 만들어준다.
	@ManyToOne	//	Many = Board, One = User				//연관관계 		//한명의 유저는 여러개의 게시글을 쓸 수있다. 여러개의 게시글은 한명의 유저에 의해 쓰여질 수 있다.
	@JoinColumn(name="userId")
	private User userId;		//DB는 오브젝트를 저장할 수 없다. 따라서 FK 사용. 근데 자바는 오브젝트를 저장할 수 있다.
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
