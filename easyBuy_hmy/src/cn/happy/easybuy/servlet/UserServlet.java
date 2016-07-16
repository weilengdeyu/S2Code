package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.User;
import cn.happy.easybuy.until.UserPage;


public class UserServlet extends HttpServlet {
	UserDaoImpl user = new UserDaoImpl();
	/**
	李晓鹏
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
		
	}

	/**
	李晓鹏
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if (oper.equals("list")) {
			selectUser(request,response);
		}else if(oper.equals("modify")){
			modifyUser(request,response);
		}else if(oper.equals("del")){
			delUserInfo(request,response);
		}else if (oper.equals("modifytrue")){
			modifyUserInfo(request,response);
		}
		
	}
/**
 * 实现查询所有用户信息的方法
 */
	public void selectUser(HttpServletRequest request, HttpServletResponse response){

		UserPage cpage1 =new UserPage();
		cpage1.setPageSize(5);
	      try {
	    	//返回总记录数   	  
	    	  cpage1.setPageSum(user.getUserCount());     	
	    	  cpage1.setPageCount((int)Math.ceil(cpage1.getPageSum()*1.0/cpage1.getPageSize()));
	      	//当用户没有点击下一页时默认显示第一页数据
	      	int pageIndex=1;
	      	String uindex=request.getParameter("pageIndex");
	      	if (uindex!=null&&!uindex.equals("")) {
	  			pageIndex=Integer.parseInt(uindex);
	  		   //给总页数赋值
		      	if(pageIndex<1){
		      		pageIndex=1;
		      	}
		      	if(pageIndex>cpage1.getPageCount()){
		      		pageIndex=cpage1.getPageCount();
		      		
		      	}
	  		}
	      	
	      	
	      	cpage1.setPageIndex(pageIndex);
	      	//给集合赋值
	      	cpage1.setList(user.getUserInfo(cpage1.getPageIndex(), cpage1.getPageSize()));
	      	//将page对象放入作用域中
	      	request.setAttribute("page", cpage1);
	      	request.getRequestDispatcher("/manage/user.jsp").forward(request, response);
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

	}
	/**
	 *实现修改用户页面根据ID获取到用户的信息的方法
	 */
	public void modifyUser(HttpServletRequest request,HttpServletResponse response){
		String uid = request.getParameter("uid");
		try {
			uid=new String(uid.getBytes("iso-8859-1"),"utf-8");
			User us = user.selectByName(uid);
			request.setAttribute("us", us);
			request.getRequestDispatcher("/manage/user-modify.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实现用户真实修改
	 */
	public void modifyUserInfo(HttpServletRequest request,HttpServletResponse response){
		String uid = request.getParameter("userName");
		String name = request.getParameter("name");
		String passWord=request.getParameter("passWord");
		String sex=request.getParameter("sex");
		String birthday=request.getParameter("birthday");
		String mobile=request.getParameter("mobile");
		String address=request.getParameter("address");
		User ur = new User();
		ur.setId(uid);
		ur.setName(name);
		ur.setPassword(passWord);
		ur.setSex(sex);
		ur.setBirthday(birthday);
		ur.setMobile(mobile);
		ur.setAddress(address);
		int count =0;
		try {
			count=user.modifyUser(ur);
			if (count>0) {
				request.getRequestDispatcher("/servlet/UserServlet?oper=list").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 实现删除用户的方法
	 */
	public void delUserInfo(HttpServletRequest request,HttpServletResponse response){
		String uid = request.getParameter("uid");
		int count =0;
		try {
			count=user.removeUser(uid);
			System.out.println(count);
			if (count>0) {
				request.getRequestDispatcher("/servlet/UserServlet?oper=list").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
