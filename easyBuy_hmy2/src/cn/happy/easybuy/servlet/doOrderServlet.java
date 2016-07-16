package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.OrderDaoImpl;
import cn.happy.easybuy.dao.impl.OrderDetitalImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;
import cn.happy.easybuy.entity.OrderInfo;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.until.Page;

public class doOrderServlet extends HttpServlet {
	OrderDetitalImpl orderDetitalImpl=new OrderDetitalImpl();
	OrderDaoImpl orderDaoImpl=new OrderDaoImpl();
	ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	/**
		 鍑哄搧浜猴細宀佹湀闈欏ソ
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 鍑哄搧浜猴細宀佹湀闈欏ソ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
           String oper=request.getParameter("oper");
           if(oper.equals("list")){//展示订单信息
        	   loadlist(request,response);
           }else if(oper.equals("select")){//根据条件查询相关的订单信息
        	   //根据订单号查询订单信息或订货人查询订单信息
        	   loadOrderDetialInfoByOid(request,response);
        	  
           }else if(oper.equals("change")){//进行订单状态的改变
        	   change(request,response);
           }
	}

	/**
	 * 根据订单号查询订单信息
	 * @throws UnsupportedEncodingException 
	 * */
	public void loadOrderDetialInfoByOid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		//获取前台传来的订单号
		String entityId=request.getParameter("entityId");
		//获取前台传来的订货人
		String name=request.getParameter("userName");
		
		if(entityId==null&&name==null){//两者为空查询所有
			
			all(request,response);
		}
		if(entityId!=null&&entityId.trim().isEmpty()&&name!=null&&name.trim().isEmpty()){
			
			all(request,response);
			
		}
		
		
		
		
		//如果订单号不为空则进行以订单号查询订单详情信息
		if((entityId!=null&&!entityId.trim().isEmpty())&&(name==null||(name!=null&&name.trim().isEmpty()))){
			try {
				Order order=new Order();
				order.setId(Integer.parseInt(entityId));
				//获取订单详情
				List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
				//根据订单编号获取订单的相关信息
				order=orderDaoImpl.getInfoByOid(order);
				//对订单详情中的商品类赋值
				for (OrderDetail orderDetail : lists) {
					
					Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
					orderDetail.setProduct(pro);
				}
				//将该条信息放入map集合
				Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
				map.put(order, lists);
				//放入作用域
				request.setAttribute("map", map);
				//此时page中的数据为空  用于标识前台不用分页
				//将查询条件放入作用域  便于前台取值
				request.setAttribute("entityId", entityId);
				//转发
				request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
		
		
		//以订货人作为查询条件
		if((name!=null&&!name.trim().isEmpty())&&(entityId==null||(entityId!=null&&entityId.trim().isEmpty()))){
			try {
			//默认首次加载获取第一页的订单编号xinx
			Page page=new Page();
			page.setPageSize(1);
		    int pageIndex=1;
		  //获取符合条件的总记录数
			int totalCount=orderDaoImpl.getCountByUid(name);
			//获取总页数
			 if(totalCount%page.getPageSize()==0){
					page.setTotalPages(totalCount/page.getPageSize());
				}else{
					page.setTotalPages(totalCount/page.getPageSize()+1);
				}
			 //当用户未点击页数时，显示第一页数据
			if(request.getParameter("pageIndex")!=null&&!request.getParameter("pageIndex").equals("")){
				pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
				if(pageIndex>page.getTotalPages()){
					pageIndex=page.getTotalPages();
				} else if(pageIndex<1){
					pageIndex=1;
				}
			}
			page.setPageIndex(pageIndex);
			
			//获取指定索引页的订单
			 Order order=orderDaoImpl.getPagesOrderByUid(page.getPageSize(), page.getPageIndex(), name);
			//根据订单编号获取订单的相关信息
				//获取订单详情
				List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
				//对订单详情中的商品类赋值
				for (OrderDetail orderDetail : lists) {
					
					Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
					orderDetail.setProduct(pro);
				}
				//将该条信息放入map集合
				Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
				map.put(order, lists);
				//放入作用域
				request.setAttribute("map", map);
			//将订货人信息放入作用域  便于前台保存值
				request.setAttribute("name",name);
				//将整个page对象放入作用域     便于前台展示页码数时使用
				request.setAttribute("page",page);
				
				 int liststep = 3;//最多显示分页页数  
					int listbegin = (page.getPageIndex() - (int) Math.floor((double) liststep / 2));//从第几页开始显示分页信息
					if (listbegin < 1) { //当前页-(总显示的页列表数/2)   
			          listbegin = 1;   
			      }else if(listbegin+(int) Math.floor((double) liststep / 2)>page.getTotalPages()){
			    	  listbegin=page.getTotalPages()-liststep+1;
			      }
					int listend=page.getPageIndex() + liststep / 2;
				       
			      if(page.getTotalPages()<liststep){
			    	  listend=page.getTotalPages();
			      }else if(page.getTotalPages()>liststep&&listend<=page.getTotalPages()){
			        listend =listend<liststep?liststep:listend;//分页信息显示到第几页//当前页+(总显示的页列表数/2)
			      }else if (listend > page.getTotalPages()) {    
			          listend = page.getTotalPages();   
			      }
			      page.setListbegin(listbegin);
			      page.setListened(listend);
				
				
				request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		//两个条件结合查询
		if(name!=null&&!name.trim().isEmpty()&&entityId!=null&&!entityId.trim().isEmpty()){
			try {
				Order order=orderDaoImpl.getOrderByOidanduid(entityId, name);
				//如果订单信息不为null  则查询订单详情
				if(order!=null){
					//获取订单详情
					List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
					//对订单详情中的商品类赋值
					for (OrderDetail orderDetail : lists) {
						
						Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
						orderDetail.setProduct(pro);
					}
					//将该条信息放入map集合
					Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
					map.put(order, lists);
					//放入作用域
					request.setAttribute("map", map);
				    //将订货人信息放入作用域  便于前台保存值
					request.setAttribute("name",name);
					request.setAttribute("entityId", entityId);
				}
				
				request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
				
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
	}
	
	/**废弃
	 * 将所有的订单信息、以及当前索引页的订单详情信息  放入作用域中
	 * */
	public void loadlist(HttpServletRequest request, HttpServletResponse response){
		String entityId=request.getParameter("entityId");//获取前台传来的查询条件      订单编号
		String userName=request.getParameter("userName");//获取前台传来的查询条件      订货人
		if((entityId!=null)&&(userName!=null)){//根据订单编号查询订单信息  并分页
		
		}else{
			all(request,response);
		}
	}
	
    /**
     * 展示订单信息   所有
     * */
	public void all(HttpServletRequest request, HttpServletResponse response){
     try {
			
			//分页
			Page page=new Page();
			//设置页面大小
			page.setPageSize(1);
			//默认，索引页面为第一页
			int pageIndex=1;
			//获取订单详情的总记录数
			int allCounts=orderDaoImpl.getAllOrderCount();
			//设置总页数
			if(allCounts%page.getPageSize()==0){//如果整除
				page.setTotalPages(allCounts/page.getPageSize());
			}else{
				page.setTotalPages(allCounts/page.getPageSize()+1);
			}
			//判断索引页
			if(request.getParameter("pageIndex")!=null&&!request.getParameter("pageIndex").equals("")){
				pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
				if(pageIndex>page.getTotalPages()){
					pageIndex=page.getTotalPages();
				} else if(pageIndex<1){
					pageIndex=1;
				}
			}	
			page.setPageIndex(pageIndex);
			//获取该索引页的订单信息
			Order order=orderDaoImpl.getPagesOrder(page.getPageSize(), page.getPageIndex());
			
			//获取对应订单的订单详情
			List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
			List<OrderDetail> list=new ArrayList<OrderDetail>();
			//对订单详情中的商品类赋值
			for (OrderDetail orderDetail : lists) {
				
				Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
				orderDetail.setProduct(pro);
			}
			
			//将该条信息放入map集合
			Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
			map.put(order, lists);
			//放入作用域
			request.setAttribute("map", map);
			//将整个page对象放入作用域     便于前台展示页码数时使用
			request.setAttribute("page",page);
			
			
			 int liststep = 3;//最多显示分页页数  
				int listbegin = (page.getPageIndex() - (int) Math.floor((double) liststep / 2));//从第几页开始显示分页信息
				if (listbegin < 1) { //当前页-(总显示的页列表数/2)   
		          listbegin = 1;   
		      }else if(listbegin+(int) Math.floor((double) liststep / 2)>page.getTotalPages()){
		    	  listbegin=page.getTotalPages()-liststep+1;
		      }
				int listend=page.getPageIndex() + liststep / 2;
			       
		      if(page.getTotalPages()<liststep){
		    	  listend=page.getTotalPages();
		      }else if(page.getTotalPages()>liststep&&listend<=page.getTotalPages()){
		        listend =listend<liststep?liststep:listend;//分页信息显示到第几页//当前页+(总显示的页列表数/2)
		      }else if (listend > page.getTotalPages()) {    
		          listend = page.getTotalPages();   
		      }
		      page.setListbegin(listbegin);
		      page.setListened(listend);
			
			//转发
			request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 废弃
	 * */
	public void selectByentityId(HttpServletRequest request, HttpServletResponse response,String entityId){
		//分页
		Page page=new Page();
		//设置页面大小
		page.setPageSize(1);
		//默认，索引页面为第一页
		int pageIndex=1;
		//获取订单详情的总记录数
		page.setPageIndex(pageIndex);
		Order order=new Order();
		order.setId(Integer.parseInt(entityId));
		try {
			List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
			page.setOrderDetailList(lists);
			//对订单详情中的商品类赋值
			for (OrderDetail orderDetail : lists) {
				
				Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
				orderDetail.setProduct(pro);
			}
			//将该条信息放入map集合
			Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
			map.put(order, lists);
			//放入作用域
			request.setAttribute("map", map);
			//将整个page对象放入作用域     便于前台展示页码数时使用
			request.setAttribute("page",page);
			//转发
			request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进行订单状态的改变
	 * */
	public void change(HttpServletRequest request, HttpServletResponse response){
		//获取传来的状态值
		String statusValue=request.getParameter("num");
		//获取订单编号
		String oid=request.getParameter("oid");
		//根据订单编号去改变数据库中的订单状态
		Order order=new Order();
		order.setId(Integer.parseInt(oid));
		order.setStatus(Integer.parseInt(statusValue));
		try {
			//获取更改结果  并将结果回送回去
			boolean flag=orderDaoImpl.changeStatusByOid(order);
			response.getWriter().print(flag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
