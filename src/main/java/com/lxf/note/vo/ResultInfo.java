package com.lxf.note.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultInfo <T>{
    private Integer status;     //状态信息  1=成功    2=失败

    private String tips;        //提示信息

    private T result;           //封装用户信息的对象
}
