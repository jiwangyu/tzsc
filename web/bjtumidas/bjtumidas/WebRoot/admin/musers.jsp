<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			String user = null;
	try {
		user = session.getAttribute("user").toString();
	} catch (Exception e) {
				response.sendRedirect("../login.jsp?info=unlogin");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理用户</title>
<link href="../css/vjpublic.css" rel="stylesheet" type="text/css" />
<link href="../css/jqueryui.css" rel="stylesheet" type="text/css" />
<link href="../css/layout-default.css" rel="stylesheet" type="text/css" />
<link href="../css/tlayout.css" rel="stylesheet" type="text/css" />
<link href="../css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link href="../css/cum.css" rel="stylesheet" type="text/css" />
<link href="../css/vjpage.css" rel="stylesheet" type="text/css" />
<link href="../css/config.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/resizeComponents.js"></script>
<script type="text/javascript" src="../layout/jquery.layout.js"></script>
<script type="text/javascript" src="../js/grid.locale-cn.js"></script>
<script type="text/javascript" src="../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../js/page3.js"></script>
<script language="javascript">
	function opendilogs() {
		$("#page3_jDialog1").dialog("open");//打开窗口
	}

	function closedilogs() {
		$("#page3_jDialog1").dialog("close");//关闭窗口
	}
	function onsearch() {
		//按条件进行查询
		var select = jQuery("#page3_jEdit1").val();
		jQuery("#page3_jDataGrid1_table").jqGrid('setGridParam', {
			url : "UsersServlet?type=querywhere&select=" + select,
			page : 1
		}).trigger("reloadGrid");
	}
	function submitform() {
		document.getElementById("page3_jHtmlForm1").submit();
	}
	function deleteuser() {
		//删除用户
		var sIds = $("#page3_jDataGrid1_table").jqGrid("getGridParam",
				"selarrrow");
		//alert(sIds);
		if (sIds.length > 0) {
			for ( var i = 0; i < sIds.length; i++) {
				var ret = jQuery("#page3_jDataGrid1_table").jqGrid(
						'getRowData', sIds[i]);
				var uId = ret.uId;
				var isdelete = confirm("您确定要删除: " + ret.uNo + " 吗？");
				if (isdelete) {
					jQuery("#page3_jDataGrid1_table").jqGrid('setGridParam', {
						url : "UsersServlet?type=delete&uId=" + uId,
						page : 1
					}).trigger("reloadGrid");
				}
			}
		} else {
			alert("未选择任何人");
		}
	};
</script>
</head>
<body class="body_style1">
	<div id="page3_jContainer1" class="tlayout_style2 tlayout_noborder">
		<div id="page3_jContainer1_container" class="container">
			<div id="page3_jRegion1" class="page3_jContainer1_north">
				<div id="page3_jToolbar1" class="TJToolbar">
					<div id="page3_jButton1"
						class="vjbutton vjbutton_c_style2 border_radius_3"
						onclick="opendilogs()">
						<div class="vjbutton_txtR">
							<span class="btniconcum1"><img src="../images/jadd.png"
								width="16" height="16" /> </span><span class="btniconcum2"><img
								src="../images/jadd.png" width="16" height="16" /> </span><span
								class="btntxt"></span>增加
						</div>
					</div>
					<div id="page3_jButton3"
						class="vjbutton vjbutton_c_style2 border_radius_3"
						onclick="deleteuser()">

						<div class="vjbutton_txtR">
							<span class="btniconcum1"><img src="../images/jdelete.png"
								width="16" height="16" /> </span><span class="btniconcum2"><img
								src="../images/jdelete.png" width="16" height="16" /> </span><span
								class="btntxt"></span>删除
						</div>

					</div>
					<div id="page3_jButton4" onclick="location.reload(false)"
						class="vjbutton vjbutton_c_style2 border_radius_3">
						<div class="vjbutton_txtR">
							<span class="btniconcum1"><img
								src="../images/jarrow_rotate_clockwise.png" width="16"
								height="16" /> </span><span class="btniconcum2"><img
								src="../images/jarrow_rotate_clockwise.png" width="16"
								height="16" /> </span><span class="btntxt"></span>刷新
						</div>
					</div>
					<img class="TSeparator TSeparator_style1" id="page3_jSeparator1"
						src="../images/jToolsSeparator.png" />
					<div id="page3_jLabel1" class="text">关键字：</div>
					<input type="text" class="Edit Edit_style1" value=""
						id="page3_jEdit1" />
					<div id="page3_jButton5" onclick="onsearch()"
						class="vjbutton vjbutton_c_style2 border_radius_3">
						<div class="vjbutton_txtR">
							<span class="btniconcum1"><img src="../images/jzoom.png"
								width="16" height="16" /> </span><span class="btniconcum2"><img
								src="../images/jzoom.png" width="16" height="16" /> </span><span
								class="btntxt"></span>查询
						</div>
					</div>
					<img class="TSeparator TSeparator_style1" id="page3_jSeparator2"
						src="../images/jToolsSeparator.png" />
				</div>
			</div>
			<div id="page3_jRegion2" class="page3_jContainer1_center">
				<div class="TDataGrid" id="page3_jDataGrid1">
					<table id="page3_jDataGrid1_table"></table>
					<div id="page3_jDataGrid1_pager"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="page3_jDialog1" title="添加用户(密码默认为学号后6位)">
		<%
			String info = request.getParameter("info");
			if ("existed".equals(info)) {
		%>
		<script type="text/javascript">
			alert("该账户已经被注册！");
		</script>
		<%
			} else if ("successful".equals(info)) {
		%>
		<script type="text/javascript">
			alert("注册成功！");
		</script>
		<%
			} else if ("failed".equals(info)) {
		%>
		<script type="text/javascript">
			alert("发送未知错误");
			opendilogs();
		</script>
		<%
			}
		%>
		<div id="page3_jHtmlForm1_form">
			<form id="page3_jHtmlForm1" name="page3_jHtmlForm1" method="post"
				action="UsersServlet?type=add">
				<div id="page3_jLabel2" class="text">手机号：</div>
				<input type="text" class="Edit Edit_style1" name="uPhone" value=""
					id="page3_jEdit2" />
				<div id="page3_jLabel3" class="text">学号：</div>
				<input type="text" class="Edit Edit_style1" name="uNo" value=""
					id="page3_jEdit3" />
				<div id="page3_jLabel4" class="text">性别：</div>
				<div id="page3_jButton2" onclick="submitform()"
					class="vjbutton vjbutton_c_style1 border_radius_3">
					<div class="vjbutton_txtR">确定</div>
				</div>
				<div id="page3_jButton6"
					class="vjbutton vjbutton_c_style1 border_radius_3"
					onclick="closedilogs()">
					<div class="vjbutton_txtR">取消</div>
				</div>
				<div class="radio radio_style1" id="page3_jRadioButton1">
					<label><input type="radio" name="uSex" checked="checked"
						id="page3_jRadioButton1_1" value="1" />男</label>
				</div>
				<div class="radio radio_style1" id="page3_jRadioButton2">
					<label><input type="radio" name="uSex"
						id="page3_jRadioButton2_1" value="0" />女</label>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
