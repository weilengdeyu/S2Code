package cn.happy.easybuy.dao;

import java.util.List;

import cn.happy.easybuy.entity.Order;
import cn.happy.easybuy.entity.OrderDetail;

public interface OrderDetitalDao {
   //���һ����������
	public boolean addOrderDetital(OrderDetail orderDetail) throws Exception;
	//��ѯ���еĶ���������Ϣ
	public List<OrderDetail> getAllDetital() throws Exception;
	
	//���ݷ�ҳ��ѯ����������Ϣ
	public List<OrderDetail> getPagesDetital(int pageSize,int pageIndex) throws Exception;
	
	//���ݶ���������ܼ�¼��
	public int getAllCount() throws Exception;
	
	//���ݶ�����Ϣ���Ҷ�Ӧ�Ķ�����Ϣ����
	public List<OrderDetail> getDetailByOid(Order order) throws Exception;
}
