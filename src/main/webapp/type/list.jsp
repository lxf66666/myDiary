<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-md-9">


    <div class="data_list">
        <div class="data_list_title">
            <span class="glyphicon glyphicon-list"></span>&nbsp;类型列表
            <span class="noteType_add">
			<button class="btn btn-sm btn-success" type="button" id="addBtn" onclick="addType()">添加类别</button>
		</span>

        </div>
        <div>
            <table class="table table-hover table-striped " id="typeListTbale">
                <tbody id="tbodys">
                <tr id="tableHead">
                    <th>编号</th>
                    <th>类型</th>
                    <th>操作</th>
                </tr>


            <%--
                    1.用if 去判断是否有数据
                    没有数据 就提示信息给用户

                    2有信息 foreach进行打印
            --%>
                <c:if test="${empty typeList}">
                    <tr><td><h3>暂未查询到类型数据</h3></td></tr>
                </c:if>

                <c:if test="${!empty typeList}">
                    <c:forEach items="${typeList}" var="item">
                        <tr id="tr_${item.typeId}">
                            <td>${item.typeId}</td>
                            <td>${item.typeName}</td>
                            <td>
                                <button class="btn btn-primary" type="button" onclick="modifyType(${item.typeId})">修改</button>&nbsp;
                                <button class="btn btn-danger del" type="button" id="deleteType" onclick="deleteType(${item.typeId},'${item.typeName}')">删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>









                </tbody></table>
        </div>
    </div>

    <!--模态框-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="typename">类型名称</label><b id="tipsMsg" style="font-size: 12px;color: red;margin-left: 10px;"></b>
                        <input type="hidden" name="typeId" id="typeId" value="">
                        <input type="text" name="typename" class="form-control" id="typename" placeholder="类型名称">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <span class="glyphicon glyphicon-remove"></span>关闭</button>
                    <button type="button" id="btn_submit" class="btn btn-primary" onclick="saveUpadateSubmit()">
                        <span class="glyphicon glyphicon-floppy-disk" ></span>保存</button>
                </div>
            </div>
        </div>
    </div>


</div>