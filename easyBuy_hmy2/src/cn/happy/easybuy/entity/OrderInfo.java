package cn.happy.easybuy.entity;

import java.util.List;


//订单信息
public class OrderInfo {
   private int id;//订单编号
   private Order order;//订单信息
   private List<OrderDetail> orderDetail;//订单详情
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Order getOrder() {
	return order;
}
public void setOrder(Order order) {
	this.order = order;
}
public List<OrderDetail> getOrderDetail() {
	return orderDetail;
}
public void setOrderDetail(List<OrderDetail> orderDetail) {
	this.orderDetail = orderDetail;
}
}
