package com.lxf.note.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonUtil {
    /**
     * 把对象  转换 为 json格式的字符串   写出 给   ajax回调函数
     * @param response
     * @param result
     */
    public static void toJson(HttpServletResponse response,Object result){
        PrintWriter printWriter = null;
        try{
            //设置响应类型
            response.setContentType("application/json;charset=UTF-8");

            //获取printWriter流
             printWriter= response.getWriter();

             //把对象 转换为  json字符串
            String json = JSON.toJSONString(result);

            //写出去
            printWriter.write(json);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            printWriter.close();
        }


    }
}
