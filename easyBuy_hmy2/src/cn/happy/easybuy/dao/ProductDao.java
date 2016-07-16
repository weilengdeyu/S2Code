package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public interface ProductDao {
    //��ȡ���е���Ʒ��Ϣ
	public List<Product> getAllProduct() throws Exception;
	//����ָ�������ѯ��Ʒ��Ϣ
	public List<Product> getAllProductByProCate(ProductCategory productCategory) throws Exception; 
	//��ѯ���е���Ʒ��¼��
	public int getAllRecords() throws Exception;
	//��ȡָ��ҳ��ļ���
	public List<Product> getPageList(int pageSize, int pageIndex) throws Exception;
	//���ݱ�Ų��Ҷ�Ӧ����Ʒ��Ϣ
	public Product getProductById(int id) throws Exception;
	//����ָ��һ�������µ���Ʒ�ܼ�¼��
	public int getproductCountByPid(int pid) throws Exception;
	//����ָ����һ��������Ҷ�Ӧ����ҳ����Ʒ����
	public List<Product> getProductPagesByPid(int pageSize,int pageIndex,int pid) throws Exception;
	//��ѯ��Ӧ���������µ���Ʒ��Ϣ
	public List<Product> getProductPagesByCid(int pageSize,int pageIndex,int cid) throws Exception;
	//��ѯ��Ӧ���������µ���Ʒ�ܼ�¼��
	public int getProductCountByCid(int cid) throws Exception;
	//���ݱ��ɾ����Ӧ����Ʒ��Ϣ
	public boolean delByPId(int pid) throws Exception;
	//�����Ʒ��Ϣ
	public boolean addProduct(Product product) throws Exception;
	
	//������Ʒ����޸Ķ�Ӧ����Ʒ��Ϣ
	public boolean modifyProductByProId(Product product) throws Exception;
	
}
