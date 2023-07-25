package com.lxf.note;

import com.lxf.note.dao.BaseDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestBaseDao {
    @Test
    public void executeUpdate(){
        String sql="insert into tb_user(uname,upwd,nick,head,mood) values(?,?,?,?,?)";
        List<Object> list = new ArrayList();
        list.add("libai");
        list.add("libai");
        list.add("南北诗人");
        list.add("404.jpg");
        list.add("nb");
        BaseDao.executeUpdate(sql,list);
    }
}
