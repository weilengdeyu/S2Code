--���ű�
create table newsdetail
(
   nid int primary key identity(1,1) not null, --���
   ntitle  nvarchar(32),   --����
   nauthor nvarchar(32),   --����
   ncontent nvarchar(max),   --����
   ndate date  --����ʱ��
)
insert into  newsdetail
values('�����������Щ�¶�','΢�������','���ۻ��ǹ��ݺ�','2016-6-13 16:24:00')

insert into  newsdetail
values('����2017������ĸ�����','΢�����','�����������˽������������˵ĺ��Ӳ�����ѧ��ֱ�ӻ�ȡ��ѧ��ҵ֤','2016-6-13 16:24:00')

insert into  newsdetail
values('����S2��ҵ����','΢�����','ȷ��100%һ��ͨ��','2016-6-13 16:24:00')






