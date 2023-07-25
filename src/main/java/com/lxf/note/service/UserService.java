package com.lxf.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.lxf.note.dao.BaseDao;
import com.lxf.note.dao.UserDao;
import com.lxf.note.po.User;
import com.lxf.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.beans.DesignMode;
import java.io.IOException;

public class UserService {
    //业务逻辑层
    private  UserDao userDao = new UserDao();

    //验证昵称  是否已存在
    public  Integer checkName(Integer userId, String nick) {
        //判断是否为空
       if(StrUtil.isBlank(nick)){
           return 0;
       }
       User user = userDao.queryUserNameByUserIdAndNick(userId,nick);
        //System.out.println(user.getUname());
       if(user != null){
           return 0;
       }else{
           return 1;
       }

    }

    //用户登录处理
    public ResultInfo<User> userLogin(String uname,String upwd){
        ResultInfo resultInfo = new ResultInfo();
        //判断用户名密码是否为空
        if(StrUtil.isBlank(uname)  || StrUtil.isBlank(upwd)){
            resultInfo.setStatus(0);
            resultInfo.setTips("用户名或密码不能为空");
            return resultInfo;
        }

        //连接数据库
        User user = userDao.queryUserByName(uname);

        //查询为空处理
        if(user==null){
            resultInfo.setStatus(0);
            resultInfo.setTips("用户名不存在");
            return resultInfo;
        }

        //查询不为空   对密码进行md5加密
        upwd = DigestUtil.md5Hex(upwd);

        //判断密码是否一致
        if(!upwd.equals(user.getUpwd())){
            resultInfo.setStatus(0);
            resultInfo.setTips("用户密码错误");
            return resultInfo;
        }

        //成功处理
        resultInfo.setStatus(1);
        resultInfo.setResult(user);
        return resultInfo;
    }

    //更新用户信息
    public ResultInfo<User> updateUser(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        //获取参数
        String nick = request.getParameter("nick");
        String mood = request.getParameter("mood");

        //参数非空验证
        if(StrUtil.isBlank(nick)){
            resultInfo.setStatus(0);
            resultInfo.setTips("昵称不能为空");
            return resultInfo;
        }

        //从session作用域中获取用户对象（获取用户对象中默认的头像）
        User user = (User)request.getSession().getAttribute("user");
        user.setNick(nick);
        user.setMood(mood);

        try{
            //获取part对象
            Part part = request.getPart("img");

            //获取头部信息
            String header = part.getHeader("Content-Disposition");

            String str = header.substring(header.lastIndexOf("=")+2);

            //获取文件名字
            String fileName = str.substring(0,str.length()-1);

            //判断文件名是否为空
            if(!StrUtil.isBlank(fileName)){
                //如果用户上传了文件  更新用户中的头像信息
                user.setHead(fileName);

                //获取文件存放的路径， web-inf/download/
                String filePath = request.getServletContext().getRealPath("/WEB-INF/download/");

//                System.out.println("***************");
//                System.out.println(filePath+fileName);
//                String allPath=filePath+"\\"+fileName;
//                System.out.println(allPath);

                part.write(filePath+"\\"+fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //调用Dao层的更新方法，返回受影响的行数
        int row = userDao.updateUser(user);

        //判断受影响的行数
        if(row>0){
            resultInfo.setStatus(1);
            request.getSession().setAttribute("user",user);
        }else {
            resultInfo.setStatus(0);
            resultInfo.setTips("更新失败");
        }


        return resultInfo;
    }
}
