package com.xinlab.blueapple.contenttool.cfilter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VideoRank extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		StringBuffer url = request.getRequestURL();
		String sessionid = request.getRequestedSessionId();
		System.out.println("uri:"+uri);
		System.out.println("url:"+url.toString());
		System.out.println("servletpath:"+request.getServletPath());
		System.out.println("ServletName:"+request.getServerName());
		System.out.println("QueryString:"+request.getQueryString());
		System.out.println(sessionid);
		System.out.println(request.getParameter("rank"));
		System.out.println(request.getParameter("textarea"));
		System.out.println(request.getHeader("referer"));
		System.out.println(request.getHeader("accept"));
		System.out.println(request.getCharacterEncoding());
		request.getRequestDispatcher("/cfilter/videorank.jsp").forward(request, response);
	}

}
