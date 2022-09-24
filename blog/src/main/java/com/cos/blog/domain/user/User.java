package com.cos.blog.domain.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private String address;
	private String userRole; // admin, user -> 도메인이 있는거니까 enum으로 만드는게 더 좋음
	private Timestamp createDate;
	
}
