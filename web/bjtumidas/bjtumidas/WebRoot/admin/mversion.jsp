<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int vCode = (Integer) request.getAttribute("vCode");
	String vURL = (String) request.getAttribute("vURL");
	String vTime = (String) request.getAttribute("vTime");
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
<title>版本控制</title>
<link href="../css/vjpublic.css" rel="stylesheet" type="text/css" />
<link href="../css/jqueryui.css" rel="stylesheet" type="text/css" />
<link href="../css/vjpage.css" rel="stylesheet" type="text/css" />
<link href="../css/config.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../js/page10.js"></script>
<script language="javascript">
	function opendilogs() {
		$("#page10_jDialog1").dialog("open");//打开窗口
	}
	function closedilogs() {
		$("#page10_jDialog1").dialog("close");//关闭窗口
	}
	function onsubmit1() {
		document.getElementById("page10_jHtmlForm1").submit();
	}
</script>
</head>
<body class="body_style1">
	<div id="page3_jButton4" onclick="location.reload(false)"
		class="vjbutton vjbutton_c_style2 border_radius_3">
		<div class="vjbutton_txtR">
			<span class="btniconcum1"><img
				src="../images/jarrow_rotate_clockwise.png" width="16" height="16" />
			</span><span class="btniconcum2"><img
				src="../images/jarrow_rotate_clockwise.png" width="16" height="16" />
			</span><span class="btntxt"></span>刷新
		</div>
	</div>
	<div id="page10_jLabel1" class="text">客户端版本控制</div>
	<div class="fieldsetbox fieldset_01" id="page10_jGroupBox1">
		<fieldset style="height:142px">
			<legend>当前版本信息：</legend>
			<div class="fieldset_padding"></div>
			<div>
				<div style="position:relative">
					<div id="page10_jLabel2" class="text">版本号：</div>
					<div id="page10_jLabel3" class="text">下载地址：</div>
					<div id="page10_jLabel4" class="text">更新时间：</div>
					<div id="page10_jLabel5" class="text"><%=vCode%></div>
					<div class="linkbox link_01" id="page10_jLink1">
						<a href="<%=vURL%>" target="_blank"><%=vURL%></a>
					</div>
					<div id="page10_jLabel6" class="text"><%=vTime%></div>
				</div>
			</div>
		</fieldset>

	</div>
	<div id="page10_jDialog1" title="更新客户端版本">
		<%
			String info = (String) request.getAttribute("info");
			if ("successful".equals(info)) {
		%>
		<script type="text/javascript">
			alert("更新成功");
		</script>
		<%
			} else if ("query".equals(info)) {

			} else {
		%>
		<script type="text/javascript">
			alert("更新失败,请查看版本号是否比当前大");
		</script>
		<%
			}
		%>
		<div id="page10_jButton2" onclick="onsubmit1()"
			class="vjbutton vjbutton_c_style1 border_radius_3">
			<div class="vjbutton_txtR">确定</div>
		</div>
		<div id="page10_jButton3"
			class="vjbutton vjbutton_c_style1 border_radius_3"
			onclick="closedilogs()">
			<div class="vjbutton_txtR">取消</div>
		</div>
		<div id="page10_jHtmlForm1_form">
			<form id="page10_jHtmlForm1" name="page10_jHtmlForm1" method="post"
				action="VersionServlet?type=upload" enctype="multipart/form-data">
				版本号：<input type="text" name="vCode"> </br> 选择上传的apk:<input
					type="file" name="apk">
			</form>
		</div>
	</div>
	<div id="page10_jButton1"
		class="vjbutton vjbutton_c_style1 border_radius_3"
		onclick="opendilogs() ">
		<div class="vjbutton_txtR">更新</div>
	</div>

</body>
</html>
