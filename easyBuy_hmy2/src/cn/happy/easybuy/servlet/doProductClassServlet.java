package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.until.Page;

public class doProductClassServlet extends HttpServlet {

	
	ProductCategoryDaoImpl productCategoryDaoImpl=new ProductCategoryDaoImpl();
	
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
		//�������
          request.setCharacterEncoding("utf-8");
          String oper=request.getParameter("oper");
		if(oper.equals("list")){//����������ҳ��
			getCatePages(request,response);		
			
		}else if(oper.equals("add")){//����תվ��Я�����ݵ�add.jspҳ��
			load(request,response);
			request.getRequestDispatcher("/manage/productClass-add.jsp").forward(request, response);
		}else if(oper.equals("addtrue")){//������������Ӳ���
			add(request,response);
		}else if(oper.equals("delChildCate")){//ɾ��ѡ�е��ӷ���
			delChileCate(request,response);
		}else if(oper.equals("delPcate")){//ɾ��ѡ�е�һ������
			delPCate(request,response);
		}else if(oper.equals("modify")){//����תվ  Я�����ݵ�modify.jspҳ��
			modify(request,response);
		}else if(oper.equals("modifytrue")){//�����������޸Ĳ���
			modifytrue(request,response);
		}
	}

	
	/**
	 * ����������ҳ��
	 * */
	public void getCatePages(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//���ն�������ĸ������з�ҳ
		try{
		 // ʵ����page���󣬸�page��index��size��ֵ����ʾ��һҳ������ʾ8����¼
		   Page page=new Page();
		   page.setPageSize(1);
		   int pageIndex=1;
		   int totalPages=0;
		   //��ȡ�ܼ�¼��
			totalPages = productCategoryDaoImpl.getCountPid();
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
		   ProductCategory pc=productCategoryDaoImpl.getPagesPid(page.getPageIndex(), page.getPageSize());
		   //����һ���������Ϣ���Ҷ�Ӧ�Ķ����������Ϣ
		   
		   List<ProductCategory> list=productCategoryDaoImpl.getChildCateByPid(pc.getId());
		   
		   //����Ӧ��Ϣ����map����
		   Map<ProductCategory,List<ProductCategory>> map=new HashMap<ProductCategory,List<ProductCategory>>();
		   map.put(pc, list);
		   
		   //��map���Ϸ���������
		   request.setAttribute("map",map);
			
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
		   
			//ת��
			request.getRequestDispatcher("/manage/productClass.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����е�һ������  �� �����������������
	 */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//���е�һ������  ������������
			List<ProductCategory> parentList=productCategoryDaoImpl.getAllParentCate();
			request.setAttribute("parentList", parentList);
			//���еĶ�������  ������������
			List<ProductCategory> childList=productCategoryDaoImpl.getAllChildCate();
			request.setAttribute("childList", childList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������������Ӳ���
	 * */
	public void add(HttpServletRequest request, HttpServletResponse response){
		//��ȡ����ӷ���ĸ�����
		String pcate=request.getParameter("parentId");
		//��ȡ����ӵķ��������
		String cate=request.getParameter("className");
		
		ProductCategory productCategory=new ProductCategory();
		productCategory.setName(cate);
		productCategory.setParentId(Integer.parseInt(pcate));
		
		try {
			boolean flag=productCategoryDaoImpl.add(productCategory);
			if(flag){//��ӳɹ�
				System.out.println("��ӳɹ���");
			}else{
				System.out.println("���ʧ�ܣ�");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��ѡ�еĶ�������
	 * 
	 * */
	public void delChileCate(HttpServletRequest request, HttpServletResponse response){
		//��ȡѡ��ɾ���Ķ��������id
		String id=request.getParameter("id");
		try {
			boolean flag=productCategoryDaoImpl.delChildCate(Integer.parseInt(id));
			if(flag){//ɾ���ɹ�
				System.out.println("ɾ���ɹ���");
			}else{
				System.out.println("ɾ��ʧ�ܣ�");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * ɾ��ѡ�е�һ������
     * */
	public void delPCate(HttpServletRequest request, HttpServletResponse response){
		//��ȡѡ�е�һ�������id
		String pid=request.getParameter("id");
		try {
			boolean flag=productCategoryDaoImpl.delPcate(Integer.parseInt(pid));
			if(flag){
				System.out.println("ɾ���ɹ���");
			}else{
				System.out.println("ɾ��ʧ�ܣ�");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���޸ĵ���תվ
	 * */
	public void modify(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//��ȡҪ�޸ĵķ���ı��
		String id=request.getParameter("id");
		//���ݷ����Ų��Ҹ÷������Ϣ
		try {
			ProductCategory productCategory=productCategoryDaoImpl.getInfoById(Integer.parseInt(id));
			//������Ϣ���������򣬱���ǰ̨չʾ
			request.setAttribute("productCategory", productCategory);
			//���ݲ��һ�������Ϣ��ȡ�丸id  ��ȡ������������Ϣ
			ProductCategory PCategory=productCategoryDaoImpl.getInfoById(productCategory.getParentId());
			//����������
			request.setAttribute("PCategory", PCategory);
			//ת��
			request.getRequestDispatcher("/manage/productClass-modify.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����������޸Ĳ���
	 * */
	public void modifytrue(HttpServletRequest request, HttpServletResponse response){
		//��ȡҪ�޸ĵķ���ı��
		String id=request.getParameter("id");
		//��ȡ�޸ĺ������
		String value=request.getParameter("className");
		//��ȡ�޸ĺ���һ�����໹�Ƕ�������  ����������
		String pid=request.getParameter("parentId");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setId(Integer.parseInt(id));
		productCategory.setName(value);
		productCategory.setParentId(Integer.parseInt(pid));
        //�����޸Ĳ���
		try {
			boolean flag=productCategoryDaoImpl.modifyByid(productCategory);
			if(flag){//�޸ĳɹ�
				System.out.println("�޸ĳɹ���");
			}else{
				System.out.println("�޸�ʧ�ܣ�");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
}
