<%@page import="cn.happy.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	
  </head>
  
  <body>
  
     <%
       request.setAttribute("uname", "happy1");
      %>
      <%
       session.setAttribute("uname", "happy2");
      %>
       <%
       pageContext.setAttribute("uname", "happy0");
      %>
      <!-- pageContext,request, session  ,appliction  -->
       <!-- 腾海：  -->
      取值：${uname}
      
      ----------------el用法还有四钟作用域 ${域中的key} 查找次序是从小范围依次查找，如果小范围已经存在同名key值，那么就直接作为最后的结果------
      
      <!-- 02.使用对象获取值   page   request   session  application 黄金法则:命名规范,一直用.当点取不到的时候用[] -->
    <%
      User user=new User();
      user.setName("微冷的雨");
      request.setAttribute("user2", user);
    %>
    <!-- 作用域  getName()         -->
    ${user2.name }
     <hr color="red"/>
    
<!-- 03.使用List<T>获取值   page   request   session  application 黄金法则:命名规范,一直用.当点娶不到的时候用[] -->
<%
   
     List<User> list=new ArrayList<User>();

     User user1=new User();
     user1.setName("乐乐好人");
     
     User user2=new User();
     user2.setName("凯凯好人");
     
     list.add(user1);
     list.add(user2);
     
     //设置到作用域
     request.setAttribute("list",list);
   %>
   
   ${list[0].name}
       <hr color="red"/>
<br/><br/>
*********************************************
 
   <br/><br/><br/>


<!-- 04.使用Map获取值   page   request   session  application 黄金法则:命名规范,一直用.当点娶不到的时候用[] -->
  <%
  
    Map<String,User> map=new HashMap<String,User>();
  
	  User user3=new User();
	  user3.setName("贺斌");
	  user3.setAge(22);
	  
	  User user4=new User();
	  user4.setName("小红");
	  user4.setAge(22);
	  
	  map.put(user3.getName(), user3);
	  map.put(user4.getName(),user4);
	  
	  request.setAttribute("map", map);
	  
  %>
  <br/>
  <br/>
  ---------------------------------------
   ${map["小红"].age} <br/>
   ------------------------------------
   
        <hr color="red"/>
        
   ${pageContext.request.contextPath }
   
   
  


   <br/>
   <%
		Map<Map<User, User>, Map<User, User>> map2 = new HashMap<Map<Usez\]r, User>, Map<User, User>>();
		User uKey = new User();
		uKey.setName("Ukey1");
		uKey.setAge(2);

		User uValue = new User();
		uValue.setName("Uvalue1");
		uValue.setAge(1);

		User uKey2 = new User();
		uKey2.setName("Ukey2");
		uKey2.setAge(22);

		User uValue2 = new User();
		uValue2.setName("Uvalue2");
		uValue2.setAge(11);

		Map<User, User> mapKey = new HashMap<User, User>();
		Map<User, User> mapValue = new HashMap<User, User>();

		mapKey.put(uKey, uValue);
		mapValue.put(uKey2, uValue2);

		map2.put(mapKey, mapValue);
       
		request.setAttribute("MMap", map2);
		
	%>
	
	
	<div style="border:1px solid red;">
	<c:forEach var="mine" items="${MMap }">
	   <c:forEach var="child" items=" ${mine.key}">
	      <c:out value=" ${child}"></c:out>
	   </c:forEach>
	</c:forEach>
	</div>
	
	<!-- 接下来EL的 关系云算符的用法 -->
	******************************************
	${2 == 2 }  比较
	       <hr color="red"/>
	${1 lt 2 }  比较
	<!-- 接下来EL的 Empty操作符的用法 -->
	
	******************************************
	
	*****************************************
	<br/>
	
	*****************************************
	<br/>

	<!-- 接下来EL的 逻辑云算符的用法 -->
	******************************************
	${1==1 && 2==12 }逻辑运算符
	<hr color="red"/> 
	${ empty usertest }<br/>
	*****************************************
	${pageContext.request.contextPath}
	<%=path %>
	<hr/>
   
  </body>
</html>






















