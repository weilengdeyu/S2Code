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
  //一级标签
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
	//根据父id查找对应的二级分类
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
	
	//添加一个一级分类
	@Override
	public boolean add(ProductCategory productCategory) throws Exception {
		String sql="insert into dbo.EASYBUY_PRODUCT_CATEGORY values(?,?)";
		Object[] objs={
				productCategory.getName(),
				productCategory.getParentId()
		};
		int count=executeUpdate(sql, objs);
		int num=0;//保存父级分类的编号
		if(count>0){
			if(productCategory.getParentId()==0){//如果根节点为0，则此分类为一级分类
				String sqls="select @@IDENTITY as counts from dbo.EASYBUY_PRODUCT_CATEGORY";//获取刚插入的分类编号 作为自身的父分类
				ResultSet rs=executeQuery(sqls);
				if(rs!=null){
					while(rs.next()){
						num=rs.getInt("counts");
					}
				}
				//再进行一次更新
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
	
	//删除二级分类
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
	//删除一级分类
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
	//根据分类编号查找对应的分类的信息
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
	
	//根据分类编号修改该分类的信息
	@Override
	public boolean modifyByid(ProductCategory productCategory) throws Exception {
		int count=0;
		if(productCategory.getParentId()==0){//将此分类改为一级分类
			String sql="update  dbo.EASYBUY_PRODUCT_CATEGORY set  EPC_NAME=?,EPC_PARENT_ID=? where EPC_ID=?";
			Object[] objs={
					productCategory.getName(),
					productCategory.getId(),
					productCategory.getId()
			};
			count=executeUpdate(sql, objs);
			//将所有该分类的商品的父分类全部更新EPC_ID, EPC_CHILD_ID
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
			//将所有该分类的商品的父分类全部更新EPC_ID, EPC_CHILD_ID
			String sql2="update EASYBUY_PRODUCT set EPC_ID=? where EPC_CHILD_ID=?";
			Object[] paras={productCategory.getParentId(),productCategory.getId()};
			int counts=executeUpdate(sql2, paras);
		}
		
		if(count>0){
			return true;
		}
		return false;
	}
	
	//根据二级分类查找对应的一级编号的值
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
	
	//查询对应索引页的二级分类信息
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
	
	//查询所有的二级分类的记录数
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
	
	//查询所有的一级分类的记录数
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
	//根据索引页查找对应的一级分类的信息
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
