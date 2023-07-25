package com.lxf.note.web;

import cn.hutool.core.util.StrUtil;
import com.lxf.note.dao.BaseDao;
import com.lxf.note.dao.NoteDao;
import com.lxf.note.po.Note;
import com.lxf.note.po.NoteType;
import com.lxf.note.po.User;
import com.lxf.note.service.NoteService;
import com.lxf.note.service.NoteTypeService;
import com.lxf.note.vo.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/note")
public class NoteServlet extends HttpServlet {
    private NoteService noteService = new NoteService();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("actionName");

        if("view".equals(actionName)){
            noteView(request,response);
        }else if("addOrUpdate".equals(actionName)){
            noteAddOrUpdate(request,response);
        }else if("detail".equals(actionName)){
            noteDetail(request,response);
        }else if("deleteNote".equals(actionName)){
            noteDelete(request,response);
        }
    }

    //删除日记
    private void noteDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String noteId = request.getParameter("noteId");

        int row = noteService.deleteNoteById(noteId);
       response.getWriter().write(row+" ");
       response.getWriter().close();
    }

    //查看云记详情
    private void noteDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String noteId = request.getParameter("noteId");
        //提交给service层 note
        Note note =  noteService.noteDetail(noteId);
        request.setAttribute("note",note);

        request.setAttribute("changePage","note/detail.jsp");

        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    //发布云记  提交表单的处理
    private void noteAddOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取参数
        String typeId = request.getParameter("typeId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        String noteId = request.getParameter("noteId");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");

        ResultInfo<Note> resultInfo = noteService.addOrUpdate(typeId,title,content,noteId,lon,lat);



        //判断 status  如果 为  1 = 发布成功   0= 发布失败
        if(resultInfo.getStatus() == 1){
            //重定向到index.jsp
            response.sendRedirect("index");
        }else{
            String url ="note?actionName=view";
            if(!StrUtil.isBlank(noteId)){
                url += "&noteId"+noteId;
            }
            //发布失败  设置request  作用域  把 resultInfo进行存储
            request.setAttribute("resultInfo",resultInfo);
            //转发到原页面
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

    //用户点击  【发表云日记】  事件处理
    private void noteView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //修改日记  ---开始
        String noteId = request.getParameter("noteId");
        Note note =  new NoteDao().findNoteById(noteId);
        request.setAttribute("modfiyNote",note);

      //修改日记 ---结束

       //取user用户
       User user = (User) request.getSession().getAttribute("user");

       //通过userId获取列表集合
        List<NoteType> list = new NoteTypeService().findTypeList(user.getUserId());

        request.setAttribute("typeList",list);
       //页面跳转  高亮
        request.setAttribute("changePage","note/view.jsp");
        request.setAttribute("menu_page","note");

        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
