package cn.news.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 普通类      servelt  
 * @author happy
 *
 */
public class FirstServlet implements Servlet{

	//销毁的方法   1次
	@Override
	public void destroy() {
		System.out.println("====================destory=========");
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	//初始化  1次  
	@Override
	public void init(ServletConfig config) throws ServletException {
		String value = config.getInitParameter("happy");
		System.out.println("====================init=========");
		System.out.println(value);
	}
	
	//特殊的方法，client过来一次请求，执行一次。处理请求

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRemoteHost()+"====================service=========");
		
	}

}
