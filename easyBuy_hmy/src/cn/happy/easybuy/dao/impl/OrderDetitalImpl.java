package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.OrderDetitalDao;
import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

public class OrderDetitalImpl extends BaseDao implements OrderDetitalDao {
	//添加一条订单详情
	@Override
	public boolean addOrderDetital(OrderDetail orderDetail) throws Exception {
		//EOD_ID, EO_ID, EP_ID, EOD_QUANTITY, EOD_COST
				String sql="insert into dbo.EASYBUY_ORDER_DETAIL values(?,?,?,?) ";
				Object[] objs={
						orderDetail.getOrderId(),
						orderDetail.getProductId(),
						orderDetail.getQuantity(),
						orderDetail.getCost()
				};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	
	//查询所有的订单详情信息
	@Override
	public List<OrderDetail> getAllDetital() throws Exception {
		//String s
		return null;
	}
	//根据分页查询订单详情信息
	@Override
	public List<OrderDetail> getPagesDetital(int pageSize,int pageIndex) throws Exception {
		String sql="select top "+pageSize+" * from dbo.EASYBUY_ORDER_DETAIL where EOD_ID not in(select top "+(pageIndex-1)*pageSize+" EOD_ID from dbo.EASYBUY_ORDER_DETAIL order by EOD_ID desc)  order by EOD_ID desc ";
		ResultSet rs=executeQuery(sql);
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		if(rs!=null){
			while(rs.next()){
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(rs.getDouble("EOD_COST"));
				orderDetail.setId(rs.getInt("EOD_ID"));
				orderDetail.setOrderId(rs.getInt("EO_ID"));
				orderDetail.setProductId(rs.getInt("EP_ID"));
				orderDetail.setQuantity(rs.getInt("EOD_QUANTITY"));
				list.add(orderDetail);
			}
		}
		return list;
	}
	//根据订单详情的总记录数
	@Override
	public int getAllCount() throws Exception {
		String sql="select count(1) as mycount from EASYBUY_ORDER_DETAIL";
		int count=0;
		ResultSet rs=executeQuery(sql);
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		return count;
	}

	//根据订单信息查找对应的订单信息详情
	@Override
	public List<OrderDetail> getDetailByOid(Order order) throws Exception {
        String sql="select * from dbo.EASYBUY_ORDER_DETAIL where EO_ID=?";
        Object[] objs={order.getId()};
        ResultSet rs=executeQuery(sql,objs);
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		if(rs!=null){
			while(rs.next()){
				OrderDetail orderDetail=new OrderDetail();
				orderDetail.setCost(rs.getDouble("EOD_COST"));
				orderDetail.setId(rs.getInt("EOD_ID"));
				orderDetail.setOrderId(rs.getInt("EO_ID"));
				orderDetail.setProductId(rs.getInt("EP_ID"));
				orderDetail.setQuantity(rs.getInt("EOD_QUANTITY"));
				list.add(orderDetail);
			}
		}
		return list;
	}


}
