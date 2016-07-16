package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.User;

public interface UserDao {
	//查询所有的用户信息
	public List<User> getAllUserInfo() throws Exception;
	//添加用户信息
	public boolean add(User user) throws Exception;
	
	//验证该用户名是否存在
	public boolean useridOrNot(User user) throws Exception;
	//验证是否登录成功
	public boolean isLoginOrNot(User user) throws Exception;
	
	//根据用户名查找对应的用户的所有信息
	public User getUserInfoByUid(String uid) throws Exception;

	
	/**
	 * 根据用户名查找用户方法
	 * @param userID
	 * @return
	 * @throws Exception 
	 */
	public User selectByName(String userID) throws Exception;
	/**
	 * 修改用户方法
	 * @return
	 * @throws Exception 
	 */
	public int modifyUser(User user) throws Exception;
	
	/**
	 * 删除用户方法
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public int removeUser(String userId) throws Exception;
	/**
	 * 查询新闻总记录数
	 */
	public int getUserCount() throws Exception;
	/**
	 * 查询分页内容相关的用户内容
	 * 
	 */
	public List<User> getUserInfo(int pageIndex,int pageSize)throws Exception;
	
	
	
}
