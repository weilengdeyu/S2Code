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
		//解决乱码
          request.setCharacterEncoding("utf-8");
          String oper=request.getParameter("oper");
		if(oper.equals("list")){//进入分类管理页面
			getCatePages(request,response);		
			
		}else if(oper.equals("add")){//做中转站，携带数据到add.jsp页面
			load(request,response);
			request.getRequestDispatcher("/manage/productClass-add.jsp").forward(request, response);
		}else if(oper.equals("addtrue")){//进行真正的添加操作
			add(request,response);
		}else if(oper.equals("delChildCate")){//删除选中的子分类
			delChileCate(request,response);
		}else if(oper.equals("delPcate")){//删除选中的一级分类
			delPCate(request,response);
		}else if(oper.equals("modify")){//做中转站  携带数据到modify.jsp页面
			modify(request,response);
		}else if(oper.equals("modifytrue")){//进行真正的修改操作
			modifytrue(request,response);
		}
	}

	
	/**
	 * 进入分类管理页面
	 * */
	public void getCatePages(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//按照二级分类的个数进行分页
		try{
		 // 实例化page对象，给page的index和size赋值：显示第一页，并显示8条记录
		   Page page=new Page();
		   page.setPageSize(1);
		   int pageIndex=1;
		   int totalPages=0;
		   //获取总记录数
			totalPages = productCategoryDaoImpl.getCountPid();
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
		   ProductCategory pc=productCategoryDaoImpl.getPagesPid(page.getPageIndex(), page.getPageSize());
		   //根据一级分类的信息查找对应的二级分类的信息
		   
		   List<ProductCategory> list=productCategoryDaoImpl.getChildCateByPid(pc.getId());
		   
		   //将对应信息放入map集合
		   Map<ProductCategory,List<ProductCategory>> map=new HashMap<ProductCategory,List<ProductCategory>>();
		   map.put(pc, list);
		   
		   //将map集合放入作用域
		   request.setAttribute("map",map);
			
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
		   
			//转发
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
	 * 将所有的一级分类  、 二级分类放入作用域
	 */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try {
			//所有的一级分类  并放入作用域
			List<ProductCategory> parentList=productCategoryDaoImpl.getAllParentCate();
			request.setAttribute("parentList", parentList);
			//所有的二级分类  并放入作用域
			List<ProductCategory> childList=productCategoryDaoImpl.getAllChildCate();
			request.setAttribute("childList", childList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进行真正的添加操作
	 * */
	public void add(HttpServletRequest request, HttpServletResponse response){
		//获取待添加分类的根分类
		String pcate=request.getParameter("parentId");
		//获取待添加的分类的名称
		String cate=request.getParameter("className");
		
		ProductCategory productCategory=new ProductCategory();
		productCategory.setName(cate);
		productCategory.setParentId(Integer.parseInt(pcate));
		
		try {
			boolean flag=productCategoryDaoImpl.add(productCategory);
			if(flag){//添加成功
				System.out.println("添加成功！");
			}else{
				System.out.println("添加失败！");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除选中的二级分类
	 * 
	 * */
	public void delChileCate(HttpServletRequest request, HttpServletResponse response){
		//获取选中删除的二级分类的id
		String id=request.getParameter("id");
		try {
			boolean flag=productCategoryDaoImpl.delChildCate(Integer.parseInt(id));
			if(flag){//删除成功
				System.out.println("删除成功！");
			}else{
				System.out.println("删除失败！");
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
     * 删除选中的一级分类
     * */
	public void delPCate(HttpServletRequest request, HttpServletResponse response){
		//获取选中的一级分类的id
		String pid=request.getParameter("id");
		try {
			boolean flag=productCategoryDaoImpl.delPcate(Integer.parseInt(pid));
			if(flag){
				System.out.println("删除成功！");
			}else{
				System.out.println("删除失败！");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 做修改的中转站
	 * */
	public void modify(HttpServletRequest request, HttpServletResponse response){
		load(request,response);
		//获取要修改的分类的编号
		String id=request.getParameter("id");
		//根据分类编号查找该分类的信息
		try {
			ProductCategory productCategory=productCategoryDaoImpl.getInfoById(Integer.parseInt(id));
			//将该信息放入作用域，便于前台展示
			request.setAttribute("productCategory", productCategory);
			//根据查找回来的信息获取其父id  获取父分类的相关信息
			ProductCategory PCategory=productCategoryDaoImpl.getInfoById(productCategory.getParentId());
			//放入作用域
			request.setAttribute("PCategory", PCategory);
			//转发
			request.getRequestDispatcher("/manage/productClass-modify.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进行真正的修改操作
	 * */
	public void modifytrue(HttpServletRequest request, HttpServletResponse response){
		//获取要修改的分类的编号
		String id=request.getParameter("id");
		//获取修改后的内容
		String value=request.getParameter("className");
		//获取修改后是一级分类还是二级分类  所属分类编号
		String pid=request.getParameter("parentId");
		ProductCategory productCategory=new ProductCategory();
		productCategory.setId(Integer.parseInt(id));
		productCategory.setName(value);
		productCategory.setParentId(Integer.parseInt(pid));
        //进行修改操作
		try {
			boolean flag=productCategoryDaoImpl.modifyByid(productCategory);
			if(flag){//修改成功
				System.out.println("修改成功！");
			}else{
				System.out.println("修改失败！");
			}
			request.getRequestDispatcher("/servlet/doProductClassServlet?oper=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
}
