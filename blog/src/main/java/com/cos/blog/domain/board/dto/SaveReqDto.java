package com.cos.blog.domain.board.dto;

import lombok.Data;

// @Data는 getter&setter 생성
@Data
public class SaveReqDto {
	private int userId;
	private String title;
	private String content;
}
