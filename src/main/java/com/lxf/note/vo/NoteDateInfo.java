package com.lxf.note.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class NoteDateInfo implements Serializable {
    public long count;//月份统计数量
    public String groupName;//月份分组的名字

    public long typeId;
}
