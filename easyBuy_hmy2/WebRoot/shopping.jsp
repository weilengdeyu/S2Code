
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>易买网 - 首页</title>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/style.css" />
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
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
<div id="position" class="wrap">
	您现在的位置：<a href="<%=path %>/servlet/doIndexServlet">易买网</a> &gt; 购物车
</div>
<div class="wrap">
	<div id="shopping">
		<form action="<%=path %>/servlet/doAddressServlet?oper=buy&type=cartbuy" method="post">
			<table>
				<tr>
					<th>商品名称</th>
					<th>商品价格</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${items }" var="item">
				  <tr id="${item.product.id }">
						<td class="thumb"><img src="<%=path %>/images/product/${item.product.fileName }" width="110" height="106"/><a href="product-view.html">${item.product.name }</a></td>
						<td class="price" id="price_id_0">
							<span>￥${item.cost }</span>
							<input type="hidden" value="${item.product.price }" />
						</td>
						<td class="number">
	                        <span name="del">-</span>
	                        <input id="number_id_0" type="text" name="number" value="${item.num }" />
	                        <span name="add">+</span>
						</td>
						<td class="delete"><a href="<%=path %>/servlet/doCartShoppingServlet?oper=del&itemid=${item.product.id}">删除</a></td>
					</tr>
				</c:forEach>
			</table>
            <div class="total"><span id="total">总计：￥0</span></div>
			<div class="button"><input type="submit" value="" /></div>
		</form>
	</div>
</div>
<div id="footer">
	Copyright &copy; 2013 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
