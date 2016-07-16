package cn.happy.easybuy.until;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.ProductDaoImpl;
import cn.happy.easybuy.entity.OrderDetail;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public class Page {
    private int pageIndex;//索引页
    private int pageSize;//每页的大小
    private int totalPages;//总页数
    private List<Product> list;//根据页数和索引页查到的list集合
    private List<OrderDetail> orderDetailList;//查询到的分页后的订单详情信息 
    /*以下是分页页码条相关内容*/
    private int listbegin; //页码开始
    private int listened;//分页信息显示到第几页
    private List<ProductCategory>  ProductCategorylist;//查询分页后的商品分类信息
    
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
		
		int liststep = 3;//最多显示分页页数  
		int listbegin = (this.getPageIndex() - (int) Math.floor((double) liststep / 2));//从第几页开始显示分页信息
		if (listbegin < 1) { //当前页-(总显示的页列表数/2)   
            listbegin = 1;   
        }else if(listbegin+(int) Math.floor((double) liststep / 2)>this.getTotalPages()){
      	  listbegin=this.getTotalPages()-liststep+1;
        }
		int listend=this.getPageIndex() + liststep / 2;
	       
        if(this.getTotalPages()<liststep){
      	  listend=this.getTotalPages();
        }else if(this.getTotalPages()>liststep&&listend<=this.getTotalPages()){
          listend =listend<liststep?liststep:listend;//分页信息显示到第几页//当前页+(总显示的页列表数/2)
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

