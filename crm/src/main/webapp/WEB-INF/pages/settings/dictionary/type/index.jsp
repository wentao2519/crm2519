<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() +
            "://" + request.getServerName() +
            ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>


<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        $(function () {

            function refreshDicTypeList() {
                $.ajax({
                    url: 'settings/dictionary/type/queryAllDicType.do',
                    data: null,
                    dataType: 'json',
                    type: 'post',
                    success: function (data) {
                        var htmlStr = "";
                        $.each(data.dicTypeList, function (index, obj) {
                            htmlStr += "<tr class=\"active\">";
                            htmlStr += "<td><input type=\"checkbox\" value=\"" + obj.code + "\"/></td>";
                            htmlStr += "<td>" + (index + 1) + "</td>";
                            htmlStr += "<td>" + obj.code + "</td>";
                            htmlStr += "<td>" + obj.name + "</td>";
                            htmlStr += "<td>" + obj.description + "</td>";
                            htmlStr += "</tr>";
                        });
                        $("#tBody").html(htmlStr);
                    }
                });
            }

            refreshDicTypeList();
            // 全选按钮
            $("#checkAll").click(function () {

                $("#tBody input[type='checkbox']").prop("checked", this.checked);
            });

            $("#tBody").on("click", "input[type='checkbox']", function () {

                if ($("#tBody input[type='checkbox']").size() == $("#tBody input[type='checkbox']:checked").size()) {
                    $("#checkAll").prop("checked", true);
                } else {
                    $("#checkAll").prop("checked", false);
                }
            });

            // 删除按钮
            $("#deleteDicTypeBtn").click(function () {
                var checkedIds = $("#tBody input[type='checkbox']:checked");

                if (checkedIds.size() == 0) {
                    alert("请选择要删除的数据字典类型");
                    return;
                }
                if (window.confirm("确定删除吗？")) {

                    var codes = "";
                    $.each(checkedIds, function () {
                        codes += "code=" + this.value + "&";
                    });
                    codes = codes.substring(0, codes.length - 1);

                    $.ajax({
                        url: 'settings/dictionary/type/deleteDicTypeByCodes.do',
                        data: codes,
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            if (data.code == "1") {
                                refreshDicTypeList();
                            } else {
                                alert(data.message);
                            }
                        }
                    });

                }

            });
            // 给修改按钮添加点击事件
            $("#editDicTypeBtn").click(function () {

                // 获取列表中被选中的checkbox
                var checkedIds = $("#tBody input[type='checkbox']:checked");
                if (checkedIds.size()==0){
                    alert("请选择要修改的数据字典类型！");
                    return;
                }
                if (checkedIds.size()>1){
                    alert("每次只能修改一条数据字典类型！");
                    return;
                }
                var code = checkedIds.val();
                window.location.href='settings/dictionary/type/edit.do?code='+code;

            });
        });
    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/save.do'">
            <span class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" id="editDicTypeBtn">
            <span class="glyphicon glyphicon-edit"></span> 编辑
        </button>
        <button type="button" class="btn btn-danger" id="deleteDicTypeBtn"><span
                class="glyphicon glyphicon-minus"></span> 删除
        </button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="checkAll"/></td>
            <td>序号</td>
            <td>编码</td>
            <td>名称</td>
            <td>描述</td>
        </tr>
        </thead>
        <tbody id="tBody">
        <%--<tr class="active">
            <td><input type="checkbox" /></td>
            <td>1</td>
            <td>sex</td>
            <td>性别</td>
            <td>性别包括男和女</td>
        </tr>--%>
        </tbody>
    </table>
</div>

</body>
</html>