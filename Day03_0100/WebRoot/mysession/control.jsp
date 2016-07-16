<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

     <%
      //0.解决乱码
      request.setCharacterEncoding("utf-8");
      
     
      //1.获取信息
      String name=request.getParameter("txtName");
      String pwd=request.getParameter("txtPwd");
      
      if(name.equals("1")&&pwd.equals("1")){
    	   //2.保存到session
          session.setAttribute("user", name);
          //3.跳转到success.jsp
          //需要项目名称
          response.sendRedirect(path+"/mysession/success.jsp");
          
      }else{
    	  //login.jsp
    	  response.sendRedirect(path+"/mysession/login.jsp");
      }
      
   
      
     
     
     %>

