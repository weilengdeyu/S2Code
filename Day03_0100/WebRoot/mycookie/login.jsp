<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>222</title>

  </head>
  
  <body>
   <%
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals("cookieName")){
					response.sendRedirect(path+"/mycookie/welcome.jsp");
				}
			}
		}
   %>
   abcxxxx、
  <%=path %>
     <form action="<%=path %>/mycookie/do.jsp" method="post" >
		       用户名: <input type="text" name="txtName"/>
		      密码：    <input type="password" name="txtPwd"/>
		      <input type="submit" value="Login"/>
      </form>
  </body>
</html>
