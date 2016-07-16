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
<style>
   .u-flyer {display: block;width: 50px;height: 50px;border-radius: 50px;position: fixed;z-index: 9999;}

</style>
<link type="text/css" rel="stylesheet" href="<%=path %>/css/style.css" />
<link rel="stylesheet" href="<%=path %>/css/magiczoomplus.css" type="text/css"></link>
<link rel="stylesheet" href="<%=path %>/css/zzsc.css" type="text/css"></link>
<script type="text/javascript" src="<%=path %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/function.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/jquery.fly.min.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/jquery.fly.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/mzp-packed.js"></script>
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
	您现在的位置：<a href="<%=path %>/servlet/doIndexServlet">易买网</a> 
	&gt; <a href="product-list.jsp">图书音像</a>
	 &gt; <c:forEach items="${childlist }" var="item">
	 <c:if test="${item.id eq proid}">${item.name }</c:if>
	 </c:forEach>
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
	</div>
	<div id="product" class="main">
        <c:forEach items="${list }" var="item">
           <c:if test="${item.id eq proid }">
              <h1>${item.name }</h1>
		      <div class="infos">
			    <div class="thumb">
			    
			    <a href="<%=path %>/images/product/${item.fileName }" id="zoom1" class="MagicZoom MagicThumb">
		        <img src="<%=path %>/images/product/${item.fileName }" class="main_img" style="width:210px; height:210px;" />
		        </a>
			    </div>
			    <script type="text/javascript">

//使用ajax请求去进行添加操作
function ajax(){
      $.ajax({
    	       url:'<%=path %>/servlet/doCartShoppingServlet?oper=add&proid=${proid}',
    	       type:'post',
    	       //回调函数 data代表server回送的数据
    	       success:function(data){
    	    	   
    	       }
    	    });
}



$(function(){

$("#cart").on("click",addcart);
function addcart(){
var startset=$(".thumb").offset();
var offset=$("#shoppingBag").offset(),flyer=$('<img class="u-flyer" src="<%=path %>/images/product/${item.fileName }"/>');
flyer.fly({
    start:{
          left:startset.left,
          top:startset.top
      
    },
    end:{
       left:offset.left,
       top:offset.top,
        width: 18,
        height: 18
    } 
});
}

});


</script>
			    
			    <div class="buy">
				      商城价：<span class="price">￥${item.price }</span><br />
				      <c:choose>
				      <c:when test="${item.stock gt 0}">
				               库　存：有货
				      </c:when>
				      <c:otherwise>
				              库　存：无货
				      </c:otherwise>
				      </c:choose>
				    
			    <div class="button"><input type="button" name="button" value="" onclick="location.href ='<%=path %>/servlet/doAddressServlet?oper=buy&type=direct&proid=${item.id}'" />
			                                             <label id="cart" onclick="ajax()">放入购物车</label></div>
			    </div>
			     <div class="clear"></div>
		        </div>
		        <div class="introduce">
			    <h2><strong>商品详情</strong></h2>
			    <div class="text">
				   ${item.description }
			   </div>
		     </div>
	        <div class="clear"></div>
           </c:if>
        </c:forEach>
         </div>
	</div>
<div id="footer">
	Copyright &copy; 2013 北大青鸟 All Rights Reserved. 京ICP证1000001号
</div>
</body>
</html>
