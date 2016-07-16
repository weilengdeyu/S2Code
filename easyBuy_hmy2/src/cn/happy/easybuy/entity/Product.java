package cn.happy.easybuy.entity;

import java.io.Serializable;

//存放商品基本信息
public class Product implements Serializable {
     private int id;//商品编号
	 private String name;//商品名字
	 private String description;//商品描述
	 private double price;//商品价格
	 private int stock;//商品库存
	 private int categoryId;//所属分类ID
	 private int chileCategoryId;//所属二级分类ID
	 private String fileName;//上传的文件名
	 
	 
	 
	 
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
