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
		<h2>分类管理</h2>
		<div class="manage">
			<table class="list">
				<tr>
					<th>编号</th>
					<th>分类名称</th>
					<th>操作</th>
				</tr>
                <c:forEach items="${map}" var="item">
                
                <tr style="background:pink">
						<td class="first w4 c" >${item.key.id }</td>
						<td>${item.key.name }</td>
						<td class="w1 c"><a href="<%=path %>/servlet/doProductClassServlet?oper=modify&id=${item.key.id}">修改</a> <a class="manageDel" href="<%=path %>/servlet/doProductClassServlet?oper=delPcate&id=${item.key.id}">删除</a></td>
					</tr>
					<c:forEach items="${item.value }" var="childitem">
				    
						<tr>
							<td class="first w4 c">${childitem.id }</td>
							<td class="childClass">${childitem.name }</td>
							<td class="w1 c"><a href="<%=path %>/servlet/doProductClassServlet?oper=modify&id=${childitem.id}">修改</a> <a  class="manageDel" href="<%=path %>/servlet/doProductClassServlet?oper=delChildCate&id=${childitem.id }">删除</a></td>
						</tr>
				    
				    </c:forEach>
				    
                </c:forEach>
					
				
				
				
			</table>
		</div>
	</div>
	<div class="clear"></div>
    <div class="pager">
				<ul class="clearfix">
					<c:if test="${pages.totalPages ge 1}">	
					<li><a href="${pageContext.request.contextPath}/servlet/doProductClassServlet?oper=list&pageIndex=1">首页</a></li>	
					<!-- 上一页-->	
					  <!-- 显示分页码-->
					
					  <li>
					   <c:forEach  begin="${pages.listbegin}" end="${pages.listened}" var="i" varStatus="status">
					   
					       <c:choose>
                           <c:when test="${pages.pageIndex eq status.index}">
                              <span class="current">
                             	 <a style="color: #f60;">[${i}]</a>
                              </span>
                           </c:when>
                           <c:otherwise>
                               <span><a href="${pageContext.request.contextPath}/servlet/doProductClassServlet?oper=list&pageIndex=${i}">${i}</a></span>
                           </c:otherwise>
                        </c:choose>
					  </c:forEach>
					  </li>
					<!-- 尾页 -->			
					<li><a href="${pageContext.request.contextPath}/servlet/doProductClassServlet?oper=list&pageIndex=${pages.totalPages}">尾页 </a></li>
					</c:if>		
				</ul>
			</div>
</div>
<div id="footer">
	Copyright &copy; 2013 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
