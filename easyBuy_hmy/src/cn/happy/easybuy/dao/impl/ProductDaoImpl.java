package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.ProductDao;
import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public class ProductDaoImpl extends BaseDao implements ProductDao{

	@Test
	public void test(){
		try {
			List<Product> list= getPageList(5, 1);
			for (Product product : list) {
				System.out.println(product.getFileName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public List<Product> getAllProduct() throws Exception {
		String sql="select * from dbo.EASYBUY_PRODUCT";
		ResultSet rs=executeQuery(sql);
		List<Product> list=new ArrayList<Product>();
		if(rs!=null){
			while(rs.next()){
				Product product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
				list.add(product);
			}
		}
		return list;
	}
	//����ָ�������ѯ��Ʒ��Ϣ
	@Override
	public List<Product> getAllProductByProCate(ProductCategory productCategory) throws Exception {
		String sql="select * from dbo.EASYBUY_PRODUCT where EPC_ID=?";
		Object[] objs={
				productCategory.getParentId()
		};
		ResultSet rs=executeQuery(sql, objs);
		List<Product> list=new ArrayList<Product>();
		if(rs!=null){
			while(rs.next()){
				Product product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
				list.add(product);
			}
		}
		return list;
	}
	@Override
	public int getAllRecords() throws Exception {
		String sql="select count(1) as mycount from dbo.EASYBUY_PRODUCT";
		ResultSet rs=executeQuery(sql);
		int count=0;
		if(rs!=null){
			if(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		return count;
	}
	@Override
	public List<Product> getPageList(int pageSize, int pageIndex)
			throws Exception {
		List<Product> list =new ArrayList<Product>();
		String sql = "select top "+pageSize+" * from dbo.EASYBUY_PRODUCT where EP_ID not in (select top "+(pageIndex-1)*pageSize+" EP_ID from dbo.EASYBUY_PRODUCT)";
		ResultSet rs = executeQuery(sql);
		if(rs!=null){
			while(rs.next()){
				Product product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
				list.add(product);
			}
		}
		return list;
	}
	//���ݱ�Ų��Ҷ�Ӧ����Ʒ��Ϣ
	@Override
	public Product getProductById(int id) throws Exception {
		String sql="select * from EASYBUY_PRODUCT where EP_ID=?";
		Object[] objs={id};
		ResultSet rs = executeQuery(sql,objs);
		Product product=null;
		if(rs!=null){
			while(rs.next()){
				product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
			}
		}
		return product;
	}
	
	//����ָ��һ�������µ���Ʒ�ܼ�¼��
	@Override
	public int getproductCountByPid(int pid) throws Exception {
		String sql="select count(1) as mycount from EASYBUY_PRODUCT where EPC_ID=?";
		Object[] objs={pid};
		ResultSet rs=executeQuery(sql, objs);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		return count;
	}
	//����ָ����һ��������Ҷ�Ӧ����ҳ����Ʒ����
	@Override
	public List<Product> getProductPagesByPid(int pageSize,int pageIndex,int pid) throws Exception {
		List<Product> list =new ArrayList<Product>();
		String sql = "select top "+pageSize+" * from dbo.EASYBUY_PRODUCT where EPC_ID=? and EP_ID not in (select top "+(pageIndex-1)*pageSize+" EP_ID from dbo.EASYBUY_PRODUCT where EPC_ID=?)";
		Object[] objs={pid,pid};
		ResultSet rs = executeQuery(sql,objs);
		if(rs!=null){
			while(rs.next()){
				Product product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
				list.add(product);
			}
		}
		return list;
	}
	//��ѯ��Ӧ���������µ���Ʒ��Ϣ
	@Override
	public List<Product> getProductPagesByCid(int pageSize, int pageIndex,
			int cid) throws Exception {
		List<Product> list =new ArrayList<Product>();
		String sql = "select top "+pageSize+" * from dbo.EASYBUY_PRODUCT where EPC_CHILD_ID=? and  EP_ID not in (select top "+(pageIndex-1)*pageSize+" EP_ID from dbo.EASYBUY_PRODUCT where EPC_CHILD_ID=?)";
		Object[] objs={cid,cid};
		ResultSet rs = executeQuery(sql,objs);
		if(rs!=null){
			while(rs.next()){
				Product product=new Product();
				product.setCategoryId(rs.getInt("EPC_ID"));
				product.setChileCategoryId(rs.getInt("EPC_CHILD_ID"));
				product.setDescription(rs.getString("EP_DESCRIPTION"));
				product.setFileName(rs.getString("EP_FILE_NAME"));
				product.setId(rs.getInt("EP_ID"));
				product.setName(rs.getString("EP_NAME"));
				product.setPrice(rs.getDouble("EP_PRICE"));
				product.setStock(rs.getInt("EP_STOCK"));
				list.add(product);
			}
		}
		return list;
	}
	//��ѯ��Ӧ���������µ���Ʒ�ܼ�¼��
	@Override
	public int getProductCountByCid(int cid) throws Exception {
		String sql="select count(1) as mycount from EASYBUY_PRODUCT where EPC_CHILD_ID=?";
		Object[] objs={cid};
		ResultSet rs=executeQuery(sql, objs);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		return count;
	}
	
	//���ݱ��ɾ����Ӧ����Ʒ��Ϣ
	@Override
	public boolean delByPId(int pid) throws Exception {
		String sql="delete EASYBUY_PRODUCT where EP_ID=?";
		Object[] objs={pid};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//�����Ʒ��Ϣ
	@Override
	public boolean addProduct(Product product) throws Exception {
		
		//������Ʒ�Ķ�������ȥ���Ҹö��������Ӧ��һ������
		ProductCategoryDaoImpl ProductCategoryDaoImpl=new ProductCategoryDaoImpl();
		product.setCategoryId(ProductCategoryDaoImpl.getPidByCid(product.getChileCategoryId()));
		//EP_ID, EP_NAME, EP_DESCRIPTION, EP_PRICE, EP_STOCK, EPC_ID, EPC_CHILD_ID, EP_FILE_NAME
		String sql="insert into dbo.EASYBUY_PRODUCT values(?,?,?,?,?,?,?)";
		Object[] objs={
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getStock(),
				product.getCategoryId(),
				product.getChileCategoryId(),
				product.getFileName()
		};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//������Ʒ����޸Ķ�Ӧ����Ʒ��Ϣ
	@Override
	public boolean modifyProductByProId(Product product) throws Exception {
		StringBuffer sb=new StringBuffer("update dbo.EASYBUY_PRODUCT set EP_NAME=?, EP_DESCRIPTION=?, EP_PRICE=?, EP_STOCK=? ,EPC_ID=? ,EPC_CHILD_ID=?");
		int count=0;
		if(product.getFileName()==null){
			sb.append(" where EP_ID=?");
			Object[] objs={
					product.getName(),
					product.getDescription(),
					product.getPrice(),
					product.getStock(),
					product.getCategoryId(),
					product.getChileCategoryId(),
					product.getId()
			};
			count=executeUpdate(sb.toString(), objs);
		}else{
			sb.append(",EP_FILE_NAME=?");
			sb.append(" where EP_ID=?");
			Object[] objs={
					product.getName(),
					product.getDescription(),
					product.getPrice(),
					product.getStock(),
					product.getCategoryId(),
					product.getChileCategoryId(),
					product.getFileName(),
					product.getId()
			};
			count=executeUpdate(sb.toString(), objs);
		}
		if(count>0){
			return true;
		}
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
