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
<script type="text/javascript">
//使用ajax对订单的状态进行一个提交
function ajax(num,oid,before,text){
    	    $.ajax({
    	       url:'<%=path %>/servlet/doOrderServlet?oper=change&oid='+oid,
    	       type:'post',
    	       data:'num='+num,
    	       //回调函数 data代表server回送的数据
    	       success:function(data){
    	    	   if(data=="true"){//表示更改成功
    	    	      $("#before").text(text);
    	    	   }else{//更改失败
    	    	     $("#before").text(before);
    	    	   }
    	       }
    	    });
    }
$(function(){
     //先获取下拉框状态改变之前的值
     var before=$.trim($("#before").text());
      //当下拉框的状态改变时  就调用一次ajax
	$("#selectStatus").change(function(){
	   //获取当前选中的一行的value
	   var value=$("#selectStatus").val();
	   //获取选中行的文本值
	   var text=$("#"+value).text();
	   //获取该订单号
	   var oid=$("#oid").text();
	   ajax(value,oid,before,text);
	});
});
</script>
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
		<h2>订单管理</h2>
		<div class="manage">
			<div class="search">				
			</div>
			<div class="spacer"></div>
            <form id="orderForm" method="post"  action="<%=path %>/servlet/doOrderServlet?oper=select">
                 订单号：<input type="text" class="text" name="entityId" id="entityId" value="${entityId }"/>
                 订货人：<input type="text" class="text" name="userName" value="${name }" />
                 <label class="ui-blue"><input type="submit" name="submit" value="查询" /></label>
            </form>
			<table class="list">

			<c:forEach items="${map}" var="item">
			    <tr>
					<th colspan="2">单号：<span id="oid">${item.key.id }</span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间：${item.key.creatTime }</th>
					<th colspan="2" >状态:<span id="before">
					    <c:if test="${item.key.status eq 1}">待审核</c:if>
						<c:if test="${item.key.status eq 2}">审核通过</c:if>
						<c:if test="${item.key.status eq 3}">配货</c:if>
						<c:if test="${item.key.status eq 4}">送货中</c:if>
						<c:if test="${item.key.status eq 5}">收货并确认</c:if>
						</span>
						<c:if test="${item.key.status ne 5 }">
						<select id="selectStatus" name="status" >			    
								<option value="1" id="1" >待审核</option>
								<option value="2" id="2" >审核通过</option>
								<option value="3" id="3" >配货</option>
								<option value="4" id="4">发货</option>
								<option value="5" id="5" >收货确认</option>
						</select>
						</c:if>
						</th>					
				</tr>
			    <c:forEach items="${item.value }" var="items">
			    <tr>
					<td class="first w4 c"><img src="<%=path %>/images/product/${items.product.fileName }" width="110" height="106"/>${items.product.name }</td>
					<td >${items.cost }</td>
					<td>${items.quantity }</td>					
				  </tr>
			    </c:forEach>
			</c:forEach>
				
				
			</table>
			<div class="pager">
				<ul class="clearfix">
				
				<c:if test="${page.totalPages ge 1}">	
					<li><a href="${pageContext.request.contextPath}/servlet/doOrderServlet?oper=select&pageIndex=1&entityId=${entityId}&userName=${name}">首页</a></li>	
					<!-- 上一页-->	
					  <!-- 显示分页码-->
					
					  <li>
					   <c:forEach  begin="${page.listbegin}" end="${page.listened}" var="i" varStatus="status">
					   
					       <c:choose>
                           <c:when test="${page.pageIndex eq status.index}">
                              <span class="current">
                             	 <a style="color: #f60;">[${i}]</a>
                              </span>
                           </c:when>
                           <c:otherwise>
                               <span><a href="${pageContext.request.contextPath}/servlet/doOrderServlet?oper=select&pageIndex=${i}&entityId=${entityId}&userName=${name}">${i}</a></span>
                           </c:otherwise>
                        </c:choose>
					  </c:forEach>
					  </li>
					<!-- 尾页 -->			
					<li><a href="${pageContext.request.contextPath}/servlet/doOrderServlet?oper=select&pageIndex=${page.totalPages}&entityId=${entityId}&userName=${name}">尾页 </a></li>
					</c:if>		
				
				
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
