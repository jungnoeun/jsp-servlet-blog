package com.cos.blog.domain.board.dto;

import lombok.Data;

@Data
public class DetailRespDto {
	private int id;
	private String title;
	private String content;
	private int readCount;
	private String username;
	private int userId;
	
	// 제목에 스크립트 코드 들어올때 에러발생하는 문제 막기
	public String getTitle() {
		// DB에는 <나 >가 들어가도 화면에는 각각 &lt;, &gt; 로 뜰 것이다.
		return title.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
}
