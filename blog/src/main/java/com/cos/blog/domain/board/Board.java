package com.cos.blog.domain.board;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	private int id;
	private int userId;
	private String title;
	private String content;
	private int readCount; // 조회수 , 디폴트값:0
	private Timestamp createDate; // 작성한 날짜
	
	// 제목에 스크립트 코드 들어올때 에러발생하는 문제 막기
	public String getTitle() {
		// DB에는 <나 >가 들어가도 화면에는 각각 &lt;, &gt; 로 뜰 것이다.
		return title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
}
