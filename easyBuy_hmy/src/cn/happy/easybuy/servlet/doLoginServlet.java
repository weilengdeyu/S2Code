package cn.happy.easybuy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.happy.easybuy.dao.impl.UserDaoImpl;
import cn.happy.easybuy.entity.User;

public class doLoginServlet extends HttpServlet {

	/**
		���¾���
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		���¾���
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            String code=request.getParameter("code");
            String msg=null;
            UserDaoImpl userDaoImpl=new UserDaoImpl();
            //��֤�������֤���Ƿ���ȷ  �����ȷ����֤�û���Ϣ�Ƿ���ȷ
            String rand=(String)request.getSession().getAttribute("rand");
            if(code.equalsIgnoreCase(rand)){//��֤��������ȷ
            	//�Ա��û���Ϣ
            	User user=new User();
            	user.setId(request.getParameter("userId"));
            	user.setPassword(request.getParameter("password"));
            	try {
            		//����֤�û����Ƿ����
            		boolean flag1=userDaoImpl.useridOrNot(user);
            		if(flag1){//����û������� ����֤�����Ƿ���ȷ
            			boolean flag=userDaoImpl.isLoginOrNot(user);
    					if(flag){//��¼�ɹ�  ֱ�ӽ�����ҳ
    						msg=user.getId();
    						response.setCharacterEncoding("utf-8");
    						//����¼��Ϣ���浽session��
    						request.getSession().setAttribute("loginname",msg);
    						User users=userDaoImpl.getUserInfoByUid(msg);
    						//����session��
    						request.getSession().setAttribute("userInfo", users);
    						response.getWriter().print(msg);
    					}else{//��¼ʧ��
    						msg="�����������";
    						response.setCharacterEncoding("utf-8");
    						response.getWriter().print(msg);
    					}
            		}else{//�û��������ڣ�����ʾ�û���������
            			msg="���û��������ڣ�";
						response.setCharacterEncoding("utf-8");
						response.getWriter().print(msg);
            		}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            }else{//��֤���������
            	msg="��֤��������������룡";
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(msg);
            }
	}

}
