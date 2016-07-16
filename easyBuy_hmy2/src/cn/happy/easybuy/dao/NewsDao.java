package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.News;

public interface NewsDao {
	/**
	 *  ��ѯ����������Ϣ
	 * @return
	 * @throws Exception
	 */
	public List<News> getAllNews() throws Exception;
	/**
	 * ��ѯָ��Id������
	 * @param enId
	 * @return
	 * @throws Exception 
	 */
	public News selectNewsById(int Nid) throws Exception;
	/**
	 * 
	 * ��ѯ��������
	 */
	public int getNewsCount() throws Exception;
	/**
	 * ���������Ϣ
	 * @return
	 */
	public int addNews(News news);
	/** 
	 * ɾ��������Ϣ 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int removeNews(int Nid) throws Exception;
	/**
	 * �޸�������Ϣ
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public int modifyNews(News news) throws Exception;
	/**
	 * ��ѯ��ҳ������ص���������
	 * 
	 */
	public List<News> getNewsInfo(int pageIndex,int pageSize)throws Exception;
}
