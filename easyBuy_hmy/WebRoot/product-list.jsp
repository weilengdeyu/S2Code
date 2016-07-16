<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
	您现在的位置：<a href="<%=path %>/servlet/doIndexServlet">易买网</a> &gt; <a href="product-list.jsp">图书音像</a> &gt; 图书
</div>
<div id="main" class="wrap">
	<div class="lefter">
		<div class="box">
			<h2>商品分类</h2>
			<dl>
				<c:forEach items="${parentlist }" var="item">
			     <dt>${item.name }</dt>
			     <c:forEach items="${childlist }" var="childitem">
				     <c:if test="${item.id eq childitem.parentId }">
				       <dd><a href="<%=path %>/servlet/doProductServlet?oper=proCateSelect&cateid=${childitem.id}&pid=${childitem.parentId}">${childitem.name }</a></dd>
				     </c:if>
			     </c:forEach>
			</c:forEach>
			</dl>
		</div>
		<div class="spacer"></div>
		<div class="last-view">
			<h2>最近浏览</h2>
			<dl class="clearfix">
				<c:forEach var="item" items="${lookproduct }">
			    <dt><img src="<%=path %>/images/product/${item.fileName }" width="60" height="60" /></dt>
			    <dd>
			    <a href="<%=path %>/servlet/doProductServlet?oper=prodetital&proid=${item.id }&pid=${item.categoryId}"  target="_self">${fn:substring(item.description,0,9) }........</a>
			    <a href="product-view.jsp"></a>
			    </dd>
			    <br/>
			</c:forEach>
		  </dl>
		</div>
	</div>
	<div class="main">
		<div class="product-list">
			<h2>全部商品</h2>			
			<div class="clear"></div>
			<ul class="product clearfix">
			<c:forEach items="${pages.list }" var="item">
			<c:if test="${item.chileCategoryId eq cid }">
			     <li>
					<dl>
						<dt><a href="<%=path %>/servlet/doProductServlet?oper=prodetital&proid=${item.id }&pid=${item.categoryId}" target="_self"><img src="<%=path %>/images/product/${item.fileName}" width="110" height="106"/></a></dt>
						<dd class="title"><a href="product-view.html" target="_self">${item.name }</a></dd>
						<dd class="price">￥${item.price }</dd>
					</dl>
				 </li>
			</c:if>
			</c:forEach>
			</ul>
			<div class="clear"></div>
			<div class="pager">
				<ul class="clearfix">
					<c:if test="${pages.totalPages ge 1}">	
					<li><a href="${pageContext.request.contextPath}/servlet/doProductServlet?oper=proCateSelect&pid=${id }&pageIndex=1&cateid=<c:if test="${ empty cid}">1</c:if><c:if test="${not empty cid}">${cid }</c:if>">首页</a></li>	
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
                               <span><a href="${pageContext.request.contextPath}/servlet/doProductServlet?oper=proCateSelect&pid=${id }&pageIndex=${i}&cateid=<c:if test="${ empty cid}">1</c:if><c:if test="${not empty cid}">${cid }</c:if>">${i}</a></span>
                           </c:otherwise>
                        </c:choose>
					  </c:forEach>
					  </li>
					<!-- 尾页 -->			
					<li><a href="${pageContext.request.contextPath}/servlet/doProductServlet?oper=proCateSelect&pid=${id }&pageIndex=<c:out value='${pages.totalPages}'/>&cateid=<c:if test="${ empty cid}">1</c:if><c:if test="${not empty cid}">${cid }</c:if>">尾页 </a></li>
					</c:if>	
				</ul>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div id="footer">
	Copyright &copy; 2010 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
