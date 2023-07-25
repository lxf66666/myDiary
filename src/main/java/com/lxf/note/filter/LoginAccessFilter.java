package com.lxf.note.filter;

import cn.hutool.core.util.StrUtil;
import com.lxf.note.po.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginAccessFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //非法登录拦截
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //对静态资源放行
        //1.获取项目路径
        String path = request.getRequestURI();

        //2.对static目录进行放行
        if(path.contains("/statics")){
            filterChain.doFilter(request,response);
            return;
        }

        //对指定页面放行  例如：登录页面
        if(path.contains("/login.jsp")){
            filterChain.doFilter(request,response);
            return;
        }

        //对指定行为进行放行  例如：  /user 提交用户名和密码
        if(path.contains("/user")){
            String value = request.getParameter("actionName");
            if("login".equals(value)){
                filterChain.doFilter(request,response);
                return;
            }
        }

        //对session域中读取 user对象  如果不为空  放行
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            filterChain.doFilter(request,response);
            return;
        }

        //对注册页面放行
        if(path.contains("/Register.jsp")){
            filterChain.doFilter(request,response);
            return;
        }

        //自动登录   如果本地浏览器有cookie  并且 k值为时 user  自动登录

            Cookie[] cookies = request.getCookies();

            if(cookies != null && cookies.length>0){
                for(Cookie cookie : cookies){
                    if("user".equals(cookie.getName())){
                        String[] value = cookie.getValue().split("-");
                        String name = value[0];
                        String pass = value[1];

                        String res = "user?userName="+name+"&userPwd="+pass+"&actionName=login&savePassword=remember";

                        request.getRequestDispatcher(res).forward(request,response);
                        return;
                    }
                }
            }


        //其他的全部拦截  重定向到  login.jsp
        response.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {

    }
}
