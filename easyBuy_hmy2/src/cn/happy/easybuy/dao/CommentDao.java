package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Comment;

public interface CommentDao {
/*
 * 添加留言的一个方法
 * 李晓鹏
 */
	public int addComment(Comment comment);
	/*
	 * 查询留言总记录数
	 */
	public int getCommentCount() throws Exception;
	/*
	 * 查询所有留言信息
	 */
	public List<Comment>getCommentInfo (int pageIndex,int pageSize) throws Exception;
	/**
	 * 根据留言Id查询相对应的留言信息方法
	 * @param 李晓鹏
	 * @return list
	 */
	public Comment selectCommentById(int id)throws Exception;
	
	/**
	 * 根据ID删除相对的留言信息方法
	 * @param 李晓鹏
	 * @return INT
	 * @throws Exception 
	 */
	public int removeComment(int id) throws Exception;
    
	/**
	 * 回复留言方法
	 * @param 李晓鹏
	 * @return INT
	 * @throws Exception 
	 */
	public int getRecordCount(Comment comment) throws Exception;
	
}
