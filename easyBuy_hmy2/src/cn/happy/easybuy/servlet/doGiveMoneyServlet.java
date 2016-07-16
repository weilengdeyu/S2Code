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
		 ���¾���
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 ���¾���
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  //�������
		   request.setCharacterEncoding("utf-8");
		  String oper=request.getParameter("oper");
		  if(oper.equals("givemoney")){//����
			  String type=request.getParameter("type");//�ж���ֱ�ӹ��򣬻��ǹ��ﳵ����
			  if(type.equals("direct")){//ֱ�ӹ����γ�һ�Ŷ�����¼��һ�����������¼
				  directBuy(request,response);
			  }else if(type.equals("cartbuy")){//ѡ���ﳵ���㣬�γ�һ��������¼�Ͷ�Ӧ�����Ķ��������¼
				  cartBuy(request,response);
			  }
			  
		  }	
	}

	
	/**�������г�ʼ��Ϣ
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//���е�һ������
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//���еĶ�������
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//���е���Ʒ��Ϣ
			List<Product> productlist=productDaoImpl.getAllProduct();
			request.setAttribute("productlist",productlist);
			request.setAttribute("childlistonly",childlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**ֱ�ӵ������
	 * */
	public void directBuy(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		try {
			//��ȡ�ջ���ַ
			String address=request.getParameter("address");
			address=new String(address.getBytes("iso-8859-1"),"utf-8");
			//��ȡ�����û�����Ϣ
			String uid=(String)request.getSession().getAttribute("loginname");
			/**
			 * �����û�id��ȡ�û��������Ϣ 
			* */
			User user=userDaoImpl.getUserInfoByUid(uid);
			//��ȡ��ֱ�ӹ������Ʒ��Ϣ
			Product product=(Product)request.getSession().getAttribute("directbuy");
			//�γ�һ�Ŷ�����¼
			Order order=new Order();
			order.setCost(product.getPrice());
			//��ȡ��ǰ����
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
			if(flag){//������¼��ӳɹ�
				//��Ӷ�������
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(product.getPrice());
				//��ȡ�������
				int oid=OrderDaoImpl.getNewOid();
				orderDetail.setOrderId(oid);
				orderDetail.setProductId(product.getId());
				orderDetail.setQuantity(1);
				boolean flag2=orderDetitalImpl.addOrderDetital(orderDetail);
				if(flag2){//�����������ӳɹ�
					//��ת������ɹ�ҳ��
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
	 * �ӹ��ﳵ����
	 * */
	public void cartBuy(HttpServletRequest request, HttpServletResponse response){
		try {
		//��ȡ�ջ���ַ
		String address=request.getParameter("address");
		address=new String(address.getBytes("iso-8859-1"),"utf-8");
		//��ȡ�����û�����Ϣ
		String uid=(String)request.getSession().getAttribute("loginname");
		/**
		 * �����û�id��ȡ�û��������Ϣ 
		* */
		User user=userDaoImpl.getUserInfoByUid(uid);
		
		//��ȡ���ﳵ�еļ���
		ShoppingCart sh=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
		List<ShoppingCartItem> items=sh.getItems();
		double totalcost=0;
		//�γ�һ�Ŷ�����¼
		Order order=new Order();
		for (ShoppingCartItem shoppingCartItem : items) {
			totalcost+=shoppingCartItem.getCost();
		}
		order.setCost(totalcost);
		//��ȡ��ǰ����
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
		//��ȡ�������
		int oid=OrderDaoImpl.getNewOid();
		for (ShoppingCartItem shoppingCartItem : items) {
			if(flag){//������¼��ӳɹ�
				//��Ӷ�������
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(shoppingCartItem.getCost());
				orderDetail.setOrderId(oid);
				orderDetail.setProductId(shoppingCartItem.getProduct().getId());
				orderDetail.setQuantity(1);
				flag2=orderDetitalImpl.addOrderDetital(orderDetail);
			}
		}
		if(flag2){//�����������ӳɹ�
			//�����ύ�ɹ�֮�� ������ﳵ�е�����
			request.getSession().removeAttribute("shoppingCart");
			//��ת������ɹ�ҳ��
			request.setAttribute("oid",oid);
			request.getRequestDispatcher("/shopping-result.jsp").forward(request, response);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
