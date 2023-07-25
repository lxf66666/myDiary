package com.lxf.note.filter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ListenerServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //System.out.println("servletContext被创建了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       //销毁session域中的对象
        //System.out.println("servletContext被销毁了");

    }
}
