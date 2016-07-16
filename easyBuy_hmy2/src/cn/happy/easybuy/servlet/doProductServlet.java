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
            if(oper.equals("prodetital")){//查看商品详情
            	loadProductDetatil(request,response);
            }else if(oper.equals("proCateSelect")){//根据左侧对应的分类查看对应的商品
            	loadProductInfoByLeftCate(request,response);
            }else if(oper.equals("proManage")){//后台商品管理
            	proManage(request,response);
            	
            }else if(oper.equals("houTaiModify")){//后台商品的修改
            	houTaiModify(request,response);
            }else if(oper.equals("houTaiModifyTrue")){//进行后台真正的修改操作
            	
            	houTaiModifyTrue(request,response);
            }else if(oper.equals("houTaidel")){//后台商品的删除
            	houTaidel(request,response);
            }else if(oper.equals("backProductAdd")){//携带分类数据到商品添加页面
            	
            	backProductAdd(request,response);
            }else if(oper.equals("backProductAddTrue")){//进行后台商品真正的添加操作
            	addTrue(request,response);
            }
	}
	
	
	/**
	 * 后台商品添加  做中转站
	 * */
	public void backProductAdd(HttpServletRequest request, HttpServletResponse response){
		//加载所有的分类信息，用集合保存
		getCateByList(request,response);
		//转发
		try {
			request.getRequestDispatcher("/manage/product-add.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        
        
	}
	
	/**
	 * 加载所有的分类信息，用集合保存
	 * */
	public void getCateByList(HttpServletRequest request, HttpServletResponse response){
		try {
			//父类做键，对应的子类集合做值
			Map<ProductCategory, List<ProductCategory>> categoryMap = new HashMap<ProductCategory, List<ProductCategory>>();
			//父类做键，对应子类集合的个数做值
			Map<ProductCategory, Integer> indexMap = new HashMap<ProductCategory, Integer>();
			//保存一级分类集合
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			for (int i = 0; i < parentlist.size(); i++) {
				//根据一级分类编号获取对应的二级分类的集合
				List<ProductCategory> childs = pcd.getChildCateByPid(parentlist.get(i).getId());
				//根据对应关系放入categoryMap集合中
				categoryMap.put(parentlist.get(i), childs);
				indexMap.put(parentlist.get(i), childs.size()-1);
			}
			//将集合放入作用域
			request.setAttribute("categoryMap", categoryMap);
			request.setAttribute("indexMap", indexMap);
			request.setAttribute("parentlist", parentlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 进行后台商品的真正的添加操作
	 * @throws UnsupportedEncodingException 
	 * */
	public void addTrue(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		        //添加商品
				Product product=new Product();
				String uploadFileName="";//上传的文件名
		        String fieldName="";//表单字段元素的name属性值
				//判断请求中的信息是否是multipart类型
		        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
		        //上传文件的存储路径
		        String uploadFilePath=request.getSession().getServletContext().getRealPath("images/product");
		        //如果是multipart类型上传
		        if(isMultipart){
		        	FileItemFactory factory=new DiskFileItemFactory();
		        	ServletFileUpload upload=new ServletFileUpload(factory);
					try {
						 //解析form表单中所有文件  每一个items代表一个表单元素
						 List<FileItem> items = upload.parseRequest(request);
						 Iterator<FileItem> iter=items.iterator();
						 //依次处理每个文件
						 while(iter.hasNext()){
							 FileItem item=(FileItem)iter.next();
							 if(item.isFormField()){//普通表单字段
								 //获取表单字段的name属性值
								 fieldName=item.getFieldName();
								 if(fieldName.equals("productName")){//商品名称
									 product.setName(item.getString("utf-8"));
								 }else if(fieldName.equals("productDetail")){//商品描述
									 product.setDescription(item.getString("utf-8"));
								 }else if(fieldName.equals("parentId")){//商品所属分类
									 product.setChileCategoryId(Integer.parseInt(item.getString("utf-8")));
								 }else if(fieldName.equals("productPrice")){//商品价格
									 product.setPrice(Double.parseDouble(item.getString("utf-8")));
								 }else if(fieldName.equals("productNumber")){//库存
									 product.setStock(Integer.parseInt(item.getString("utf-8")));
								 }
								 
							 }else{//文件表单字段
								 //文件表单字段的name属性值
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
		        //将该商品加入到后台数据库
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
	 * 后台商品的删除
	 * */
    public void houTaidel(HttpServletRequest request, HttpServletResponse response){
    	//获取要删除的商品的编号
    	String id=request.getParameter("id");
    	try {
			boolean flag=productDaoImpl.delByPId(Integer.parseInt(id));
			//转发
			request.getRequestDispatcher("/servlet/doProductServlet?oper=proManage").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 进行后台真正的修改操作
	 * @throws UnsupportedEncodingException 
	 * */
	public void houTaiModifyTrue(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		//修改商品
		Product product=new Product();
		
		//获取要修改的商品的编号
		String id=request.getParameter("id");
		product.setId(Integer.parseInt(id));
		
		String uploadFileName="";//上传的文件名
        String fieldName="";//表单字段元素的name属性值
		//判断请求中的信息是否是multipart类型
        boolean isMultipart=ServletFileUpload.isMultipartContent(request);
        //上传文件的存储路径
        String uploadFilePath=request.getSession().getServletContext().getRealPath("images/product");
        //如果是multipart类型上传
        if(isMultipart){
        	FileItemFactory factory=new DiskFileItemFactory();
        	ServletFileUpload upload=new ServletFileUpload(factory);
			try {
				 //解析form表单中所有文件  每一个items代表一个表单元素
				 List<FileItem> items = upload.parseRequest(request);
				 Iterator<FileItem> iter=items.iterator();
				 //依次处理每个文件
				 while(iter.hasNext()){
					 FileItem item=(FileItem)iter.next();
					 if(item.isFormField()){//普通表单字段
						 //获取表单字段的name属性值
						 fieldName=item.getFieldName();
						 if(fieldName.equals("productName")){//商品名称
							 product.setName(item.getString("utf-8"));
						 }else if(fieldName.equals("productdescription")){//商品描述
							 product.setDescription(item.getString("utf-8"));
						 }else if(fieldName.equals("parentId")){//商品所属分类
							 product.setChileCategoryId(Integer.parseInt(item.getString("utf-8")));
						 }else if(fieldName.equals("productPrice")){//商品价格
							 product.setPrice(Double.parseDouble(item.getString("utf-8")));
						 }else if(fieldName.equals("productstock")){//库存
							 product.setStock(Integer.parseInt(item.getString("utf-8")));
						 }
						 
					 }else{//文件表单字段
						 //文件表单字段的name属性值
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
        
        //进行后台数据库的更新
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
	 * 后台商品的修改
	 * */
	public void houTaiModify(HttpServletRequest request, HttpServletResponse response){
		getCateByList(request,response);
		//获取修改的商品的编号
		String id=request.getParameter("id");
		//获取到该商品编号的详细信息
		try {
			Product product=productDaoImpl.getProductById(Integer.parseInt(id));
			//放入作用域
			request.setAttribute("product", product);
			//转发
			request.getRequestDispatcher("/manage/product-modify.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 后台商品管理跳转到后台的product.jsp
	 * */

	public void proManage(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		
		//读取分页后的集合传入前台
		// 实例化page对象，给page的index和size赋值：显示第一页，并显示8条记录
		   Page page=new Page();
		   page.setPageSize(4);
		   int pageIndex=1;
		   int totalPages=0;
		try {
			totalPages = productDaoImpl.getAllRecords();
		   //为总页数赋值
		   if(totalPages%page.getPageSize()==0){
				page.setTotalPages(totalPages/page.getPageSize());
			}else{
				page.setTotalPages(totalPages/page.getPageSize()+1);
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
		   List<Product> list=productDaoImpl.getPageList(page.getPageSize(), page.getPageIndex());
			page.setList(list);
		   //放入作用域中
		   request.setAttribute("pages", page);
		   
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
		   request.getRequestDispatcher("/manage/product.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 加载所有的商品信息
	 * */
	public void loadAll(HttpServletRequest request, HttpServletResponse response){
		try{
			//将二级分类放入作用域中
        	List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//将一级分类放入作用域中
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//将所有商品放入作用域中
			List<Product> list=productDaoImpl.getAllProduct();
			request.setAttribute("list",list);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查看商品详情
	 * */
	public void loadProductDetatil(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		try {
			String id=request.getParameter("proid");//对应商品的编号
			/**根据商品编号将对应的商品信息写入cookie
			 * */
			productToCookie(request,response,id);
			//将该商品的编号放入作用域，用于在前台展示时显示匹配的商品详细信息
			request.setAttribute("proid", id);
			/**将该商品对应的一级分类放入作用域，用于定位一级分类的位置
			 * */
			String pid=request.getParameter("pid");
			request.setAttribute("id",pid);
			request.getRequestDispatcher("/product-view.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**根据商品编号将对应的商品信息写入cookie
	 * */
    public void productToCookie(HttpServletRequest request, HttpServletResponse response,String id){
    	//写入cookie
    	String writeCookie=getHistory(request, id);
    	Cookie addcookie=new Cookie("name",writeCookie);
    	response.addCookie(addcookie);
    	//显示用户最近浏览过的商品
    	Cookie cookies[]=request.getCookies();
    	List<Product> list=new LinkedList<Product>();
    	for (Cookie item : cookies) {//遍历cookie中的数据
		    if(item.getName().equals("name")){
		    	String proHistory=item.getValue();
		    	String[] ids=proHistory.split("\\_");
		    	for (String string : ids) {
					//形成一个商品对象（得到商品名称）
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
    	//将最近浏览的商品信息放入session
    	request.getSession().setAttribute("lookproduct",list);
    }
    
    /**
	 * 
	 * @param request
	 *            封装的Request对象
	 * @param id
	 *            最新浏览的商品编号
	 * @return 前三个浏览过的商品字符串
	 */
	private String getHistory(HttpServletRequest request, String id) {
		// 获取用户所有携带的cookie
		// 可能情况
		// 第一次访问:proHistory=null 1 proHistory=1
		// 非第一次:proHistory=3_2_5 1 proHistory=1_3_2
		// 非第一次:proHistory=3_1_5 1 proHistory=1_3_5
		// 非第一次:proHistory=3_2 1 proHistory=1_3_2
		// 拆解出cookies集合
		Cookie[] cookies = request.getCookies();
		// 浏览过的商品形成的大字符串
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
		// 根据_分割数据
		// 在java中 \代表转义字符 \n \t 等，而 \\ 代表一个反斜杠 而.代表一个元字符
		// 要表示一个.就需要用 要用\.
		// 所以"\\." 在实际编译中 就代表 .
		List mylist = Arrays.asList(proHistory.split("\\_"));
		LinkedList<String> list = new LinkedList<String>();
		list.addAll(mylist);
		if (list.contains(id)) {// 集合中含有该商品
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
	 * 根据左侧的分类加载商品信息
	 * */
	public void loadProductInfoByLeftCate(HttpServletRequest request, HttpServletResponse response){
		loadAll(request,response);
		try {
			  String cid=request.getParameter("cateid");
			  /**
			    * 获取对应一级分类下的商品信息
			    * */
			   // 实例化page对象，给page的index和size赋值：显示第一页，并显示8条记录
			   Page page=new Page();
			   page.setPageSize(8);
			   int pageIndex=1;
			   int totalPages=0;
				totalPages = productDaoImpl.getProductCountByCid(Integer.parseInt(cid));
			   //为总页数赋值
			   if(totalPages%page.getPageSize()==0){
					page.setTotalPages(totalPages/page.getPageSize());
				}else{
					page.setTotalPages(totalPages/page.getPageSize()+1);
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
			   List<Product> list=productDaoImpl.getProductPagesByCid(page.getPageSize(), page.getPageIndex(), Integer.parseInt(cid));
				page.setList(list);
				
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
				
			   //放入作用域中
			   request.setAttribute("pages", page);
			  /**
			   * 查询对应二级分类的一级分类的所有二级分类
			   * */
			   String pid=request.getParameter("pid");
			   List<ProductCategory> childlist = pcd.getChildCateByPid(Integer.parseInt(pid));
			   request.setAttribute("childlistonly",childlist);
			   
			   /**将对应的一级分类放入作用域，便于前台展示时，定位到某一个一级分类
			    * */
			   request.setAttribute("id", pid);
			   
			   
			   /**将本次耳机分类放入作用域，便于前台展示对应耳机分类的商品信息
			    * */
			   request.setAttribute("cid", cid);
			   
			   /**
			    * 转发
			    * */
			   request.getRequestDispatcher("/product-list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
}




}