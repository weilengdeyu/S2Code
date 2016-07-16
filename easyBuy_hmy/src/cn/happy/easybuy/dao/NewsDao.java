package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.News;

public interface NewsDao {
	/**
	 *  查询所有新闻信息
	 * @return
	 * @throws Exception
	 */
	public List<News> getAllNews() throws Exception;
	/**
	 * 查询指定Id的新闻
	 * @param enId
	 * @return
	 * @throws Exception 
	 */
	public News selectNewsById(int Nid) throws Exception;
	/**
	 * 
	 * 查询新闻总数
	 */
	public int getNewsCount() throws Exception;
	/**
	 * 添加新闻信息
	 * @return
	 */
	public int addNews(News news);
	/** 
	 * 删除新闻信息 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int removeNews(int Nid) throws Exception;
	/**
	 * 修改新闻信息
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int modifyNews(News news) throws Exception;
	/**
	 * 查询分页内容相关的新闻内容
	 * 
	 */
	public List<News> getNewsInfo(int pageIndex,int pageSize)throws Exception;
}
