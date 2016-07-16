<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易买网 - 首页</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/function.js"></script>
<script type="text/javascript">
$(function(){
 
 
});
//用Ajax验证是否登录成功
function isLoginOrNot(){
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
      var code=$("#code").val();
      var password=$("#password").val();
      var userId=$("#userId").val();
      $.ajax({
    	       url:'<%=path %>/servlet/doLoginServlet?code='+code+'&password='+password+'&userId='+userId,
    	       type:'post',
    	       //回调函数 data代表server回送的数据
    	       success:function(data){
    	    	   if(data=="验证码错误！请重新输入！"){
    	    	    $("#code").next().html(data).addClass("error");
    	    	   
    	    	   }else if(data=="该用户名不存在！"){
    	    	     $("#userId").next().html(data).addClass("error");
    	    	   }else if(data=="密码输入错误！"){
    	    	   $("#password").next().html(data).addClass("error");
    	    	   }else {
    	    	        parent.layer.close(index);
    	    	        //location.href="<%=path %>/servlet/doIndexServlet?loginname="+data;
    	    	   }
    	       }
    	    });
}


</script>
</head>
<body>

<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎回到易买网</h1>
			<form id="loginForm" method="post" action="#" >
				<table>
					<tr>
						<td class="field">用户名：</td>
						<td>
                            <input class="text" type="text" id="userId" name="userId" />
							<span></span>
						</td>
					</tr>
					<tr>
						<td class="field">登录密码：</td>
						<td>
                            <input class="text" type="password" id="password" name="password" />
							<span></span>
						</td>
					</tr>
					<tr>
					<td class="field">验证码：</td>
						<td>
                            <img src="<%=path %>/servlet/getCodeServlet" id="safeCode"/><a id="changeCode" href="#">看不清，换一张</a><br>
	                        <input type="text" name="code" id="code">
							<span></span>
						</td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="button" onclick="isLoginOrNot()" name="submit" id="login" value="立即登录" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>

</body>
</html>
