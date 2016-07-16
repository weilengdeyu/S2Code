package cn.happy.easybuy.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.happy.easybuy.dao.BaseDao;
import cn.happy.easybuy.dao.UserDao;
import cn.happy.easybuy.entity.User;

public class UserDaoImpl extends BaseDao implements UserDao {
	//查询所有的用户信息
	@Override
	public List<User> getAllUserInfo() throws Exception {
		String sql="select * from EASYBUY_USER";
		ResultSet rs=executeQuery(sql);
		List<User> list=new ArrayList<User>();
		if(rs!=null){
			while(rs.next()){
				User user=new User(); 
				user.setId(rs.getString("EU_USER_ID"));
				user.setAddress(rs.getString("EU_ADDRESS"));
				user.setBirthday(rs.getString("EU_BIRTHDAY"));
				user.setEmail(rs.getString("EU_EMAIL"));
				user.setIdentityCode(rs.getString("EU_IDENTITY_CODE"));
				user.setLogin(rs.getString("EU_LOGIN"));
				user.setMobile(rs.getString("EU_MOBILE"));
				user.setName(rs.getString("EU_USER_NAME"));
				user.setPassword(rs.getString("EU_PASSWORD"));
				user.setSex(rs.getString("EU_SEX"));
				user.setStatus(rs.getInt("EU_STATUS"));
				list.add(user);
			}
		}
		return list;
	}
	//添加用户信息
	@Override
	public boolean add(User user) throws Exception {
		//EU_USER_ID, EU_USER_NAME, EU_PASSWORD, EU_SEX, EU_BIRTHDAY, EU_IDENTITY_CODE,
		 //EU_EMAIL, EU_MOBILE, EU_ADDRESS, EU_STATUS, EU_LOGIN
		String sql="insert into EASYBUY_USER values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] objs={
				user.getId(),
				user.getName(),
				user.getPassword(),
				user.getSex(),
				user.getBirthday(),
				user.getIdentityCode(),
				user.getEmail(),
				user.getMobile(),
				user.getAddress(),
				user.getStatus(),
				user.getLogin()
		};
		int count=executeUpdate(sql, objs);
		if(count>0){
			return true;
		}
		return false;
	}
	//验证是否登录成功
	@Override
	public boolean isLoginOrNot(User user) throws Exception {
		String sql="select count(1) as mycount from EASYBUY_USER where EU_USER_ID=? and EU_PASSWORD=?";
		Object[] objs={
				user.getId(),
				user.getPassword()
		};
		ResultSet rs=executeQuery(sql, objs);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		if(count>0){
			return true;
		}
		return false;
	}
	//验证是否存在该用户名
	@Override
	public boolean useridOrNot(User user) throws Exception {
		String sql="select count(1) as mycount from EASYBUY_USER where EU_USER_ID=? ";
		Object[] objs={user.getId()};
		ResultSet rs=executeQuery(sql, objs);
		int count=0;
		if(rs!=null){
			while(rs.next()){
				count=rs.getInt("mycount");
			}
		}
		if(count>0){
			return true;
		}
		return false;
	}
	
	//根据用户名查找对应的用户的所有信息
	@Override
	public User getUserInfoByUid(String uid) throws Exception {
		String sql="select * from dbo.EASYBUY_USER  where EU_USER_ID=?";
		Object[] objs={uid};
		ResultSet rs=executeQuery(sql, objs);
		User user=new User();
		if(rs!=null){
			while(rs.next()){
				user.setId(rs.getString("EU_USER_ID"));
				user.setAddress(rs.getString("EU_ADDRESS"));
				user.setBirthday(rs.getString("EU_BIRTHDAY"));
				user.setEmail(rs.getString("EU_EMAIL"));
				user.setIdentityCode(rs.getString("EU_IDENTITY_CODE"));
				user.setLogin(rs.getString("EU_LOGIN"));
				user.setMobile(rs.getString("EU_MOBILE"));
				user.setName(rs.getString("EU_USER_NAME"));
				user.setPassword(rs.getString("EU_PASSWORD"));
				user.setSex(rs.getString("EU_SEX"));
				user.setStatus(rs.getInt("EU_STATUS"));
			}
		}
		return user;
	}
	
	/**
	 * 根据用户查询所有用户信息
	 * @throws Exception 
	 */
	@Override
	public User selectByName(String userID) throws Exception {
		// TODO Auto-generated method stub
				String sql ="select * from easybuy_user where eu_user_id=?";
				Object[] objs ={userID};
				ResultSet rs = executeQuery(sql, objs);
				User user = new User();
			
				try {
					if (rs!=null) {
						while (rs.next()) {
							user.setId(rs.getString("EU_USER_ID"));
							user.setName(rs.getString("EU_USER_NAME"));
							user.setSex(rs.getString("EU_SEX"));
							user.setEmail(rs.getString("EU_EMAIL"));
							user.setBirthday(rs.getString("EU_BIRTHDAY"));
							user.setAddress(rs.getString("EU_ADDRESS"));
							user.setIdentityCode(rs.getString("EU_IDENTITY_CODE"));
							user.setMobile(rs.getString("EU_MOBILE"));
							user.setPassword(rs.getString("EU_PASSWORD"));
							user.setLogin(rs.getString("EU_LOGIN"));
							user.setStatus(rs.getInt("EU_STATUS"));
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return user;
	}
	/**
	 * 修改用户信息
	 * @throws Exception 
	 */
	@Override
	public int modifyUser(User user) throws Exception {
		// TODO Auto-generated method stub
				String sql ="update  EASYBUY_USER set eu_user_name = ? ,eu_password = ?, eu_sex = ? , eu_birthday = ? ," +
						" eu_mobile = ?,eu_address = ? where eu_user_id= ?";
				Object[] objs ={
						user.getName(),
						user.getPassword(),
						user.getSex(),
						user.getBirthday(),
						user.getMobile(),
						user.getAddress(),
						user.getId()
				};
				int count =executeUpdate(sql,objs);
				return count;
	}
	/**
	 * 删除用户
	 * @throws Exception 
	 */
	@Override
	public int removeUser(String userId) throws Exception {
		// TODO Auto-generated method stub
				String sql ="delete from EASYBUY_USER where eu_user_id = ?";
				Object[]objs={userId};
				int count =executeUpdate(sql, objs);
				return count;
	}
	/**
	 * 获取用户信息总记录数
	 */
	@Override
	public int getUserCount() throws Exception {
		String sql = "select count(1) as count from dbo.EASYBUY_USER";
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
	 * 获取用户分页所展示的信息
	 */
	@Override
	public List<User> getUserInfo(int pageIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		String sql="select top "+pageSize+" * from dbo.EASYBUY_USER where EU_USER_ID not in(select top "+(pageIndex-1)*pageSize+" EU_USER_ID from dbo.EASYBUY_USER order by EU_USER_ID desc) order by EU_USER_ID desc";
		ResultSet rs = executeQuery(sql);
		List<User> list=new ArrayList <User>();
		if (rs!=null) {
			try {
				while (rs.next()) {
					 User  n = new User();	
	// EU_USER_ID, EU_USER_NAME, EU_PASSWORD, EU_SEX, EU_BIRTHDAY, EU_IDENTITY_CODE, EU_EMAIL, EU_MOBILE, EU_ADDRESS, EU_STATUS, EU_LOGIN
					n.setId(rs.getString("EU_USER_ID"));
					n.setName(rs.getString("EU_USER_NAME"));
		            n.setPassword(rs.getString("EU_PASSWORD"));
		            n.setSex(rs.getString("EU_SEX"));
		            n.setBirthday(rs.getString("EU_BIRTHDAY"));
		            n.setIdentityCode(rs.getString("EU_IDENTITY_CODE"));
		            n.setEmail(rs.getString("EU_EMAIL"));
		            n.setMobile(rs.getString("EU_MOBILE"));
		            n.setAddress(rs.getString("EU_ADDRESS"));
		            n.setStatus(rs.getInt("EU_STATUS"));
		            n.setLogin(rs.getString("EU_LOGIN"));
		            
					list.add(n);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
