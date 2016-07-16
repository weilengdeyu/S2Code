package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;



import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.NewsDao;
import cn.happy.easybuy.entity.News;

public class NewsDaoImpl extends BaseDao implements NewsDao {
/**
 * 查询新闻信息
 * 李晓鹏
 */
	@Override
	public List<News> getAllNews() throws Exception {
		List <News> list = new ArrayList<News>();
		//查询新闻信息
		String sql ="select * from dbo.EASYBUY_NEWS order by EN_ID desc";
		ResultSet rs = executeQuery(sql);
		if (rs!=null) {
			while (rs.next()) {
				News ns = new News();
				ns.setId(rs.getInt("EN_ID"));
				ns.setContent(rs.getString("EN_CONTENT"));
				ns.setCreatTime(rs.getString("EN_CREATE_TIME"));
				ns.setTitle(rs.getString("EN_TITLE"));
				list.add(ns);
			}
		}
		return list;
	}
	@Test
	public void test(){
		try {
			List<News> list= getAllNews();
			for (News news : list) {
				System.out.println(news.getTitle());
			}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	/**
	 * 根据新闻ID查询相关的新闻信息
	 * 李晓鹏
	 * @throws Exception 
	 */
	@Override
	public News selectNewsById(int Nid) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from dbo.EASYBUY_NEWS where en_id=?";
		Object[] objs ={Nid};
		ResultSet rs = executeQuery(sql, objs);
		News ns = new News();
		try {
			if (rs!=null) {
				while (rs.next()) {
					//EN_ID, EN_TITLE, EN_CONTENT, EN_CREATE_TIME
					ns = new News();
					ns.setId(rs.getInt("EN_ID"));
					ns.setTitle(rs.getString("EN_TITLE"));
					ns.setContent(rs.getString("EN_CONTENT"));
					ns.setCreatTime(rs.getString("EN_CREATE_TIME"));
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ns;
	}
	/**
	 * 添加新闻信息
	 * 李晓鹏
	 */
	@Override
	public int addNews(News news) {
		int count = 0;
		try {
			boolean flag=true;//默认可添加
			//获取所有的新闻信息
			List<News> list= getAllNews();
			for (News news2 : list) {
				if(news2.getTitle().equals(news.getTitle())){//如果新闻标题相同  则标识不可添加
					flag=false;
				}
			}
			
			//出了for循环  判断是否可添加
			if(flag){//如果为true 可添加				
				String sql="insert into dbo.EASYBUY_NEWS values(?,?,?)";
				Object[] para={news.getTitle(),news.getContent(),news.getCreatTime()};
				count=executeUpdate(sql, para);
			}
				
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}
	/**
	 * 删除新闻信息
	 * 李晓鹏
	 * @throws Exception 
	 */
	@Override
	public int removeNews(int Nid) throws Exception {
		// TODO Auto-generated method stub
		String sql="delete easybuy_news where en_id=?";
		Object[] objs={Nid};
		int count =executeUpdate(sql, objs);
		return count;
	}
	/**
	 * 修改新闻信息
	 * 李晓鹏
	 * @throws Exception 
	 */
	@Override
	public int modifyNews(News news) throws Exception {
		// TODO Auto-generated method stub
		String sql="update easybuy_news set en_title=?,en_content=? where en_id=?";
		Object[] objs={
				news.getTitle(),
				news.getContent(),
				news.getId()
		};
		int count =executeUpdate(sql,objs);
		return count; 
	}
	/**
	 * 查询新闻总数
	 * 李晓鹏
	 */
	@Override
	public int getNewsCount() throws Exception {
		String sql = "select count(1) as count from dbo.easybuy_news";
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
	/**
	 * 查询分页相关的新闻内容
	 */
	@Override
	public List<News> getNewsInfo(int pageIndex, int pageSize) throws Exception {

		String sql="select top "+pageSize+" * from easybuy_news where EN_ID not in(select top "+(pageIndex-1)*pageSize+" EN_ID from dbo.easybuy_news order by EN_ID desc) order by EN_ID desc";
		ResultSet rs = executeQuery(sql);
		List<News> list=new ArrayList <News>();
		if (rs!=null) {
			try {
				while (rs.next()) {
					 News  n = new News();	
					 //EN_ID, EN_TITLE, EN_CONTENT, EN_CREATE_TIME
					n.setId(rs.getInt("EN_ID"));
					n.setTitle(rs.getString("EN_TITLE"));
					n.setContent(rs.getString("EN_CONTENT"));
					n.setCreatTime(rs.getString("EN_CREATE_TIME"));
					list.add(n);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
