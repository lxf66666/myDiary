package com.lxf.note.web;

import com.lxf.note.po.Note;
import com.lxf.note.po.User;
import com.lxf.note.service.NoteService;
import com.lxf.note.util.JsonUtil;
import com.lxf.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置高亮
        request.setAttribute("menu_page","report");

        //获取行为
        String actionName = request.getParameter("actionName");

        if("info".equals(actionName)){
            reportInfo(request,response);
        }else if("month".equals(actionName)){
            queryNoteCountByMonth(request,response);
        }else if("location".equals(actionName)){
            queryNoteLonAndLat(request,response);
        }
    }

    //查询日记的经纬度
    private void queryNoteLonAndLat(HttpServletRequest request, HttpServletResponse response) {
        // 从Session作用域中获取用户对象
        User user = (User) request.getSession().getAttribute("user");
        // 调用Service层的查询方法，返回ResultInfo对象
        ResultInfo<List<Note>> resultInfo = new NoteService().queryNoteLonAndLat(user.getUserId());
        // 将ResultInfo对象转换成JSON格式的字符串，响应给AJAX的回调函数
        JsonUtil.toJson(response, resultInfo);
    }

    //查询月份的数据  和  对应月份 发布日记的数量
    private void queryNoteCountByMonth(HttpServletRequest request, HttpServletResponse response) {
        //获取是哪个用户  才能去查询对应  月份 和对应月份发布的日记数量
        User user = (User)request.getSession().getAttribute("user");

        //提交给service层处理 返回resultInfo
        ResultInfo<Map<String,Object>> resultInfo =  new NoteService().queryNoteCountByMonth(user.getUserId());

        //把对象转换为json字符串
        //把结果返回给ajax回调函数
        JsonUtil.toJson(response,resultInfo);


    }

    //首页的动态包含的页面
    private void reportInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("changePage","report/info.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
