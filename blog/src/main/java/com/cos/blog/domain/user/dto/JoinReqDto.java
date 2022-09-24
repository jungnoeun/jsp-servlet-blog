package com.cos.blog.domain.user.dto;

import lombok.Data;

@Data
public class JoinReqDto {
	// 회원가입 요청시 필요한 데이터
	private String username;
	private String password;
	private String email;
	private String address;
	//권한-> 회원가입하면 무조건 유저이다.
	//시간 -> 현재시간 넣으면 된다.
}
