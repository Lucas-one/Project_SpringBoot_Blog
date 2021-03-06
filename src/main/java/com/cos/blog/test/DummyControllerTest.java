package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패했습니다. 해당 ID는 DB에 없습니다.";
		}
		return "삭제되었습니다. id : " + id;
	}
	
	
	//email, password 변경
	//Transactional => 함수 종료시에 자동 commit됨
	//더티체킹이 나쁜뜻이아니다. 바뀐 찌꺼기들 확인해서 처리하는 것이다.
	//DB에서의 더티체킹은 바뀔거 모아놨다가 한번에 하는것을 더티체킹이라고 한다. JPA에서는 변경될때마다 바꿔주는것을 더티체킹이라 한다.
	@Transactional//Transactional을하면 더티체킹 !. 이걸 걸면 save하지 않아도 update된다. save설명은 밑에 참고
	@PutMapping("/dummy/user/{id}")//밑에보면 똑같은 URI로 요청하는게 있는데 그건 Get이고 이건 Put이다. 알아서 잘 구분해서 처리해준다.	
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {	//JSON으로 받으려면 @RequestBody 필요
		System.out.println("id :" + id);
		System.out.println("pw :" + requestUser.getPassword());
		System.out.println("email :" + requestUser.getEmail());
		
		requestUser.setId(id);
		requestUser.setUsername("ssar");
		
		//save는 원래 insert때 하는거
		//pw, email, ssar만 넣었기 때문에 다른 값들은 null이된다..(문제 발생) 따라서 save로는 잘 쓰지 않는다.
		//save(insert)를 쓸려면 해당 user찾아서 변경해줘서 넣어야 한다.
		
		//save함수는 id를 전달하지 않으면 insert를 해주고
		//save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
		//sava함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
		
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//위처럼 바꿔준 것에
		//save하면 수정된다. 하지만 Transactional이 있으면 더티체킹으로 수정된다.
		//userRepository.save(requestUser);
		
		return user;
	}
	
	////http://localhost:8000/blog/dummy/user
	//전체 다받을 것이기 때문에 파라미터가 필요 없다.
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	//JSP에서는 페이징할 때 복잡하다.
	//Sort 저부분 order by 하는 것
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있다.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {//id 똑같이 적어줘야 그대로 쏙 들어온다
		/*
		 * userRepository.findById(id)의 리턴현 Optional이다.
		 * user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		 * 그럼 return null이 리턴이 되잖아.. 그럼 프로그램에 문제가 있지 않겠니?
		 * Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해
		 * 
		 * Optional에서 여러가지 기능이 있는데 .get()은 'null이 리턴 될일이 절대 없어 그러니까 그냥 바로 User객체로 가져와'라는 뜻이다.
		 * 
		 */
//		User user = userRepository.findById(id).get();	우리꺼에는 조금 위험하다.
		
		
		/* .orElseGet(other) '만약에 없으면 니가 User객체 하나 만들어서 넣어줘 ~'라는 뜻 =>other는 Supplier타입이다.
		 * Supplier는 인터페이스 => get 구현해줘야한다.
		 * 인터페이스를 new하려면 익명객체를 만들어줘야한다.. <= 이 부분 공부하자 ㅠ
		 * 빈객체를 넣어주면 null은 아니다.
		 */
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				return User();
//			}
//		});
		
		////////////
		//이렇게 하는 걸 선호한다. 
		
		//람다식 <= 이렇게 하는 게 훨씬 좋다
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//		});
		
		//id가 없어서 오류가 뜨면 처리하는 flow작성(던져준다)
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//얘를 변환해야한다. 웹브라우저가 이해할 수 있는 데이터로 ! => json(예전 같으면 Gson 라이브러리 사용)
		//그러나 SpringBoot는 MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이버러리를 호출해서 
		//user 오브젝트를 json으로 변환해서 브라우저에 전달한다.
		return user;
		
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {//key=value (약속된 규칙)

		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
