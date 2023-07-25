<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="col-md-9">



    <div class="data_list">
        <div class="data_list_title"><span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心 </div>
        <div class="container-fluid">
            <div class="row" style="padding-top: 20px;">
                <div class="col-md-8">
                    <form class="form-horizontal" method="post" action="user" enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="hidden" name="actionName" value="updateUser">
                            <label for="nickName" class="col-sm-2 control-label">昵称:</label>
                            <div class="col-sm-3">
                                <input class="form-control" name="nick" id="nickName" placeholder="昵称" value="${user.nick}">
                            </div>
                            <label for="img" class="col-sm-2 control-label">头像:</label>
                            <div class="col-sm-5">
                                <input type="file" id="img" name="img">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mood" class="col-sm-2 control-label">心情:</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" name="mood" id="mood" rows="3">${user.mood}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" id="btn" class="btn btn-success" onclick="return updateUser()">修改</button>&nbsp;&nbsp;
                                <span style="color:red;font-size: 12px;" id="msg"></span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4"><img style="width:260px;height:200px" src="user?actionName=userhead&headName=${user.head}"></div>
            </div>
        </div>
    </div>
    <script>

        //获取nick昵称的文本框控件的失焦  聚焦
        $("#nickName").blur(function(){
            //失去焦点
            //1.判断是否为空
            var nickName = $("#nickName").val();
            if(isEmpty(nickName)){
                //为空  提示用户
                $("#msg").html("昵称不能为空");
                $("#btn").prop("disabled",true);
                return;
            }
            //2.判断是否修改
            //2.1没有修改
            //从session中获取user对象的nick值  判断是否修改
            var nickValue = '${user.nick}';

            if(nickName == nickValue){
                return;
            }

            //2.2有修改
            $.ajax({
                type:"get",
                url:"user",
                data:{
                    actionName:"checkNick",
                    nick:nickName,
                },
                success:function(result){
                    if(result == 1){
                        //可用
                        $("#msg").html("");
                        $("#btn").prop("disabled",false);
                    }else if(result == 0) {
                        //不可用
                        $("#msg").html("昵称被占用");
                        $("#btn").prop("disabled",true);
                    }
                }
            })
        }).focus(function(){
            //聚焦
            $("#msg").html("");
            $("#btn").prop("disabled",false);
        });

        function updateUser(){
            //判断昵称不能为空
            var nickName = $("#nickName").val();
            if(isEmpty(nickName)){
                //为空  提示用户
                $("#msg").html("昵称不能为空");
                $("#btn").prop("disabled",true);
                return false;
            }

            return true;
        }

    </script>
</div>