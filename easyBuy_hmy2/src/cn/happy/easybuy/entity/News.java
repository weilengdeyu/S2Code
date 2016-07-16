package cn.happy.easybuy.entity;

import java.util.Date;

//存放新闻信息
public class News {
    private int id;//编号
    private String title;//标题
    private String content;//内容
    private String creatTime;//录入时间
    
    
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
}
