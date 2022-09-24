package com.cos.blog.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharConfig implements Filter{
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding("utf-8"); //들어오는 문자 세팅 (utf-8로) 요청의 경우, request.getParameter("-")로 한번 파싱하면 다시 다른거로 setCharacterEncoding("-")해도 적용안된다. 이미 파싱을 했기 때문
		response.setContentType("text/html; charset=utf-8"); //응답의 경우, 밑에 다른거로 하면 바뀜
		
//		//filter가 잘 작동되는지(한글이 안깨지고 나오는지 확인하는 로그)
//		String username = request.getParameter("username");
//		System.out.println("username: "+username);
//		
//		//응답 확인(필터 안타게 해야 해서 chain.doFilter(request, response); 를 주석처리한다.)
//		PrintWriter out = response.getWriter();
//		out.println("안녕"); // 이 글자가 안깨지고 나오면 성공이다. 응답이 text/html로 설정했으니 "안녕"자체를 html로 인식한다. charset=utf-8로 안하면 깨질 수 있다.
//		out.flush();
		
		//다시 필터를 타게 한다.
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
