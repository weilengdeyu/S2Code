package cn.news.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecondServlet extends GenericServlet{
	
	//ֻ��עһ������
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		 System.out.println("service");
	}

}
