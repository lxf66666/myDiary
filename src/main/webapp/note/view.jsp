<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-9">

<div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;
            <c:if test="${empty modfiyNote}">
                发布类型
            </c:if>
            <c:if test="${!empty modfiyNote}">
                修改类型
            </c:if>
        </div>
        <div class="container-fluid">
            <div class="container-fluid">
                <div class="row" style="padding-top: 20px;">
                    <div class="col-md-12">
                        <c:if test="${empty typeList}">
                            <h2>暂未查询到云记类型</h2>
                            <h3><a href="type?actionName=list">
                                添加云记

                            </a></h3>
                        </c:if>

                        <c:if test="${!empty typeList}">
                            <form class="form-horizontal" method="post" action="note">
                                <div class="form-group">
                                    <label for="typeId" class="col-sm-1 control-label">类别:</label>
                                    <div class="col-sm-11">
                                        <select id="typeId" class="form-control" name="typeId">

                                            <option value="-1" selected="">请选择云记类别...</option>

                                            <c:forEach var="item" items="${typeList}">
                                                <c:choose>
                                                    <c:when test="${!empty resultInfo}">
                                                        <option <c:if test="${resultInfo.result.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option <c:if test="${modfiyNote.typeId == item.typeId}">selected</c:if> value="${item.typeId}">${item.typeName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <input type="hidden" name="actionName" value="addOrUpdate" />
                                <input type="hidden" name="lon" value="" id="lon" />
                                <input type="hidden" name="lat" value="" id="lat" />

                                <input type="hidden" name="noteId" value="${modfiyNote.noteId}" />
                                <div class="form-group">

                                    <label for="title" class="col-sm-1 control-label">标题:</label>
                                    <div class="col-sm-11">
                                       <c:choose>
                                           <c:when test="${!empty resultInfo}">
                                               <input class="form-control" name="title" id="title" placeholder="云记标题" value="${resultInfo.result.title}">
                                           </c:when>
                                           <c:otherwise>
                                               <input class="form-control" name="title" id="title" placeholder="云记标题" value="${modfiyNote.title}">
                                           </c:otherwise>
                                       </c:choose>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label for="title" class="col-sm-1 control-label">内容:</label>
                                    <div class="col-sm-11">
                                       <c:choose>
                                           <c:when test="${!empty resultInfo}">
                                               <textarea name="content" id="content">${resultInfo.result.content}</textarea>
                                           </c:when>
                                           <c:otherwise>
                                               <textarea name="content" id="content">${modfiyNote.content}</textarea>
                                           </c:otherwise>
                                       </c:choose>

                                    </div>
                                    <div class="col-sm-11 col-sm-offset-1"  style="margin-top:15px;">
                                        <font id="error" color="red">${resultInfo.tips}</font>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-6 col-sm-4">
                                        <input type="submit" class="btn btn-primary" onclick="return saveNote();" value="保存">

                                    </div>
                                </div>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>
</div>

<script type="text/javascript">
    var ue;
    $(function(){
         ue = UE.getEditor("content");

    });

    function saveNote(){
        //获取option被选中的值
        //var optionValue  = $("option:checked").val();
        //alert(optionValue);
        var opt = $("#typeId").val();
        //alert(opt);

        //获取标题内容
        var title = $("#title").val();
        //alert(title);

        //获取富文本的内容
       // alert(content);
        ue = UE.getEditor("content");
        var content  = ue.getContent();
        //alert(content);

        if(opt == -1){
            //说明用户 没有选择  日记类型  提示用户
            $("#error").html("*请选择发布的日记类别！");
            return  false;
        }

        //标题文档框失焦时判断是否为空
        if(isEmpty(title)){
            $("#error").html("*标题不能为空！");
            return  false;
        }

        if(isEmpty(content)){
            $("#error").html("*内容不能为空！");
            return  false;
        }

        return true;
    }

    //当作用域发生改变的时候  清除提示信息
    $("#typeId").change(function (){
        $("#error").html("");
    });

    //当标题文本框失去焦点时  清空提示信息
    $("#title").blur(function(){
        $("#error").html("");
    });

    //富文本聚焦  清空提示信息

    //===================================================



</script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&&type=webgl&ak=Ff5xj7MK7KDgWIvn0IAD57fqYM4oiC7L"></script>
<script type="text/javascript">
    var geolocation = new BMapGL.Geolocation();
    geolocation.getCurrentPosition(function (r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            //alert("你的位置："+r.point.lng+","+r.point.lat);
            $("#lon").val(r.point.lng);
            $("#lat").val(r.point.lat);
        }else{
            console.log("faile:"+this.getStatus());
        }

    });
</script>
