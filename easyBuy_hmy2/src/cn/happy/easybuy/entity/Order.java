package cn.happy.easybuy.entity;

import java.util.Date;

//��Ŷ��������Ϣ
public class Order {
    private int id;//���
	private String userId;//�û�ID
	private String userName;//�û���
	private String userAddress;//�û���ַ
	private String creatTime;//����ʱ��
	private double cost;//���
	private int status;//״̬    1Ϊ����� 2λ���ͨ��  3���  4�����ѷ��� 5���ջ� 
	public int type;//���ʽ
	
	
	
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
