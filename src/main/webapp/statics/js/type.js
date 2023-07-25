// $(
//     function(){
//         //显示模态窗口
//         $("#addBtn").click(
//             function(){
//                 $("#myModalLabel").text("新增");
//                 $('#myModal').modal();
//                 $("input[name='typeId']").val('');
//                 $('#typename').val('');
//             }
//         );
//
//         //确保本人的类型唯一
//         var target=$('#typename');
//         //获取焦点可用状态
//         target.focus(
//             function(){
//                 $('#btn_submit').html('保存').removeClass("btn-danger").addClass("btn-info").attr('disabled',false);
//             }
//         );
//         //使用ajax验证
//         target.blur(
//             function(){
//                 var val =target.val(); //获取值
//                 if(val.length==0){
//                     return;
//                 }
//                 $.getJSON("type",{
//                     act:'unique',
//                     typename:val
//                 },function(data){
//                     if(data.resultCode==-1){
//                         $('#btn_submit').html('名称已存在').removeClass("btn-primary").addClass("btn-danger").attr('disabled',true);
//                     }else{
//                         $('#btn_submit').html('保存').removeClass("btn-danger").addClass("btn-info").attr('disabled',false);
//
//                     }
//                 });
//             }
//         );
//
//         //保存数据
//         $('#btn_submit').click(
//             function(){
//                 var idVal =$("input[name='typeId']").val();
//                 $.getJSON("type",{
//                     act:'save',
//                     typename:$('#typename').val(),
//                     typeId:idVal
//                 },function(data){
//                     //追加一行
//                     if(data.resultCode==1){
//                         if(idVal.length==0){ //新增
//                             var noteType =data.result;
//                             var tr="<tr><td>"+noteType.typeId+"</td>";
//                             tr +="<td>"+noteType.typeName+"</td>";
//                             tr +="<td><button class=\"btn btn-primary\" type=\"button\">修改</button>&nbsp;";
//                             tr +="<button class=\"btn btn-danger\" type=\"button\">删除</button></td></tr>";
//                             //追加到当前表格中
//                             //$(“.table”).filter(“.table-hover”).filter(“.table-striped”)
//                             //$("[class='table table-hover table-striped']");
//                             $(".table.table-hover.table-striped").append(tr);
//
//                         }else{ //修改
//                             //遍历表格
//                             var targetTr ;
//                             $("table tr:gt(0)").each(function(i){
//                                 $(this).children("td:eq(0)").each(function(i){
//                                     if($(this).text()==idVal){
//                                         targetTr=$(this).parent();
//                                         return false;
//                                     }
//                                 });
//                             });
//                             targetTr.children('td').eq(1).html($('#typename').val());
//                         }
//                         //隐藏模态窗口
//                         $('#myModal').modal('hide');
//                     }
//                 });
//             }
//         );
//
//         //修改
//         //$(".table tr td:nth-child(2)").addClass('red');
//         //$(this).children('td').eq(1).addClass('red');
//         //1、绑定所有修改按钮事件
//         $('.table').on('click','.btn-primary',function(){
//             //2、获取对应的值  typeId typeName
//             var tr=$(this).parent().parent();
//             var typeId=tr.children('td').eq(0).text();
//             var typeName=tr.children('td').eq(1).text();
//
//             //3、显示模态窗口
//             $("#myModalLabel").text("修改");
//             $('#myModal').modal();
//             //4、按钮重置样式
//             $('#btn_submit').html('保存').removeClass("btn-danger").addClass("btn-info").attr('disabled',true);
//             //5、填充值
//             $('#typename').val(typeName);
//             $("input[name='typeId']").val(typeId);
//         });
//
//
//         //删除 1、绑定所有修改按钮事件
//         $('.table').on('click','.btn-danger',function(){
//             //2、获取对应的值  typeId typeName
//             var tr=$(this).parent().parent();
//             var typeId=tr.children('td').eq(0).text();
//             var typeName=tr.children('td').eq(1).text();
//             //3、使用sweet-alert
//             swal({  //弹出框的title
//                 text: "确定删除【"+typeName+"】吗？",  //弹出框里面的提示文本
//                 type: "warning",    //弹出框类型
//                 showCancelButton: true, //是否显示取消按钮
//                 confirmButtonColor: "#DD6B55",//确定按钮颜色
//                 cancelButtonText: "取消",//取消按钮文本
//                 confirmButtonText: "是的，确定删除！"//确定按钮上面的文档
//             }).then(function(isConfirm) {
//                 if (isConfirm === true) {
//                     $.getJSON("type",{
//                         act:'del',
//                         typeId:typeId
//                     },function(data){
//                         if(data.resultCode==1){
//                             swal('操作成功!','已成功删除数据','success');
//                             //移除tr
//                             var targetTr ;
//                             $("table tr:gt(0)").each(function(i){
//                                 $(this).children("td:eq(0)").each(function(i){
//                                     if($(this).text()==typeId){
//                                         targetTr=$(this).parent();
//                                         return false;
//                                     }
//                                 });
//                             });
//                             targetTr.remove();
//                         }else if(data.resultCode==0){
//                             swal('操作失败!','存在子记录不能删除','error');
//                         }else{
//                             swal('操作失败!','未知问题','error');
//                         }
//                     });
//
//                 }
//             });
//         });
//     }
// );


function  deleteType(typeId){
            var valueStr = $("#tr_"+typeId+" td").eq(1).html();
            swal({  //弹出框的title
                text: "确定删除["+valueStr+"]吗？",  //弹出框里面的提示文本
                type: "warning",    //弹出框类型
                showCancelButton: true, //是否显示取消按钮
                confirmButtonColor: "#DD6B55",//确定按钮颜色
                cancelButtonText: "取消",//取消按钮文本
                confirmButtonText: "是的，确定删除！"//确定按钮上面的文档
            }).then(function(isConfirm) {
                //确认删除的执行
                $.ajax({
                    type:"post",
                    url:"type",
                    data:{
                        actionName:"delete",
                        typeId:typeId
                    },
                    success:function(result){

                        if(result.status == 1){
                            swal("","<h3>删除成功</h3>","success");
                            //删除dom中的文档
                            deleteDom(typeId);
                        }else{
                            swal("","<h3>"+result.tips+"</h3>","error");
                        }
                    }
                });

            });

}

//删除表中的列表
function deleteDom(typeId){
    //获取表元素
    var table = $("#typeListTbale");
    //获取行的长度
    var length = $("#typeListTbale  tr").length;

    if(length==2){
        $("#tr_"+typeId).remove();
        //填写提示信息
        var str ="<tr><td>暂未查询到类型数据</td></tr>";
        $("#tableHead").after($(str));

        //删除左边列表
        $("#li_"+typeId).remove();
    }else{
        $("#tr_"+typeId).remove();
    }

}

//添加类型
function addType(){
    $("#myModalLabel").text("新增类型");
    $('#myModal').modal();
    $("#tipsMsg").html("");
    $('#typeId').val('');
    $('#typename').val('');
}

//修改类型
function modifyType(typeId){
    $("#myModalLabel").text("修改类型");
    $('#myModal').modal();
    $("#tipsMsg").html("");

    var typeName = $("#tr_"+typeId+" td").eq(1).html();
    //要修改的值
    $('#typeId').val(typeId);
    $('#typename').val(typeName);
}

function saveUpadateSubmit(){

    //获取类型的名字  和  id
    var typeName = $("#typename").val();
    var typeId = $("#typeId");

    //判断类型名字是否为空  提示用户信息
    if(isEmpty(typeName)){
        $("#tipsMsg").text("*不能为可空");
        return;
    }

    //如果是修改  修改前后一样  直接关闭模态框
    if(typeId != null || typeId != ""){

        var id = typeId.val();

        var prop = $("#tr_"+id+" td").eq(1).html();

        if(prop == typeName){
            $("#tipsMsg")[0].innerHTML="";
            $('#myModal').modal('hide');
            return;
        }
    }

    var typeIdValue = $("#typeId").val();
    $.ajax({
        type:"post",
        url:"type",
        data: {

            actionName:"addOrUpdate",
            typeName:typeName,
            typeId:typeIdValue


        },
        success:function (result){
            if(result.status==1){

                //添加成功
                $("#myModal").modal("hide");
                if(typeIdValue == ""){
                    //alert(123);
                    //执行添加dom操作
                    //alert("执行添加dom操作");
                    //获取当前typeName  所属的元素
                    addDomAction(result.result,typeName);
                }else{

                   //执行修改dom操作
                    //alert("执行修改dom操作 ");
                    //获取当前元素
                    modifyDomAction(typeIdValue,typeName);
                }
            }else{
                //添加或者修改失败  提示信息
                $("#tipsMsg").text(result.tips);
            }
        }
    })


}

//修改dom操作  addDomAction
function modifyDomAction(typeId,typeName){
    $("#tr_"+typeId+" td").eq(1).html(typeName);
    //还要修改对应左侧的类型名字
    $("#sp_"+typeId).html(typeName);
}

//添加dom操作   modifyDomAction
function addDomAction(key,typeName){
    /**
     *  <tr id="tr_${item.typeId}">
     *                             <td>${item.typeId}</td>
     *                             <td>${item.typeName}</td>
     *                             <td>
     *                                 <button class="btn btn-primary" type="button" onclick="modifyType(${item.typeId})">修改</button>&nbsp;
     *                                 <button class="btn btn-danger del" type="button" id="deleteType" onclick="deleteType(${item.typeId},'${item.typeName}')">删除</button>
     *                             </td>
     *                         </tr>
     * @type {string}
     */

        var str='<tr id="tr_'+key+'">'+'<td>'+key+'</td>'+'<td>'+typeName+'</td>'+'<td>'+'<button class="btn btn-primary" type="button" onclick="modifyType('+key+')">修改</button>&nbsp;&nbsp;'+'<button class="btn btn-danger del" type="button" id="deleteType" onclick="deleteType('+key+')">删除</button>'+'</td>'+'</tr>';

        //获得所在元素
    //alert(str);
    $("#tbodys").append(str);

    //修改要联动
    //<li id="li_30"><a href=""><span id="sp_30">语录</span> <span className="badge">9</span></a></li>
    var liStr = '<li id="li_'+key+'"><a href="#"><span id="sp_'+key+'">'+typeName+'</span> <span className="badge">9</span></a></li>';

    $("#navTypeList").append(liStr);
}