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
<style type="text/css">
#pwdpower {
width:195px;
border:0px none;
background-color:#f1f1f1
}
#pwdpower td {
text-align:center;
vertical-align:middle;
padding:1px;
color:#adadac;
}
#pwdpower #pweak{
border-right:1px solid #dedede;
}
#pwdpower #pmedium{
border-right:1px solid #dedede;
width: 50px;
}
#pwdpower #pstrong{

}
</style>
<script type="text/javascript" src="http://www.wosowoso.com/google/b2/jpg/checkpwd.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/function.js"></script>
<script type="text/javascript" src="<%=path %>/layer/layer.js"></script>
<script type="text/javascript">
$(function(){
  $("#toplogin").click(function(){
    layer.open({
      type:2,
      area: ['900px', '560px'], //宽高
      content: ['<%=path %>/login.jsp', 'no'] 
    });
  });
});
</script>
<script type="text/javascript">
//判断用户名是否注册(自行添加)
  function ajax(){
    var uname=$("#userId").val();
    $.ajax({
       url:'<%=path %>/servlet/doRegisterServlet?oper=checkname',
       type:'post',
       data:'name='+uname,
       //回调函数 data代表server回送的数据
       success:function(data){
       if(data=="true"){
          $("#userId").parent().find("span").html("该用户名可注册");
       }else{
          $("#userId").parent().find("span").html("该用户名已注册").addClass("error");
       } 
       }
    });
  }   
  $(function(){
    $("#userId").blur(function(){
    var value=$("#userId").val();
    if(value == "") {
                    errorMessage = "用户名不能为空";
                }else if(!/[a-zA-Z0-9]+/.test(value)){
                    errorMessage = "用户名只能是英文字母或者数字";
                }else{
               	ajax();
                    isError = false ;
                }
    });
  });
</script>
</head>
<body>
<div id="header" class="wrap">
	<div id="logo"><img src="<%=path %>/images/logo.gif" /></div>
	<div class="help"><a href="<%=path %>/servlet/doCartShoppingServlet?oper=list" id="shoppingBag" class="shopping" >购物车
	<c:choose>
	<c:when test="${sum eq null }">0</c:when>
	<c:otherwise>${sum }</c:otherwise>
	</c:choose>
	件</a>
	<c:choose>
	<c:when test="${empty loginname}">
	<a href="javascript:;" id="toplogin">登录${loginname }</a>
	</c:when>
	<c:otherwise>
	  ${loginname}
	</c:otherwise>
	</c:choose>
	
	<c:choose>
	<c:when test="${empty loginname}">
	</c:when>
	<c:otherwise>
	 <a class="button" id="logout" href="<%=path %>/servlet/doLoginOut">注销</a>
	</c:otherwise>
	</c:choose>
	<a href="<%=path %>/servlet/doRegisterServlet?oper=list">注册</a>
	<a href="<%=path%>/servlet/CommentServlet?oper=list">留言</a>
	<c:choose>
	<c:when test="${userInfo.status eq '2' }">
	  <a href="<%=path %>/manage/index.jsp">后台管理</a>
	</c:when>
	<c:otherwise>
	</c:otherwise>
	</c:choose>
	</div>
	<div class="navbar">
		<ul class="clearfix">
			<c:choose>
			<c:when test="${empty id }">
			<li class="current" id="0"><a href="<%=path %>/servlet/doIndexServlet?oper=loadchildByParent&id=0">首页</a></li>
			</c:when>
			<c:otherwise>
			<li id="0"><a href="<%=path %>/servlet/doIndexServlet?oper=loadchildByParent&id=0">首页</a></li>
			</c:otherwise>
			</c:choose>
			<c:forEach items="${parentlist}" var="item">
			<c:choose>
			<c:when test="${id eq item.id }">
			<li class="current" id="${item.id }"><a href="<%=path %>/servlet/doIndexServlet?oper=loadchildByParent&id=${item.id}">${item.name }</a></li>
			</c:when>
			<c:otherwise>
			<li id="${item.id }"><a href="<%=path %>/servlet/doIndexServlet?oper=loadchildByParent&id=${item.id}">${item.name }</a></li>
			</c:otherwise>
			</c:choose>
			</c:forEach>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="wrap">
		<ul class="clearfix">
			<c:forEach items="${childlistonly }" var="item" varStatus="status">
			
			<c:choose>
			<c:when test="${status.first}">
			<li class="first"><a href="#?parentid=${item.id }">${item.name }</a></li>
			</c:when>
			<c:otherwise>
			
			<c:choose>
			<c:when test="${status.last }">
			<li class="last"><a href="#?parentid=${item.id }">${item.name }</a></li>
			</c:when>
			<c:otherwise>
			<li><a href="#?parentid=${item.id }">${item.name }</a></li>
			</c:otherwise>
			</c:choose>
			</c:otherwise>
			</c:choose>
			</c:forEach>
		</ul>
	</div>
</div>
<div id="register" class="wrap">
	<div class="shadow">
		<em class="corner lb"></em>
		<em class="corner rt"></em>
		<div class="box">
			<h1>欢迎注册易买网</h1>
			<ul class="steps clearfix">
				<li class="current"><em></em>填写注册信息</li>
				<li class="last"><em></em>注册成功</li>
			</ul>
			<form id="regForm" method="post" action="<%=path %>/servlet/doRegisterServlet?oper=add" >
				<table>
					<tr>
						<td class="field">用户名(*)</td>
						<td>
							<input class="text" type="text" name="userId"  id="userId" maxlength="10"  />
							<span></span>
						</td>
					</tr>
					<tr>
						<td class="field">真实姓名(*)：</td>
						<td><input class="text" type="text" name="userName" /><span></span></td>
					</tr>
					<tr>
						<td class="field">登录密码(*)：</td>
						<td><input class="text" type="password" id="password" name="password" style="font-size: 16px;"  maxlength="16" onchange="javascript:EvalPwd(this.value);" onkeyup="javascript:EvalPwd(this.value);" size="25"/><span></span></td>
					</tr>
					<tr cellpadding="0" cellspacing="0" border="0" id="pwdpower">
                    <td id="pweak" style="">弱</td>
                    <td id="pmedium" style="">中</td>
                    <td id="pstrong" style="">强</td>
                    </tr>
					<tr>
						<td class="field">确认密码(*)：</td>
						<td><input class="text" type="password" name="confirmPassword" /><span></span></td>
					</tr>
					<tr>
						<td class="field">性别(*)：</td>
						<td>
						  <input class="radio" type="radio" name="sex" value="male" checked="checked">
						     男性</input>
						  <input class="radio" type="radio" name="sex" value="female"						  
						  >女性</input>
						  <span></span></td>
					</tr>
					<tr>
						<td class="field">出生日期：</td>
						<td><input id="birthday" class="text" type="text" name="birthday" /><span></span></td>
					</tr>
					<tr>
						<td class="field">身份证：</td>
						<td><input class="text" type="text" name="identityCode" /><span></span></td>
					</tr>
					<tr>
						<td class="field">电子邮件：</td>
						<td><input class="text" type="text" name="email" /><span></span></td>
					</tr>
					<tr>
						<td class="field">手机(*)：</td>						
						<td><input class="text" type="text" name="mobile" /><span></span></td>
					</tr>
					<tr>
						<td class="field">收货地址(*)：</td>
						<td><input class="text" type="text" name="address" /><span></span></td>
					</tr>
					<tr>
						<td></td>
						<td><label class="ui-green"><input type="submit" name="submit" value="提交注册" /></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2010 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
