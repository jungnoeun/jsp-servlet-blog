package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.UserDao;

public class BoardService {

	private BoardDao boardDao;
	
	public BoardService() {
		boardDao = new BoardDao();
	}
	
	public int 글삭제(int id) {
		return boardDao.deleteById(id);
	}
	
	
	
	// 하나의 서비스안에 여러가지 DB관련 로직이 있음 => 이것이 서비스다!!!
	public DetailRespDto 글상세보기(int id) {
		// 글 상세보기에서 두가지 일이 일어남- 1. DB에서 데이터를 한건 가져오고, 2. 들고올 데이터의 조회수를 1 증가시킨다.
		int result = boardDao.updateReadCount(id);
		if(result == 1) { // 조회수가 정상적으로 증가하면
			return boardDao.findById(id);
		} else {
			return null;
		}
		
	}
	
	public int 글개수() {
		return boardDao.count();
	}
	
	public int 글쓰기(SaveReqDto dto) {
		
		return boardDao.save(dto);
	}
	
	public List<Board> 글목록보기(int page) {
		return boardDao.findAll(page);
	}
	
}
