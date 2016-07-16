package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.ProductCategory;

public interface ProductCategoryDao {
	 /*
     * 查询所有父级分类
     * */
	public List<ProductCategory> getAllParentCate() throws Exception;
	/*
	 * 查询所有的二级分类
	 */
	public List<ProductCategory> getAllChildCate() throws Exception;
	
	//根据父id查找对应的二级分类
	public List<ProductCategory> getChildCateByPid(int pid) throws Exception;
	
	//添加一个分类
	public boolean add(ProductCategory productCategory) throws Exception;
	
	//删除二级分类
	public boolean delChildCate(int cid) throws Exception;
	
	//删除一级分类
	public boolean delPcate(int pid) throws Exception;
	//根据分类编号查找对应的分类的信息
	public ProductCategory getInfoById(int id) throws Exception;
	
	//根据分类编号修改该分类的信息
	public boolean modifyByid(ProductCategory productCategory) throws Exception;
	
	//根据二级分类查找对应的一级编号的值
	public int getPidByCid(int cid) throws Exception;
	
	//查询对应索引页的二级分类信息
	public List<ProductCategory> getPages(int pageIndex,int pageSize) throws Exception;
	
	//查询所有的二级分类的记录数
	public int getAllCateRecoreds() throws Exception;
	
	//查询所有的一级分类的记录数
	public int getCountPid() throws Exception;
	
	//根据索引页查找对应的一级分类的信息
	public ProductCategory getPagesPid(int pageIndex,int pageSize) throws Exception;
	
}
