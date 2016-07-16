package cn.happy.easybuy.until;

import java.util.List;


import cn.happy.easybuy.entity.User;

public class UserPage {
	private int pageIndex=1; //当前默认显示第一页数据
	private int pageSize=3;//每页显示3条数据
	private int pageSum;//总记录数
	private int pageCount;//总页数
	private List<User>list;//保存消息分页信息的集合
	
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
	public int getPageSum() {
		return pageSum;
	}
	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
	
}
