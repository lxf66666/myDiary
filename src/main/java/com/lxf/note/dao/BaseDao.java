package com.lxf.note.dao;

import com.lxf.note.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    /**
     * 更新操作  对数据库  进行  添加  修改  删除
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, List<Object> params){
        int row = 0;

        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            if(params != null && params.size()>0){
                for(int i=0;i<params.size();i++){
                    pst.setObject(i+1,params.get(i));
                }
            }

            row = pst.executeUpdate();
        }catch (Exception e){
            System.out.println("executeUpdate  对数据库进行  添加  修改  删除异常");
            e.printStackTrace();
        }finally {
            DBUtil.close(null,pst,conn);
        }
        return row;
    }

    /**
     * 查询一个字段值
     * @param sql
     * @param params
     * @return
     */
    public static Object findSingleValue(String sql,List<Object> params){
        Object object = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            if(params != null && params.size()>0){
                for(int i=0;i<params.size();i++){
                    pst.setObject(i+1,params.get(i));
                }
            }
            rs = pst.executeQuery();
            if(rs.next()){
                object = rs.getObject(1);
            }
        }catch (Exception e){
            System.out.println("findSingleValue  查询一个数据发生异常");
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,pst,conn);
        }

        return object;
    }

    /**
     *查询集合
     * @param sql
     * @param params
     * @return
     */
    public static List queryRows(String sql,List<Object> params,Class clazz){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            if(params != null && params.size()>0){
                for(int i=0;i<params.size();i++){
                    pst.setObject(i+1,params.get(i));
                }
            }
            rs = pst.executeQuery();

            //得到结果集的元数据对象  [查询到的  字段数量  和  哪些字段]
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            //得到查询字段的数量
            int columnNum =  resultSetMetaData.getColumnCount();

            while(rs.next()){
                //获取实例化对象
                Object object = clazz.getDeclaredConstructor().newInstance();

                //遍历字段数量  给对象的每个字段赋值
                for(int i=1;i<=columnNum;i++){
                    //得到每一个列名
                    String columnName = resultSetMetaData.getColumnLabel(i);
                    //通过反射获取列名 filed对象
                    Field fileld = clazz.getDeclaredField(columnName);

                    //拼接set方法
                    String setMethod = "set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1);

                    //通过反射，将set方法的字符串反射成指定的set方法
                    Method method = clazz.getDeclaredMethod(setMethod,fileld.getType());

                    //获取值
                    Object value = rs.getObject(columnName);

                    method.invoke(object,value);
                }

                list.add(object);
            }

        }catch (Exception e){
            System.out.println("queryRows  查询多个数据异常");
            e.printStackTrace();
        }finally {
            DBUtil.close(rs,pst,conn);
        }

        return list;
    }

    //查询一个对象
    public static Object queryRow(String sql,List<Object> params,Class clazz){
        Object object = null;
        List list = queryRows(sql, params, clazz);
        if(list != null && list.size() > 0 ){
            object = list.get(0);
        }

        return  object;
    }


}
