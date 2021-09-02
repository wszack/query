package com.web.servlet;

import com.domain.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Auther: zzc
 * @Date: 2021/8/28-08-28-15:28
 * @Description: ${PACKAGE_NAME}
 * @version: 1.0
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取数据
        //2.1获取用户填写的验证码
        String verifycode = request.getParameter("verifycode");
        Map<String, String[]> map = request.getParameterMap();

        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//确保验证码一次性
        if(!checkcode_server.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误！");
            //调整登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        //4.封装对象
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //5.调用service查询
        UserService service=new UserServiceImpl();
        User loginuser=service.login(user);
        //6.判断释放登录成功
        if(loginuser!=null){
            //登录成功
            //将用户存入session
            session.setAttribute("user",loginuser);
            //跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");

        }
        else {
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误！");
            //调整登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
