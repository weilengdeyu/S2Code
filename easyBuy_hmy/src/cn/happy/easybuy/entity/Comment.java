package cn.happy.easybuy.entity;

import java.util.Date;
//����û���������Ϣ
public class Comment {
    private int id;//���
	private String content;//�������������
	private String createTime;//����ʱ��
	private String reply;//������ԵĻظ�
	private String replyTime;//�ظ�ʱ��
	private String nickName;//�����û��ǳ�
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
