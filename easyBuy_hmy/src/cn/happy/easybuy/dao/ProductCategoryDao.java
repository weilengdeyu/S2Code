package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.ProductCategory;

public interface ProductCategoryDao {
	 /*
     * ��ѯ���и�������
     * */
	public List<ProductCategory> getAllParentCate() throws Exception;
	/*
	 * ��ѯ���еĶ�������
	 */
	public List<ProductCategory> getAllChildCate() throws Exception;
	
	//���ݸ�id���Ҷ�Ӧ�Ķ�������
	public List<ProductCategory> getChildCateByPid(int pid) throws Exception;
	
	//���һ������
	public boolean add(ProductCategory productCategory) throws Exception;
	
	//ɾ����������
	public boolean delChildCate(int cid) throws Exception;
	
	//ɾ��һ������
	public boolean delPcate(int pid) throws Exception;
	//���ݷ����Ų��Ҷ�Ӧ�ķ������Ϣ
	public ProductCategory getInfoById(int id) throws Exception;
	
	//���ݷ������޸ĸ÷������Ϣ
	public boolean modifyByid(ProductCategory productCategory) throws Exception;
	
	//���ݶ���������Ҷ�Ӧ��һ����ŵ�ֵ
	public int getPidByCid(int cid) throws Exception;
	
	//��ѯ��Ӧ����ҳ�Ķ���������Ϣ
	public List<ProductCategory> getPages(int pageIndex,int pageSize) throws Exception;
	
	//��ѯ���еĶ�������ļ�¼��
	public int getAllCateRecoreds() throws Exception;
	
	//��ѯ���е�һ������ļ�¼��
	public int getCountPid() throws Exception;
	
	//��������ҳ���Ҷ�Ӧ��һ���������Ϣ
	public ProductCategory getPagesPid(int pageIndex,int pageSize) throws Exception;
	
}
