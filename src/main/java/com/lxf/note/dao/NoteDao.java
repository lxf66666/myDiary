package com.lxf.note.dao;

import cn.hutool.core.util.StrUtil;
import com.lxf.note.po.Note;
import com.lxf.note.vo.NoteDateInfo;

import java.util.ArrayList;
import java.util.List;


public class NoteDao {
    //删除一条云日记
    public static int deleteNoteById(String noteId) {
        String sql = "delete from tb_note where noteId =? ";
        List<Object> list = new ArrayList<>();
        list.add(noteId);

        int row = BaseDao.executeUpdate(sql,list);

        return row;
    }

    //发布云日记    添加或者修改操作
    public int addOrUpdate(String typeId, String title, String content,String noteId,String lon,String lat) {
        String sql = null;

        List<Object> list = new ArrayList<>();
        list.add(title);
        list.add(content);
        list.add(typeId);
        list.add(lon);
        list.add(lat);

        if(StrUtil.isBlank(noteId)){
            sql ="insert into tb_note(title,content,typeId,lon,lat,pubTime) values(?,?,?,?,?,now())";
        }else{
            //修改
            sql ="update tb_note set title=?,content=?,typeId=?,pubTime=now() where noteId=?";
            list.add(noteId);
        }



        System.out.println(sql);
        int row = BaseDao.executeUpdate(sql,list);


        return row;
    }

    //
    public long findNoteListCount(Integer userId,String title,String dateName,String typeId) {
        String sql="select count(*) from tb_note_type t1 join tb_note t2 on t1.typeId = t2.typeId and userId = ?";
        List<Object> list = new ArrayList<>();
        list.add(userId);

        if(!StrUtil.isBlank(title)){
            sql += " and  title  like  concat('%',?,'%')";
            list.add(title);
        }

        if(!StrUtil.isBlank(dateName)){
            sql += " and DATE_FORMAT(pubTime,'%Y年%m月') =?";
            list.add(dateName);
        }

        if(!StrUtil.isBlank(typeId)){
            sql += " and t1.typeId = ?";
            list.add(typeId);
        }

        long res = (long)BaseDao.findSingleValue(sql,list);
        return res;
    }

    public List<Note> findNoteListByPage(Integer userId, int index, int viewPageCount,String title,String dateName,String typeId) {
        String sql="select t2.noteId,t2.title,t2.pubTime from tb_note_type t1 join tb_note t2 on t1.typeId = t2.typeId and userId = ? ";
        List<Object> list = new ArrayList<>();
        list.add(userId);

        //拼接title
        if(!StrUtil.isBlank(title)){
            sql += " and  title  like  concat('%',?,'%')";
            list.add(title);
        }

        if(!StrUtil.isBlank(dateName)){
            sql += " and DATE_FORMAT(pubTime,'%Y年%m月') =?";
            list.add(dateName);
        }

        if(!StrUtil.isBlank(typeId)){
            sql += " and t1.typeId = ?";
            list.add(typeId);
        }

        list.add(index);
        //System.out.println(index);
        list.add(viewPageCount);
        //System.out.println(viewPageCount);



        sql += " order by pubTime desc LIMIT ?,?";

        //System.out.println(sql);
        List<Note> res = BaseDao.queryRows(sql,list,Note.class);
        return res;
    }


    //查询指定用户的月份的数据
    public List<NoteDateInfo> findNoteListByDate(Integer userId) {
        String sql = "select count(*) count,DATE_FORMAT(pubTime,'%Y年%m月') groupName from tb_note n  join tb_note_type t on n.typeId = t.typeId where t.userId=? GROUP BY DATE_FORMAT(pubTime,'%Y年%m月') order by groupName desc";
        List<Object> list = new ArrayList<>();
        list.add(userId);

        List<NoteDateInfo> res = BaseDao.queryRows(sql,list, NoteDateInfo.class);

        return res;
    }

    public List<NoteDateInfo> findNoteListByType(Integer userId) {
        String sql ="select count(noteId) count,t.typeName groupName,t.typeId from tb_note_type t  left join tb_note n on t.typeId = n.typeId" +
                " where t.userId=? GROUP BY t.typeId ORDER BY count(noteId) desc";
        List<Object> list = new ArrayList<>();
        list.add(userId);

        List<NoteDateInfo> res = BaseDao.queryRows(sql,list, NoteDateInfo.class);

        return res;
    }

    public Note findNoteById(String noteId) {
        String sql = "select noteId,title,content,n.typeId,pubTime,typeName from tb_note n join tb_note_type t on n.typeId = t.typeId" +
                " where noteId=? ";

        List<Object> list = new ArrayList<>();
        list.add(noteId);

        Note note =(Note)BaseDao.queryRow(sql,list,Note.class);
        return note;
    }

    public List<Note> queryNoteList(Integer userId) {
        // 定义SQL语句
        String sql = "select lon, lat from  tb_note n inner join tb_note_type t on n.typeId = t.typeId where userId = ?";

        // 设置参数
        List<Object> params = new ArrayList<>();
        params.add(userId);

        // 调用BaseDao
        List<Note> list = BaseDao.queryRows(sql, params, Note.class);

        return list;
    }
}
