package cn.happy.easybuy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.happy.easybuy.dao.impl.CommentDaoImpl;
import cn.happy.easybuy.dao.impl.NewsDaoImpl;
import cn.happy.easybuy.dao.impl.ProductCategoryDaoImpl;
import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.Comment;
import cn.happy.easybuy.entity.News;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;
import cn.happy.easybuy.until.CommentPage;


public class CommentServlet extends HttpServlet {	
	CommentDaoImpl comm = new CommentDaoImpl();
	
	//ʵ����ʹ�õ�����
	   ProductCategoryDaoImpl pcd = new ProductCategoryDaoImpl();
	   NewsDaoImpl ndi = new NewsDaoImpl();	
	   ProductDaoImpl productDaoImpl=new ProductDaoImpl();
	   UserDaoImpl userDaoImpl=new UserDaoImpl();
	/**
	������
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String oper=request.getParameter("oper");
		if(oper!=null){
			if(oper.equals("list")){
				if(request.getSession().getAttribute("loginname")==null){
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}else{
					load(request,response);
					pages(request, response);
				}
				
			}else if(oper.equals("add")){
				try {
					addComment(request,response);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if(oper.equals("managelist")){
				try {
					managelistpages(request,response);
//					List<Comment> list=comm.getCommentInfo(1, 5);
//					request.setAttribute("list",list);
//					request.getRequestDispatcher("/manage/guestbook.jsp").forward(request, response);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(oper.equals("reply")){
				try {
					getRecordCount(request,response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(oper.equals("modify")){
				try {
					getRecordCount(request,response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(oper.equals("both")){//�����������޸ĺͻظ�
				String name=request.getParameter("reply");//�ظ�
				String name2=request.getParameter("modify");//�޸�
				if(name!=null){//���лظ�����
					String replyContent = request.getParameter("commentcontent");
					int id = Integer.parseInt(request.getParameter("commentid"));

					//��ȡ��ǰʱ��
					Date date=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yy-mm-dd hh:mm:ss");
					String dates=format.format(date);
					//��������
					Comment com = new Comment();					
					com.setId(id);
					com.setReply(replyContent);
					com.setReplyTime(dates);
					
					int count =0;
					try {
						count = comm.getRecordCount(com);
						if(count > 0){
							System.out.println("<script>alert('�ظ��ɹ�!')</script>");
						}else{
							System.out.println("<script>alert('�ظ�ʧ��!')</script>");
						}
						request.getRequestDispatcher("/servlet/CommentServlet?oper=list").forward(request, response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				if(name2!=null){//�����޸ĵĲ���
					String replyContent = request.getParameter("commentcontent");
					int id = Integer.parseInt(request.getParameter("commentid"));

					//��ȡ��ǰʱ��
					Date date=new Date();
					SimpleDateFormat format=new SimpleDateFormat("yy-mm-dd hh:mm:ss");
					String dates=format.format(date);
					//��������
					Comment com = new Comment();					
					com.setId(id);
					com.setReply(replyContent);
					com.setReplyTime(dates);
					
					int count =0;
					try {
						count = comm.getRecordCount(com);
						if(count > 0){
							request.getRequestDispatcher("/servlet/CommentServlet?oper=list").forward(request, response);
						}else{
							System.out.println("<script>alert('�޸�ʧ��!')</script>");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else if (oper.equals("del")) {//ִ��ɾ��
			String id = request.getParameter("cid");
			int count =0;
			try {
				count=comm.removeComment(Integer.parseInt(id));
				if (count>0) {
					request.getRequestDispatcher("/manage/manage-result.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/manage/manage-result.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if(oper.equals("managelistpages")){//��̨���Է�ҳ
			managelistpages(request,response);
		}
		}
	
	}
	
	//��ҳ
	public void managelistpages(HttpServletRequest request, HttpServletResponse response){
		CommentPage cpage =new CommentPage();
	      //ָ��ÿҳ��ʾ3������
		cpage.setPageSize(3);
	      try {
	    	
	  		//�����ܼ�¼��
	    	  
	    	  cpage.setPageSum(comm.getCommentCount());
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
	      	
	      	
	      	//cpage.setPageIndex(pageIndex);
	      	//�����ϸ�ֵ
	      	cpage.setList(comm.getCommentInfo(cpage.getPageIndex(), cpage.getPageSize()));
	      	//��page���������������
	      	request.setAttribute("page", cpage);
	      //	request.getRequestDispatcher("/guestbook.jsp").forward(request, response);
	      	request.getRequestDispatcher("/manage/guestbook.jsp").forward(request, response);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
		/*
		 * ����һ����ҳ�ķ���
		 * @������
		 */
	public void pages(HttpServletRequest request, HttpServletResponse response){
		CommentPage cpage =new CommentPage();
	      //ָ��ÿҳ��ʾ3������
		cpage.setPageSize(3);
	      try {
	    	
	  		//�����ܼ�¼��
	    	  
	    	  cpage.setPageSum(comm.getCommentCount());
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
	      	cpage.setList(comm.getCommentInfo(cpage.getPageIndex(), cpage.getPageSize()));
	      	//��page���������������
	      	request.setAttribute("page", cpage);
	      	request.getRequestDispatcher("/guestbook.jsp").forward(request, response);
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	/**
	������
	 */
}
	/*
	 * ��ӵķ���
	 * @������
	 */
	 public void addComment(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		 load(request,response);
			     String message=request.getParameter("guestContent");
			     
			     Date date=new Date(); 
			     SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			     String time=df.format(date);
			    Comment con=new Comment();
			    con.setNickName("hubjb");
			    con.setContent(message);
			    con.setCreateTime(time);
			    System.out.println(con.getNickName());
			    int count=comm.addComment(con);
			    if(count>0){
			    	System.out.println("�ɹ�");
			    }
			    else{
			    	System.out.println("ʧ��");
			    }
		try {
			request.getRequestDispatcher("/servlet/CommentServlet?oper=list").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  }
	 /**
		* �ظ�����
		 * @throws Exception 
		 * @throws NumberFormatException 
		 */
	public void getRecordCount(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception{
			 String commentid=request.getParameter("commentid");
			 Comment comment=comm.selectCommentById(Integer.parseInt(commentid));
			 request.setAttribute("comment",comment);
			 request.getRequestDispatcher("/manage/guestbook-modify.jsp").forward(request, response);
		}
	
	
	/**
	 * ��������һ������  ��������   ��Ϣ
	 * 
	 * */
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
	
	
	
	
	
	
	
	
	
	
	
}