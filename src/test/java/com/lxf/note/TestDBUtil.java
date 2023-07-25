package com.lxf.note;

import com.lxf.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class TestDBUtil {
    private Logger logger = LoggerFactory.getLogger(TestDBUtil.class);

    @Test
    public void testConnection(){
       logger.info("获取到数据连接对象：{}",DBUtil.getConnection());
    }

    @Test
    public void testClose(){
        Connection conn = DBUtil.getConnection();
        DBUtil.close(null,null,conn);
    }
}
