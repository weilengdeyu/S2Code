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
<title>后台管理 - 易买网</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/function.js"></script>
</head>
<body>
<div id="header" class="wrap">
	<div id="logo"><img src="<%=path %>/images/logo.gif" /></div>
	<div class="help"><a href="<%=path %>/servlet/doIndexServlet">返回前台页面</a></div>
	<div class="navbar">
		<ul class="clearfix">
			<li class="current"><a href="<%=path %>/manage/index.jsp">首页</a></li>
			<li><a href="<%=path %>/servlet/UserServlet?oper=list">用户</a></li>
			<li><a href="<%=path %>/servlet/doProductServlet?oper=proManage">商品</a></li>
			<li><a href="<%=path %>/servlet/doOrderServlet?oper=list">订单</a></li>
			<li><a href="<%=path %>/servlet/CommentServlet?oper=managelist">留言</a></li>
			<li><a href="<%=path %>/servlet/NewsServlet?oper=list">新闻</a></li>
		</ul>
	</div>
</div>
<div id="childNav">
	<div class="welcome wrap">
		管理员${loginname }您好，今天是${date }，欢迎回到管理后台。
	</div>
</div>
<div id="position" class="wrap">
	您现在的位置：<a href="<%=path %>/manage/index.jsp">易买网</a> &gt; 管理后台
</div>
<div id="main" class="wrap">
	<div id="menu-mng" class="lefter">
		<div class="box">
			<dl>
				<dt>用户管理</dt>
				<dd><a href="<%=path %>/servlet/UserServlet?oper=list">用户管理</a></dd>
			  <dt>商品信息</dt>
				<dd><em><a href="<%=path %>/servlet/doProductClassServlet?oper=add">新增</a></em><a href="<%=path %>/servlet/doProductClassServlet?oper=list">分类管理</a></dd>
				<dd><em><a href="<%=path %>/servlet/doProductServlet?oper=backProductAdd">新增</a></em><a href="<%=path %>/servlet/doProductServlet?oper=proManage">商品管理</a></dd>
				<dt>订单管理</dt>
				<dd><a href="<%=path %>/servlet/doOrderServlet?oper=list">订单管理</a></dd>
				<dt>留言管理</dt>
				<dd><a href="<%=path %>/servlet/CommentServlet?oper=managelist">留言管理</a></dd>
				<dt>新闻管理</dt>
				<dd><em><a href="<%=path %>/manage/news-add.jsp">新增</a></em><a href="<%=path %>/servlet/NewsServlet?oper=list">新闻管理</a></dd>
			</dl>
		</div>
	</div>
	<div class="main">
		<h2>留言管理</h2>
		<div class="manage">
			<table class="list">
				<tr>
					<th>ID</th>
					<th>姓名</th>
					<th>留言内容</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${page.list }" var="item">
				<tr>
					<td class="first w4 c">${item.id }</td>
					<td class="w1 c">${item.nickName }</td>
					<td>${item.content }</td>
					<td class="w1 c">
					<c:choose>
						<c:when test="${item.reply eq NULL }">
						未回复
						<td class="w1 c"><a href="<%=path %>/servlet/CommentServlet?oper=reply&commentid=${item.id}">回复</a> <a class="manageDel" href="<%=path %>/servlet/CommentServlet?oper=del&cid=${item.id }">删除</a></td>
						</c:when>
						<c:otherwise>
						已回复
						<td class="w1 c"><a href="<%=path %>/servlet/CommentServlet?oper=modify&commentid=${item.id}">修改</a> <a class="manageDel" href="<%=path %>/servlet/CommentServlet?oper=del&cid=${item.id }">删除</a></td>
						</c:otherwise>
						</c:choose>
					</td>
				</tr>
				</c:forEach>	
			</table>
			<div class="pager">
				<ul class="clearfix">
					<li><a  href="<%=path %>/servlet/CommentServlet?oper=managelist&pageIndex=1">首页</a></li>
					<li><a href="<%=path %>/servlet/CommentServlet?oper=managelist&pageIndex=${page.pageIndex-1}">上一页</a></li>
					<li>[${page.pageIndex }/${page.pageCount }]</li>
                    <li><a href="<%=path %>/servlet/CommentServlet?oper=managelist&pageIndex=${page.pageIndex+1}">下一页</a></li>
					<li><a  href="<%=path %>/servlet/CommentServlet?oper=managelist&pageIndex=${page.pageCount}">尾页</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2013 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
