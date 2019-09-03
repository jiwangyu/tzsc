<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>jQuery入门</title>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			type : "post",
			url : "LoginServlet",
			data : "username=123&pwd=132",
			success : function(msg) {
				alert("data " + msg);
				//$("p").html(msg);
				$("p").html(msg);
			}
		});
	});
</script>

</head>

<body>
	<p></p>
</body>

</html>
