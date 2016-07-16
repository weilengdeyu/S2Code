package cn.happy.easybuy.until;

import java.io.Serializable;

import cn.happy.easybuy.entity.Product;

public class ShoppingCartItem implements Serializable {
	private Product product;//�������Ʒ����
    private int num;//�������Ʒ����
    private double cost;//�۸�С��
    
    
    //���캯��
    public ShoppingCartItem(Product product, int num) {
		this.product = product;
		this.num = num;
		this.cost =(product.getPrice()*1);
	}
    
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
