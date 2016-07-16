package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public class doProductCategoryServlet extends HttpServlet {

	/**
		���¾��� 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 ���¾���
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
           String oper=request.getParameter("oper");
           ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
           List<ProductCategory> childlist=null;
           if(oper.equals("select")){//����һ����ǩ��ѯ��Ӧ�Ķ�����ǩ
        	   String pid=request.getParameter("num");
        	   if(pid==null){//����������ҳ
        		   try {
					childlist=pcd.getAllChildCate();
				} catch (Exception e) {
					e.printStackTrace();
				}  
        	}else{//��Ӧ�ĸ�����ǩ����
        		try {
        			childlist=pcd.getChildCateByPid(Integer.parseInt(pid));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	} 
        	 
        	   //����һ�������Ӧ�Ķ����������������
        	   request.setAttribute("childlist",childlist);
        	   //����һ�������Ӧ�����еĲ�Ʒ��ҳչʾ
        	   
        	   //ת��
        	   request.getRequestDispatcher("/servlet/doIndexServlet").forward(request, response);
        	   
           }
	}

}
