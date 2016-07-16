package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.OrderDaoImpl;
import cn.happy.easybuy.dao.impl.OrderDetitalImpl;
import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.entity.User;
import cn.happy.easybuy.until.ShoppingCart;
import cn.happy.easybuy.until.ShoppingCartItem;

public class doGiveMoneyServlet extends HttpServlet {
	ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	 UserDaoImpl userDaoImpl=new UserDaoImpl();
	 OrderDaoImpl OrderDaoImpl=new OrderDaoImpl();
	 OrderDetitalImpl orderDetitalImpl=new OrderDetitalImpl();
	 ProductDaoImpl  productDaoImpl=new ProductDaoImpl();
	/**
	/**
		 岁月静好
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  //解决乱码
		   request.setCharacterEncoding("utf-8");
		  String oper=request.getParameter("oper");
		  if(oper.equals("givemoney")){//结账
			  String type=request.getParameter("type");//判断是直接购买，还是购物车结算
			  if(type.equals("direct")){//直接购买，形成一张订单记录和一条订单详情记录
				  directBuy(request,response);
			  }else if(type.equals("cartbuy")){//选择购物车结算，形成一条订单记录和对应条数的订单详情记录
				  cartBuy(request,response);
			  }
			  
		  }	
	}

	
	/**加载所有初始信息
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//所有的一级分类
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//所有的二级分类
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//所有的商品信息
			List<Product> productlist=productDaoImpl.getAllProduct();
			request.setAttribute("productlist",productlist);
			request.setAttribute("childlistonly",childlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**直接点击购买
	 * */
	public void directBuy(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		try {
			//获取收货地址
			String address=request.getParameter("address");
			address=new String(address.getBytes("iso-8859-1"),"utf-8");
			//获取购买用户的信息
			String uid=(String)request.getSession().getAttribute("loginname");
			/**
			 * 根据用户id获取用户的相关信息 
			* */
			User user=userDaoImpl.getUserInfoByUid(uid);
			//获取到直接购买的商品信息
			Product product=(Product)request.getSession().getAttribute("directbuy");
			//形成一张订单记录
			Order order=new Order();
			order.setCost(product.getPrice());
			//获取当前日期
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			order.setCreatTime(time);
			order.setUserId(user.getId());
			order.setStatus(1);
			order.setType(1);
			order.setUserName(user.getName());
			order.setUserAddress(address);
			boolean flag=OrderDaoImpl.addOrder(order);
			if(flag){//订单记录添加成功
				//添加订单详情
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(product.getPrice());
				//获取订单编号
				int oid=OrderDaoImpl.getNewOid();
				orderDetail.setOrderId(oid);
				orderDetail.setProductId(product.getId());
				orderDetail.setQuantity(1);
				boolean flag2=orderDetitalImpl.addOrderDetital(orderDetail);
				if(flag2){//订单详情表添加成功
					//跳转到购物成功页面
					request.setAttribute("oid",oid);
					request.getRequestDispatcher("/shopping-result.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 从购物车结算
	 * */
	public void cartBuy(HttpServletRequest request, HttpServletResponse response){
		try {
		//获取收货地址
		String address=request.getParameter("address");
		address=new String(address.getBytes("iso-8859-1"),"utf-8");
		//获取购买用户的信息
		String uid=(String)request.getSession().getAttribute("loginname");
		/**
		 * 根据用户id获取用户的相关信息 
		* */
		User user=userDaoImpl.getUserInfoByUid(uid);
		
		//获取购物车中的集合
		ShoppingCart sh=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
		List<ShoppingCartItem> items=sh.getItems();
		double totalcost=0;
		//形成一张订单记录
		Order order=new Order();
		for (ShoppingCartItem shoppingCartItem : items) {
			totalcost+=shoppingCartItem.getCost();
		}
		order.setCost(totalcost);
		//获取当前日期
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(date);
		order.setCreatTime(time);
		order.setUserId(user.getId());
		order.setStatus(1);
		order.setType(1);
		order.setUserName(user.getName());
		order.setUserAddress(address);
		boolean flag=OrderDaoImpl.addOrder(order);
		boolean flag2=false;
		//获取订单编号
		int oid=OrderDaoImpl.getNewOid();
		for (ShoppingCartItem shoppingCartItem : items) {
			if(flag){//订单记录添加成功
				//添加订单详情
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(shoppingCartItem.getCost());
				orderDetail.setOrderId(oid);
				orderDetail.setProductId(shoppingCartItem.getProduct().getId());
				orderDetail.setQuantity(1);
				flag2=orderDetitalImpl.addOrderDetital(orderDetail);
			}
		}
		if(flag2){//订单详情表添加成功
			//订单提交成功之后 清除购物车中的内容
			request.getSession().removeAttribute("shoppingCart");
			//跳转到购物成功页面
			request.setAttribute("oid",oid);
			request.getRequestDispatcher("/shopping-result.jsp").forward(request, response);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
