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
<title>主页</title>
<link href="../css/vjpublic.css" rel="stylesheet" type="text/css" />
<link href="../css/jqueryui.css" rel="stylesheet" type="text/css" />
<link href="../css/layout-default.css" rel="stylesheet" type="text/css" />
<link href="../css/tlayout.css" rel="stylesheet" type="text/css" />
<link href="../css/vjpage.css" rel="stylesheet" type="text/css" />
<link href="../css/config.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript" src="../layout/jquery.layout.js"></script>
<script type="text/javascript" src="../js/page2.js"></script>
<script language="javascript">
	//设置显示时间
	function realSysTime(clock) {
		var now = new Date(); //创建Date对象 
		var year = now.getFullYear(); //获取年份 
		var month = now.getMonth(); //获取月份 
		var date = now.getDate(); //获取日期 
		var day = now.getDay(); //获取星期 
		var hour = now.getHours(); //获取小时 
		var minu = now.getMinutes(); //获取分钟 
		var sec = now.getSeconds(); //获取秒钟 
		month = month + 1;
		var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六");
		var week = arr_week[day]; //获取中文的星期 
		var time = year + "年" + month + "月" + date + "日 " + week + " " + hour
				+ ":" + minu + ":" + sec; //组合系统时间 
		clock.innerHTML = time; //显示系统时间 
	}
	window.onload = function() {
		window.setInterval("realSysTime(clock)", 1000); //实时获取并显示系统时间 
	};
	function onsubmit1() {
		document.getElementById("page2_jHtmlForm1").submit();
	}
</script>
<script language="javascript">
	function CloseWebPage() {
		if (navigator.userAgent.indexOf("MSIE") > 0) {
			if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
				window.opener = null;
				window.close();
			} else {
				window.open('', '_top');
				window.top.close();
			}
		} else if (navigator.userAgent.indexOf("Firefox") > 0) {
			window.location.href = 'about:blank ';
		} else {
			window.opener = null;
			window.open('', '_self', '');
			window.close();
		}
	}
	function opendilogs() {
		$("#page2_jDialog1").dialog("open");//打开窗口
	}

	function closedilogs() {
		$("#page2_jDialog1").dialog("close");//关闭窗口
	}
</script>
<style type="text/css">
</style>
</head>
<body class="body_style1">
	<div id="page2_jContainer1" class="tlayout_style1">
		<div id="page2_jPanel1" class="Panel Panel_Null">
			<div id="page2_jLabel1" class="text">北交二手平台后台管理系统</div>
			<div id="page2_jPanel2" class="Panel Panel_Null">
				<div class="linkbox link_01" id="page2_jLink2"
					onclick="opendilogs()">
					<a href="#" target="">修改密码</a>
				</div>
				<div class="linkbox link_01" id="page2_jLink3">
					<a href="../login.jsp" target="_blank">重新登录</a>
				</div>
				<div class="linkbox link_01" id="page2_jLink4"
					onclick="CloseWebPage()">
					<a href="#" target="">退出</a>
				</div>
				<div id="page2_jLabel2" class="text">|</div>
				<div id="page2_jLabel4" class="text">|</div>
			</div>
		</div>
		<div id="page2_jPanel3" class="Panel Panel_Null colorp_grain3">
			<div id="page2_jLabel5" class="text">
				当前登录用户：<%=user%></div>
			<div id="page2_jLabel6" class="text">
				<label id="clock"></label>
			</div>

		</div>
		<div id="page2_jIframe1_div">
			<iframe id="page2_jIframe1" name="page2_jIframe1"
				src="ChartServlet?type=users" frameborder="0" scrolling="auto"
				width=100%; height=100%></iframe>
		</div>

		<div class="MenuV MenuV_style2" id="page2_jMenuV1">
			<ul>
				<li><a href="musers.jsp" target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管&nbsp;&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</a></li>
				<li class="has-sub"><a href="#"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管&nbsp;&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;商&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</a>
					<ul>
						<li><a href="mgoods.jsp" target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已&nbsp;&nbsp;&nbsp;发&nbsp;&nbsp;&nbsp;布&nbsp;&nbsp;&nbsp;商&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</a></li>
						<li class="last"><a href="mgoodstype.jsp"
							target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</a></li>
					</ul>
				</li>
				<li><a href="mcomment.jsp" target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管&nbsp;&nbsp;&nbsp;理&nbsp;&nbsp;&nbsp;评&nbsp;&nbsp;&nbsp;论&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</a></li>
				<li class="has-sub"><a href="#"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;看&nbsp;数&nbsp;据&nbsp;统&nbsp;计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</a>
					<ul>
						<li><a href="ChartServlet?type=users" target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</a></li>
						<li class="last"><a href="ChartServlet?type=goods"
							target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商&nbsp;&nbsp;&nbsp;品&nbsp;&nbsp;&nbsp;量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						</a></li>
					</ul>
				</li>
				<li class="last"><a href="VersionServlet?type=query"
					target="page2_jIframe1"><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版&nbsp;&nbsp;&nbsp;本&nbsp;&nbsp;&nbsp;控&nbsp;&nbsp;&nbsp;制&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				</a></li>
			</ul>
		</div>
	</div>
	<div id="page2_jDialog1" title="修改密码">
		<div id="page2_jHtmlForm1_form">
			<form id="page2_jHtmlForm1" name="page2_jHtmlForm1" method="post"
				action="ModifyAdminPwdServlet">
				<div id="page2_jLabel3" class="text">旧密码：</div>
				<input type="password" class="Edit Edit_style4" value=""
					id="page2_jEdit1" name="oldPwd" />
				<div id="page2_jLabel8" class="text">新密码：</div>
				<input type="password" name="newPwd" class="Edit Edit_style4"
					value="" id="page2_jEdit2" />
				<div id="page2_jLabel9" class="text">确认新密码：</div>
				<input type="password" name="rePwd" class="Edit Edit_style4"
					value="" id="page2_jEdit3" />
				<div id="page2_jButton1" onclick="onsubmit1()"
					class="vjbutton vjbutton_c_style1 border_radius_3">
					<div class="vjbutton_txtR">确定</div>
				</div>
				<div id="page2_jButton2"
					class="vjbutton vjbutton_c_style1 border_radius_3"
					onclick="closedilogs()">
					<div class="vjbutton_txtR">取消</div>
				</div>
			</form>
		</div>
	</div>

</body>
<script type="text/javascript" src="../js/TmenuV.js"></script>
</html>
