package com.cos.blog.domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cos.blog.config.DB;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;

public class UserDao {
	
	
	public User findByUsernameAndPassword(LoginReqDto dto) {
		// select *하면 안된다. 세션에 패스워드 넣으면 안됨
		String sql = "SELECT id, username, email, address FROM user WHERE username = ? AND password=?";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;	// PreparedStatement를 사용하는 이유: injection공격을 자동으로 막아준다.
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery(); 
			// select 하는 것이므로 executeQuery()를 사용하여 resultSet을 반환받는다.
			
			// 세션, 내가넣은 id와 패스워드가 정상적이면 유저오브젝트를 만들어 리턴해줄것이다.
			if(rs.next()) {
				// 있으면 rs에 담긴값을 유저객체에 넣어서 유저객체를 return해주면 된다. -> 스프링에선 Persistence API가 알아서 해줌
				// 빌더를 사용해 객체 생성 (new User로 만들어줘도 됨)
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.address(rs.getString("address"))
						.build();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally { //무조건 실행
			
			DB.close(conn, pstmt, rs);
			//resultSet도 있으면 resultSet도 닫아줘야 한다.
			//가비지 컬렉션에 걸리겠지만 즉각적으로 일어나지 않으므로, 최대한 즉각적으로 닫아준다. -> db연결을 최소화시키기 위해
		}
		return null;
	}
	
	
	public int findByUsername(String username) { // 아이디가 있는지만 보는것. 아이디 중복체크기능이 아니라 그 한 부분
		String sql = "SELECT * FROM user WHERE username = ?";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery(); 
			// select 하는 것이므로 executeQuery()를 사용하여 resultSet을 반환받는다.
			
			if(rs.next()) {
				return 1; //유저네임이 이미 있어.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally { //무조건 실행
			
			DB.close(conn, pstmt, rs);
			//resultSet도 있으면 resultSet도 닫아줘야 한다.
			//가비지 컬렉션에 걸리겠지만 즉각적으로 일어나지 않으므로, 최대한 즉각적으로 닫아준다. -> db연결을 최소화시키기 위해
		}
		
		return -1; // 유저네임 없어
	}
	
	
	public int save(JoinReqDto dto) { // 회원 가입
		String sql = "INSERT INTO user(username, password, email, address, userRole, createDate) VALUES (?,?,?,?,'USER',now())";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getAddress());
			int result = pstmt.executeUpdate(); // executeUpdate() 가 성공하면 1, 실패하면 0 또는 -1
			// select 하는 게 아니므로 executeUpdate()이다. executeQuery()는 resultSet을 반환한다.
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally { //무조건 실행
			
			DB.close(conn, pstmt);
			//resultSet도 있으면 resultSet도 닫아줘야 한다.
			//가비지 컬렉션에 걸리겠지만 즉각적으로 일어나지 않으므로, 최대한 즉각적으로 닫아준다. -> db연결을 최소화시키기 위해
		}
		
		return -1;
	}
	
	public void update() { // 회원 수정
		
	}
	
	public void usernameCheck() { // 아이디 중복 체크
		
	}
	
	public void findById() { //회원 정보 보기
		
	}
}
