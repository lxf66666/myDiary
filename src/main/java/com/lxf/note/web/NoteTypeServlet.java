package com.lxf.note.web;

import com.alibaba.fastjson.JSON;
import com.lxf.note.po.NoteType;
import com.lxf.note.po.User;
import com.lxf.note.service.NoteTypeService;
import com.lxf.note.util.JsonUtil;
import com.lxf.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {
    private NoteTypeService noteTypeService = new NoteTypeService();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");

        //判断操作行为
        if("list".equals(actionName)){
            typeList(request,response);
        }else if("delete".equals(actionName)){
            deleteType(request,response);
        }else if("addOrUpdate".equals(actionName)){
            addOrUpdate(request,response);
        }

    }

    //类别管理页面  对类别  分别 进行  添加和修改
    private void addOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        //获取参数  类别的名字  类别的ID
        String typeName = request.getParameter("typeName");
        String typeId = request.getParameter("typeId");

        User user = (User)request.getSession().getAttribute("user");

        //提交给service层
        ResultInfo<Integer> resultInfo = noteTypeService.addOrUpdate(typeName,typeId,user.getUserId());

        //响应resultInfo给  ajax
        JsonUtil.toJson(response,resultInfo);
    }

    /**
     * 实现删除日记类型列表中的单个数据
     * @param request
     * @param response
     */
    private void deleteType(HttpServletRequest request, HttpServletResponse response) {
        //获取typeid
        String typeId = request.getParameter("typeId");
        //提交给service层处理
        ResultInfo<NoteType> resultInfo = noteTypeService.deletType(typeId);
        //把ResultInfo  写出到回调函数
        //System.out.println(resultInfo.getStatus()+"\t"+resultInfo.getTips()+"\t"+resultInfo.getResult());
        JsonUtil.toJson(response,resultInfo);
    }

    private void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取userId   通过session对象中  获取user对象
        User user = (User)request.getSession().getAttribute("user");

        //通过查询 得到  结果
        List<NoteType> list = noteTypeService.findTypeList(user.getUserId());

        //list.forEach(System.out::println);
        //把结果设置到request中
        request.setAttribute("typeList",list);

        request.setAttribute("changePage","type/list.jsp");
        //导航栏高亮
        request.setAttribute("menu_page","type");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
