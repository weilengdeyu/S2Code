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
		岁月静好
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		岁月静好
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            String code=request.getParameter("code");
            String msg=null;
            UserDaoImpl userDaoImpl=new UserDaoImpl();
            //验证输入的验证码是否正确  如果正确再验证用户信息是否正确
            String rand=(String)request.getSession().getAttribute("rand");
            if(code.equalsIgnoreCase(rand)){//验证码输入正确
            	//对比用户信息
            	User user=new User();
            	user.setId(request.getParameter("userId"));
            	user.setPassword(request.getParameter("password"));
            	try {
            		//先验证用户名是否存在
            		boolean flag1=userDaoImpl.useridOrNot(user);
            		if(flag1){//如果用户名存在 再验证密码是否正确
            			boolean flag=userDaoImpl.isLoginOrNot(user);
    					if(flag){//登录成功  直接进入首页
    						msg=user.getId();
    						response.setCharacterEncoding("utf-8");
    						//将登录信息保存到session中
    						request.getSession().setAttribute("loginname",msg);
    						User users=userDaoImpl.getUserInfoByUid(msg);
    						//放入session中
    						request.getSession().setAttribute("userInfo", users);
    						response.getWriter().print(msg);
    					}else{//登录失败
    						msg="密码输入错误！";
    						response.setCharacterEncoding("utf-8");
    						response.getWriter().print(msg);
    					}
            		}else{//用户名不存在，则提示用户名不存在
            			msg="该用户名不存在！";
						response.setCharacterEncoding("utf-8");
						response.getWriter().print(msg);
            		}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            }else{//验证码输入错误
            	msg="验证码错误！请重新输入！";
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(msg);
            }
	}

}
