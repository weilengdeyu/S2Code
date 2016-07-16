<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<link rel="stylesheet" href="<%=path %>/layer/skin/layer.css" type="text/css"></link>
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/function.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/index.js"></script>
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


 <div id="welcomeImage">
    <img width="100%" height="150" src="<%=path %>/images/banner.jpg" alt="welcome">
</div>
<div id="header" class="wrap">
	<div id="logo"><img src="<%=path %>/images/logo.gif" /></div>
	<div class="help"><a href="<%=path %>/servlet/doCartShoppingServlet?oper=list" id="shoppingBag" class="shopping" >购物车
	<c:choose>
	<c:when test="${sum eq null }">0</c:when>
	<c:otherwise>${sum }</c:otherwise>
	</c:choose>件</a>
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
		<div class="price-off">
            <div class="slideBox">
                <ul id="slideBox">
                      <li><img src="<%=path %>/images/product/qq04.jpg"/></li>
                       <li><img src="<%=path %>/images/product/qq03.jpg"/></li>
                        <li><img src="<%=path %>/images/product/qq02.jpg"/></li>
                         <li><img src="<%=path %>/images/product/qq05.jpg"/></li>
                </ul>
            </div>
			<h2>商品列表</h2>
			<ul class="product clearfix">
			<c:forEach items="${pages.list }" var="item">
			<li>
					<dl>
						<dt><a href="<%=path %>/servlet/doProductServlet?oper=prodetital&proid=${item.id }"  target="_self"><img src="<%=path %>/images/product/${item.fileName}" width="110" height="106"/></a></dt>
						<dd class="title"><a href="<%=path %>/servlet/doProductServlet?oper=prodetital&proid=${item.id }" target="_self"></a>${item.name }</dd>
						<dd class="price">￥${item.price }</dd>
					</dl>
				</li>
			</c:forEach>
			</ul>
			<div class="pager">
				<ul class="clearfix">
				
				<c:if test="${pages.totalPages ge 1}">	
					<li><a href="${pageContext.request.contextPath}/servlet/doIndexServlet?oper=getPages&pageIndex=1&id=<c:if test="${ empty id}">1</c:if><c:if test="${not empty id}">${id }</c:if>">首页</a></li>	
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
                               <span><a href="${pageContext.request.contextPath}/servlet/doIndexServlet?oper=getPages&pageIndex=${i}&id=<c:if test="${ empty id}">1</c:if><c:if test="${not empty id}">${id }</c:if>">${i}</a></span>
                           </c:otherwise>
                        </c:choose>
					  </c:forEach>
					  </li>
					<!-- 尾页 -->			
					<li><a href="${pageContext.request.contextPath}/servlet/doIndexServlet?oper=getPages&pageIndex=<c:out value='${pages.totalPages}'/>&id=<c:if test="${ empty id}">1</c:if><c:if test="${not empty id}">${id }</c:if>">尾页 </a></li>
					</c:if>		
					
				
				</ul>
			</div>
		</div>
		<div class="side">			
		
			<div class="spacer"></div>
			<div class="news-list">
			
				<h4>新闻动态</h4>
				<ul id="express">
				<c:forEach items="${newslist }" var="newsitem">
				<li><a href="<%=path %>/servlet/NewsServlet?oper=newsDetial&nid=${newsitem.id}"  target="_self">${newsitem.title }</a></li>
				</c:forEach>				
					
				</ul>
			</div>
		    
		</div>
		<div class="spacer clear"></div>
    </div>
</div>
<div id="footer">
	Copyright &copy; 2013 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
