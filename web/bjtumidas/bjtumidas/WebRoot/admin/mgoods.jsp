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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>已发布</title>
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
<script type="text/javascript" src="../js/page4.js"></script>
<script language="javascript">
	function onsearch() {
		//按条件进行查询
		var select = jQuery("#page4_jEdit1").val();
		jQuery("#page4_jDataGrid1_table").jqGrid('setGridParam', {
			url : "MGoodsServlet?type=querywhere&select=" + select,
			page : 1
		}).trigger("reloadGrid");
	}
	function deleteuser() {
		//删除用户
		var sIds = $("#page4_jDataGrid1_table").jqGrid("getGridParam",
				"selarrrow");
		//alert(sIds);
		if (sIds.length > 0) {
			for ( var i = 0; i < sIds.length; i++) {
				var ret = jQuery("#page4_jDataGrid1_table").jqGrid(
						'getRowData', sIds[i]);
				var gId = ret.gId;
				var isdelete = confirm("您确定要删除用户 " + ret.uInfo + " 的商品  ("
						+ ret.gTitle + ")吗？");
				if (isdelete) {
					jQuery("#page4_jDataGrid1_table").jqGrid('setGridParam', {
						url : "MGoodsServlet?type=delete&gId=" + gId,
						page : 1
					}).trigger("reloadGrid");
				}
			}
		} else {
			alert("未选择任何商品");
		}
	};
</script>
</head>
<body class="body_style1">
	<div id="page4_jExtPanel1"
		class="Panelbar Panelbar_grain Panelbar_TT14">
		<div class="PanelBarCaptionbox colorp_grain2">
			<div class="PanelBarCaption border_grain01">
				<span>管理已发布商品</span>
			</div>
		</div>
		<div class="PanelContent colorp_grain5 ">
			<div id="page4_jContainer1" class="tlayout_style2 tlayout_noborder">
				<div id="page4_jContainer1_container" class="container">
					<div id="page4_jRegion1" class="page4_jContainer1_north">
						<div id="page4_jToolbar1" class="TJToolbar">
							<div id="page4_jButton3"
								class="vjbutton vjbutton_c_style2 border_radius_3"
								onclick="deleteuser()">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img
										src="../images/jdelete.png" width="16" height="16" /> </span><span
										class="btniconcum2"><img src="../images/jdelete.png"
										width="16" height="16" /> </span><span class="btntxt"></span>删除
								</div>
							</div>
							<div id="page4_jButton4" onclick="location.reload(false)"
								class="vjbutton vjbutton_c_style2 border_radius_3">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img
										src="../images/jarrow_rotate_clockwise.png" width="16"
										height="16" /> </span><span class="btniconcum2"><img
										src="../images/jarrow_rotate_clockwise.png" width="16"
										height="16" /> </span><span class="btntxt"></span>刷新
								</div>
							</div>
							<img class="TSeparator TSeparator_style1" id="page4_jSeparator1"
								src="../images/jToolsSeparator.png" />
							<div id="page4_jLabel1" class="text">关键字：</div>
							<input type="text" class="Edit Edit_style1" value=""
								id="page4_jEdit1" />
							<div id="page4_jButton5" onclick="onsearch()"
								class="vjbutton vjbutton_c_style2 border_radius_3">
								<div class="vjbutton_txtR">
									<span class="btniconcum1"><img src="../images/jzoom.png"
										width="16" height="16" /> </span><span class="btniconcum2"><img
										src="../images/jzoom.png" width="16" height="16" /> </span><span
										class="btntxt"></span>查询
								</div>
							</div>
							<img class="TSeparator TSeparator_style1" id="page4_jSeparator2"
								src="../images/jToolsSeparator.png" />
						</div>
					</div>
					<div id="page4_jRegion2" class="page4_jContainer1_center">
						<div class="TDataGrid" id="page4_jDataGrid1">
							<table id="page4_jDataGrid1_table"></table>
							<div id="page4_jDataGrid1_pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
