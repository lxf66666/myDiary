package com.lxf.note.service;

import cn.hutool.core.util.StrUtil;
import com.lxf.note.dao.BaseDao;
import com.lxf.note.dao.NoteDao;
import com.lxf.note.po.Note;
import com.lxf.note.util.Page;
import com.lxf.note.vo.NoteDateInfo;
import com.lxf.note.vo.ResultInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteService {
    private NoteDao noteDao = new NoteDao();

    /**
     * 发布云日记    添加或者修改操作
     * @param typeId
     * @param title
     * @param content
     * @return
     */
    public ResultInfo<Note> addOrUpdate(String typeId, String title, String content,String noteId,String lon,String lat) {
        ResultInfo<Note> resultInfo = new ResultInfo<>();
        //判断参数是否为空  为空  设置 提示信息  return
        if(StrUtil.isBlank(typeId)){
            resultInfo.setStatus(0);
            resultInfo.setTips("*请选择发布的云记类别！");
            return resultInfo;
        }

        if(StrUtil.isBlank(title)){
            resultInfo.setStatus(0);
            resultInfo.setTips("*云记标题不能为空！");
            return resultInfo;
        }

        if(StrUtil.isBlank(content)){
            resultInfo.setStatus(0);
            resultInfo.setTips("*云记内容不能为空！");
            return resultInfo;
        }

        if(lon == null || lat == null){
            //设置默认值
            lon = "121.395";
            lat = "31.2536";
        }

        Note note = new Note();
        note.setTypeId(Integer.parseInt(typeId));
        note.setTitle(title);
        note.setContent(content);
        note.setLon(Float.parseFloat(lon));
        note.setLat(Float.parseFloat(lat));

        if(!StrUtil.isBlank(noteId)){
            note.setNoteId(Integer.parseInt(noteId));
        }

        //提交给dao层 返回受影响的行数
        int row = noteDao.addOrUpdate(typeId,title,content,noteId,lon,lat);
        resultInfo.setResult(note);

        //判断是否大于0  设置status为1   否则为0
        if(row > 0){
            //添加云记成功
            resultInfo.setStatus(1);
            return resultInfo;
        }else{
            //添加云记失败
            resultInfo.setStatus(0);
            resultInfo.setTips("*更新失败！");
            resultInfo.setResult(note);
            return resultInfo;
        }

    }

    //分页查询云记列表
    public Page<Note> findNoteListByPage(String currentPageStr, String viewPageCountStr, Integer userId,String title,String dateName,String typeId) {
        int currentPage = 1;
        int viewPageCount = 3;

        //如果当前页面数不为空   进行设置
        if(!StrUtil.isBlank(currentPageStr)){
            currentPage = Integer.parseInt(currentPageStr);
        }

        //如果当前页面显示个数不为空   进行设置
        if(!StrUtil.isBlank(viewPageCountStr)){
            viewPageCount = Integer.parseInt(viewPageCountStr);
        }

        //查询当前用户的日记总数量
        long count = noteDao.findNoteListCount(userId,title,dateName,typeId);
        if(count<1){
            return null;
        }

        //获取数据库查询的开始下标
        int index = (currentPage-1)*viewPageCount;

        List<Note> noteList =  noteDao.findNoteListByPage(userId,index,viewPageCount,title,dateName,typeId);



        Page<Note> page = new Page<>(currentPage,viewPageCount,count);
        page.setDataList(noteList);

         return page;
    }

    public List<NoteDateInfo> findNoteListByDate(Integer userId) {
        return noteDao.findNoteListByDate(userId);
    }

    public List<NoteDateInfo> findNoteListByType(Integer userId) {
        return noteDao.findNoteListByType(userId);
    }

    //查看云记详情
    public Note noteDetail(String noteId) {
        if(StrUtil.isBlank(noteId)){
            return null;
        }

        Note note = noteDao.findNoteById(noteId);
        return note;
    }

    //删除一条日记
    public int deleteNoteById(String noteId) {
        //判断是否为空
        if(StrUtil.isBlank(noteId)){
            return 0;
        }

        int row = NoteDao.deleteNoteById(noteId);
        if(row > 0){
            return 1;
        }else{
            return 0;
        }

    }

    //查询月份的数据  和  对应月份 发布日记的数量
    public ResultInfo<Map<String, Object>> queryNoteCountByMonth(Integer userId) {
        ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();

        //通过日记分组查询云记数量
        List<NoteDateInfo> list =  noteDao.findNoteListByDate(userId);

        if(list.size()>0 && list != null){
            List<Integer> dataArray = new ArrayList<>();
            List<String> monthArray = new ArrayList<>();

            for(NoteDateInfo info : list){
                dataArray.add((int)info.getCount());
                monthArray.add(info.getGroupName());
            }

            Map<String,Object> map = new HashMap();
            map.put("dataArray",dataArray);
            map.put("monthArray",monthArray);

            resultInfo.setStatus(1);
            resultInfo.setResult(map);
        }


        return  resultInfo;
    }

    public ResultInfo<List<Note>> queryNoteLonAndLat(Integer userId) {
        ResultInfo<List<Note>> resultInfo = new ResultInfo<>();

        // 通过用户ID查询云记列表
        List<Note> noteList = noteDao.queryNoteList(userId);

        // 判断是否为空
        if (noteList != null && noteList.size() > 0) {
            resultInfo.setStatus(1);
            resultInfo.setResult(noteList);
        }

        return resultInfo;
    }
}
