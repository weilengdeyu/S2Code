package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.happy.easybuy.until.NewsPage;


public class NewsServlet extends HttpServlet {
	NewsDaoImpl nd = new NewsDaoImpl();
	//实例化使用到的类
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	   
	   ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	   UserDaoImpl userDaoImpl=new UserDaoImpl();
	/**
	李晓鹏
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	李晓鹏
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if(oper.equals("list")){//进行新闻列表展示
			pages(request,response);
		}else if (oper.equals("modify")) {
			modifyNews(request,response);
			
		}else if(oper.equals("del")){
			delNews(request,response);
		}else if(oper.equals("modifytrue")){
			tmodify(request,response);
		}else if(oper.equals("add")){
			addNewsInfo(request,response);
		}else if(oper.equals("newsDetial")){//根据新闻编号获取新闻信息
			newsDetial(request,response);
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
		List<News> newslist =nd.getAllNews();
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
	 * //根据新闻编号获取新闻信息
	 * @throws IOException 
	 * @throws ServletException 
	 * */
	public void newsDetial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		load(request,response);
		String id = request.getParameter("nid");
		try {
			News news = nd.selectNewsById(Integer.parseInt(id));
			request.setAttribute("item", news);
			request.getRequestDispatcher("/news-view.jsp").forward(request, response);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	}
	
	/**
	 * 创建一个分页的方法
	 * @李晓鹏
	 * @param response
	 */
public void pages(HttpServletRequest request, HttpServletResponse response){
	NewsPage cpage =new NewsPage();
      //指定每页显示5条数据
	cpage.setPageSize(5);
      try {

    		//返回总记录数   	  
      	  cpage.setPageSum(nd.getNewsCount());  
      	  cpage.setPageCount((int)Math.ceil(cpage.getPageSum()*1.0/cpage.getPageSize()));
        	//当用户没有点击下一页时默认显示第一页数据
        	int pageIndex=1;
        	String uindex=request.getParameter("pageIndex");
        	if (uindex!=null&&!uindex.equals("")) {
    			pageIndex=Integer.parseInt(uindex);
    		   //给总页数赋值
  	      	if(pageIndex<1){
  	      		pageIndex=1;
  	      	}
  	      	if(pageIndex>cpage.getPageCount()){
  	      		pageIndex=cpage.getPageCount();
  	      		
  	      	}
    		}
        	
        	cpage.setPageIndex(pageIndex);
        	//给集合赋值
        	cpage.setList(nd.getNewsInfo(cpage.getPageIndex(), cpage.getPageSize()));
        	//将page对象放入作用域中
        	request.setAttribute("page", cpage);
        	request.getRequestDispatcher("/manage/news.jsp").forward(request, response);
	
}catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}

}
/**
 * 添加新闻的方法
 */
public void addNewsInfo(HttpServletRequest request, HttpServletResponse response){
	String title= request.getParameter("title");
	String content = request.getParameter("content");
	//获取当前时间
	Date date=new Date();
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dates=format.format(date);
	News news = new News();
	news.setTitle(title);
	news.setContent(content);
	news.setCreatTime(dates);
	int count =nd.addNews(news);
	try {
		if (count>0) {
			request.getRequestDispatcher("/servlet/NewsServlet?oper=list").forward(request, response);
		}else{
			request.getRequestDispatcher("/servlet/NewsServlet?oper=list").forward(request, response);
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
}
/**
 * 修改新闻的方法
 * @李晓鹏
 */
public void modifyNews(HttpServletRequest request, HttpServletResponse response){
	String nid = request.getParameter("Nid");
	try {
		News news = nd.selectNewsById(Integer.parseInt(nid));
		request.setAttribute("mnews", news);
		request.getRequestDispatcher("/manage/news-modify.jsp").forward(request, response);
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 删除新闻的方法
 * @李晓鹏
 */
public void delNews(HttpServletRequest request, HttpServletResponse response){
	String nid = request.getParameter("Nid");	
	int count =0;
	try {
		count = nd.removeNews(Integer.parseInt(nid));
		if (count>0) {
			request.getRequestDispatcher("/servlet/NewsServlet?oper=list").forward(request, response);
		}else {
			request.getRequestDispatcher("/servlet/NewsServlet?oper=list").forward(request, response);
		}
		//request.getRequestDispatcher("/manage/news-modify.jsp").forward(request, response);
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 进行真正的修改
 * @李晓鹏
 */
public void tmodify(HttpServletRequest request, HttpServletResponse response){
	int id = Integer.parseInt(request.getParameter("nid"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	News news = new  News();
	news.setTitle(title);
	news.setContent(content);
	news.setId(id);
	int count = 0;
	try {
		count =nd.modifyNews(news);
		if (count>0) {
			request.getRequestDispatcher("/servlet/NewsServlet?oper=list").forward(request, response);
		}else{
			System.out.println("<script>alert('修改失败!')</script>");
		}
	} catch (Exception e) {
		// TODO: handle exception
		
		e.printStackTrace();
	}
}

}

