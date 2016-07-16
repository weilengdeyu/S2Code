--新闻表
create table newsdetail
(
   nid int primary key identity(1,1) not null, --编号
   ntitle  nvarchar(32),   --标题
   nauthor nvarchar(32),   --作者
   ncontent nvarchar(max),   --内容
   ndate date  --发布时间
)
insert into  newsdetail
values('端午节龙舟那些事儿','微冷的鳄鱼','龙舟还是广州好','2016-6-13 16:24:00')

insert into  newsdetail
values('关于2017年教育改革事项','微冷的鱼','教育部发布了教育声明，穷人的孩子不用上学，直接获取大学毕业证','2016-6-13 16:24:00')

insert into  newsdetail
values('关于S2结业考试','微冷的鱼','确定100%一次通过','2016-6-13 16:24:00')






