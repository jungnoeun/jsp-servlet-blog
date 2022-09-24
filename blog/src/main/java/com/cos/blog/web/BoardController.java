package com.cos.blog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.DeleteReqDto;
import com.cos.blog.domain.board.dto.DeleteRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

// 접근되는 주소: http://localhost:8080/blog/board
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BoardController() {
        super();
    }

	// GET/POST 요청을 하면 doProcess 실행
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// saveForm을 요청하는 라우터를 생성
		// contextPath = 내 프로젝트 명
		 String cmd = request.getParameter("cmd");
		BoardService boardService = new BoardService();
			 
		// http://localhost:8080/blog/board?cmd=saveForm
		HttpSession session = request.getSession();
		if(cmd.equals("saveForm")) {
			// 바로 redirect가 안되고 로그인 인증이 필요함 
			User principal = (User)session.getAttribute("principal"); // 로그인하면 세션에 principal로 유저정보가 담겨있음
			if(principal != null) {
//				response.sendRedirect("board/saveForm.jsp");
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
		    	dis.forward(request, response);
			} else {
//				response.sendRedirect("user/loginForm.jsp");
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
			}
		} else if(cmd.equals("save")) {
			// session으로 부터 userId 받기
//			User principal = (User) session.getAttribute("principal");
//			int userId = principal.getId();
			
			// 사용자로부터 userId 받기 - saveForm의 hidden값으로 받아옴 - hidden값의 좋은점: 요청을 한 주체가 누군지 알게 된다.
			int userId = Integer.parseInt(request.getParameter("userId"));
			
			String title = request.getParameter("title");
			String content = request.getParameter("content"); // html태그가 포함된 내용이 content에 저장된다. - 이걸 디비에 넣어놨다 다시 꺼내면 된다.
			
			//System.out.println("content" + content);	// summernote가 html 데이터로 만들어준다.
			
			// 객체생성하고 그안에 값 넣기 -> 빌더 사용해도 됨 
			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			
			// database에 넣어준다.
			int result = boardService.글쓰기(dto);
			if(result == 1) { // 정상(글쓰기 성공)
				response.sendRedirect("index.jsp");
			} else {
				Script.back(response, "글쓰기 실패");
			}
		}else if(cmd.equals("list")) {
			// 목록보기: 파라미터로 받을 거 없음 > 그냥 데이터베이스에서 정보들을 가져오면 됨
			// 세션 체크할 것도 없음
			int page = Integer.parseInt(request.getParameter("page"));
			List<Board> boards =  boardService.글목록보기(page);
			request.setAttribute("boards", boards);
			
			// 계산(전체 데이터 수랑 한페이지에 몇개인지 -> 총 몇 페이지 나와야 하는지 계산) 3page라면 max값은 2
			// page == 2가 되는 순간 isEnd = true
			// request.setAttribute("isEnd", true);
			int boardCount = boardService.글개수();
			int lastPage = (boardCount-1)/4;
			double currentPosition = (double)page/(lastPage)*100; // 현재 페이지 위치를 막대바로 표시
			
			request.setAttribute("lastPage", lastPage); // 이거 안하면 jsp페이지로 lastPage값이 안넘어감
			request.setAttribute("currentPosition",currentPosition);
			
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
		} else if(cmd.equals("detail")) {
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id); // board테이블 + user테이블 = 조인된 데이터 필요!!
			
			if(dto == null) { // 조회수 증가가 실패 or 상세보기 실패 dto=null
				Script.back(response, "상세보기에 실패하였습니다");
			} else {
				request.setAttribute("dto", dto);
//				System.out.println("DetailRespDto " + dto);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}
			
		} else if(cmd.equals("delete")) {
			// 주는 데이터가 request.Parameter()가 아니라 바디 데이터이다. 그래서 버퍼로 읽어야 한다.
			// 1. 요청받은 json데이터를 자바 오브젝트로 파싱
			BufferedReader br = request.getReader();
			String data = br.readLine();
			// data에는 {"boardId" : 16} 과 같은 키&값 이 들어온다.(json값) -> 이 값을 받기 위한 dto(DeleteReqDto)가 필요하다.
			// Gson: java object를 json으로 json을 java object로 바꿔줌
			Gson gson = new Gson();
			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class); // java object로 파싱함
			
			System.out.println("data "+ data);
			System.out.println("dto "+dto);
			
			// 2. DB에서 id값으로 글 삭제
			int result = boardService.글삭제(dto.getBoardId());
			
			// 3. 응답할 json데이터를 생성 (자바오브젝트로 일단 만들고(DeleteRespDto), gson.toJson()으로 json화 시킨것이다)
			DeleteRespDto respDto = new DeleteRespDto();
			if(result == 1) {
				respDto.setStatus("ok");
			} else {
				respDto.setStatus("fail");
			}
			
			String respData = gson.toJson(respDto); // json으로 변환
			
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
			
			// 사실 1,3번은 리플렉션으로 생략가능하다.
		}
		
	}
}
