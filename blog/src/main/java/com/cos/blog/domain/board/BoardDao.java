package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.dto.JoinReqDto;

public class BoardDao {
	
	
	public int deleteById(int id) { // 글삭제
		String sql = "DELETE FROM board WHERE id = ?";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
		
			int result = pstmt.executeUpdate(); // executeUpdate()는 수정된 행의 개수를 리턴한다.
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


	public int updateReadCount(int id) { // 조회수 증가
		String sql = "UPDATE board SET readCount = readCount+1 WHERE id = ?";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
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
	
	
	public DetailRespDto findById(int id) {
		// 빌더를 사용- StringBuffer
		StringBuffer sb = new StringBuffer();
		sb.append("select b.id, b.title, b.content, b.readCount, b.userId, u.username "); //뒤에 반드시 한칸 띄어야 한다. 안그러면 다음문장이랑 붙어서 인식함
		sb.append("from board b inner join user u ");
		sb.append("on b.userId = u.id ");
		sb.append("where b.id = ?");
		
		String sql = sb.toString();
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;	// PreparedStatement를 사용하는 이유: injection공격을 자동으로 막아준다.
		ResultSet rs = null;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id); 
			rs = pstmt.executeQuery(); 
			// select 하는 것이므로 executeQuery()를 사용하여 resultSet을 반환받는다.
			
			// Persistence API 
			if(rs.next()) { // 커서를 이동하는 함수
				DetailRespDto dto = new DetailRespDto();
				dto.setId(rs.getInt("b.id"));
				dto.setTitle(rs.getString("b.title"));
				dto.setContent(rs.getString("b.content"));
				dto.setReadCount(rs.getInt("b.readCount"));
				dto.setUserId(rs.getInt("b.userId"));
				dto.setUsername(rs.getString("u.username"));
				return dto;
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
	
	
	public int count() {
		
		String sql = "SELECT count(*) FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); 
			// select 하는 것이므로 executeQuery()를 사용하여 resultSet을 반환받는다.
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally { //무조건 실행
			DB.close(conn, pstmt, rs);
		}
		
		return -1;
	}
	
	
	public List<Board> findAll(int page) {
		// 필요한건 title과 글의 id이지만 칼럼 몇개 더 가져와도 속도차이가 없다.
		String sql = "SELECT * FROM board ORDER BY id DESC LIMIT ?,4";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;	// PreparedStatement를 사용하는 이유: injection공격을 자동으로 막아준다.
		ResultSet rs = null;
		List<Board> boards = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page*4); // page: 0->0, 1->4, 2->8
			rs = pstmt.executeQuery(); 
			// select 하는 것이므로 executeQuery()를 사용하여 resultSet을 반환받는다.
			
			// Persistence API 
			while(rs.next()) { // 커서를 이동하는 함수
				Board board = Board.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.userId(rs.getInt("userId"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
			e.printStackTrace();
		}finally { //무조건 실행
			
			DB.close(conn, pstmt, rs);
			//resultSet도 있으면 resultSet도 닫아줘야 한다.
			//가비지 컬렉션에 걸리겠지만 즉각적으로 일어나지 않으므로, 최대한 즉각적으로 닫아준다. -> db연결을 최소화시키기 위해
		}
		return null;
	}
	
	
	public int save(SaveReqDto dto) { // 글쓰기
		String sql = "INSERT INTO board(userId, title, content, createDate) VALUES (?,?,?,now())";
		// DB 연결
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
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
	
	
	
}
