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
    function deleteById() {
        var id = getCheckId()
        if (id) {
            if (confirm("你确认要删除此条记录吗？")) {
                location.href = "${ctx}/cargo/export/delete.do?id=" + id;
            }
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function submit() {
        var id = getCheckId()
        if (id) {
            location.href = "${ctx}/cargo/export/submit.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function cancel() {
        var id = getCheckId()
        if (id) {
            location.href = "${ctx}/cargo/export/cancel.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function exportE() {
        var id = getCheckId();

        if (id) {
            location.href = "${ctx}/cargo/export/exportE.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function getCheckIds() {
        var ids = '';
        $.each($('input:checkbox:checked'), function (i) {
            ids = ids + $(this).val();
            if (i != $('input[type=checkbox]:checked').length - 1) {
                //最后一条
                ids += ',';
            }
        });
        return ids;
    }

    function markBox() {
        var id = getCheckIds();
        console.log(id);
        if (id) {
            location.href = "${ctx}/cargo/packing/createBox.do?id=" + id;
        } else {
            alert("选择错误");
        }
    }


    //   查询当前报运状态
    function checkStatOp(opType) {
        console.log(opType);
        var id = getCheckId();
        $.post(
            "${ctx}/cargo/export/findExportById.do",
            {id: id},
            function (data) {
                console.log(data);
                if (opType == 'exportE') {
                    if (data == '1') {
                        exportE();
                    } else {
                        alert("操作非法，草稿或已报运不能进行电子报运");
                    }
                } else if (opType == 'cancel') {
                    if (data == '1') {
                        //    提交之后才能取消
                        cancel();
                    } else {
                        alert("操作非法，电子报运后不可取消");
                    }
                } else if (opType == 'submit') {
                    if (data == '0') {
                        submit();
                    } else {
                        alert("操作非法，不可重复提交");
                    }
                } else if (opType == 'deleteById') {
                    if (data == '0') {
                        deleteById();
                    } else {
                        alert("操作非法，非草稿状态不允许删除");
                    }
                }
            },
            "text"
        );

    }
</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            装箱管理
            <small>报运单列表</small>
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
                <h3 class="box-title">报运单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="删除"
                                        onclick='markBox()'><i
                                        class="fa fa-trash-o"></i> 装箱
                                </button>
                                <%--<button type="button" class="btn btn-default" title="提交"
                                        onclick='checkStatOp("submit")'><i
                                        class="fa fa-file-o"></i> 提交
                                </button>
                                <button type="button" class="btn btn-default" title="取消"
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
                            <th class="sorting">报运号</th>
                            <th class="sorting">货物/附件</th>
                            <th class="sorting">信用证号</th>
                            <th class="sorting">收货地址</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">目的港</th>
                            <th class="sorting">运输方式</th>
                            <th class="sorting">价格条件</th>
                            <th class="sorting">状态</th>
                            <%--                            <th class="text-center">操作</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                <td><input type="checkbox" name="id" value="${o.id}"/></td>
                                <td>${o.id}</td>
                                <td align="center">
                                        ${o.proNum}/${o.extNum}
                                </td>
                                <td>${o.lcno}</td>
                                <td>${o.consignee}</td>
                                <td>${o.shipmentPort}</td>
                                <td>${o.destinationPort}</td>
                                <td>${o.transportMode}</td>
                                <td>${o.priceCondition}</td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                                </td>
                                    <%-- <td>
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
                    <jsp:param value="cargo/export/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>