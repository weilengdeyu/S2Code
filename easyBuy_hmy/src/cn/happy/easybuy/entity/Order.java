package cn.happy.easybuy.entity;

import java.util.Date;

//存放订单相关信息
public class Order {
    private int id;//编号
	private String userId;//用户ID
	private String userName;//用户名
	private String userAddress;//用户地址
	private String creatTime;//创建时间
	private double cost;//金额
	private int status;//状态    1为待审核 2位审核通过  3配货  4卖家已发货 5已收货 
	public int type;//付款方式
	
	
	
	public Order() {
	}

	public Order( String userId, String userName, String userAddress,
			String creatTime, double cost, int status, int type) {
		this.userId = userId;
		this.userName = userName;
		this.userAddress = userAddress;
		this.creatTime = creatTime;
		this.cost = cost;
		this.status = status;
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
