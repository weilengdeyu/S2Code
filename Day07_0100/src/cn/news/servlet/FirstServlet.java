package cn.news.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ��ͨ��      servelt  
 * @author happy
 *
 */
public class FirstServlet implements Servlet{

	//���ٵķ���   1��
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

	//��ʼ��  1��  
	@Override
	public void init(ServletConfig config) throws ServletException {
		String value = config.getInitParameter("happy");
		System.out.println("====================init=========");
		System.out.println(value);
	}
	
	//����ķ�����client����һ������ִ��һ�Ρ���������

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRemoteHost()+"====================service=========");
		
	}

}
