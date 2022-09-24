package com.cos.blog.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cos.blog.config.DB;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserDao;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.domain.user.dto.UpdateReqDto;

public class UserService {
	// 회원 가입, 회원 수정, 로그인, 로그아웃, 아이디중복체크
	// 로그아웃은 세션 invalidate만 하면 된다. 로그아웃 함수안에서 세션을 찾을 수없다.-> request와 response가 없기 때문. 
	// 로그아웃은 DB관련 기능이 하나도 없다. 그냥 세션만 날리면 된다. 그래서 서비스로 만들지 않고 컨트롤러에서 처리한다. 왜냐하면 서비스에까지 request를 끌고 오면 안되기 때문이다. 컨트롤러가 request관련된 일을 다 하게해야 한다.
	// 서비스에는 request로 다 처리된 결과만 가져와서 처리해야 한다.
	
	// DB에 select가 아닌 insert 요청 (select인지 insert인지에 따라 응답형태가 달라진다.)
	// select: 리턴하는값이 데이터, insert: 리턴하는 값이 성공 또는 실패를 의미하는 응답값
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	
	public int 회원가입(JoinReqDto dto) {	
		// 회원가입의 경우 insert 요청 
		
		// Data access object(dao)만 실행
//		UserDao userDao = new UserDao();
		int result = userDao.save(dto);
		return result;
	}
	
	public User 로그인(LoginReqDto dto) {
		//로그인은 select 요청 그리고 나서 세션 만들것
		//로그인하고 select하고 그 행을 찾아 리턴할 것임
		//select * from user where username = ? and password = ? >> * 들은 Model에 매핑이 된다.
		//join하는 경우:  select * from user inner join board where username = ? and password = ? >> * 들은 dto로 받아야 한다.
		
		// 리턴한 값을 세션에 담을 것이다. 그래서 리턴값: User 오브젝트이다.
		return userDao.findByUsernameAndPassword(dto);
	}
	
	public int 회원수정(UpdateReqDto dto) {
		// insert하는 기능
		
		return -1;
	}
	
	public int 유저네임중복체크(String username) {
		//사용자로부터 아이디만 받으면 되서 dto를 만들 필요가 없다.
		int result = userDao.findByUsername(username);
		return result;
	}
	
	
}
