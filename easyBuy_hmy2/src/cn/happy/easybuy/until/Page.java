package cn.happy.easybuy.until;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.entity.OrderDetail;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public class Page {
    private int pageIndex;//����ҳ
    private int pageSize;//ÿҳ�Ĵ�С
    private int totalPages;//��ҳ��
    private List<Product> list;//����ҳ��������ҳ�鵽��list����
    private List<OrderDetail> orderDetailList;//��ѯ���ķ�ҳ��Ķ���������Ϣ 
    /*�����Ƿ�ҳҳ�����������*/
    private int listbegin; //ҳ�뿪ʼ
    private int listened;//��ҳ��Ϣ��ʾ���ڼ�ҳ
    private List<ProductCategory>  ProductCategorylist;//��ѯ��ҳ�����Ʒ������Ϣ
    
    public List<ProductCategory> getProductCategorylist() {
		return ProductCategorylist;
	}


	public void setProductCategorylist(List<ProductCategory> productCategorylist) {
		ProductCategorylist = productCategorylist;
	}


	public int getListbegin() {
		return listbegin;
	}


	public void setListbegin(int listbegin) {
		this.listbegin = listbegin;
	}
	public int getListened() {
		return listened;
	}
	public void setListened(int listened) {
		this.listened = listened;
	}
    
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}
	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<Product> getList() {
		return list;
	}
	public void setList(List<Product> list) {
		this.list = list;
	}
	
	public Page getPages(HttpServletRequest request, HttpServletResponse response,int pageSize){
		  this.setPageSize(pageSize);
		  int mytotalPages=0;
		  int pageIndex=1;
		  ProductDaoImpl dao=new ProductDaoImpl();
		  int totalPages=0;
		try {
			totalPages = dao.getAllRecords();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
			if(totalPages%this.getPageSize()==0){
				mytotalPages=totalPages/this.getPageSize();
			}else{
				mytotalPages=totalPages/this.getPageSize()+1;
			}
		if(request.getParameter("pageIndex")!=null&&!request.getParameter("pageIndex").equals("")){
			pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
			if(pageIndex>mytotalPages){
				pageIndex=mytotalPages;
			} else if(pageIndex<1){
				pageIndex=1;
			}
		}	
		this.setTotalPages(mytotalPages);
		this.setPageIndex(pageIndex);
		
		int liststep = 3;//�����ʾ��ҳҳ��  
		int listbegin = (this.getPageIndex() - (int) Math.floor((double) liststep / 2));//�ӵڼ�ҳ��ʼ��ʾ��ҳ��Ϣ
		if (listbegin < 1) { //��ǰҳ-(����ʾ��ҳ�б���/2)   
            listbegin = 1;   
        }else if(listbegin+(int) Math.floor((double) liststep / 2)>this.getTotalPages()){
      	  listbegin=this.getTotalPages()-liststep+1;
        }
		int listend=this.getPageIndex() + liststep / 2;
	       
        if(this.getTotalPages()<liststep){
      	  listend=this.getTotalPages();
        }else if(this.getTotalPages()>liststep&&listend<=this.getTotalPages()){
          listend =listend<liststep?liststep:listend;//��ҳ��Ϣ��ʾ���ڼ�ҳ//��ǰҳ+(����ʾ��ҳ�б���/2)
        }else if (listend > this.getTotalPages()) {    
            listend = this.getTotalPages();   
        }
        this.setListbegin(listbegin);
        this.setListened(listend);
		
		
		List<Product> list=null;
		try {
			list = dao.getPageList(this.getPageSize(), this.getPageIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setList(list);
		return this;
	}
	
    
}

