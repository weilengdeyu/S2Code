package cn.happy.easybuy.entity;

import java.util.List;


//������Ϣ
public class OrderInfo {
   private int id;//�������
   private Order order;//������Ϣ
   private List<OrderDetail> orderDetail;//��������
   
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
