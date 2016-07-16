package cn.happy.easybuy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.junit.Test;



/**
 * ���ݷ��ʹ�����
 * @version 1.1
 * @author happy
 *
 */
public class BaseDao {
	// 01. �������ݵ�׼��
   private static final String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
   private static final String url="jdbc:sqlserver://localhost:1433; DatabaseName=easybuy_hmy";
   private static final String username="sa";
   private static final String pwd="6375196";
   
   //02, �ӿڶ����׼��
   Connection con=null;
   PreparedStatement ps=null;
   public ResultSet rs=null;

   /**
    * 01.дһ���û���ȡ��һ�����Ӷ���ķ����������ķ���ֵ��Connection����
    * @return   ���Ӷ���
    * @throws Exception
    */
   
   @Test
   public void test() throws Exception{
	   Connection conn=getConnection();
	   System.out.println(conn);
   }
   public Connection getConnectionDS() throws Exception{
	   Class.forName(driver);
	   //ʲô�����£�����connection����
	   if (con==null||con.isClosed()) {
		   con=DriverManager.getConnection(url, username, pwd);
    	}
	   //ͬ־������һ��
	   return con;
   }
   
   public Connection getConnection() throws Exception{
	    Context ctx=new InitialContext();
	    DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/news");
	    con=ds.getConnection();
	    return con;	    
   }
   //��Ԫ����
   //java junit
  
   //�������
 
   
   /**
    * ִ�в�ѯ����  Ŀ�ģ�����һ����ȡ��
    * @param sql  sql���
    * @param objs  �����б�
    * @return     ��ȡ������
    * @throws Exception
    */
   public ResultSet  executeQuery(String sql,Object... objs) throws Exception{
	   con=getConnectionDS();
	   ps = con.prepareStatement(sql);
	   for (int i = 0; i < objs.length; i++) {
		   ps.setObject(i+1, objs[i]);
	   }
	   rs= ps.executeQuery();
	   return rs;
   }

   /**
    * ִ����ɾ�ò���
    * @param sql  sql���
    * @param objs  �����б�
    * @return     ��Ӱ������
    * @throws Exception
    */
   public int executeUpdate(String sql,Object... objs) throws Exception{
	    con=getConnectionDS();
	    ps = con.prepareStatement(sql);
	    for (int i = 0; i < objs.length; i++) {
			   ps.setObject(i+1, objs[i]);
		}
	    int count = ps.executeUpdate(); 
	    return count;
   }
   
   
   
   
  /**
   * ����������Դ
   * @throws Exception
   */
   public void closeAll() throws Exception{
	   //���Ż���
	   if(rs!=null){
		   rs.close();
	   }
	   if (ps!=null) {
		ps.close();
	   }
	   if(con!=null){
		   con.close();
	   }
   }
   
}
