package cn.happy.easybuy.entity;

import java.util.List;

//��Ŷ���������Ϣ
public class OrderDetail {
    private int id;//���
	private int orderId;//�������
	private int productId;//��ƷID
	private int quantity;//����
	private double cost;//���
	private Product product;//��Ʒid��Ӧ����Ʒ��Ϣ
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
