package com.lxf.note.util;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static Properties properties = null;

    //加载数据库配置文件     加载driver数据库驱动
    static{
        properties = new Properties();
        try {
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));

            Class.forName(properties.getProperty("driver"));

        } catch (IOException e) {
            System.out.println("加载dbproperties配置文件异常");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("driver驱动类不存在");
            throw new RuntimeException(e);
        }
    }
    private DBUtil(){}

   //获取mysql数据库连接对象
    public static Connection getConnection()  {
        try {
            return DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("username"),properties.getProperty("password"));
        } catch (SQLException e) {
            System.out.println("获取conntion连接异常");
            throw new RuntimeException(e);
        }
    }

    //关闭连接数据库相关的资源
    public static void close(ResultSet rs, PreparedStatement pst, Connection con)  {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("rs关闭资源异常");
                throw new RuntimeException(e);
            }
        }

        if(pst != null){
            try {
                pst.close();
            } catch (SQLException e) {
                System.out.println("pst关闭资源异常");
                throw new RuntimeException(e);
            }
        }

        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("con关闭资源异常");
                throw new RuntimeException(e);
            }
        }

    }
}
