package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.ProductCategoryDao;
import cn.happy.easybuy.entity.ProductCategory;

public class ProductCategoryDaoImpl extends BaseDao implements ProductCategoryDao {
@Test
public void test(){
	try {
		List<ProductCategory> list= getAllChildCate();
		for (ProductCategory productCategory : list) {
			System.out.println(productCategory.getName());
		}
	} catch (Exception e) {
		
		e.printStackTrace();
	}
}
  //һ����ǩ
	@Override
	public List<ProductCategory> getAllParentCate() throws Exception {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		String sql="select * from dbo.EASYBUY_PRODUCT_CATEGORY where EPC_ID=EPC_PARENT_ID";
		ResultSet rs = executeQuery(sql);
		if (rs!=null) {
			while (rs.next()) {
				ProductCategory pc =new ProductCategory();
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
				list.add(pc);
			}
		}
			
		return list;
	}
  //
	@Override
	public List<ProductCategory> getAllChildCate() throws Exception {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		String sql="select * from dbo.EASYBUY_PRODUCT_CATEGORY where EPC_ID!=EPC_PARENT_ID";
		ResultSet rs = executeQuery(sql);
		if (rs!=null) {
			while (rs.next()) {
				ProductCategory pc =new ProductCategory();
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
				list.add(pc);
			}
		}
			
		return list;
	}
	//���ݸ�id���Ҷ�Ӧ�Ķ�������
	@Override
	public List<ProductCategory> getChildCateByPid(int pid) throws Exception {
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		String sql="select * from dbo.EASYBUY_PRODUCT_CATEGORY where EPC_PARENT_ID=? and EPC_ID!=EPC_PARENT_ID";
		Object[] objs={pid};
		ResultSet rs=executeQuery(sql, objs);
		if (rs!=null) {
			while (rs.next()) {
				ProductCategory pc =new ProductCategory();
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
				list.add(pc);
			}
		}
			
		return list;
	}
	
	//���һ��һ������
	@Override
	public boolean add(ProductCategory productCategory) throws Exception {
		String sql="insert into dbo.EASYBUY_PRODUCT_CATEGORY values(?,?)";
		Object[] objs={
				productCategory.getName(),
				productCategory.getParentId()
		};
		int count=executeUpdate(sql, objs);
		int num=0;//���游������ı��
		if(count>0){
			if(productCategory.getParentId()==0){//������ڵ�Ϊ0����˷���Ϊһ������
				String sqls="select @@IDENTITY as counts from dbo.EASYBUY_PRODUCT_CATEGORY";//��ȡ�ղ���ķ����� ��Ϊ����ĸ�����
				ResultSet rs=executeQuery(sqls);
				if(rs!=null){
					while(rs.next()){
						num=rs.getInt("counts");
					}
				}
				//�ٽ���һ�θ���
				String str="update EASYBUY_PRODUCT_CATEGORY set EPC_PARENT_ID=? where EPC_ID=?";
				Object[] paras={
						num,
						num
				};
				int counts=executeUpdate(str, paras);
				if(counts>0){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	//ɾ����������
	@Override
	public boolean delChildCate(int cid) throws Exception {
		String sql="delete dbo.EASYBUY_PRODUCT_CATEGORY where EPC_ID=?";
		Object[] objs={cid};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//ɾ��һ������
	@Override
	public boolean delPcate(int pid) throws Exception {
		String sql="delete dbo.EASYBUY_PRODUCT_CATEGORY where EPC_PARENT_ID=?";
		Object[] objs={pid};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//���ݷ����Ų��Ҷ�Ӧ�ķ������Ϣ
	@Override
	public ProductCategory getInfoById(int id) throws Exception {
		String sql="select * from EASYBUY_PRODUCT_CATEGORY where EPC_ID=?";
		Object[] objs={id};
		ResultSet rs=executeQuery(sql, objs);
		ProductCategory pc =new ProductCategory();
		if (rs!=null) {
			while (rs.next()) {
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
			}
		}
			
		return pc;
	}
	
	//���ݷ������޸ĸ÷������Ϣ
	@Override
	public boolean modifyByid(ProductCategory productCategory) throws Exception {
		int count=0;
		if(productCategory.getParentId()==0){//���˷����Ϊһ������
			String sql="update  dbo.EASYBUY_PRODUCT_CATEGORY set  EPC_NAME=?,EPC_PARENT_ID=? where EPC_ID=?";
			Object[] objs={
					productCategory.getName(),
					productCategory.getId(),
					productCategory.getId()
			};
			count=executeUpdate(sql, objs);
			//�����и÷������Ʒ�ĸ�����ȫ������EPC_ID, EPC_CHILD_ID
			String sql2="update EASYBUY_PRODUCT set EPC_ID=? where EPC_CHILD_ID=?";
			Object[] paras={productCategory.getId(),productCategory.getId()};
			int counts=executeUpdate(sql2, paras);
		}else{
			String sql="update  dbo.EASYBUY_PRODUCT_CATEGORY set  EPC_NAME=?,EPC_PARENT_ID=? where EPC_ID=?";
			Object[] objs={
					productCategory.getName(),
					productCategory.getParentId(),
					productCategory.getId()
			};
			count=executeUpdate(sql, objs);
			//�����и÷������Ʒ�ĸ�����ȫ������EPC_ID, EPC_CHILD_ID
			String sql2="update EASYBUY_PRODUCT set EPC_ID=? where EPC_CHILD_ID=?";
			Object[] paras={productCategory.getParentId(),productCategory.getId()};
			int counts=executeUpdate(sql2, paras);
		}
		
		if(count>0){
			return true;
		}
		return false;
	}
	
	//���ݶ���������Ҷ�Ӧ��һ����ŵ�ֵ
	@Override
	public int getPidByCid(int cid) throws Exception {
		String sql="select EPC_PARENT_ID from dbo.EASYBUY_PRODUCT_CATEGORY where EPC_ID=?";
		Object[] objs={cid};
		int count=0;
		ResultSet rs=executeQuery(sql, objs);
		if(rs!=null){
			if(rs.next()){
				count=rs.getInt("EPC_PARENT_ID");
			}
		}
		return count;
	}
	
	//��ѯ��Ӧ����ҳ�Ķ���������Ϣ
	@Override
	public List<ProductCategory> getPages(int pageIndex,int pageSize) throws Exception {
		String sql="select top "+pageSize+" * from EASYBUY_PRODUCT_CATEGORY where EPC_ID not in(select top "+(pageIndex-1)*pageSize+" EPC_ID from EASYBUY_PRODUCT_CATEGORY where EPC_ID!=EPC_PARENT_ID) and EPC_ID!=EPC_PARENT_ID ";
		ResultSet rs=executeQuery(sql);
		List<ProductCategory> list=new ArrayList<ProductCategory>();
		if (rs!=null) {
			while (rs.next()) {
				ProductCategory pc =new ProductCategory();
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
				list.add(pc);
			}
		}		
		return list;
	}
	
	//��ѯ���еĶ�������ļ�¼��
	@Override
	public int getAllCateRecoreds() throws Exception {
		String sql="select count(1) as mycount from EASYBUY_PRODUCT_CATEGORY where EPC_ID!=EPC_PARENT_ID";
		int count=0;
		ResultSet rs=executeQuery(sql);
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		
		return count;
	}
	
	//��ѯ���е�һ������ļ�¼��
	@Override
	public int getCountPid() throws Exception {
		String sql="select count(1) as mycount from EASYBUY_PRODUCT_CATEGORY where EPC_ID=EPC_PARENT_ID";
		int count=0;
		ResultSet rs=executeQuery(sql);
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		
		return count;
	}
	//��������ҳ���Ҷ�Ӧ��һ���������Ϣ
	@Override
	public ProductCategory getPagesPid(int pageIndex, int pageSize)
			throws Exception {
		String sql="select top "+pageSize+" * from EASYBUY_PRODUCT_CATEGORY where EPC_ID not in(select top "+(pageIndex-1)*pageSize+" EPC_ID from EASYBUY_PRODUCT_CATEGORY where EPC_ID=EPC_PARENT_ID) and EPC_ID=EPC_PARENT_ID ";
		ResultSet rs=executeQuery(sql);
		ProductCategory pc =new ProductCategory();
		if (rs!=null) {
			while (rs.next()) {
				
				pc.setId(rs.getInt("EPC_ID"));
				pc.setName(rs.getString("EPC_NAME"));
				pc.setParentId(rs.getInt("EPC_PARENT_ID"));
				
			}
		}		
		return pc;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
