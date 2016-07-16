package cn.happy.easybuy.dao;

import java.util.List;
import java.util.Map;

import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

//������
public interface OrderDao {
     
	//���һ������
	public boolean addOrder(Order order) throws Exception;
	//��ȡ������ӵĶ������
	public int getNewOid() throws Exception;
	
	//��ѯ���еĶ�����Ϣ
	public List<Order> getAllOrderInfo() throws Exception;
	
	//��ȡ�������ܼ�¼��
	public int getAllOrderCount() throws Exception;
	
	//���շ�ҳ��ѯһ��������Ϣ
	public Order getPagesOrder(int pageSize,int pageIndex) throws Exception;
	
	//���ݶ����˻�ȡ��ҳ��Ķ�����Ϣ
	public Order getPagesOrderByUid(int pageSize,int pageIndex,String uid) throws Exception;
	
	//���ݶ�����Ÿ��Ķ�����״̬
	public boolean changeStatusByOid(Order order) throws Exception;
	
	//���ݶ�����Ų�ѯ�����������Ϣ
	public Order getInfoByOid(Order order) throws Exception;
	
	//��ȡָ�������˵����ж�����¼����
	public int getCountByUid(String uid) throws Exception;
	
	//���ݶ����˺Ͷ������  ��ϲ�ѯ������Ϣ
	public Order getOrderByOidanduid(String oid,String uid) throws Exception;
	
	
	
}
