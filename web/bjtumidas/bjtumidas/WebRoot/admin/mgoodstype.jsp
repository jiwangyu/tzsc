<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
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
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商品类型</title>
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
<script type="text/javascript" src="../js/page6.js"></script>


<script language="javascript">

	function opendilogs() {
		$("#page6_jDialog1").dialog("open");//打开窗口
	}

	function closedilogs() {
		$("#page6_jDialog1").dialog("close");//关闭窗口
	}
	function deleteuser() {
		//删除用户
		var sIds = $("#page6_jDataGrid1_table").jqGrid("getGridParam",
				"selarrrow");
		//alert(sIds);
		if (sIds.length > 0) {
			for ( var i = 0; i < sIds.length; i++) {
				var ret = jQuery("#page6_jDataGrid1_table").jqGrid(
						'getRowData', sIds[i]);
				var tId = ret.tId;
				var isdelete = confirm("您确定要删除类型 (" + ret.tName + ")吗？");
				if (isdelete) {
					jQuery("#page6_jDataGrid1_table").jqGrid('setGridParam', {
						url : "MGoodsTypeServlet?type=delete&tId=" + tId,
						page : 1
					}).trigger("reloadGrid");
				}
			}
		} else {
			alert("未选择任何商品类型");
		}
	};
	function submitform() {
		document.getElementById("page6_jHtmlForm1").submit();
	}
</script>
</head>
<body class="body_style1">
	<div id="page6_jExtPanel1"
		class="Panelbar Panelbar_grain Panelbar_TT14">
		<div class="PanelBarCaptionbox colorp_grain2">
			<div class="PanelBarCaption border_grain01">
				<span>管理商品类型</span>
			</div>
		</div>
		<div class="PanelContent colorp_grain5 ">
			<div id="page6_jContainer1" class="tlayout_style2 tlayout_noborder">
				<div id="page6_jContainer1_container" class="container">
					<div id="page6_jRegion1" class="page6_jContainer1_north">
						<div id="page6_jToolbar1" class="TJToolbar">
							<div id="page6_jButton1"
								class="vjbutton vjbutton_c_style2 border_radius_3"
								onclick="opendilogs()">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img src="../images/jadd.png"
										width="16" height="16" /> </span><span class="btniconcum2"><img
										src="../images/jadd.png" width="16" height="16" /> </span><span
										class="btntxt"></span>增加
								</div>
							</div>
							<div id="page6_jButton3"
								class="vjbutton vjbutton_c_style2 border_radius_3"
								onclick="deleteuser()">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img
										src="../images/jdelete.png" width="16" height="16" /> </span><span
										class="btniconcum2"><img src="../images/jdelete.png"
										width="16" height="16" /> </span><span class="btntxt"></span>删除
								</div>
							</div>
							<div id="page6_jButton4" onclick="location.reload(false)"
								class="vjbutton vjbutton_c_style2 border_radius_3">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img
										src="../images/jarrow_rotate_clockwise.png" width="16"
										height="16" /> </span><span class="btniconcum2"><img
										src="../images/jarrow_rotate_clockwise.png" width="16"
										height="16" /> </span><span class="btntxt"></span>刷新
								</div>
							</div>
							<img class="TSeparator TSeparator_style1" id="page6_jSeparator1"
								src="../images/jToolsSeparator.png" />
						</div>
					</div>
					<div id="page6_jRegion2" class="page6_jContainer1_center">
						<div class="TDataGrid" id="page6_jDataGrid1">
							<table id="page6_jDataGrid1_table"></table>
							<div id="page6_jDataGrid1_pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="page6_jDialog1" title="添加商品类型">
		<%
			String info = request.getParameter("info");
			if ("successful".equals(info)) {
		%>
		<script type="text/javascript">
			alert("添加成功！");
		</script>
		<%
			} else if ("failed".equals(info)) {
		%>
		<script type="text/javascript">
			alert("添加失败了");
		</script>
		<%
			}
		%>
		<div id="page6_jHtmlForm1_form">
			<form id="page6_jHtmlForm1" name="page6_jHtmlForm1" method="post"
				action="MGoodsTypeServlet?type=add">
				<div id="page6_jLabel1" class="text">类型名称：</div>
				<input type="text" class="Edit Edit_style1" name="tName" value=""
					id="page6_jEdit1" />
				<div id="page6_jButton2" onclick="submitform()"
					class="vjbutton vjbutton_c_style1 border_radius_3">
					<div class="vjbutton_txtR">确定</div>
				</div>
				<div id="page6_jButton5"
					class="vjbutton vjbutton_c_style1 border_radius_3"
					onclick="closedilogs()">
					<div class="vjbutton_txtR">取消</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>
