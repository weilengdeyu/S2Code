package cn.news.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdServlet extends HttpServlet {

	/**
		  author:Happy
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		
		String value = getInitParameter("happy");
		
		//获取到servletContext   : 
		
		getServletContext().getServlet("").getServletConfig();
		
	}

	/**
		  出品人：微冷的雨
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			System.out.println("666");
	}

}
