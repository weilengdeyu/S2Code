package cn.happy.easybuy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class doLoginOut extends HttpServlet {

	/**
		 出品人：岁月静好
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 出品人：岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//ִ��ע������
		//������ﳵ�е���Ʒ
		request.getSession().removeAttribute("shoppingCart");
		request.getSession().removeAttribute("sum");
		//����û��ĵ�¼��Ϣ
		request.getSession().removeAttribute("loginname");
		//ת����index.jspҳ��
		request.getRequestDispatcher("/servlet/doIndexServlet").forward(request, response);
	}

}
