package com.cos.blog.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//web.xml에 서블릿 맵핑을 따로 안해도 됨 -> 이미 필터가 @WebServlet 이라는 어노테이션 분석해서  서블릿 맵핑을 해주었을 것 = 리플렉션
//localhost:8080/blog/test 로 들어오면 여기로 들어와짐 (Get, Post)
@WebServlet("/test")
public class ApiServerTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ApiServerTest() {
        super();
    }

	//Get방식으로 들어오면 이 함수 동작 -> 함수의 파라미터를 분석해서 톰캣이 request,response객체를 주입해준 것이다. 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response); //Get방식도 되게 하려면
	}

	//Post방식으로 들어오면 이 함수 동작	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getContentType(); // 할 필요가 없음 -> 왜? 이미  x-www-form-unlencoded로 보내라고 알려줬음
		
		
//		String mime = request.getContentType();
//		System.out.println(mime);
//		
//		request.setCharacterEncoding("UTF-8"); //이부분은 나중에 필터에 걸면 된다.
//		
//		// 이렇게 Content-Type을 분리해놓는 경우는 없음 -> Api문서에 요청할때 json으로 보내라고 적어놔야 한다. -> 그래서 Api문서가 필요하다.
//		if(mime.equals("application/json")) {
//			BufferedReader br = request.getReader();
//			String input;
//			StringBuffer buffer = new StringBuffer();
//			while((input = br.readLine()) != null) {
//				buffer.append(input);
//			}
//			System.out.println(buffer.toString());
//		}else { // x-www-form-unlencoded 로 받음
//			String food = request.getParameter("food");
//			String method = request.getParameter("method");	
//			System.out.println(food);
//			System.out.println(method);
//		}
	
		
		
		//DB에 insert하고 끝
		
		
		//int result = 1; //정상
//		PrintWriter out = response.getWriter();
//		out.println(1);
//		out.flush();
		
		//브라우저에게 Content-Type을 알려줌
		response.setContentType("application/json; charset=utf-8");
		//response.setContentType("text/html; charset=utf-8");
		//response.sendRedirect("index.jsp");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>안녕</h1>");
		out.println("</body>");
		out.println("<html>");
		out.flush();
		// 1) text/plain 으로 응답 
//		out.println(result);
//		out.flush();
		
		// 2) application/json 으로 응답
//		if(result ==1 ) {
//			out.println("{\"food\": "+food+",\"method\": "+method+"}");
//		}else {
//			out.println("{\"error\":\"fail\"}");
//		}
		
	}

}
