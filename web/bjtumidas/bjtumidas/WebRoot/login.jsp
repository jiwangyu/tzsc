<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String info = request.getParameter("info");
	String msg = "";
	if ("loginfailed".equals(info)) {
		msg = "用户名或密码错误";
	} else if ("unlogin".equals(info)) {
		msg = "系统检测到您还没有登录或长时间没有操作，请重新登录";
	} else if ("successful".equals(info)) {
		msg = "修改成功，请凭正确的登录信息登录";
	} else if ("failed".equals(info)) {
		msg = "警告！发生未知错误，请重新登录";
	} else if ("error".equals(info)) {
		msg = "警告！检测到您正在修改密码，两次密码不一致，请重新登录";
	} else if ("unagreement".equals(info)) {
		msg = "警告！检测到您正在修改密码，原密码有误，请重新登录";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link href="css/style1.css" type="text/css" rel="stylesheet"
	rev="stylesheet" />
<style type="text/css">
.unchanged {
	border: 0;
}
}
</style>
<script language="javascript" type="text/javascript">
	var code; //在全局 定义验证码   
	function createCode() {
		code = "";
		var codeLength = 4;//验证码的长度   
		var checkCode = document.getElementById("checkCode");
		var selectChar = new Array(3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z');//所有候选组成验证码的字符，当然也可以用中文的      
		for ( var i = 0; i < codeLength; i++) {
			var charIndex = Math.floor(Math.random() * 52);
			code += selectChar[charIndex];
		}
		if (checkCode) {
			checkCode.className = "code";
			checkCode.value = code;
			checkCode.blur();
		}
	}
	function validate() {
		var inputCode = document.getElementById("yzm").value;
		code = code.toUpperCase();
		if (inputCode.length <= 0) {
			alert("请输入验证码！");
		} else if (inputCode.toUpperCase() != code) {
			alert("验证码输入错误！");
			createCode();//刷新验证码   
		} else {
			document.getElementById("login").submit();
		}
	}
</script>
</head>
<body class="denglu02" onLoad="createCode()">
	<div class="dl">
		<div class="biaoti">
			<img src="css/images/ico02.png" style="width: 600px;height: 150px" />
		</div>
		<div class="log">
			<ul class="xuzhi02">
				<li class="xz">格言</li>
				<li>1.理想是人生的太阳。 —— 德莱赛</li>
				<li>2.真诚才是人生最高的美德。 —— 乔叟</li>
				<li>3.才能的火花，常常在勤奋的磨石上迸发。 —— 威廉</li>
				<li>4.把时间用在思考上是最能节省时间的事情。 —— 卡曾斯</li>
			</ul>
			<ul class="deng02">
				<form id="login" name="login" method="post"
					action="AdminLoginServlet">
					<font color="red"><%=msg%></font>
					<li style=" width:100%; height:60px;">
						<p
							style="float:left;font-size:18px; color:#666;line-height:30px; ">用户名:</p>
						<input id="username" name="username" class="i-text" type="text"
						maxlength="10">
					</li>
					<div style="clear:both;"></div>

					<li style=" width:100%; height:60px;">
						<p
							style="float:left;font-size:18px; color:#666;line-height:30px; ">密&nbsp;&nbsp;&nbsp;码:</p>
						<input id="pwd" name="pwd" class="i-text" type="password"
						maxlength="32">
					</li>
					<div style="clear:both;"></div>
					<li style=" width:100%; height:60px;">
						<p
							style="float:left;font-size:18px; color:#666;line-height:30px; ">验证码:</p>
						<input id="yzm" class="i-text1" type="text" maxlength="100">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="text" onClick="createCode()" readonly="readonly"
							id="checkCode" class="unchanged"
							style="font-size:20px;width: 55px;height:25px;cursor:pointer;background-image:url('images/yzm.png')" />
					</li>
					<li style=" width:100%; height:60px;">
						<button id="logonbtn" class="btn-login02" onclick="validate()"
							type="button">
							<span>登&nbsp;&nbsp;&nbsp;&nbsp;录</span>
						</button>
					</li>
				</form>
			</ul>

		</div>
	</div>
</body>
</html>
