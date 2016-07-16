package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.entity.User;

public class doRegisterServlet extends HttpServlet {

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
            request.setCharacterEncoding("utf-8");
            ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
            UserDaoImpl UserDaoImpl=new UserDaoImpl();
            String oper=request.getParameter("oper");
            //二级分类
            List<ProductCategory> childlist=null;
            //一级分类
            List<ProductCategory> parentlist=null;
            try{
	 			    childlist=pcd.getAllChildCate();
	 				request.setAttribute("childlist", childlist);
	 				parentlist = pcd.getAllParentCate();
	 				request.setAttribute("parentlist", parentlist); 
	 				request.setAttribute("childlistonly", childlist);
	 		    }catch(Exception e) {
	 				e.printStackTrace();
	 		    }
 		    if(oper.equals("list")){//首次进入注册页面
 		    	request.getRequestDispatcher("/register.jsp").forward(request, response);
 		    }else if(oper.equals("checkname")){//使用ajax判断用户是否注册
            	String name=request.getParameter("name");
                boolean flag=true;//初始进入为可注册
                try {
					List<User> list=UserDaoImpl.getAllUserInfo();
					for (User user : list) {//判断接受的uname中是否与数据库中的相同
						if(name.equals(user.getId())){
							flag=false;//标记为不可注册
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
            	response.getWriter().print(flag);
            }else if(oper.equals("add")){//进行注册操作
            	User user=new User();
            	user.setAddress(request.getParameter("address"));
            	user.setBirthday(request.getParameter("birthday"));
            	user.setEmail(request.getParameter("email"));
            	user.setId(request.getParameter("userId"));
            	user.setIdentityCode(request.getParameter("identityCode"));
            	user.setLogin("0");
            	user.setMobile(request.getParameter("mobile"));
            	user.setName(request.getParameter("userName"));
            	user.setPassword(request.getParameter("password"));
            	user.setSex(request.getParameter("sex"));
            	user.setStatus(1);
            	try {
					boolean flag=UserDaoImpl.add(user);
                    if(flag){
                    	System.out.println("注册成功！");
                    	request.getRequestDispatcher("/reg-result.jsp").forward(request, response);
                    }else{
                    	System.out.println("注册失败！");
                    	request.getRequestDispatcher("/servlet/doRegisterServlet?oper=list").forward(request, response);
                    }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
 		    
 		    
 		    
 		    
				
		
	}

}
