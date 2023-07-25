package com.lxf.note.dao;

import com.lxf.note.po.NoteType;
import com.lxf.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//操作日记类型  相应的数据库处理的类
public class NoteTypeDao {
    //查询 当前用户 的  所有类型的日记  信息
    public List<NoteType> findTypeListByUserId(Integer userId){
        String sql = "select typeId,typeName,userId from tb_note_type where userId=?";
        List<Object> list = new ArrayList<>();
        list.add(userId);

        //通过用户Id查询用户分别的所有类型的数据
        List<NoteType> res = BaseDao.queryRows(sql,list, NoteType.class);
        //res.forEach(System.out::println);
        return res;

    }

    //删除一条云日记中  日记类型  记录 tb_note_type
    public int deleteTypeById(String typeId) {
        String sql = "delete from tb_note_type  where typeId=?";
        List<Object> list = new ArrayList<>();
        list.add(typeId);

        int row = BaseDao.executeUpdate(sql,list);

        return row;
    }

    //查询日记 tb_note 是否存在记录
    public long findNoteCountByTypeId(String typeId) {
        String sql = "select count(1) from tb_note where typeId = ?";
        List<Object> list = new ArrayList<>();
        list.add(typeId);

        long count = (long)BaseDao.findSingleValue(sql,list);
        return count;
    }

    //通过类型名字  和 类型的id  和 用户ID  查询
    //如果  用户id中 是否已经有 类型名字的字段
    //如果一样返回0     没有返回1
    public Integer checkTypeName(String typeName, String typeId, Integer userId) {
        String sql = "select * from tb_note_type where userId=? and typeName =?";
        List<Object> list = new ArrayList<>();
        list.add(userId);
        list.add(typeName);

        NoteType noteType = (NoteType) BaseDao.queryRow(sql,list,NoteType.class);

        if(noteType == null){
            //则数据库中该用户没有这个类型模块  返回1
            return 1;
        }

        //则数据库中有该用户的类型模块  返回0
        //不为空  要判断当前的typeName  和 userId  中的typeName  是否是同一条数据
        //是同一条数据 返回1
        if(typeId.equals(noteType.getUserId().toString())){
            return 1;
        }

        return 0;
    }

    //向数据库中添加 类型模块的  返回主键
    public Integer addType(String typeName, Integer userId) {
        String sql = "insert into tb_note_type(typeName,userId) VALUES(?,?)";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int key = 0;

        try{
           conn =  DBUtil.getConnection();
           pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           pst.setString(1,typeName);
           pst.setInt(2,userId);

           int row = pst.executeUpdate();

           if(row > 0){
               ResultSet resultSet = pst.getGeneratedKeys();

               if(resultSet.next()){
                   key = resultSet.getInt(1);
               }
           }
        }catch (Exception e){

        }finally {
            DBUtil.close(rs,pst,conn);
        }

        return key;
    }

    //更新数据  类型名称  通过找到typeId  然后进行数据覆盖
    public Integer updateType(String typeName, String typeId) {
        String sql = "update tb_note_type set typeName=? where typeId = ?";

        List<Object> list = new ArrayList<>();
        list.add(typeName);
        list.add(typeId);

        int row = BaseDao.executeUpdate(sql,list);


        return row;
    }
}
