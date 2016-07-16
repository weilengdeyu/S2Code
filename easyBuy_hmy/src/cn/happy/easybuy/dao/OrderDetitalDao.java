package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

public interface OrderDetitalDao {
   //添加一条订单详情
	public boolean addOrderDetital(OrderDetail orderDetail) throws Exception;
	//查询所有的订单详情信息
	public List<OrderDetail> getAllDetital() throws Exception;
	
	//根据分页查询订单详情信息
	public List<OrderDetail> getPagesDetital(int pageSize,int pageIndex) throws Exception;
	
	//根据订单详情的总记录数
	public int getAllCount() throws Exception;
	
	//根据订单信息查找对应的订单信息详情
	public List<OrderDetail> getDetailByOid(Order order) throws Exception;
}
