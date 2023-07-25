package com.lxf.note.dao;

import com.lxf.note.po.User;
import com.lxf.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    //通过用户名获取 用户对象
    public User queryUserByName(String userName){
        String sql ="SELECT * FROM `tb_user` where uname =?";
        List<Object> list = new ArrayList<>();
        list.add(userName);
        User user = (User)BaseDao.queryRow(sql,list, User.class);
        return user;
    }

    //通过用户名获取 用户对象
    public User queryUserByName2(String userName){
        User user = null;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql ="SELECT * FROM `tb_user` where uname =?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1,userName);
            rs = pst.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUname(rs.getString("uname"));
                user.setUpwd(rs.getString("upwd"));
                user.setNick(rs.getString("nick"));
                user.setHead(rs.getString("head"));
                user.setMood(rs.getString("mood"));
            }

        }catch (Exception e){
            System.out.println("queryUserByName 数据库查询  异常");
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,pst,conn);
        }

        return user;
    }


    public User queryUserNameByUserIdAndNick(Integer userId, String nick) {
        String sql = "SELECT * FROM `tb_user` where nick=? and userid != ?";
        List<Object> list = new ArrayList<>();
        list.add(nick);
        list.add(userId);

        User obj = (User)BaseDao.queryRow(sql,list, User.class);
        return  obj;
    }

    //更新用户信息
    public int updateUser(User user) {
        String sql = "update tb_user set nick=?,head=?,mood=? where userId =?";
        List<Object> list = new ArrayList<>();
        list.add(user.getNick());
        list.add(user.getHead());
        list.add(user.getMood());

        list.add(user.getUserId());

       int row = BaseDao.executeUpdate(sql,list);

       return row;
    }
}
