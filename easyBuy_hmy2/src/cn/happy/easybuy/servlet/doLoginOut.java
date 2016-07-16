package cn.happy.easybuy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class doLoginOut extends HttpServlet {

	/**
		 鍑哄搧浜猴細宀佹湀闈欏ソ
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 鍑哄搧浜猴細宀佹湀闈欏ソ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//执行注销操作
		//清除购物车中的商品
		request.getSession().removeAttribute("shoppingCart");
		request.getSession().removeAttribute("sum");
		//清除用户的登录信息
		request.getSession().removeAttribute("loginname");
		//转发到index.jsp页面
		request.getRequestDispatcher("/servlet/doIndexServlet").forward(request, response);
	}

}
