package com.lxf.note.web;

import cn.hutool.crypto.digest.DigestUtil;
import com.lxf.note.po.User;
import com.lxf.note.service.UserService;
import com.lxf.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数actionName的值  依次调用不同的方法进行处理
        String actionName = request.getParameter("actionName");
        if("login".equals(actionName)){
            //登录验证
            userLogin(request,response);
        } else if("logout".equals(actionName)){
            //退出登录
            userLogOut(request,response);
        } else if ("userCenter".equals(actionName)) {
            //跳转到个人中心
            userCenter(request,response);
        } else if ("userhead".equals(actionName)) {
            //个人中心  获取头像资源
            userHead(request,response);
        } else if("checkNick".equals(actionName)){
            //验证昵称
            checkNick(request,response);
        } else if("updateUser".equals(actionName)){
            userUpdate(request,response);
        }
    }

    //更新用户信息
    private void userUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo<User> resultInfo = userService.updateUser(request,response);

        request.setAttribute("resultInfo",resultInfo);

        request.getRequestDispatcher("user?actionName=userCenter").forward(request,response);
    }

    /**
     * 验证昵称是否存在  给以响应   除自身以外  如果存在  返回true  否则 false
     * @param request
     * @param response
     */
    private void checkNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取nick参数  昵称
        String nick = request.getParameter("nick");
        //获取 用户 id
        User user = (User)request.getSession().getAttribute("user");

        // System.out.println(nick+"\t"+user.getUserId());

        //提交给service层进行处理.
        Integer code = userService.checkName(user.getUserId(),nick);

        //System.out.println(code);
        //响应
        response.getWriter().write(code+"");
    }

    //个人中心  获取头像资源
    private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取图片的名字
        String headName = request.getParameter("headName");
        //System.out.println(headName);

        //获取项目下存放图片目录的路径
        String download = request.getServletContext().getRealPath("/WEB-INF/download/");

        //拼接成一个完整的路径  file对象
        File file = new File(download,headName);
        //System.out.println(file.toString());

        //通过不同的文件类型  进行相应
        String pic = headName.substring(headName.lastIndexOf(".")+1);

        if("png".equalsIgnoreCase(pic)){
            response.setContentType("img/png");
        } else if ("jpg".equalsIgnoreCase(pic) || "jpeg".equalsIgnoreCase(pic)) {
            response.setContentType("img/jpeg");
        } else if ("gif".equalsIgnoreCase("img/gif")) {
            response.setContentType("img/gif");
        }

        FileUtils.copyFile(file,response.getOutputStream());
    }

    /**
     * 跳转到个人中心
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //改变页面可变区域
        request.setAttribute("changePage","user/info.jsp");
        //导航栏高亮
        request.setAttribute("menu_page","user");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    //用户退出登录
    private void userLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //清除session对象
        request.getSession().invalidate();

        //清除cookie对象
        Cookie cookie = new Cookie("user","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        //重定向到登录页面
        response.sendRedirect("login.jsp");
    }

    //用户登录处理
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取用户名和密码
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");


        //发送给业务逻辑层进行处理
        ResultInfo<User> resultInfo = userService.userLogin(userName,userPwd);



        //对返回的状态码进行判断  0和1
        if(resultInfo.getStatus() == 0){

            User user = new User();
            user.setUname(userName);
            user.setUpwd(userPwd);
            resultInfo.setResult(user);

            //用户登录为失败状态
            request.setAttribute("resultInfo",resultInfo);

            //转发到登录页
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }

        if(resultInfo.getStatus() == 1){
            //用户登录为成功状态

            //把用户信息存到session作用域中
            request.getSession().setAttribute("user",resultInfo.getResult());

            //判断用户是否选择记住密码
            String savePassword = request.getParameter("savePassword");
            if("remember".equals(savePassword)){
                //记住密码
                //把用户和密码设置到cookie中  设置保持时间为3天
                Cookie cookie = new Cookie("user",userName+"-"+userPwd);
                cookie.setMaxAge(60*60*24*3);
                response.addCookie(cookie);
            }else{
                //不记住密码
                //清空cookie对象
                Cookie[] cookies = request.getCookies();
                for(Cookie cookie : cookies){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }


            }

            //重定向到首页  [登录成功跳转]
            response.sendRedirect("index");
            return;
        }
    }
}
