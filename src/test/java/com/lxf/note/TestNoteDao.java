package com.lxf.note;

import com.lxf.note.dao.NoteDao;
import com.lxf.note.po.Note;
import com.lxf.note.vo.NoteDateInfo;
import org.junit.Test;

import java.util.List;

public class TestNoteDao {
//    @Test
//    public void findNoteListCount(){
//        //通过用户id查询日记个数
//        NoteDao noteDao = new NoteDao();
//        long value = noteDao.findNoteListCount(4,"上海");
//        System.out.println(value);
//    }

    @Test
    public void findNoteListByPage(){
        //通过用户id查询日记个数
        NoteDao noteDao = new NoteDao();
        List<Note> list = noteDao.findNoteListByPage(4,0,1,null,null,"2");
        System.out.println(list.size());
    }

//
//    @Test
//    public void findNoteListByDate(){
//        NoteDao noteDao = new NoteDao();
//        List<NoteDateInfo> list = noteDao.findNoteListByType(4);
//        list.forEach(System.out::println);
//    }
}
