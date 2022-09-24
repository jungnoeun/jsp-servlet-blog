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

// 이제부터 내부에서의 모든 요청은 RequestDispatcher로 해야한다.
// 그래야 다시 필터를 타지 않는다.

public class ForbiddenUrlConfig implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		System.out.println("ForbiddenUrlConfig 접근");
		System.out.println(request.getRequestURL()); // 전체경로
		System.out.println(request.getRequestURI()); // context이하 주소
		
		// 직접적인 URL접근은 index.jsp만 가능하게 해줌.
		if(request.getRequestURI().equals("/blog/") || request.getRequestURI().equals("/blog/index.jsp")) {
			chain.doFilter(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("잘못된 접근입니다.");
			out.flush();
		}
		
	}
	
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	
	
}
