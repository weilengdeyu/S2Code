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
		岁月静好 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
           String oper=request.getParameter("oper");
           ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
           List<ProductCategory> childlist=null;
           if(oper.equals("select")){//根据一级标签查询相应的二级标签
        	   String pid=request.getParameter("num");
        	   if(pid==null){//悬浮的是首页
        		   try {
					childlist=pcd.getAllChildCate();
				} catch (Exception e) {
					e.printStackTrace();
				}  
        	}else{//对应的父级标签分类
        		try {
        			childlist=pcd.getChildCateByPid(Integer.parseInt(pid));
				} catch (Exception e) {
					e.printStackTrace();
				}
        	} 
        	 
        	   //将该一级分类对应的二级分类放入作用域
        	   request.setAttribute("childlist",childlist);
        	   //将该一级分类对应的所有的产品分页展示
        	   
        	   //转发
        	   request.getRequestDispatcher("/servlet/doIndexServlet").forward(request, response);
        	   
           }
	}

}
