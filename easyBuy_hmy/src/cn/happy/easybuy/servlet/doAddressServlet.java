package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.entity.User;

public class doAddressServlet extends HttpServlet {
	 ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	 UserDaoImpl userDaoImpl=new UserDaoImpl();
	 ProductDaoImpl productDaoImpl=new ProductDaoImpl();
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
		  if(oper.equals("buy")){//直接点击购买 或购物车点击结算时，跳转到该servlet，再转向到address.jsp页面
			  //先判断用户是否登录
			  if(request.getSession().getAttribute("loginname")==null){//未登录，先进入登录页面
				  request.getRequestDispatcher("/login.jsp").forward(request, response);
			  }else{//进入结算页面
				  buy(request,response);
			  }
			  
		  }
		
	   }
	
	/**加载所有初始信息
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//所有的一级分类
			List<ProductCategory> parentlist= pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//所有的二级分类
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			request.setAttribute("childlistonly",childlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**直接点击购买
	 * */
	public void buy(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//查询对应用户的收货信息
		String uid=(String)request.getSession().getAttribute("loginname");
		try {
			/**将购买类型放入作用域  是直接点击购买 还是从购物车进行结算
			 * */
			String type=request.getParameter("type");
			request.setAttribute("type",type);
			//获取直接购买的商品编号
			String proid=request.getParameter("proid");
			if(proid!=null){
				//根据商品编号查找商品信息 并放入session作用域
				Product product=productDaoImpl.getProductById(Integer.parseInt(proid));
				request.getSession().setAttribute("directbuy", product);
			}
			User user=userDaoImpl.getUserInfoByUid(uid);
			//将用户信息放入作用域
			request.setAttribute("user",user);
			request.getRequestDispatcher("/address.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
