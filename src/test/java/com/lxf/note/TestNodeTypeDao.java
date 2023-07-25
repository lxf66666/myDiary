package com.lxf.note;

import com.lxf.note.dao.NoteTypeDao;
import org.junit.Test;

public class TestNodeTypeDao {

    @Test
    public void checkTypeName(){
        NoteTypeDao noteTypeDao = new NoteTypeDao();
        Integer i = noteTypeDao.checkTypeName("测试","4",4);
        System.out.println(i);
    }
}
