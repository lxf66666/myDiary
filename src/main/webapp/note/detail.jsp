<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-md-9">



    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-eye-open"></span>&nbsp;查看云记
        </div>
        <div>


            <div class="note_title"><h2>${note.title}</h2></div>
            <div class="note_info">
                发布时间：『 <fmt:formatDate value="${note.pubTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> 』&nbsp;&nbsp;云记类别：${note.typeName}
            </div>
            <div class="note_content">
                <p>${note.content}</p>
            </div>
            <div class="note_btn">
                <button class="btn btn-primary" type="button" onclick="updateNote(${note.noteId})">修改</button>
                <button class="btn btn-danger" type="button" onclick="deleteNote(${note.noteId})">删除</button>
            </div>



        </div>


    </div>

    <script>


        function deleteNote(data){
            //使用sweet-alert
            swal({title: "删除提示",   //弹出框的title
                text: "确定删除吗？",  //弹出框里面的提示文本
                type: "warning",    //弹出框类型
                showCancelButton: true, //是否显示取消按钮
                confirmButtonColor: "#DD6B55",//确定按钮颜色
                cancelButtonText: "取消",//取消按钮文本
                confirmButtonText: "是的，确定删除！"//确定按钮上面的文档
            }).then(function(isConfirm) {
               $.ajax({
                   type:"post",
                   url:"note",
                   data:{
                       actionName:"deleteNote",
                       noteId:data
                   },
                   success:function(result){
                       if(result == 1){
                           //swal("","删除成功","success");
                           //删除成功
                           window.location.href="index";
                       }else{
                           //删除失败
                           swal("","<h2>删除失败</h2>","error");
                       }
                   }
               });
            });
        }

        function updateNote(noteId){
            window.location.href="note?actionName=view&noteId="+noteId;
        }
    </script>

</div>

