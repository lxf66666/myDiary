package com.lxf.note.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteType {
    private Integer typeId;  //类型ID
    private String typeName; //类型的名字
    private Integer userId;  //用户的ID
}
