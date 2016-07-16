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
		 出品人：岁月静好
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 出品人：岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           request.setCharacterEncoding("utf-8");
           String oper=request.getParameter("oper");
           if(oper.equals("list")){//չʾ������Ϣ
        	   loadlist(request,response);
           }else if(oper.equals("select")){//����������ѯ��صĶ�����Ϣ
        	   //���ݶ����Ų�ѯ������Ϣ�򶩻��˲�ѯ������Ϣ
        	   loadOrderDetialInfoByOid(request,response);
        	  
           }else if(oper.equals("change")){//���ж���״̬�ĸı�
        	   change(request,response);
           }
	}

	/**
	 * ���ݶ����Ų�ѯ������Ϣ
	 * @throws UnsupportedEncodingException 
	 * */
	public void loadOrderDetialInfoByOid(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		//��ȡǰ̨�����Ķ�����
		String entityId=request.getParameter("entityId");
		//��ȡǰ̨�����Ķ�����
		String name=request.getParameter("userName");
		
		if(entityId==null&&name==null){//����Ϊ�ղ�ѯ����
			
			all(request,response);
		}
		if(entityId!=null&&entityId.trim().isEmpty()&&name!=null&&name.trim().isEmpty()){
			
			all(request,response);
			
		}
		
		
		
		
		//��������Ų�Ϊ��������Զ����Ų�ѯ����������Ϣ
		if((entityId!=null&&!entityId.trim().isEmpty())&&(name==null||(name!=null&&name.trim().isEmpty()))){
			try {
				Order order=new Order();
				order.setId(Integer.parseInt(entityId));
				//��ȡ��������
				List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
				//���ݶ�����Ż�ȡ�����������Ϣ
				order=orderDaoImpl.getInfoByOid(order);
				//�Զ��������е���Ʒ�ำֵ
				for (OrderDetail orderDetail : lists) {
					
					Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
					orderDetail.setProduct(pro);
				}
				//��������Ϣ����map����
				Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
				map.put(order, lists);
				//����������
				request.setAttribute("map", map);
				//��ʱpage�е�����Ϊ��  ���ڱ�ʶǰ̨���÷�ҳ
				//����ѯ��������������  ����ǰ̨ȡֵ
				request.setAttribute("entityId", entityId);
				//ת��
				request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
		
		
		//�Զ�������Ϊ��ѯ����
		if((name!=null&&!name.trim().isEmpty())&&(entityId==null||(entityId!=null&&entityId.trim().isEmpty()))){
			try {
			//Ĭ���״μ��ػ�ȡ��һҳ�Ķ������xinx
			Page page=new Page();
			page.setPageSize(1);
		    int pageIndex=1;
		  //��ȡ�����������ܼ�¼��
			int totalCount=orderDaoImpl.getCountByUid(name);
			//��ȡ��ҳ��
			 if(totalCount%page.getPageSize()==0){
					page.setTotalPages(totalCount/page.getPageSize());
				}else{
					page.setTotalPages(totalCount/page.getPageSize()+1);
				}
			 //���û�δ���ҳ��ʱ����ʾ��һҳ����
			if(request.getParameter("pageIndex")!=null&&!request.getParameter("pageIndex").equals("")){
				pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
				if(pageIndex>page.getTotalPages()){
					pageIndex=page.getTotalPages();
				} else if(pageIndex<1){
					pageIndex=1;
				}
			}
			page.setPageIndex(pageIndex);
			
			//��ȡָ������ҳ�Ķ���
			 Order order=orderDaoImpl.getPagesOrderByUid(page.getPageSize(), page.getPageIndex(), name);
			//���ݶ�����Ż�ȡ�����������Ϣ
				//��ȡ��������
				List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
				//�Զ��������е���Ʒ�ำֵ
				for (OrderDetail orderDetail : lists) {
					
					Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
					orderDetail.setProduct(pro);
				}
				//��������Ϣ����map����
				Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
				map.put(order, lists);
				//����������
				request.setAttribute("map", map);
			//����������Ϣ����������  ����ǰ̨����ֵ
				request.setAttribute("name",name);
				//������page�������������     ����ǰ̨չʾҳ����ʱʹ��
				request.setAttribute("page",page);
				
				 int liststep = 3;//�����ʾ��ҳҳ��  
					int listbegin = (page.getPageIndex() - (int) Math.floor((double) liststep / 2));//�ӵڼ�ҳ��ʼ��ʾ��ҳ��Ϣ
					if (listbegin < 1) { //��ǰҳ-(����ʾ��ҳ�б���/2)   
			          listbegin = 1;   
			      }else if(listbegin+(int) Math.floor((double) liststep / 2)>page.getTotalPages()){
			    	  listbegin=page.getTotalPages()-liststep+1;
			      }
					int listend=page.getPageIndex() + liststep / 2;
				       
			      if(page.getTotalPages()<liststep){
			    	  listend=page.getTotalPages();
			      }else if(page.getTotalPages()>liststep&&listend<=page.getTotalPages()){
			        listend =listend<liststep?liststep:listend;//��ҳ��Ϣ��ʾ���ڼ�ҳ//��ǰҳ+(����ʾ��ҳ�б���/2)
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
		
		//����������ϲ�ѯ
		if(name!=null&&!name.trim().isEmpty()&&entityId!=null&&!entityId.trim().isEmpty()){
			try {
				Order order=orderDaoImpl.getOrderByOidanduid(entityId, name);
				//���������Ϣ��Ϊnull  ���ѯ��������
				if(order!=null){
					//��ȡ��������
					List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
					//�Զ��������е���Ʒ�ำֵ
					for (OrderDetail orderDetail : lists) {
						
						Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
						orderDetail.setProduct(pro);
					}
					//��������Ϣ����map����
					Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
					map.put(order, lists);
					//����������
					request.setAttribute("map", map);
				    //����������Ϣ����������  ����ǰ̨����ֵ
					request.setAttribute("name",name);
					request.setAttribute("entityId", entityId);
				}
				
				request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
				
				
			} catch (Exception e) {
			
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
	}
	
	/**����
	 * �����еĶ�����Ϣ���Լ���ǰ����ҳ�Ķ���������Ϣ  ������������
	 * */
	public void loadlist(HttpServletRequest request, HttpServletResponse response){
		String entityId=request.getParameter("entityId");//��ȡǰ̨�����Ĳ�ѯ����      �������
		String userName=request.getParameter("userName");//��ȡǰ̨�����Ĳ�ѯ����      ������
		if((entityId!=null)&&(userName!=null)){//���ݶ�����Ų�ѯ������Ϣ  ����ҳ
		
		}else{
			all(request,response);
		}
	}
	
    /**
     * չʾ������Ϣ   ����
     * */
	public void all(HttpServletRequest request, HttpServletResponse response){
     try {
			
			//��ҳ
			Page page=new Page();
			//����ҳ���С
			page.setPageSize(1);
			//Ĭ�ϣ�����ҳ��Ϊ��һҳ
			int pageIndex=1;
			//��ȡ����������ܼ�¼��
			int allCounts=orderDaoImpl.getAllOrderCount();
			//������ҳ��
			if(allCounts%page.getPageSize()==0){//�������
				page.setTotalPages(allCounts/page.getPageSize());
			}else{
				page.setTotalPages(allCounts/page.getPageSize()+1);
			}
			//�ж�����ҳ
			if(request.getParameter("pageIndex")!=null&&!request.getParameter("pageIndex").equals("")){
				pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
				if(pageIndex>page.getTotalPages()){
					pageIndex=page.getTotalPages();
				} else if(pageIndex<1){
					pageIndex=1;
				}
			}	
			page.setPageIndex(pageIndex);
			//��ȡ������ҳ�Ķ�����Ϣ
			Order order=orderDaoImpl.getPagesOrder(page.getPageSize(), page.getPageIndex());
			
			//��ȡ��Ӧ�����Ķ�������
			List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
			List<OrderDetail> list=new ArrayList<OrderDetail>();
			//�Զ��������е���Ʒ�ำֵ
			for (OrderDetail orderDetail : lists) {
				
				Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
				orderDetail.setProduct(pro);
			}
			
			//��������Ϣ����map����
			Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
			map.put(order, lists);
			//����������
			request.setAttribute("map", map);
			//������page�������������     ����ǰ̨չʾҳ����ʱʹ��
			request.setAttribute("page",page);
			
			
			 int liststep = 3;//�����ʾ��ҳҳ��  
				int listbegin = (page.getPageIndex() - (int) Math.floor((double) liststep / 2));//�ӵڼ�ҳ��ʼ��ʾ��ҳ��Ϣ
				if (listbegin < 1) { //��ǰҳ-(����ʾ��ҳ�б���/2)   
		          listbegin = 1;   
		      }else if(listbegin+(int) Math.floor((double) liststep / 2)>page.getTotalPages()){
		    	  listbegin=page.getTotalPages()-liststep+1;
		      }
				int listend=page.getPageIndex() + liststep / 2;
			       
		      if(page.getTotalPages()<liststep){
		    	  listend=page.getTotalPages();
		      }else if(page.getTotalPages()>liststep&&listend<=page.getTotalPages()){
		        listend =listend<liststep?liststep:listend;//��ҳ��Ϣ��ʾ���ڼ�ҳ//��ǰҳ+(����ʾ��ҳ�б���/2)
		      }else if (listend > page.getTotalPages()) {    
		          listend = page.getTotalPages();   
		      }
		      page.setListbegin(listbegin);
		      page.setListened(listend);
			
			//ת��
			request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����
	 * */
	public void selectByentityId(HttpServletRequest request, HttpServletResponse response,String entityId){
		//��ҳ
		Page page=new Page();
		//����ҳ���С
		page.setPageSize(1);
		//Ĭ�ϣ�����ҳ��Ϊ��һҳ
		int pageIndex=1;
		//��ȡ����������ܼ�¼��
		page.setPageIndex(pageIndex);
		Order order=new Order();
		order.setId(Integer.parseInt(entityId));
		try {
			List<OrderDetail> lists=orderDetitalImpl.getDetailByOid(order);
			page.setOrderDetailList(lists);
			//�Զ��������е���Ʒ�ำֵ
			for (OrderDetail orderDetail : lists) {
				
				Product pro=productDaoImpl.getProductById(orderDetail.getProductId());
				orderDetail.setProduct(pro);
			}
			//��������Ϣ����map����
			Map<Order,List<OrderDetail>> map=new HashMap<Order,List<OrderDetail>>();
			map.put(order, lists);
			//����������
			request.setAttribute("map", map);
			//������page�������������     ����ǰ̨չʾҳ����ʱʹ��
			request.setAttribute("page",page);
			//ת��
			request.getRequestDispatcher("/manage/order.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ж���״̬�ĸı�
	 * */
	public void change(HttpServletRequest request, HttpServletResponse response){
		//��ȡ������״ֵ̬
		String statusValue=request.getParameter("num");
		//��ȡ�������
		String oid=request.getParameter("oid");
		//���ݶ������ȥ�ı����ݿ��еĶ���״̬
		Order order=new Order();
		order.setId(Integer.parseInt(oid));
		order.setStatus(Integer.parseInt(statusValue));
		try {
			//��ȡ���Ľ��  ����������ͻ�ȥ
			boolean flag=orderDaoImpl.changeStatusByOid(order);
			response.getWriter().print(flag);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
