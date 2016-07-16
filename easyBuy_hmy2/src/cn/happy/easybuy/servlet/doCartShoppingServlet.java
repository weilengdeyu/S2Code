package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.entity.News;
import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.until.ShoppingCart;
import cn.happy.easybuy.until.ShoppingCartItem;


public class doCartShoppingServlet extends HttpServlet {
	   ShoppingCart shoppingCart=null;
	   ProductDaoImpl  productDaoImpl=new ProductDaoImpl();
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
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
            if(shoppingCart!=null){
            	shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
            }else{
            	shoppingCart=new ShoppingCart();
            	request.getSession().setAttribute("shoppingCart",shoppingCart);
            }
            if(oper.equals("add")){//进行加入购物车的操作
            	/**先判断用户是否登录
        		 * */
        		if(request.getSession().getAttribute("loginname")==null){//未登录则跳转到登录页面
        			try {
        				request.getRequestDispatcher("/login.jsp").forward(request, response);
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}else{
        			addToCart(request,response);
        		}
            }else if(oper.equals("list")){
            	show(request,response);
            }else if(oper.equals("del")){//进行删除操作
            	int index=Integer.parseInt(request.getParameter("itemid"));
            	shoppingCart.removeItem(index);
            	request.getRequestDispatcher("/servlet/doCartShoppingServlet?oper=list").forward(request, response);
            }else if(oper.equals("allItems")){//查询所有购物车中的内容
            	List<ShoppingCartItem> items=shoppingCart.getItems();
            	//引入自动将list转成JSON串的工具 gjson
            	Gson tool=new Gson();
            	String json=tool.toJson(items);
            	response.setCharacterEncoding("utf-8");
            	response.getWriter().print(json);	
            }else if(oper.equals("givemoney")){//结账操作  一旦结账，就形成一条订单记录
            	//查询购物车中的商品
            	shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
            	List<ShoppingCartItem> items=shoppingCart.getItems();
            	//先形成一张订单
            	Order order=new Order();
            	//再形成订单详情
            	
            }else if(oper.equals("ProductNumber")){//进行数量的加减操作
            		poductNumber(request,response);
            	
            }
           
		 	
		
	}
	
	/**
	 * 进入购物车展示时
	 * */
	public void show(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		List<ShoppingCartItem> items=shoppingCart.getItems();
    	request.setAttribute("items", items);
    	try {
			request.getRequestDispatcher("/shopping.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * 加载所有的初始信息
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try{
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
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//进行购物车的添加操作
	public void addToCart(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//对应的商品编号
    	String proid=request.getParameter("proid");
    	//去数据库查找对应的商品
    	try {
    		Product product=productDaoImpl.getProductById(Integer.parseInt(proid));
    		//加入购物车
    		shoppingCart.addItem(product,1);
    		request.getSession().setAttribute("shoppingCart",shoppingCart);
    		List<ShoppingCartItem> items=shoppingCart.getItems();
    		int sum=items.size();
    		request.getSession().setAttribute("sum",sum);
    		response.getWriter().print("true");
    		/**转发
    		 * */
    		//request.getRequestDispatcher("/servlet/doCartShoppingServlet?oper=list").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
	//执行购物车中点+号进行的操作
	public void poductNumber(HttpServletRequest request, HttpServletResponse response){
		String dotype=request.getParameter("do");//标识进行加减的哪种操作
		//获取商品编号
		String id=request.getParameter("id");
		//获取购物车中的商品信息
		shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
    	List<ShoppingCartItem> items=shoppingCart.getItems();
    	//保存修改后的数量
		int num=0;
    	if(dotype.equals("addNumber")){//进行加的操作
    		//给该id的商品数量加1
    		for (ShoppingCartItem shoppingCartItem : items) {
				if(shoppingCartItem.getProduct().getId()==Integer.parseInt(id)){
					shoppingCartItem.setNum(shoppingCartItem.getNum()+1);
					num=shoppingCartItem.getNum();
					shoppingCartItem.setCost(shoppingCartItem.getNum()*shoppingCartItem.getProduct().getPrice());
					break;
				}
			}
    	}else if(dotype.equals("jianNumber")){//进行减操作
    		//给该id的商品数量-1
    		for (ShoppingCartItem shoppingCartItem : items) {
				if(shoppingCartItem.getProduct().getId()==Integer.parseInt(id)){
					shoppingCartItem.setNum(shoppingCartItem.getNum()-1);
					num=shoppingCartItem.getNum();
					shoppingCartItem.setCost(shoppingCartItem.getNum()*shoppingCartItem.getProduct().getPrice());
					break;
				}
			}
    		
    	}
    	//进行购物车的一个更新操作
    	shoppingCart.setItems(items);    
    	//重新将购物车对象  放入作用域中
    	request.getSession().setAttribute("shoppingCart", shoppingCart);
    	//把数量值写入相应流中
    	response.setCharacterEncoding("utf-8");
        try {
			response.getWriter().print(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
