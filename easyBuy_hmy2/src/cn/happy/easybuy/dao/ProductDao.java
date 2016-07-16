package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Product;
import cn.happy.easybuy.entity.ProductCategory;

public interface ProductDao {
    //获取所有的商品信息
	public List<Product> getAllProduct() throws Exception;
	//根据指定分类查询商品信息
	public List<Product> getAllProductByProCate(ProductCategory productCategory) throws Exception; 
	//查询所有的商品记录数
	public int getAllRecords() throws Exception;
	//拿取指定页面的集合
	public List<Product> getPageList(int pageSize, int pageIndex) throws Exception;
	//根据编号查找对应的商品信息
	public Product getProductById(int id) throws Exception;
	//查找指定一级分类下的商品总记录数
	public int getproductCountByPid(int pid) throws Exception;
	//根据指定的一级分类查找对应索引页的商品集合
	public List<Product> getProductPagesByPid(int pageSize,int pageIndex,int pid) throws Exception;
	//查询对应二级分类下的商品信息
	public List<Product> getProductPagesByCid(int pageSize,int pageIndex,int cid) throws Exception;
	//查询对应二级分类下的商品总记录数
	public int getProductCountByCid(int cid) throws Exception;
	//根据编号删除对应的商品信息
	public boolean delByPId(int pid) throws Exception;
	//添加商品信息
	public boolean addProduct(Product product) throws Exception;
	
	//根据商品编号修改对应的商品信息
	public boolean modifyProductByProId(Product product) throws Exception;
	
}
