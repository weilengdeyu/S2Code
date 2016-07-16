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
            if(shoppingCart!=null){
            	shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
            }else{
            	shoppingCart=new ShoppingCart();
            	request.getSession().setAttribute("shoppingCart",shoppingCart);
            }
            if(oper.equals("add")){//���м��빺�ﳵ�Ĳ���
            	/**���ж��û��Ƿ��¼
        		 * */
        		if(request.getSession().getAttribute("loginname")==null){//δ��¼����ת����¼ҳ��
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
            }else if(oper.equals("del")){//����ɾ������
            	int index=Integer.parseInt(request.getParameter("itemid"));
            	shoppingCart.removeItem(index);
            	request.getRequestDispatcher("/servlet/doCartShoppingServlet?oper=list").forward(request, response);
            }else if(oper.equals("allItems")){//��ѯ���й��ﳵ�е�����
            	List<ShoppingCartItem> items=shoppingCart.getItems();
            	//�����Զ���listת��JSON���Ĺ��� gjson
            	Gson tool=new Gson();
            	String json=tool.toJson(items);
            	response.setCharacterEncoding("utf-8");
            	response.getWriter().print(json);	
            }else if(oper.equals("givemoney")){//���˲���  һ�����ˣ����γ�һ��������¼
            	//��ѯ���ﳵ�е���Ʒ
            	shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
            	List<ShoppingCartItem> items=shoppingCart.getItems();
            	//���γ�һ�Ŷ���
            	Order order=new Order();
            	//���γɶ�������
            	
            }else if(oper.equals("ProductNumber")){//���������ļӼ�����
            		poductNumber(request,response);
            	
            }
           
		 	
		
	}
	
	/**
	 * ���빺�ﳵչʾʱ
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
	 * �������еĳ�ʼ��Ϣ
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try{
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
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//���й��ﳵ����Ӳ���
	public void addToCart(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//��Ӧ����Ʒ���
    	String proid=request.getParameter("proid");
    	//ȥ���ݿ���Ҷ�Ӧ����Ʒ
    	try {
    		Product product=productDaoImpl.getProductById(Integer.parseInt(proid));
    		//���빺�ﳵ
    		shoppingCart.addItem(product,1);
    		request.getSession().setAttribute("shoppingCart",shoppingCart);
    		List<ShoppingCartItem> items=shoppingCart.getItems();
    		int sum=items.size();
    		request.getSession().setAttribute("sum",sum);
    		response.getWriter().print("true");
    		/**ת��
    		 * */
    		//request.getRequestDispatcher("/servlet/doCartShoppingServlet?oper=list").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    
	//ִ�й��ﳵ�е�+�Ž��еĲ���
	public void poductNumber(HttpServletRequest request, HttpServletResponse response){
		String dotype=request.getParameter("do");//��ʶ���мӼ������ֲ���
		//��ȡ��Ʒ���
		String id=request.getParameter("id");
		//��ȡ���ﳵ�е���Ʒ��Ϣ
		shoppingCart=(ShoppingCart)request.getSession().getAttribute("shoppingCart");
    	List<ShoppingCartItem> items=shoppingCart.getItems();
    	//�����޸ĺ������
		int num=0;
    	if(dotype.equals("addNumber")){//���мӵĲ���
    		//����id����Ʒ������1
    		for (ShoppingCartItem shoppingCartItem : items) {
				if(shoppingCartItem.getProduct().getId()==Integer.parseInt(id)){
					shoppingCartItem.setNum(shoppingCartItem.getNum()+1);
					num=shoppingCartItem.getNum();
					shoppingCartItem.setCost(shoppingCartItem.getNum()*shoppingCartItem.getProduct().getPrice());
					break;
				}
			}
    	}else if(dotype.equals("jianNumber")){//���м�����
    		//����id����Ʒ����-1
    		for (ShoppingCartItem shoppingCartItem : items) {
				if(shoppingCartItem.getProduct().getId()==Integer.parseInt(id)){
					shoppingCartItem.setNum(shoppingCartItem.getNum()-1);
					num=shoppingCartItem.getNum();
					shoppingCartItem.setCost(shoppingCartItem.getNum()*shoppingCartItem.getProduct().getPrice());
					break;
				}
			}
    		
    	}
    	//���й��ﳵ��һ�����²���
    	shoppingCart.setItems(items);    
    	//���½����ﳵ����  ������������
    	request.getSession().setAttribute("shoppingCart", shoppingCart);
    	//������ֵд����Ӧ����
    	response.setCharacterEncoding("utf-8");
        try {
			response.getWriter().print(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
