package cn.happy.easybuy.entity;
//存放商品的分类基本信息
public class ProductCategory {
    private int id;//编号
	private String name;//名字
	private int parentId;//父分类
	
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
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}
