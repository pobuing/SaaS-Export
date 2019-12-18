<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>企业列表</title>
</head>

<body>
    <!--数据列表-->
    <table id="dataList">
        <thead>
            <tr>
                <th class="sorting" style="font-size: 20px;">企业名称</th>
                <th class="sorting" style="font-size: 20px;">所在地</th>
                <th class="sorting" style="font-size: 20px;">地址</th>
                <th class="sorting" style="font-size: 20px;">企业法人</th>
                <th class="sorting" style="font-size: 20px;">联系方式</th>
                <th class="sorting" style="font-size: 20px;">所属行业</th>
                <th class="sorting" style="font-size: 20px;">状态</th>
                <th class="sorting" style="font-size: 20px;">余额</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="item">
            <tr>
                <td width="300" align="center">${item.name} </td>
                <td width="100" align="center">${item.city}</td>
                <td width="200" align="center">${item.address}</td>
                <td width="100" align="center">${item.representative}</td>
                <td width="150" align="center">${item.phone}</td>
                <td width="100" align="center">${item.industry}</td>
                <td width="100" align="center">${item.state}</td>
                <td width="100" align="center">${item.balance}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</body>

</html>