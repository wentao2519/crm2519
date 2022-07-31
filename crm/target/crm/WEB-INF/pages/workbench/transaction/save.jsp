<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

    <script type="text/javascript">
        $(function () {

            //当容器加载完成，对容器调用工具函数
            $(".myDate").datetimepicker({
                language: 'zh-CN', //语言
                format: 'yyyy-mm-dd',//日期的格式
                minView: 'month', //可以选择的最小视图
                initialDate: new Date(),//初始化显示的日期
                autoclose: true,//设置选择完日期或者时间之后，日否自动关闭日历
                todayBtn: true,//设置是否显示"今天"按钮,默认是false
                clearBtn: true,//设置是否显示"清空"按钮，默认是false
                pickerPosition: "bottom-left"
            });


            // 阶段下拉框 change事件
            $("#create-stage").change(function () {
                var stageValue = $("#create-stage option:selected").text();
                if (stageValue == "") {
                    // 清空可能性输入框
                    $("#create-possibility").val("");
                    return
                } else {
                    $.ajax({
                        url: 'workbench/transaction/getPossibilityByStage.do',
                        data: {
                            stageValue: stageValue
                        },
                        type: 'post',
                        dateType: 'json',
                        success: function (data) {

                            $("#create-possibility").val(data + "%");
                        }
                    });
                }
            });

            $("#create-customerName").typeahead({
                source: function (jquery, process) {
                    $.ajax({
                        url: 'workbench/transaction/queryCustomerNameByName.do',
                        data: {
                            customerName: jquery
                        },
                        type: 'post',
                        dateType: 'json',
                        success: function (data) {
                            process(data);
                        }
                    });
                }
            });
            // 市场活动源按钮 单击事件
            $("#searchActivityBtn").click(function () {

                //清空搜索框
                $("#searchActivityText").val("");
                //清空搜索的市场活动列表
                $("#aTBody").html("");

                // 弹模态窗口
                $("#searchActivityModal").modal("show");
            });


            // 市场活动源 搜索框 键盘弹起事件
            $("#searchActivityText").keyup(function () {
                var activityName = this.value;
                $.ajax({
                    url: 'workbench/transaction/queryActivityByName.do',
                    data: {
                        activityName: activityName,
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        var htmlStr = "";
                        $.each(data, function (index, obj) {
                            htmlStr += "<tr>";
                            htmlStr += "<td><input type=\"radio\" value=\"" + obj.id + "\" activityName=\"" + obj.name + "\" name=\"activity\"/></td>";
                            htmlStr += "<td>" + obj.name + "</td>";
                            htmlStr += "<td>" + obj.startDate + "</td>";
                            htmlStr += "<td>" + obj.endDate + "</td>";
                            htmlStr += "<td>" + obj.owner + "</td>";
                            htmlStr += "</tr>";
                        });
                        $("#aTBody").html(htmlStr);
                    }
                });
            });

            // 市场活动单选按钮 单击事件
            $("#aTBody").on("click", "input[type='radio']", function () {

                var id = this.value;
                var activityName = $(this).attr("activityName");
                $("#create-activityId").val(id);
                $("#create-activityName").val(activityName);

                // 关模态窗口
                $("#searchActivityModal").modal("hide");
                // console.log($("#create-activityId").val());
                // console.log($("#create-activityName").val());
            });


            // 联系人搜索按钮 单击事件
            $("#searchContactsBtn").click(function () {

                //清空搜索框
                $("#searchContactsNameText").val("");
                //清空搜索的联系人列表
                $("#cTBody").html("");

                // 弹模态窗口
                $("#searchContactsModal").modal("show");
            });


            // 市场活动源 搜索框 键盘弹起事件
            $("#searchContactsNameText").keyup(function () {
                var contactsName = this.value;
                $.ajax({
                    url: 'workbench/transaction/queryContactsByFullName.do',
                    data: {
                        contactsName: contactsName,
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        var htmlStr = "";
                        $.each(data, function (index, obj) {

                            htmlStr += "<tr>";
                            htmlStr += "<td><input type=\"radio\"  value=\"" + obj.id + "\" contactsName=\"" + obj.fullname + "\" name=\"contacts\"/></td>";
                            htmlStr += "<td>" + obj.fullname + "</td>";
                            htmlStr += "<td>" + obj.email + "</td>";
                            htmlStr += "<td>" + obj.mphone + "</td>";

                        });
                        $("#cTBody").html(htmlStr);
                    }
                });
            });

            // 市场活动单选按钮 单击事件
            $("#cTBody").on("click", "input[type='radio']", function () {

                var id = this.value;
                var contactsName = $(this).attr("contactsName");
                $("#create-contactsId").val(id);
                $("#create-contactsName").val(contactsName);

                // 关模态窗口
                $("#searchContactsModal").modal("hide");

                // console.log($("#create-contactsId").val());
                // console.log($("#create-contactsName").val());
            });


            // 保存按钮 单击事件
            $("#saveCreateTranBtn").click(function () {
                //收集参数
                var owner = $("#create-owner").val();
                var money = $.trim($("#create-money").val());
                var name = $.trim($("#create-name").val());
                var expectedDate = $("#create-expectedDate").val();
                var customerName = $.trim($("#create-customerName").val());
                var stage = $("#create-stage").val();
                var type = $("#create-type").val();
                var source = $("#create-source").val();
                var activityId = $("#create-activityId").val();
                var contactsId = $("#create-contactsId").val();
                var description = $.trim($("#create-description").val());
                var contactSummary = $.trim($("#create-contactSummary").val());
                var nextContactTime = $("#create-nextContactTime").val();
                // 表单验证
                if (owner == "") {
                    alert("所有者不能为空！");
                    return;
                }
                if (name == "") {
                    alert("名称不能为空！");
                    return;
                }
                if (customerName == "") {
                    alert("客户名称不能为空！");
                    return;
                } if (stage == "") {
                    alert("阶段不能为空！");
                    return;
                }
                 if (expectedDate == "") {
                    alert("预计成交日期不能为空！");
                    return;
                }
                var regExp = /^(([1-9]\d*)|0)$/;
                if (!regExp.test(money)) {
                    alert("金额只能为非负整数！");
                    return;
                }



                $.ajax({
                    url: 'workbench/transaction/saveCreateTran.do',
                    data: {
                        owner: owner,
                        money: money,
                        name: name,
                        expectedDate: expectedDate,
                        customerName: customerName,
                        stage: stage,
                        type: type,
                        source: source,
                        activityId: activityId,
                        contactsId: contactsId,
                        description: description,
                        contactSummary: contactSummary,
                        nextContactTime: nextContactTime
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            //跳转到交易主页面
                            window.location.href = "workbench/transaction/index.do";
                        } else {
                            //提示信息
                            alert(data.message);
                        }
                    }
                })
            });

            // 取消按钮 单击事件
            $("#cancelCreateTranBtn").click(function () {
                // window.history.go(-1); //返回上一页

                window.history.back(); //返回上一页
            });

        });
    </script>

</head>
<body>

<!-- 查找市场活动模态窗口 -->
<div class="modal fade" id="searchActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">查找市场活动</h4>
            </div>
            <div class="modal-body">
                <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                    <form class="form-inline" role="form">
                        <div class="form-group has-feedback">
                            <input type="text" id="searchActivityText" class="form-control" style="width: 300px;"
                                   placeholder="请输入市场活动名称，支持模糊查询">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <table id="activityTable3" class="table table-hover"
                       style="width: 900px; position: relative;top: 10px;">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td></td>
                        <td>名称</td>
                        <td>开始日期</td>
                        <td>结束日期</td>
                        <td>所有者</td>
                    </tr>
                    </thead>
                    <tbody id="aTBody">
                    <%--<tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 查找联系人 -->
<div class="modal fade" id="searchContactsModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 80%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">查找联系人</h4>
            </div>
            <div class="modal-body">
                <div class="btn-group" style="position: relative; top: 18%; left: 8px;">
                    <form class="form-inline" role="form">
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control" id="searchContactsNameText" style="width: 300px;"
                                   placeholder="请输入联系人名称，支持模糊查询">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </form>
                </div>
                <table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
                    <thead>
                    <tr style="color: #B3B3B3;">
                        <td></td>
                        <td>名称</td>
                        <td>邮箱</td>
                        <td>手机</td>
                    </tr>
                    </thead>
                    <tbody id="cTBody">
                    <%--<tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<div style="position:  relative; left: 30px;">
    <h3>创建交易</h3>
    <div style="position: relative; top: -40px; left: 70%;">
        <button type="button" class="btn btn-primary" id="saveCreateTranBtn">保存</button>
        <button type="button" class="btn btn-default" id="cancelCreateTranBtn">取消</button>
    </div>
    <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
    <div class="form-group">
        <label for="create-owner" class="col-sm-2 control-label">所有者<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="create-owner">
                <option></option>
                <c:forEach items="${userList}" var="user">
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
        </div>
        <label for="create-money" class="col-sm-2 control-label">金额</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="create-money">
        </div>
    </div>

    <div class="form-group">
        <label for="create-name" class="col-sm-2 control-label">名称<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="create-name">
        </div>
        <label for="create-expectedDate" class="col-sm-2 control-label">预计成交日期<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control myDate" readonly id="create-expectedDate">
        </div>
    </div>

    <div class="form-group">
        <label for="create-customerName" class="col-sm-2 control-label">客户名称<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" id="create-customerName"
                   placeholder="支持自动补全，输入客户不存在则新建">
        </div>
        <label for="create-stage" class="col-sm-2 control-label">阶段<span
                style="font-size: 15px; color: red;">*</span></label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="create-stage">
                <option></option>
                <c:forEach items="${stageList}" var="stage">
                    <option value="${stage.id}">${stage.value}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label for="create-type" class="col-sm-2 control-label">类型</label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="create-type">
                <option></option>
                <c:forEach items="${transactionTypeList}" var="tran">
                    <option value="${tran.id}">${tran.value}</option>
                </c:forEach>
            </select>
        </div>
        <label for="create-possibility" class="col-sm-2 control-label">可能性</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" readonly id="create-possibility">
        </div>
    </div>

    <div class="form-group">
        <label for="create-source" class="col-sm-2 control-label">来源</label>
        <div class="col-sm-10" style="width: 300px;">
            <select class="form-control" id="create-source">
                <option></option>
                <c:forEach items="${sourceList}" var="source">
                    <option value="${source.id}">${source.value}</option>
                </c:forEach>
            </select>
        </div>
        <label for="create-activityName" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a
                href="javascript:void(0);" id="searchActivityBtn"><span
                class="glyphicon glyphicon-search"></span></a></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" readonly id="create-activityName">
            <input type="hidden" id="create-activityId">
        </div>
    </div>

    <div class="form-group">
        <label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a
                href="javascript:void(0);" id="searchContactsBtn"><span
                class="glyphicon glyphicon-search"></span></a></label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control" readonly id="create-contactsName">
            <input type="hidden" id="create-contactsId">
        </div>
    </div>

    <div class="form-group">
        <label for="create-description" class="col-sm-2 control-label">描述</label>
        <div class="col-sm-10" style="width: 70%;">
            <textarea class="form-control" rows="3" id="create-description"></textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
        <div class="col-sm-10" style="width: 70%;">
            <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="create-nextContactTime" class="col-sm-2 control-label mydate">下次联系时间</label>
        <div class="col-sm-10" style="width: 300px;">
            <input type="text" class="form-control myDate" readonly id="create-nextContactTime">
        </div>
    </div>

</form>
</body>
</html>