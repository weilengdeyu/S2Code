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
	//ʵ����ʹ�õ�����
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	   
	   ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	   UserDaoImpl userDaoImpl=new UserDaoImpl();
	/**
	������
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	������
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if(oper.equals("list")){//���������б�չʾ
			pages(request,response);
		}else if (oper.equals("modify")) {
			modifyNews(request,response);
			
		}else if(oper.equals("del")){
			delNews(request,response);
		}else if(oper.equals("modifytrue")){
			tmodify(request,response);
		}else if(oper.equals("add")){
			addNewsInfo(request,response);
		}else if(oper.equals("newsDetial")){//�������ű�Ż�ȡ������Ϣ
			newsDetial(request,response);
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
		List<News> newslist =nd.getAllNews();
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
	 * //�������ű�Ż�ȡ������Ϣ
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
	 * ����һ����ҳ�ķ���
	 * @������
	 * @param response
	 */
public void pages(HttpServletRequest request, HttpServletResponse response){
	NewsPage cpage =new NewsPage();
      //ָ��ÿҳ��ʾ5������
	cpage.setPageSize(5);
      try {

    		//�����ܼ�¼��   	  
      	  cpage.setPageSum(nd.getNewsCount());  
      	  cpage.setPageCount((int)Math.ceil(cpage.getPageSum()*1.0/cpage.getPageSize()));
        	//���û�û�е����һҳʱĬ����ʾ��һҳ����
        	int pageIndex=1;
        	String uindex=request.getParameter("pageIndex");
        	if (uindex!=null&&!uindex.equals("")) {
    			pageIndex=Integer.parseInt(uindex);
    		   //����ҳ����ֵ
  	      	if(pageIndex<1){
  	      		pageIndex=1;
  	      	}
  	      	if(pageIndex>cpage.getPageCount()){
  	      		pageIndex=cpage.getPageCount();
  	      		
  	      	}
    		}
        	
        	cpage.setPageIndex(pageIndex);
        	//�����ϸ�ֵ
        	cpage.setList(nd.getNewsInfo(cpage.getPageIndex(), cpage.getPageSize()));
        	//��page���������������
        	request.setAttribute("page", cpage);
        	request.getRequestDispatcher("/manage/news.jsp").forward(request, response);
	
}catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}

}
/**
 * ������ŵķ���
 */
public void addNewsInfo(HttpServletRequest request, HttpServletResponse response){
	String title= request.getParameter("title");
	String content = request.getParameter("content");
	//��ȡ��ǰʱ��
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
 * �޸����ŵķ���
 * @������
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
 * ɾ�����ŵķ���
 * @������
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
 * �����������޸�
 * @������
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
			System.out.println("<script>alert('�޸�ʧ��!')</script>");
		}
	} catch (Exception e) {
		// TODO: handle exception
		
		e.printStackTrace();
	}
}

}

