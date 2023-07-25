<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Bootstrap 实例 - 模态框（Modal）插件</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="statics/js/util.js"></script>
</head>
<body>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    用户注册
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" method="post" action="register" id="myFormToRegister">
                    <div class="form-group">
                        <label for="userName" class="col-sm-3 control-label" >账号:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="userName" placeholder="8-16位/只能是字母数字下划线组成">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPwd" class="col-sm-3 control-label">密码:</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="userPwd" placeholder="8-16位/至少一个大写/小写/数字">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="nick" class="col-sm-3 control-label">昵称:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="nick" placeholder="请输入昵称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">头像:</label>

                        <div class="col-sm-7">
                            <input type="file" id="inputfile">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label"></label>
                        <div class="col-sm-7">
                            <span id="Msg" style="color: red;font-size: 12px;"></span>
                        </div>
                    </div>


                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" value="" onclick="registerUser()">注册</button>

                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>

                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
    $('#myModal').modal('show');

    function registerUser(){
        //清空提示信息
        $("#Msg").html("");

        var userName= $("#userName").val().trim();
        var userPwd = $("#userPwd").val().trim();

        //1.账号密码不能为空
        if(isEmpty(userName)){
            //提示用户不能为空
            $("#Msg").html("*用户名不能为空");
            return;
        }

        //判断是否是用户名长度  8-16位
        if(!(userName.length >=8 && userName.length<=16)){
        	$("#Msg").html("用户名长度为8-16位");
        	return;
        }

        //用户名只能是字母数字 下划线 减号
        var upattern = /^[a-zA-Z0-9_]{8,16}$/;
        //alert(upattern.test(userName));

        if(!upattern.test(userName)){
        	$("#Msg").html("用户名必须是字母数字下划线");
        	return;
        }

        //密码不能为空
        if(isEmpty(userPwd)){
            $("#Msg").html("密码不能为空");
            return;
        }

        //密码长度  8-16位
        if(!(userPwd.length >=8 && userPwd.length<=16)){
        	$("#Msg").html("密码长度为8-16位");
        	return;
        }

        //密码至少一个大写字母  一个小写字母  一个数字  组成 8-16位
        var pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/;
        if(!pwdPattern.test(userPwd)){
        	$("#Msg").html("密码至少一个大写/小写/数字");
        	return;
        }

        $("#myFormToRegister").submit();
    }
</script>

</body>
</html>
