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
    <title></title>
    <style type="text/css">
        #msg {
            color: red;
        }
    </style>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <!--BOOTSTRAP_DATETIMEPICKER插件-->
    <link rel="stylesheet" type="text/css"
          href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <script type="text/javascript">
        $(function () {

            //当容器加载完成，对容器调用工具函数
            $(".myDate").datetimepicker({
                language: 'zh-CN', //语言
                format: 'yyyy-mm-dd 00:00:00',//日期的格式
                minView: 'month', //可以选择的最小视图
                initialDate: new Date(),//初始化显示的日期
                autoclose: true,//设置选择完日期或者时间之后，日否自动关闭日历
                todayBtn: true,//设置是否显示"今天"按钮,默认是false
                clearBtn: true,//设置是否显示"清空"按钮，默认是false
                pickerPosition: "bottom-left"
            });

            $(window).keydown(function (e) {
                if (e.keyCode == 13) {
                    $("#loginBtn").click();
                }
            });

            /**
             * 注册按钮 单击事件
             */
            $("#registerBtn").click(function () {
                // 弹模态窗口
                $("#registerUserModal").modal("show");
            });

            /**
             * 保存注册 按钮 单击事件
             */
            $("#saveUserBtn").click(function () {

                var loginAct = $.trim($("#register-act").val());
                var loginPwd = $.trim($("#register-pwd").val());
                var name = $.trim($("#register-name").val());
                var email = $.trim($("#register-email").val());
                var deptno = $.trim($("#register-dept").val());
                var expireTime = $.trim($("#register-expireTime").val());
                var lockState = $('input:radio[name="state"]:checked').val();

                if (loginAct == "") {
                    alert("账号不能为空！")
                    return;
                }

                if (loginPwd == "") {
                    alert("密码不能为空！")
                    return;
                }
                if (name == "") {
                    alert("姓名不能为空！")
                    return;
                }
                if (email == "") {
                    alert("邮箱不能为空！")
                    return;
                }
                if (deptno == "") {
                    alert("部门不能为空！")
                    return;
                }
                if (expireTime == "") {
                    alert("请选择账号失效时间！")
                    return;
                }

                //正则表达式验证
                var emailRegExp = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
                if (!emailRegExp.test(email)) {
                    alert("Email格式错误！");
                    return;
                }
                if (lockState == null) {
                    alert("请选择账号状态!");
                    return;
                }

                $.ajax({
                    url: 'settings/qx/user/registerUser.do',
                    data: {
                        loginAct: loginAct,
                        loginPwd: loginPwd,
                        name: name,
                        email: email,
                        deptno: deptno,
                        expireTime: expireTime,
                        lockState: lockState
                    },
                    type: 'post',
                    dataType: 'json',
                    success:function (data) {
                        if (data.code=="1"){
                            // 登录。。
                            // 关模态窗口
                            $("#registerUserModal").modal("hide");

                            $("#loginAct").val(data.returnData.loginAct);
                            $("#loginPwd").val(data.returnData.loginPwd);

                            alert(data.message);
                        }else {
                            alert(data.message);
                        }
                    }
                });

            })

            /**
             * 登录按钮 单击事件
             */
            $("#loginBtn").click(function () {
                // 收集参数
                var loginAct = $.trim($("#loginAct").val());
                var loginPwd = $.trim($("#loginPwd").val());
                var isRemPwd = $("#isRemPwd").prop("checked");

                if (loginAct == "") {
                    // 用户名不能为空！
                    alert("用户名不能为空！");
                    return;
                }
                if (loginPwd == "") {
                    // 密码不能为空！
                    alert("密码不能为空！");
                    return;
                }
                $.ajax({
                    url: 'settings/qx/user/login.do',
                    data: {
                        loginAct: loginAct,
                        loginPwd: loginPwd,
                        isRemPwd: isRemPwd
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == "1") {
                            // 跳转到业务主页面
                            window.location.href = "workbench/index.do";
                        } else {
                            // 登录失败
                            $("#msg").html(data.message);
                        }
                    },
                    beforeSend: function () { //ajax向后台发请求前会自动执行此函数  返回true ajax会发送请求  返回false则不会
                        $("#msg").html("正在努力验证...");
                        return true;
                    }
                })
            });
        });
    </script>
</head>
<body>


<!-- 注册用户的模态窗口 -->
<div class="modal fade" id="registerUserModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 40%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h3 class="modal-title text-center" id="myModalLabel">用户注册</h3>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="register-act" class="col-sm-2 control-label">账号</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="register-act">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="register-pwd" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="password" class="form-control" id="register-pwd">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="register-name" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="register-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="register-email" class="col-sm-2 control-label">邮箱</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="register-email">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="register-dept" class="col-sm-2 control-label">部门</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="register-dept">
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="register-expireTime" class="col-sm-2 control-label">失效日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control myDate" readonly id="register-expireTime">
                        </div>
                    </div>


                    <div class="form-group">
                        <label for="register-lockState" class="col-sm-2 control-label">帐号状态</label>
                        <div class="col-sm-10 btn-group" data-toggle="buttons" style="width: 300px;"
                             id="register-lockState">
                            <label class="btn btn-success btn-primary">
                                <input type="radio" name="state" value="1">启用
                            </label>
                            <label class="btn btn-danger btn-primary">
                                <input type="radio" name="state" value="0">锁定
                            </label>
                        </div>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveUserBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 70px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2022&nbsp;waves</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" id="loginAct" type="text" value="${cookie.loginAct.value}"
                           placeholder="用户名">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" id="loginPwd" type="password" value="${cookie.loginPwd.value}"
                           placeholder="密码">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
                    <label>
                        <c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
                            <input type="checkbox" id="isRemPwd" checked>
                        </c:if>
                        <c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
                            <input type="checkbox" id="isRemPwd">
                        </c:if>
                        十天内记住密码
                    </label>
                    &nbsp;&nbsp;
                    <span id="msg"></span>
                </div>
                <button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
                <button type="button" id="registerBtn" class="btn btn-link"
                        style=" position: relative;bottom: 43px;left: 270px">立即注册
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>