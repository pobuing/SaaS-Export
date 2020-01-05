<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function createBox() {
        location.href = "${ctx}/cargo/packing/exportlist.do";
    }

    function forwardingorder() {
        var id = getCheckIds();
        console.log(id);
        if (id) {
            location.href = "${ctx}/cargo/packing/forwardOder.do?id=" + id;
        } else {
            alert("选择错误");
        }
    }


</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            装箱管理
            <small>装箱列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">装箱列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="删除"
                                        onclick='createBox()'><i
                                        class="fa fa-trash-o"></i> 新建装箱
                                </button>
                                <button type="button" class="btn btn-default" title="提交"
                                        onclick='forwardingorder()'><i
                                        class="fa fa-file-o"></i> 委托运输
                                </button>
                                <%--<button type="button" class="btn btn-default" title="取消"
                                        onclick='checkStatOp("cancel")'><i
                                        class="fa fa-file-o"></i> 取消
                                </button>
                                <button type="button" class="btn btn-default" title="电子报运"
                                        onclick='checkStatOp("exportE")'><i
                                        class="fa fa-refresh"></i> 电子报运
                                </button>--%>
                            </div>
                        </div>
                    </div>
                    <div class="box-tools pull-right">
                        <div class="has-feedback">
                            <input type="text" class="form-control input-sm" placeholder="搜索">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </div>
                    <!--工具栏/-->

                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <th class="sorting">箱号</th>
                            <th class="sorting">买方</th>
                            <th class="sorting">卖方</th>
                            <%--                            <th class="sorting">报运单号</th>--%>
                            <th class="sorting">创建人</th>
                            <th class="sorting">创建时间</th>
                            <th class="sorting">状态</th>
                            <%--                            <th class="text-center">操作</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                <td><input type="checkbox" name="id" value="${o.packingListId}"/></td>
                                <td>${o.packingListId}</td>
                                <td>${o.seller}</td>
                                <td>${o.buyer}</td>
                                    <%--                                <td>${o.exportIds}</td>--%>
                                <td>${o.createBy}</td>
                                <td>${o.createTime}</td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已装箱</font></c:if>
                                    <c:if test="${o.state==2}"><font color="green">已委托</font></c:if>
                                        <%--                                    <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>--%>
                                </td>
                                    <%--
                                     货物管理没有操作
                                    <td>
                                         <a href="${ctx }/cargo/export/toView.do?id=${o.id}">[查看]</a>
                                         <a href="${ctx }/cargo/export/toUpdate.do?id=${o.id}">[编辑]</a>
                                         <c:if test="${o.state==2}">
                                             <a href="${ctx}/cargo/export/exportPdf.do?id=${o.id}">[下载]</a>
                                         </c:if>
                                     </td>--%>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="cargo/packing/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>