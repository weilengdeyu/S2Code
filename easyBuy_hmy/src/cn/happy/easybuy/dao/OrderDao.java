package cn.happy.easybuy.dao;

import java.util.List;
import java.util.Map;

import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

//订单表
public interface OrderDao {
     
	//添加一个订单
	public boolean addOrder(Order order) throws Exception;
	//获取最新添加的订单编号
	public int getNewOid() throws Exception;
	
	//查询所有的订单信息
	public List<Order> getAllOrderInfo() throws Exception;
	
	//获取订单的总记录数
	public int getAllOrderCount() throws Exception;
	
	//按照分页查询一条订单信息
	public Order getPagesOrder(int pageSize,int pageIndex) throws Exception;
	
	//根据订货人获取分页后的订单信息
	public Order getPagesOrderByUid(int pageSize,int pageIndex,String uid) throws Exception;
	
	//根据订单编号更改订单的状态
	public boolean changeStatusByOid(Order order) throws Exception;
	
	//根据订单编号查询订单的相关信息
	public Order getInfoByOid(Order order) throws Exception;
	
	//获取指定订货人的所有订单记录数‘
	public int getCountByUid(String uid) throws Exception;
	
	//根据订货人和订单编号  结合查询订单信息
	public Order getOrderByOidanduid(String oid,String uid) throws Exception;
	
	
	
}
