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
		岁月静好
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	  //实例化使用到的类
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	   NewsDaoImpl ndi = new NewsDaoImpl();	
	   ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	   UserDaoImpl userDaoImpl=new UserDaoImpl();
	/**
		岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决乱码
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if(oper!=null){
			 if(oper.equals("loadchildByParent")){//根据一级分类加载对应的信息
				 getChildInfoByParentId(request,response);
			 }else if(oper.equals("getPages")){//点击索引页时进行的操作
				 getChildInfoByParentId(request,response);
			 }
		}else{//第一次加载首页
			loadIndexInfo(request,response);
	    }  
		 
	}


	/**
	     用户首次进入时加载一级分类，二级分类，新闻信息，商品信息
     */
	public void load(HttpServletRequest request, HttpServletResponse response){
		try{
			//所有的一级分类
			List<ProductCategory> parentlist = pcd.getAllParentCate();
			request.setAttribute("parentlist", parentlist);
			//所有的二级分类
			List<ProductCategory> childlist=pcd.getAllChildCate();
			request.setAttribute("childlist", childlist);
			//所有新闻信息
			List<News> newslist =ndi.getAllNews();
			request.setAttribute("newslist", newslist);
			//所有的商品信息
			List<Product> productlist=productDaoImpl.getAllProduct();
			request.setAttribute("productlist",productlist);
			request.setAttribute("childlistonly",childlist);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 首页加载
	 */
	public void loadIndexInfo(HttpServletRequest request, HttpServletResponse response){
		load(request,response); 
		String str=null;
		if(request.getSession().getAttribute("loginname")==null){
			//将登录信息保存到session中
			str=request.getParameter("loginname");
			request.getSession().setAttribute("loginname",str);
		}else{
			str=(String)request.getSession().getAttribute("loginname");
		}
		
		Page page=new Page();
		Page pages=page.getPages(request, response, 8);
		request.setAttribute("pages", pages);
		
		try {
			   //根据用户名查询对应的用户信息
			if(request.getSession().getAttribute("userInfo")==null){
				User user=userDaoImpl.getUserInfoByUid(str);
				//放入session中
				request.getSession().setAttribute("userInfo", user);
			}
				//User user=userDaoImpl.getUserInfoByUid(str);
				//放入session中
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
   * 根据一级分类，加载对应的二级分类，商品信息
   */
   public void getChildInfoByParentId(HttpServletRequest request, HttpServletResponse response){
	   load(request,response);
	   //一级分类的编号
	   String pid=request.getParameter("id");
	   if(Integer.parseInt(pid)==0){
		   loadIndexInfo(request,response);
	   }else{
	   load(request,response);
	   /**
	    * 获取对应一级分类下的商品信息
	    * */
	   // 实例化page对象，给page的index和size赋值：显示第一页，并显示8条记录
	   Page page=new Page();
	   page.setPageSize(8);
	   int pageIndex=1;
	   int totalPages=0;
	   
	   
	   
	   try {
	   if(Integer.parseInt(pid)==1){//总分类
		   //总记录数
		   totalPages = productDaoImpl.getAllRecords();
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
		List<Product> list = productDaoImpl.getPageList(page.getPageSize(), page.getPageIndex());
		page.setList(list);
		  
		   
	   }else{
		   totalPages = productDaoImpl.getproductCountByPid(Integer.parseInt(pid));
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
		   List<Product> list=productDaoImpl.getProductPagesByPid(page.getPageSize(), page.getPageIndex(), Integer.parseInt(pid));
			page.setList(list);
		   
	   }
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
	    * 获取对应一级分类下的二级分类信息
	    * */
	   List<ProductCategory> childlist = pcd.getChildCateByPid(Integer.parseInt(pid));
	
	   request.setAttribute("childlistonly",childlist);
	   // 把pid设置到作用域中在：用于判断首页
		if (Integer.parseInt(pid) == 0) {

		} else {
			request.setAttribute("id", pid);
		}
		
		
		
		
		
		/**
		 * 转发到首页
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
