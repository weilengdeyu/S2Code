package cn.happy.easybuy.entity;

import java.io.Serializable;

//�����Ʒ������Ϣ
public class Product implements Serializable {
     private int id;//��Ʒ���
	 private String name;//��Ʒ����
	 private String description;//��Ʒ����
	 private double price;//��Ʒ�۸�
	 private int stock;//��Ʒ���
	 private int categoryId;//��������ID
	 private int chileCategoryId;//������������ID
	 private String fileName;//�ϴ����ļ���
	 
	 
	 
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getChileCategoryId() {
		return chileCategoryId;
	}
	public void setChileCategoryId(int chileCategoryId) {
		this.chileCategoryId = chileCategoryId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	 
}
