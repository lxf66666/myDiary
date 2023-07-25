<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-md-9">
      <div class="data_list">
        <c:if test="${!empty page}">
            <div class="data_list_title"><span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
              云记列表 </div>

            <div class="note_datas">
              <ul>
                  <c:forEach items="${page.dataList}" var="item">
                        <li><fmt:formatDate value="${item.pubTime}" pattern="yyyy-MM-dd"></fmt:formatDate>&nbsp;&nbsp;<a href="note?actionName=detail&noteId=${item.noteId}">${item.title}</a> </li>
                  </c:forEach>

              </ul>
            </div>
            <nav style="text-align: center">
              <ul class="pagination   center">
                  <c:if test="${page.currentpage > 1}" >
                  <li>
                      <a href="index?currentPage=${page.prePage}&viewPageCount=${page.pageSize}&act=${act}&title=${title}&dateName=${dateName}&typeId=${typeId}">
                          <span>«</span>
                      </a>
                  </li>
                  </c:if>

                        <c:forEach begin="${page.startNavPage}" end="${page.endNavPage}" var="p">

                            <li <c:if test="${page.currentpage == p}">class="active"</c:if>><a href="index?currentPage=${p}&act=${act}&title=${title}&dateName=${dateName}&typeId=${typeId}">${p}</a></li>
                        </c:forEach>

                  <c:if test="${page.currentpage<page.totalPages}">
                    <li>
                      <a href="index?currentPage=${page.nextPage}&viewPageCount=${page.pageSize}&act=${act}&title=${title}&dateName=${dateName}&typeId=${typeId}">
                        <span>»</span>
                      </a>
                    </li>
                  </c:if>
              </ul>
            </nav>
        </c:if>
        <c:if test="${empty page}">
         无
        </c:if>
      </div>


</div>