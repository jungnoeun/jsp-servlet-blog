package com.cos.blog.web;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.service.UserService;
import com.cos.blog.util.Script;

// 접근되는 주소: http://localhost:8080/blog/user
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public UserController() {
        super();
    }

	// GET/POST 요청을 하면 doProcess 실행
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	// http://localhost:8080/blog/user?cmd = 머시기
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String cmd = request.getParameter("cmd");
		UserService userService = new UserService();
		 
		// http://localhost:8080/blog/user?cmd=loginForm
		if(cmd.equals("loginForm")) {
			//서비스 호출 필요x , 그냥 로그린 폼만 주면 된다. - sendRedirect() 실행만 하면 됨
			// 아이디 기억 서비스 추가? -> 로직 추가  -> 서비스 필요 
			// 필터 사용으로 sendRedirect() 사용 불가 -> RequestDispatcher로 대체
			// request.sendRedirect("user/loginForm.jsp")
			RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
	    	dis.forward(request, response);
			
		}else if(cmd.equals("login")) {
			// 서비스 호출
			// 과정 : 데이터 받아서 서비스 호출하는 로직
			//리플랙션 방식으로 하면 dto만들어서 들고 있음. 자동으로 되어 편리함
			// x-www-form-urlencoded 데이터 타입이므로 request.getParameter(".."); 으로 값을 받는다.
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			User userEntity = userService.로그인(dto); // 보통 db로부터 받은 데이터는 뒤에 Entity라고 붙여준다.
			if(userEntity != null) { // 정상실행하면 세션에 담아준다
				HttpSession session = request.getSession();	// 세션은 어디에서든지(모든페이지에서) 접근가능하다. request&response가 사라져도 세션은 남아있기 때문
				session.setAttribute("principal", userEntity); // 인증주체-principal, 로그인된 값을 세션에 principal로 저장함
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "로그인실패"); // response는 PrintWrite해서 응답해주는 것이다.
			}
			// userService에 request를 넘기면 안되나?
			// 답: 서비스는 요청오는 데이터 자체를 처리하는 것이 아니라 그런거는 미리하고 서비스에서는 받은 데이터를 통해 데이터베이스에 연결하는것이다.
			// 가공하는 과정은 서비스가 아닌 컨트롤러에서 하는 것이다.
			// 가공하는 과정은 리플랙션에서 필터링해서 처리하면 아주 간결해진다. > 나중에 해보기 (더 좋은 방법)
		}else if(cmd.equals("joinForm")) {
			// request.sendRedirect("user/joinForm.jsp");
			// 필터를 다시 안타게 한다. 
			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);

		}else if(cmd.equals("join")) {
			// 서비스 호출
			// 과정 : 데이터 받아서 서비스 호출하는 로직
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			dto.setAddress(address);
			System.out.println("회원가입"+dto);
			int result = userService.회원가입(dto);
			if(result == 1) {
				response.sendRedirect("index.jsp"); // 메인화면으로 가게 함
			}else {
				Script.back(response,"회원가입 실패");
			}
		}else if(cmd.equals("usernameCheck")) { // url: "/blog/user?cmd=usernameCheck" 에 의해 연결됨. 
			//request.getParameter("username"); // 전달되는 데이터가 key&value값이 아닌 text라서 이렇게 받을 수 없음
			// 버퍼로 읽으면 된다. 데이터가 username하나라서 while을 적을 필요가 없다
			BufferedReader br = request.getReader();
			String username = br.readLine();
			System.out.println(username);
			int result = userService.유저네임중복체크(username);
			PrintWriter out = response.getWriter();
			if(result == 1) {
				out.print("ok"); // 유저네임이 있다.
			}else {
				out.print("fail");
			}
			out.flush();
		} else if(cmd.equals("logout")) {
			// 세션을 무효화시키기
			HttpSession session = request.getSession();
			session.invalidate();
			// 그리고 나서 index.jsp페이지로 이동 - index.jsp 페이지로 가는것은 예외로 두었기 때문에 sendRedirect()를 사용해도 상관없다.
			response.sendRedirect("index.jsp");
		}
		
	 }
}
