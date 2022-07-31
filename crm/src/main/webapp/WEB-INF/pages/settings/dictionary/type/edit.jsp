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
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			$("#editDicTypeBtn").click(function () {
				//收集参数
				var code = $.trim($("#edit-code").val());
				var name = $.trim($("#edit-name").val());
				var description = $.trim($("#edit-description").val());
				var id = $.trim($("#dicTypeId").val());
				//表单验证
				if (code == "") {
					alert("编码不能为空");
					return;
				}
				//发送请求
				$.ajax({
					url: 'settings/dictionary/type/editDicType.do',
					data: {
						id:id,
						code: code,
						name: name,
						description: description
					},
					type: 'post',
					dataType: 'json',
					success: function (data) {
						if (data.code == "1") {
							window.location.href = "settings/dictionary/type/index.do";
						} else {
							//提示信息
							alert(data.message);
						}
					}
				});
			})
		})
	</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>修改字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="editDicTypeBtn">更新</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">

		<input type="hidden"id="dicTypeId" value="${id}">
		<div class="form-group">
			<label for="edit-code" class="col-sm-2 control-label">编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-code" style="width: 200%;" value="${dicType.code}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-name" style="width: 200%;" value="${dicType.name}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="edit-description" style="width: 200%;">${dicType.description}</textarea>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>