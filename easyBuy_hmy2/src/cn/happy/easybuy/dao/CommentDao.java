package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Comment;

public interface CommentDao {
/*
 * ������Ե�һ������
 * ������
 */
	public int addComment(Comment comment);
	/*
	 * ��ѯ�����ܼ�¼��
	 */
	public int getCommentCount() throws Exception;
	/*
	 * ��ѯ����������Ϣ
	 */
	public List<Comment>getCommentInfo (int pageIndex,int pageSize) throws Exception;
	/**
	 * ��������Id��ѯ���Ӧ��������Ϣ����
	 * @param ������
	 * @return list
	 */
	public Comment selectCommentById(int id)throws Exception;
	
	/**
	 * ����IDɾ����Ե�������Ϣ����
	 * @param ������
	 * @return INT
	 * @throws Exception 
	 */
	public int removeComment(int id) throws Exception;
    
	/**
	 * �ظ����Է���
	 * @param ������
	 * @return INT
	 * @throws Exception 
	 */
	public int getRecordCount(Comment comment) throws Exception;
	
}
