package cn.happy.easybuy.dao.impl;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.CommentDao;
import cn.happy.easybuy.entity.Comment;

public class CommentDaoImpl extends BaseDao implements CommentDao {
/*
 * ���������Ϣ����
 * @������
 */
	
	@Override
	public int addComment(Comment comment) {
		String sql="insert into dbo.EASYBUY_COMMENT values(?,?,?,?,?)";
		Object[] para={comment.getContent(),comment.getCreateTime(),comment.getReply(),comment.getReplyTime(),comment.getNickName() };
		int count=0;
		try {
			count=executeUpdate(sql,para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
/*
 * ��ѯ���԰�������Ϣ
 * @������
 */
	@Override
	public int getCommentCount() throws Exception {
		String sql="select count(1) as count from dbo.EASYBUY_COMMENT";
		ResultSet rs = executeQuery(sql);
		int id =0;
		if (rs!=null) {
			try {
				while (rs.next()) {
					id=rs.getInt("count");					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return id;
	}
/*
 * ��ѯ����������Ϣ
 * @������
 */
	@Test
	public void test(){
		Comment comment=new Comment();
		comment.setContent("sdgeaer");
		comment.setCreateTime("1694-04-12");
		comment.setNickName("ahuf");
		int count=addComment(comment);
		System.out.println(count);
	}
	@Override
	public List<Comment> getCommentInfo(int pageIndex, int pageSize) throws Exception {
		String sql="select top "+pageSize+" * from dbo.EASYBUY_COMMENT where EC_ID not in(select top "+(pageIndex-1)*pageSize+" EC_ID from dbo.EASYBUY_COMMENT order by EC_ID desc) order by EC_ID desc";
		ResultSet rs = executeQuery(sql);
		List<Comment>list=new ArrayList<Comment>();
		if (rs!=null) {
			try {
				while (rs.next()) {
					Comment c =new Comment();
					c.setId(rs.getInt("EC_ID"));
					c.setContent(rs.getString("EC_CONTENT"));
					c.setCreateTime(rs.getString("EC_CREATE_TIME"));
					c.setNickName(rs.getString("EC_NICK_NAME"));
					c.setReply(rs.getString("EC_REPLY"));
					c.setReplyTime(rs.getString("EC_REPLY_TIME"));
					list.add(c);
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			
			}			
		}
	
		return list;
	}
	/*
	 * ����Id��ѯ���Ӧ��������Ϣ
	 * @������
	 */
	@Override
	public Comment selectCommentById(int id) throws Exception {
		String sql="select * from dbo.EASYBUY_COMMENT where EC_ID =?";
		Object[] objs={id};
		ResultSet rs = executeQuery(sql,objs);
		Comment con=null;
		try {
			if (rs!=null) {
				while (rs.next()) {
					//EC_ID, EC_CONTENT, EC_CREATE_TIME, EC_REPLY, EC_REPLY_TIME, EC_NICK_NAME
					con = new Comment();
					con.setId(rs.getInt("EC_ID"));
					con.setReply(rs.getString("EC_REPLY"));
					con.setContent(rs.getString("EC_CONTENT"));
					con.setCreateTime(rs.getString("EC_CREATE_TIME"));
					con.setReplyTime(rs.getString("EC_REPLY_TIME"));
					con.setNickName(rs.getString("EC_NICK_NAME"));
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	/*
	 * ����Idɾ�����Ӧ��������Ϣ
	 * @������
	 */
	@Override
	public int removeComment(int id) throws Exception {
		String sql = "delete from EASYBUY_COMMENT where ec_id = ?";
		Object[] objs={id};
		int count =executeUpdate(sql, objs);
		
		return count;
	}
	/*
	 * �ظ�����
	 * @������
	 */
	@Override
	public int getRecordCount(Comment comment) throws Exception {
		String sql = "update  EASYBUY_COMMENT set EC_REPLY=?, EC_REPLY_TIME = ? where EC_ID=?";
		Object[] objs={
				comment.getReply(),
				comment.getReplyTime(),
				comment.getId()
		};
		int count =executeUpdate(sql,objs);
		return count;
	}

}
