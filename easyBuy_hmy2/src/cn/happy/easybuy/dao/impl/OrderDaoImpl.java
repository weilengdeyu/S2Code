package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.OrderDao;
import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

public class OrderDaoImpl extends BaseDao implements OrderDao{

	//��Ӷ�����
	@Override
	public boolean addOrder(Order order) throws Exception {
		//EO_ID, EO_USER_ID, EO_USER_NAME, EO_USER_ADDRESS, EO_CREATE_TIME, EO_COST, EO_STATUS, EO_TYPE
		String sql="insert into dbo.EASYBUY_ORDER values(?,?,?,?,?,?,?)";
		Object[] objs={
				order.getUserId(),
				order.getUserName(),
				order.getUserAddress(),
				order.getCreatTime(),
				order.getCost(),
				order.getStatus(),
				order.getType()
		};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//��ȡ������ӵĶ������
	@Override
	public int getNewOid() throws Exception {
		String sql="select  @@IDENTITY as mynum  from dbo.EASYBUY_ORDER";
		ResultSet rs=executeQuery(sql);
		int num=0;
		if(rs!=null){
			while(rs.next()){
				num=rs.getInt("mynum");
			}
		}
		return num;
	}
	//��ѯ���еĶ�����Ϣ
	@Override
	public List<Order> getAllOrderInfo() throws Exception {
		String sql="select * from dbo.EASYBUY_ORDER  order by EO_CREATE_TIME desc";
		ResultSet rs=executeQuery(sql);
		List<Order> list=new ArrayList<Order>();
		if(rs!=null){
			while(rs.next()){
				Order order=new Order();
				order.setCost(rs.getDouble("EO_COST"));
				order.setCreatTime(rs.getString("EO_CREATE_TIME"));
				order.setId(rs.getInt("EO_ID"));
				order.setStatus(rs.getInt("EO_STATUS"));
				order.setType(rs.getInt("EO_TYPE"));
				order.setUserAddress(rs.getString("EO_USER_ADDRESS"));
				order.setUserId(rs.getString("EO_USER_ID"));
				order.setUserName(rs.getString("EO_USER_NAME"));
				list.add(order);
			}
		}
		return list;
	}

	//���շ�ҳ��ѯһ��������Ϣ
	@Test
	public void test(){
		try {
			List<Order> list= getAllOrderInfo();
			for (Order order : list) {
				System.out.println(order.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Order getPagesOrder(int pageSize,int pageIndex) throws Exception {
		String sql="select top "+pageSize+" * from dbo.EASYBUY_ORDER where EO_ID not in(select top "+(pageIndex-1)*pageSize+" EO_ID from dbo.EASYBUY_ORDER order by EO_ID desc)  order by EO_ID desc ";
		ResultSet rs=executeQuery(sql);
		Order order=new Order();
		if(rs!=null){
			while(rs.next()){
				
				order.setCost(rs.getDouble("EO_COST"));
				order.setCreatTime(rs.getString("EO_CREATE_TIME"));
				order.setId(rs.getInt("EO_ID"));
				order.setStatus(rs.getInt("EO_STATUS"));
				order.setType(rs.getInt("EO_TYPE"));
				order.setUserAddress(rs.getString("EO_USER_ADDRESS"));
				order.setUserId(rs.getString("EO_USER_ID"));
				order.setUserName(rs.getString("EO_USER_NAME"));
			}
		}
		return order;
	}
	
	
	
	//��ȡ�������ܼ�¼��
	@Override
	public int getAllOrderCount() throws Exception {
		String sql="select count(1) as mycount from dbo.EASYBUY_ORDER";
		ResultSet rs=executeQuery(sql);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
				
		return count;
	}
	
	//���ݶ����˻�ȡ��ҳ��Ķ�����Ϣ
	@Override
	public Order getPagesOrderByUid(int pageSize, int pageIndex,String uid)
			throws Exception {
		String sql="select top "+pageSize+" * from dbo.EASYBUY_ORDER where EO_ID not in(select top "+(pageIndex-1)*pageSize+" EO_ID from dbo.EASYBUY_ORDER where EO_USER_ID=? order by EO_ID desc) and EO_USER_ID=? order by EO_ID desc ";
		Object[] objs={uid,uid};
		ResultSet rs=executeQuery(sql,objs);
		Order order=new Order();
		if(rs!=null){
			while(rs.next()){
				order.setCost(rs.getDouble("EO_COST"));
				order.setCreatTime(rs.getString("EO_CREATE_TIME"));
				order.setId(rs.getInt("EO_ID"));
				order.setStatus(rs.getInt("EO_STATUS"));
				order.setType(rs.getInt("EO_TYPE"));
				order.setUserAddress(rs.getString("EO_USER_ADDRESS"));
				order.setUserId(rs.getString("EO_USER_ID"));
				order.setUserName(rs.getString("EO_USER_NAME"));
			}
		}
		return order;
	}
	
	//���ݶ�����Ÿ��Ķ�����״̬
	@Override
	public boolean changeStatusByOid(Order order) throws Exception {
		String sql="update  dbo.EASYBUY_ORDER  set EO_STATUS=? where EO_ID=?";
		Object[] objs={
				order.getStatus(),
			    order.getId()
		};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	
	//���ݶ�����Ų�ѯ�����������Ϣ
	@Override
	public Order getInfoByOid(Order order) throws Exception {
		String sql="select * from dbo.EASYBUY_ORDER where EO_ID=?";
		Object[] objs={order.getId()};
		ResultSet rs=executeQuery(sql,objs);
		Order orders=new Order();
		if(rs!=null){
			while(rs.next()){
				orders.setCost(rs.getDouble("EO_COST"));
				orders.setCreatTime(rs.getString("EO_CREATE_TIME"));
				orders.setId(rs.getInt("EO_ID"));
				orders.setStatus(rs.getInt("EO_STATUS"));
				orders.setType(rs.getInt("EO_TYPE"));
				orders.setUserAddress(rs.getString("EO_USER_ADDRESS"));
				orders.setUserId(rs.getString("EO_USER_ID"));
				orders.setUserName(rs.getString("EO_USER_NAME"));
			}
		}
		return orders;
	}
	
	
	//��ȡָ�������˵����ж�����¼����
	@Override
	public int getCountByUid(String uid) throws Exception {
		String sql="select count(1) as mycount from EASYBUY_ORDER where EO_USER_ID=?";
		Object[] objs={uid};
		ResultSet rs=executeQuery(sql, objs);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		return count;
	}
	
	//���ݶ����˺Ͷ������  ��ϲ�ѯ������Ϣ
	@Override
	public Order getOrderByOidanduid(String oid, String uid) throws Exception {
		String sql="select * from EASYBUY_ORDER where EO_USER_ID=? and EO_ID=? ";
		Object[] objs={
				uid,oid
		};
		ResultSet rs=executeQuery(sql, objs);
		Order orders=new Order();
		if(rs!=null){
			while(rs.next()){
				orders.setCost(rs.getDouble("EO_COST"));
				orders.setCreatTime(rs.getString("EO_CREATE_TIME"));
				orders.setId(rs.getInt("EO_ID"));
				orders.setStatus(rs.getInt("EO_STATUS"));
				orders.setType(rs.getInt("EO_TYPE"));
				orders.setUserAddress(rs.getString("EO_USER_ADDRESS"));
				orders.setUserId(rs.getString("EO_USER_ID"));
				orders.setUserName(rs.getString("EO_USER_NAME"));
			}
		}
		return orders;
	}
	
	

	
	
	
}
