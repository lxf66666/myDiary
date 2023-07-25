package com.lxf.note.web;

import com.lxf.note.po.Note;
import com.lxf.note.po.User;
import com.lxf.note.service.NoteService;
import com.lxf.note.util.Page;
import com.lxf.note.vo.NoteDateInfo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private NoteService noteService = new NoteService();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String act = request.getParameter("act");

        String title = request.getParameter("title");
        String dateName = request.getParameter("dateName");
        String typeId = request.getParameter("typeId");


        request.setAttribute("act",act);
        if("searchTitle".equals(act)){
            request.setAttribute("title",title);
            noteList(request,response,title,null,null);
        }else if("seachDate".equals(act)){
            request.setAttribute("dateName",dateName);
            noteList(request,response,null,dateName,null);
        }else if("seachType".equals(act)){
            request.setAttribute("typeId",typeId);
            noteList(request,response,null,null,typeId);
        }

        else{
            noteList(request,response,null,null,null);
        }


       //日记列表的显示
       //

        //云记日期列表的显示
        noteDateList(request,response);
        //控制主页面  可变区域的显示页面的变化
        request.setAttribute("changePage","note/list.jsp");
        //导航栏首页高亮
        request.setAttribute("menu_page","index");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    //云记日期列表的显示
    private void noteDateList(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");
        List<NoteDateInfo> dateList = noteService.findNoteListByDate(user.getUserId());

        List<NoteDateInfo> typeList = noteService.findNoteListByType(user.getUserId());

        request.getSession().setAttribute("dateList",dateList);

        request.getSession().setAttribute("navTypeList",typeList);
    }

    public void noteList(HttpServletRequest request, HttpServletResponse response,String title,String dateName,String typeId) throws ServletException, IOException {
        //获取参数  当前页面  页面显示的数量
        String currentPage = request.getParameter("currentPage");
        String viewPageCount = request.getParameter("viewPageCount");

        User user = (User) request.getSession().getAttribute("user");
        //System.out.println(user.getUserId());


        Page<Note> page = noteService.findNoteListByPage(currentPage,viewPageCount,user.getUserId(),title,dateName,typeId);


        request.setAttribute("page",page);

    }


}
