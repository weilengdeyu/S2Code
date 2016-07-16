package cn.happy.easybuy.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.happy.easybuy.dao.impl.NewsDaoImpl;
import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.until.Page;

public class doProductServlet extends HttpServlet {

	ProductDaoImpl productDaoImpl=new ProductDaoImpl();
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
            if(oper.equals("prodetital")){//�鿴��Ʒ����
            	loadProductDetatil(request,response);
            }else if(oper.equals("proCateSelect")){//��������Ӧ�ķ���鿴��Ӧ����Ʒ
            	loadProductInfoByLeftCate(request,response);
            }else if(oper.equals("proManage")){//��̨��Ʒ����
            	proManage(request,response);
            	
            }else if(oper.equals("houTaiModify")){//��̨��Ʒ���޸�
            	houTaiModify(request,response);
            }else if(oper.equals("houTaiModifyTrue")){//���к�̨�������޸Ĳ���
            	
            	houTaiModifyTrue(request,response);
            }else if(oper.equals("houTaidel")){//��̨��Ʒ��ɾ��
            	houTaidel(request,response);
            }else if(oper.equals("backProductAdd")){//Я���������ݵ���Ʒ���ҳ��
            	
            	backProductAdd(request,response);
            }else if(oper.equals("backProductAddTrue")){//���к�̨��Ʒ��������Ӳ���
            	addTrue(request,response);
            }
	}
	
	
	/**
	 * ��̨��Ʒ���  ����תվ
	 * */
	public void backProductAdd(HttpServletRequest request, HttpServletResponse response){
		//�������еķ�����Ϣ���ü��ϱ���
		getCateByList(request,response);
		//ת��
		try {
			request.getRequestDispatcher("/manage/product-add.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
        
	}
	
	/**
	 * �������еķ�����Ϣ���ü��ϱ���
	 * */
	public void getCateByList(HttpServletRequest request, HttpServletResponse response){
		try {
			//������������Ӧ�����༯����ֵ
			Map<ProductCategory, List<ProductCategory>> categoryMap = new HashMap<ProductCategory, List<ProductCategory>>();
			//������������Ӧ���༯�ϵĸ�����ֵ
			Map<ProductCategory, Integer> indexMap = new HashMap<ProductCategory, Integer>();
			//����һ�����༯��
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			for (int i = 0; i < parentlist.size(); i++) {
				//����һ�������Ż�ȡ��Ӧ�Ķ�������ļ���
				List<ProductCategory> childs = pcd.getChildCateByPid(parentlist.get(i).getId());
				//���ݶ�Ӧ��ϵ����categoryMap������
				categoryMap.put(parentlist.get(i), childs);
				indexMap.put(parentlist.get(i), childs.size()-1);
			}
			//�����Ϸ���������
			request.setAttribute("categoryMap", categoryMap);
			request.setAttribute("indexMap", indexMap);
			request.setAttribute("parentlist", parentlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ���к�̨��Ʒ����������Ӳ���
	 * @throws UnsupportedEncodingException 
	 * */
	public void addTrue(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		        //�����Ʒ
				Product product=new Product();
				String uploadFileName="";//�ϴ����ļ���
		        String fieldName="";//���ֶ�Ԫ�ص�name����ֵ
				//�ж������е���Ϣ�Ƿ���multipart����
		        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
		        //�ϴ��ļ��Ĵ洢·��
		        String uploadFilePath=request.getSession().getServletContext().getRealPath("images/product");
		        //�����multipart�����ϴ�
		        if(isMultipart){
		        	FileItemFactory factory=new DiskFileItemFactory();
		        	ServletFileUpload upload=new ServletFileUpload(factory);
					try {
						 //����form���������ļ�  ÿһ��items����һ����Ԫ��
						 List<FileItem> items = upload.parseRequest(request);
						 Iterator<FileItem> iter=items.iterator();
						 //���δ���ÿ���ļ�
						 while(iter.hasNext()){
							 FileItem item=(FileItem)iter.next();
							 if(item.isFormField()){//��ͨ���ֶ�
								 //��ȡ���ֶε�name����ֵ
								 fieldName=item.getFieldName();
								 if(fieldName.equals("productName")){//��Ʒ����
									 product.setName(item.getString("utf-8"));
								 }else if(fieldName.equals("productDetail")){//��Ʒ����
									 product.setDescription(item.getString("utf-8"));
								 }else if(fieldName.equals("parentId")){//��Ʒ��������
									 product.setChileCategoryId(Integer.parseInt(item.getString("utf-8")));
								 }else if(fieldName.equals("productPrice")){//��Ʒ�۸�
									 product.setPrice(Double.parseDouble(item.getString("utf-8")));
								 }else if(fieldName.equals("productNumber")){//���
									 product.setStock(Integer.parseInt(item.getString("utf-8")));
								 }
								 
							 }else{//�ļ����ֶ�
								 //�ļ����ֶε�name����ֵ
								 String fileName=item.getName();
								 if(fileName!=null&&!fileName.equals("")){
									 File fullFile=new File(item.getName());
									 File saveFile=new File(uploadFilePath,fullFile.getName());
									 product.setFileName(fileName);
									 try {
										item.write(saveFile);
									} catch (Exception e) {
										e.printStackTrace();
									}
								 }
							 }
						 }
					} catch (FileUploadException e) {
						e.printStackTrace();
					}
					
		        }
		        //������Ʒ���뵽��̨���ݿ�
		        try {
					boolean flag=productDaoImpl.addProduct(product);
					if(flag){
						request.getRequestDispatcher("/servlet/doProductServlet?oper=proManage").forward(request, response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}
	
	
	/**
	 * ��̨��Ʒ��ɾ��
	 * */
    public void houTaidel(HttpServletRequest request, HttpServletResponse response){
    	//��ȡҪɾ������Ʒ�ı��
    	String id=request.getParameter("id");
    	try {
			boolean flag=productDaoImpl.delByPId(Integer.parseInt(id));
			//ת��
			request.getRequestDispatcher("/servlet/doProductServlet?oper=proManage").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * ���к�̨�������޸Ĳ���
	 * @throws UnsupportedEncodingException 
	 * */
	public void houTaiModifyTrue(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		//�޸���Ʒ
		Product product=new Product();
		
		//��ȡҪ�޸ĵ���Ʒ�ı��
		String id=request.getParameter("id");
		product.setId(Integer.parseInt(id));
		
		String uploadFileName="";//�ϴ����ļ���
        String fieldName="";//���ֶ�Ԫ�ص�name����ֵ
		//�ж������е���Ϣ�Ƿ���multipart����
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        //�ϴ��ļ��Ĵ洢·��
        String uploadFilePath=request.getSession().getServletContext().getRealPath("images/product");
        //�����multipart�����ϴ�
        if(isMultipart){
        	FileItemFactory factory=new DiskFileItemFactory();
        	ServletFileUpload upload=new ServletFileUpload(factory);
			try {
				 //����form���������ļ�  ÿһ��items����һ����Ԫ��
				 List<FileItem> items = upload.parseRequest(request);
				 Iterator<FileItem> iter=items.iterator();
				 //���δ���ÿ���ļ�
				 while(iter.hasNext()){
					 FileItem item=(FileItem)iter.next();
					 if(item.isFormField()){//��ͨ���ֶ�
						 //��ȡ���ֶε�name����ֵ
						 fieldName=item.getFieldName();
						 if(fieldName.equals("productName")){//��Ʒ����
							 product.setName(item.getString("utf-8"));
						 }else if(fieldName.equals("productdescription")){//��Ʒ����
							 product.setDescription(item.getString("utf-8"));
						 }else if(fieldName.equals("parentId")){//��Ʒ��������
							 product.setChileCategoryId(Integer.parseInt(item.getString("utf-8")));
						 }else if(fieldName.equals("productPrice")){//��Ʒ�۸�
							 product.setPrice(Double.parseDouble(item.getString("utf-8")));
						 }else if(fieldName.equals("productstock")){//���
							 product.setStock(Integer.parseInt(item.getString("utf-8")));
						 }
						 
					 }else{//�ļ����ֶ�
						 //�ļ����ֶε�name����ֵ
						 String fileName=item.getName();
						 if(fileName!=null&&!fileName.equals("")){
							 File fullFile=new File(item.getName());
							 File saveFile=new File(uploadFilePath,fullFile.getName());
							 product.setFileName(fileName);
							 try {
								item.write(saveFile);
							} catch (Exception e) {
								e.printStackTrace();
							}
						 }
					 }
				 }
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
        }
        
        //���к�̨���ݿ�ĸ���
        try {
			boolean flag=productDaoImpl.modifyProductByProId(product);
			if(flag){
				request.getRequestDispatcher("/servlet/doProductServlet?oper=proManage").forward(request, response);
			}else{
				request.getRequestDispatcher("/servlet/doProductServlet?oper=houTaiModify&id="+id).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��̨��Ʒ���޸�
	 * */
	public void houTaiModify(HttpServletRequest request, HttpServletResponse response){
		getCateByList(request,response);
		//��ȡ�޸ĵ���Ʒ�ı��
		String id=request.getParameter("id");
		//��ȡ������Ʒ��ŵ���ϸ��Ϣ
		try {
			Product product=productDaoImpl.getProductById(Integer.parseInt(id));
			//����������
			request.setAttribute("product", product);
			//ת��
			request.getRequestDispatcher("/manage/product-modify.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * ��̨��Ʒ������ת����̨��product.jsp
	 * */

	public void proManage(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		
		//��ȡ��ҳ��ļ��ϴ���ǰ̨
		// ʵ����page���󣬸�page��index��size��ֵ����ʾ��һҳ������ʾ8����¼
		   Page page=new Page();
		   page.setPageSize(4);
		   int pageIndex=1;
		   int totalPages=0;
		try {
			totalPages = productDaoImpl.getAllRecords();
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
		   List<Product> list=productDaoImpl.getPageList(page.getPageSize(), page.getPageIndex());
			page.setList(list);
		   //������������
		   request.setAttribute("pages", page);
		   
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
		   request.getRequestDispatcher("/manage/product.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * �������е���Ʒ��Ϣ
	 * */
	public void loadAll(HttpServletRequest request, HttpServletResponse response){
		try{
			//���������������������
        	List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//��һ�����������������
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//��������Ʒ������������
			List<Product> list=productDaoImpl.getAllProduct();
			request.setAttribute("list",list);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �鿴��Ʒ����
	 * */
	public void loadProductDetatil(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		try {
			String id=request.getParameter("proid");//��Ӧ��Ʒ�ı��
			/**������Ʒ��Ž���Ӧ����Ʒ��Ϣд��cookie
			 * */
			productToCookie(request,response,id);
			//������Ʒ�ı�ŷ���������������ǰ̨չʾʱ��ʾƥ�����Ʒ��ϸ��Ϣ
			request.setAttribute("proid", id);
			/**������Ʒ��Ӧ��һ������������������ڶ�λһ�������λ��
			 * */
			String pid=request.getParameter("pid");
			request.setAttribute("id",pid);
			request.getRequestDispatcher("/product-view.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**������Ʒ��Ž���Ӧ����Ʒ��Ϣд��cookie
	 * */
    public void productToCookie(HttpServletRequest request, HttpServletResponse response,String id){
    	//д��cookie
    	String writeCookie=getHistory(request, id);
    	Cookie addcookie=new Cookie("name",writeCookie);
    	response.addCookie(addcookie);
    	//��ʾ�û�������������Ʒ
    	Cookie cookies[]=request.getCookies();
    	List<Product> list=new LinkedList<Product>();
    	for (Cookie item : cookies) {//����cookie�е�����
		    if(item.getName().equals("name")){
		    	String proHistory=item.getValue();
		    	String[] ids=proHistory.split("\\_");
		    	for (String string : ids) {
					//�γ�һ����Ʒ���󣨵õ���Ʒ���ƣ�
		    		try {
						Product product=productDaoImpl.getProductById(Integer.parseInt(string));
						list.add(product);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
		    	
		    }
		}
    	//������������Ʒ��Ϣ����session
    	request.getSession().setAttribute("lookproduct",list);
    }
    
    /**
	 * 
	 * @param request
	 *            ��װ��Request����
	 * @param id
	 *            �����������Ʒ���
	 * @return ǰ�������������Ʒ�ַ���
	 */
	private String getHistory(HttpServletRequest request, String id) {
		// ��ȡ�û�����Я����cookie
		// �������
		// ��һ�η���:proHistory=null 1 proHistory=1
		// �ǵ�һ��:proHistory=3_2_5 1 proHistory=1_3_2
		// �ǵ�һ��:proHistory=3_1_5 1 proHistory=1_3_5
		// �ǵ�һ��:proHistory=3_2 1 proHistory=1_3_2
		// ����cookies����
		Cookie[] cookies = request.getCookies();
		// ���������Ʒ�γɵĴ��ַ���
		String proHistory = null;
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals("name")) {
				proHistory = cookies[i].getValue();
				break;
			}
		}
		if (proHistory == null) {
			return id;
		}
		// ����_�ָ�����
		// ��java�� \����ת���ַ� \n \t �ȣ��� \\ ����һ����б�� ��.����һ��Ԫ�ַ�
		// Ҫ��ʾһ��.����Ҫ�� Ҫ��\.
		// ����"\\." ��ʵ�ʱ����� �ʹ��� .
		List mylist = Arrays.asList(proHistory.split("\\_"));
		LinkedList<String> list = new LinkedList<String>();
		list.addAll(mylist);
		if (list.contains(id)) {// �����к��и���Ʒ
			list.remove(id);
			list.addFirst(id);
		} else {
			if (list.size() >= 3) {
				list.removeLast();
				list.addFirst(id);
			} else {
				list.addFirst(id);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (String item : list) {
			sb.append(item + "_");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	
	/**
	 * �������ķ��������Ʒ��Ϣ
	 * */
	public void loadProductInfoByLeftCate(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		try {
			  String cid=request.getParameter("cateid");
			  /**
			    * ��ȡ��Ӧһ�������µ���Ʒ��Ϣ
			    * */
			   // ʵ����page���󣬸�page��index��size��ֵ����ʾ��һҳ������ʾ8����¼
			   Page page=new Page();
			   page.setPageSize(8);
			   int pageIndex=1;
			   int totalPages=0;
				totalPages = productDaoImpl.getProductCountByCid(Integer.parseInt(cid));
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
			   List<Product> list=productDaoImpl.getProductPagesByCid(page.getPageSize(), page.getPageIndex(), Integer.parseInt(cid));
				page.setList(list);
				
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
			   * ��ѯ��Ӧ���������һ����������ж�������
			   * */
			   String pid=request.getParameter("pid");
			   List<ProductCategory> childlist = pcd.getChildCateByPid(Integer.parseInt(pid));
			   request.setAttribute("childlistonly",childlist);
			   
			   /**����Ӧ��һ��������������򣬱���ǰ̨չʾʱ����λ��ĳһ��һ������
			    * */
			   request.setAttribute("id", pid);
			   
			   
			   /**�����ζ���������������򣬱���ǰ̨չʾ��Ӧ�����������Ʒ��Ϣ
			    * */
			   request.setAttribute("cid", cid);
			   
			   /**
			    * ת��
			    * */
			   request.getRequestDispatcher("/product-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
}




}