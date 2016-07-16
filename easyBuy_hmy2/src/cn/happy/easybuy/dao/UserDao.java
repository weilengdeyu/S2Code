package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.User;

public interface UserDao {
	//��ѯ���е��û���Ϣ
	public List<User> getAllUserInfo() throws Exception;
	//����û���Ϣ
	public boolean add(User user) throws Exception;
	
	//��֤���û����Ƿ����
	public boolean useridOrNot(User user) throws Exception;
	//��֤�Ƿ��¼�ɹ�
	public boolean isLoginOrNot(User user) throws Exception;
	
	//�����û������Ҷ�Ӧ���û���������Ϣ
	public User getUserInfoByUid(String uid) throws Exception;

	
	/**
	 * �����û��������û�����
	 * @param userID
	 * @return
	 * @throws Exception 
	 */
	public User selectByName(String userID) throws Exception;
	/**
	 * �޸��û�����
	 * @return
	 * @throws Exception 
	 */
	public int modifyUser(User user) throws Exception;
	
	/**
	 * ɾ���û�����
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public int removeUser(String userId) throws Exception;
	/**
	 * ��ѯ�����ܼ�¼��
	 */
	public int getUserCount() throws Exception;
	/**
	 * ��ѯ��ҳ������ص��û�����
	 * 
	 */
	public List<User> getUserInfo(int pageIndex,int pageSize)throws Exception;
	
	
	
}
