package cn.happy.easybuy.until;

import java.util.List;

import cn.happy.easybuy.entity.News;



public class NewsPage {
	private int pageIndex=1; //��ǰĬ����ʾ��һҳ����
	private int pageSize=3;//ÿҳ��ʾ3������
	private int pageSum;//�ܼ�¼��
	private int pageCount;//��ҳ��
	private List<News>list;//������Ϣ��ҳ��Ϣ�ļ���
	
	
	
	/*
	*get set ������
	*/
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
	public List<News> getList() {
		return list;
	}
	public void setList(List<News> list) {
		this.list = list;
	}
	
}
