<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
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

</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            厂家信息
            <small>厂家列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">货物编辑</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">编辑厂家信息</div>
            <form id="editForm" action="${ctx}/baseinfo/factoryinfo/edit.do" method="post" enctype="multipart/form-data">
                <input type="text" name="id" value="${factory.id}">
                <div class="row data-type" style="margin: 0px">

                    <div class="col-md-2 title">厂家全称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家全称" name="fullName" value="${factory.fullName}">
                    </div>
                    <div class="col-md-2 title">厂家代称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家代称" name="factoryName" value="${factory.factoryName}">
                    </div>
                    <div class="col-md-2 title">联系人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="联系人" name="contacts" value="${factory.contacts}">
                    </div>
                    <div class="col-md-2 title">电话</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="电话" name="mobile" value="${factory.mobile}">
                    </div>
                    <div class="col-md-2 title">固定电话</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="固定电话" name="phone" value="${factory.phone}">
                    </div>
                    <div class="col-md-2 title">传真</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="传真" name="fax" value="${factory.fax}">
                    </div>

                    <div class="col-md-2 title">地址</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="地址" name="address" value="${factory.address}">
                    </div>
                    <div class="col-md-2 title">检验员</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="检验员" name="inspector" value="${factory.inspector}">
                    </div>
                    <div class="col-md-2 title">创建时间</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="创建时间"  name="createTime" class="form-control pull-right"
                                   value="<fmt:formatDate value="${factory.createTime}" pattern="yyyy-MM-dd"/>" id="createTime">
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->


</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#createTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

</script>
</html>