package com.lxf.note.service;

import cn.hutool.core.util.StrUtil;
import com.lxf.note.dao.NoteTypeDao;
import com.lxf.note.po.NoteType;
import com.lxf.note.vo.ResultInfo;

import java.util.List;

//日记类型的业务逻辑层
public class NoteTypeService {
    private NoteTypeDao noteTypeDao = new NoteTypeDao();

    //通过用户id  查询  用户分别日记  类型  的所有数据
    public List<NoteType> findTypeList(Integer userId){
        List<NoteType> list = noteTypeDao.findTypeListByUserId(userId);
        return list;
    }

    /**
     * 删除日记类型列表
     * @param typeId
     * @return
     */
    public ResultInfo<NoteType> deletType(String typeId) {
        ResultInfo<NoteType> resultInfo = new ResultInfo<>();
        //判断typeId是否为空
        if(StrUtil.isBlank(typeId)){
            resultInfo.setStatus(0);
            resultInfo.setTips("系统异常,请重试！");
            return resultInfo;
        }

        //提交给dao层  通过typeid查询是否有  记录
        //有记录  不能删除 提示用户
        long count = noteTypeDao.findNoteCountByTypeId(typeId);

        if(count > 0 ){
            resultInfo.setStatus(0);
            resultInfo.setTips("当前日记类型存在子记录，不能删除");
            return resultInfo;
        }

        //无记录  调用dao  进行删除
        int row = noteTypeDao.deleteTypeById(typeId);


        if(row > 0){
            resultInfo.setStatus(1);
        }else{
            resultInfo.setStatus(0);
            resultInfo.setTips("删除失败！");
        }


        return resultInfo;
    }


    //类别管理页面  对类别  分别 进行  添加和修改
    public ResultInfo<Integer> addOrUpdate(String typeName, String typeId,Integer userId) {
        ResultInfo<Integer> resultInfo = new ResultInfo<>();

        //判断类型名称是否为空
        if(StrUtil.isBlank(typeName)){
            resultInfo.setStatus(0);
            resultInfo.setTips("*类型名称不能为空");
            return resultInfo;
        }

        //查询类型名称是否  在同一用户下以存在  1=可用  0=不可用
        Integer code = noteTypeDao.checkTypeName(typeName,typeId,userId);
       if(code == 0){
           resultInfo.setStatus(0);
           resultInfo.setTips("*类型名称已存在，请重新输入");
           return resultInfo;
       }

       Integer key=null;
       if(StrUtil.isBlank(typeId)){
           key = noteTypeDao.addType(typeName,userId);
       }else{
           key = noteTypeDao.updateType(typeName,typeId);
       }

       if(key>0){
           resultInfo.setStatus(1);
           resultInfo.setResult(key);
       }else{
           resultInfo.setStatus(0);
           resultInfo.setTips("更新失败");
       }


        return resultInfo;
    }
}
