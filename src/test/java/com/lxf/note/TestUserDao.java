package com.lxf.note;

import com.lxf.note.dao.UserDao;
import com.lxf.note.po.User;
import org.junit.Test;

public class TestUserDao {
    @Test
    public void queryUserByName(){
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin123");
        System.out.println(user.getNick());
    }
}
