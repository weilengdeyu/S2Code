package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.happy.easybuy.dao.impl.NewsDaoImpl;
import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.News;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.entity.User;
import cn.happy.easybuy.until.Page;

public class doIndexServlet extends HttpServlet {

	/**
		���¾���
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	  //ʵ����ʹ�õ�����
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	   NewsDaoImpl ndi = new NewsDaoImpl();	
	   ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	   UserDaoImpl userDaoImpl=new UserDaoImpl();
	/**
		���¾���
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�������
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if(oper!=null){
			 if(oper.equals("loadchildByParent")){//����һ��������ض�Ӧ����Ϣ
				 getChildInfoByParentId(request,response);
			 }else if(oper.equals("getPages")){//�������ҳʱ���еĲ���
				 getChildInfoByParentId(request,response);
			 }
		}else{//��һ�μ�����ҳ
			loadIndexInfo(request,response);
	    }  
		 
	}


	/**
	     �û��״ν���ʱ����һ�����࣬�������࣬������Ϣ����Ʒ��Ϣ
     */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try{
			//���е�һ������
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//���еĶ�������
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//����������Ϣ
			List<News> newslist =ndi.getAllNews();
			request.setAttribute("newslist", newslist);
			//���е���Ʒ��Ϣ
			List<Product> productlist=productDaoImpl.getAllProduct();
			request.setAttribute("productlist",productlist);
			request.setAttribute("childlistonly",childlist);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * ��ҳ����
	 */
	public void loadIndexInfo(HttpServletRequest request, HttpServletResponse response){
		load(request,response); 
		String str=null;
		if(request.getSession().getAttribute("loginname")==null){
			//����¼��Ϣ���浽session��
			str=request.getParameter("loginname");
			request.getSession().setAttribute("loginname",str);
		}else{
			str=(String)request.getSession().getAttribute("loginname");
		}
		
		Page page=new Page();
		Page pages=page.getPages(request, response, 8);
		request.setAttribute("pages", pages);
		
		try {
			   //�����û�����ѯ��Ӧ���û���Ϣ
			if(request.getSession().getAttribute("userInfo")==null){
				User user=userDaoImpl.getUserInfoByUid(str);
				//����session��
				request.getSession().setAttribute("userInfo", user);
			}
				//User user=userDaoImpl.getUserInfoByUid(str);
				//����session��
				//request.setAttribute("userInfo", user);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  /**
   * ����һ�����࣬���ض�Ӧ�Ķ������࣬��Ʒ��Ϣ
   */
   public void getChildInfoByParentId(HttpServletRequest request, HttpServletResponse response){
	   load(request,response);
	   //һ������ı��
	   String pid=request.getParameter("id");
	   if(Integer.parseInt(pid)==0){
		   loadIndexInfo(request,response);
	   }else{
	   load(request,response);
	   /**
	    * ��ȡ��Ӧһ�������µ���Ʒ��Ϣ
	    * */
	   // ʵ����page���󣬸�page��index��size��ֵ����ʾ��һҳ������ʾ8����¼
	   Page page=new Page();
	   page.setPageSize(8);
	   int pageIndex=1;
	   int totalPages=0;
	   
	   
	   
	   try {
	   if(Integer.parseInt(pid)==1){//�ܷ���
		   //�ܼ�¼��
		   totalPages = productDaoImpl.getAllRecords();
		   if(totalPages%page.getPageSize()==0){
				page.setTotalPages(totalPages/page.getPageSize());
			}else{
				page.setTotalPages(totalPages/page.getPageSize()+1);
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
		List<Product> list = productDaoImpl.getPageList(page.getPageSize(), page.getPageIndex());
		page.setList(list);
		  
		   
	   }else{
		   totalPages = productDaoImpl.getproductCountByPid(Integer.parseInt(pid));
		   //Ϊ��ҳ����ֵ
		   if(totalPages%page.getPageSize()==0){
				page.setTotalPages(totalPages/page.getPageSize());
			}else{
				page.setTotalPages(totalPages/page.getPageSize()+1);
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
		   List<Product> list=productDaoImpl.getProductPagesByPid(page.getPageSize(), page.getPageIndex(), Integer.parseInt(pid));
			page.setList(list);
		   
	   }
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
    //������������
	   request.setAttribute("pages", page);
	   /**
	    * ��ȡ��Ӧһ�������µĶ���������Ϣ
	    * */
	   List<ProductCategory> childlist = pcd.getChildCateByPid(Integer.parseInt(pid));
	
	   request.setAttribute("childlistonly",childlist);
	   // ��pid���õ����������ڣ������ж���ҳ
		if (Integer.parseInt(pid) == 0) {

		} else {
			request.setAttribute("id", pid);
		}
		
		
		
		
		
		/**
		 * ת������ҳ
		 * */
			request.getRequestDispatcher("/index.jsp").forward(request,response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   
   }
	
	
	
	
	

}
