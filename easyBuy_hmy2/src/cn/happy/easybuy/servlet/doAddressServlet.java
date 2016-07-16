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
		  if(oper.equals("buy")){//ֱ�ӵ������ ���ﳵ�������ʱ����ת����servlet����ת��address.jspҳ��
			  //���ж��û��Ƿ��¼
			  if(request.getSession().getAttribute("loginname")==null){//δ��¼���Ƚ����¼ҳ��
				  request.getRequestDispatcher("/login.jsp").forward(request, response);
			  }else{//�������ҳ��
				  buy(request,response);
			  }
			  
		  }
		
	   }
	
	/**�������г�ʼ��Ϣ
	 * */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//���е�һ������
			List<ProductCategory> parentlist= pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//���еĶ�������
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			request.setAttribute("childlistonly",childlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**ֱ�ӵ������
	 * */
	public void buy(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//��ѯ��Ӧ�û����ջ���Ϣ
		String uid=(String)request.getSession().getAttribute("loginname");
		try {
			/**���������ͷ���������  ��ֱ�ӵ������ ���Ǵӹ��ﳵ���н���
			 * */
			String type=request.getParameter("type");
			request.setAttribute("type",type);
			//��ȡֱ�ӹ������Ʒ���
			String proid=request.getParameter("proid");
			if(proid!=null){
				//������Ʒ��Ų�����Ʒ��Ϣ ������session������
				Product product=productDaoImpl.getProductById(Integer.parseInt(proid));
				request.getSession().setAttribute("directbuy", product);
			}
			User user=userDaoImpl.getUserInfoByUid(uid);
			//���û���Ϣ����������
			request.setAttribute("user",user);
			request.getRequestDispatcher("/address.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
