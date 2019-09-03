<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int charts[][] = (int[][]) request.getAttribute("charts");
	int grades[][] = (int[][]) request.getAttribute("grades");
	String user = null;
	try {
		user = session.getAttribute("user").toString();
	} catch (Exception e) {
				response.sendRedirect("../login.jsp?info=unlogin");
	}
	//for (int i = 0; i < charts.length; i++) {
	//	for (int j = 0; j < charts[i].length; j++) {
	//		System.out.print(charts[i][j] + "\t");
	//	}
	//	System.out.println();
	//}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商品量</title>
<link href="../css/vjpublic.css" rel="stylesheet" type="text/css" />
<link href="../css/jqueryui.css" rel="stylesheet" type="text/css" />
<link href="../css/chart.css" rel="stylesheet" type="text/css" />
<link href="../css/vjpage.css" rel="stylesheet" type="text/css" />
<link href="../css/config.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				var page9_jChart1_chart = echarts.init(document
						.getElementById('page9_jChart1'));
				page9_jChart1_chart.setOption({
					legend : {
						data : [ '数量' ]
					},
					tooltip : {
						trigger : 'axis'
					},
					xAxis : [ {
						type : 'category',
						boundaryGap : false,
						data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月',
								'8月', '9月', '10月', '11月', '12月' ]
					} ],
					yAxis : [ {
						type : 'value',
						axisLabel : {
							formatter : '{value} 件'
						}
					} ],
					series : [ {
						name : '数量',
						type : 'line',
						data : [
<%=charts[0][1] + charts[0][2]%>
	,
<%=charts[1][1] + charts[1][2]%>
	,
<%=charts[2][1] + charts[2][2]%>
	,
<%=charts[3][1] + charts[3][2]%>
	,
<%=charts[4][1] + charts[4][2]%>
	,
<%=charts[5][1] + charts[5][2]%>
	,
<%=charts[6][1] + charts[6][2]%>
	,
<%=charts[7][1] + charts[7][2]%>
	,
<%=charts[8][1] + charts[8][2]%>
	,
<%=charts[9][1] + charts[9][2]%>
	,
<%=charts[10][1] + charts[10][2]%>
	,
<%=charts[11][1] + charts[11][2]%>
	],
						markPoint : {
							data : [ {
								type : 'max',
								name : '最大值'
							}, {
								type : 'min',
								name : '最小值'
							} ]
						}
					} ]

				});

				var page9_jChart2_chart = echarts.init(document
						.getElementById('page9_jChart2'));
				page9_jChart2_chart.setOption({
					legend : {
						orient : 'vertical',
						left : 'left',
						data : [ '男', '女' ]
					},
					tooltip : {
						trigger : 'axis',
						axisPointer : { // 坐标轴指示器，坐标轴触发有效
							type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					xAxis : [ {
						type : 'category',
						data : [ '2012级', '2013级', '2014级', '2015级' ]
					} ],
					yAxis : [ {
						type : 'value',
						axisLabel : {
							formatter : '{value} 件'
						}
					} ],
					series : [
							{
								name : '男',
								type : 'bar',
								data : [
<%=grades[0][2]%>
	,
<%=grades[1][2]%>
	,
<%=grades[2][2]%>
	,
<%=grades[3][2]%>
	]
							},
							{
								name : '女',
								type : 'bar',
								data : [
<%=grades[0][1]%>
	,
<%=grades[1][1]%>
	,
<%=grades[2][1]%>
	,
<%=grades[3][1]%>
	]
							} ]
				});

				var page9_jChart3_chart = echarts.init(document
						.getElementById('page9_jChart3'));
				page9_jChart3_chart.setOption({
					legend : {
						orient : 'vertical',
						left : 'left',
						data : [ '总件数' ]
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} 件({d}%)"
					},
					series : [ {
						name : '总件数',
						type : 'pie',
						radius : '55%',
						center : [ '50%', '60%' ],
						data : [ {
							value :
<%=grades[0][1] + grades[0][2]%>
	,
							name : '2012级'
						}, {
							value :
<%=grades[1][1] + grades[1][2]%>
	,
							name : '2013级'
						}, {
							value :
<%=grades[2][1] + grades[2][2]%>
	,
							name : '2014级'
						}, {
							value :
<%=grades[3][1] + grades[3][2]%>
	,
							name : '2015级'
						} ],
						itemStyle : {
							emphasis : {
								shadowBlur : 10,
								shadowOffsetX : 0,
								shadowColor : 'rgba(0, 0, 0, 0.5)'
							}
						}
					} ]
				});
			});
</script>
</head>
<body class="body_style1" onload="onloaddata()">
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
	<div id="page9_jChart1" class="TJChart"></div>
	<div id="page9_jChart2" class="TJChart"></div>
	<div id="page9_jChart3" class="TJChart"></div>
	<div id="page9_jLabel1" class="text">商品增长趋势图</div>
	<div id="page9_jLabel2" class="text">男女占比图</div>
	<div id="page9_jLabel3" class="text">年级占比图</div>
	<div id="page9_jLabel4" class="text">其他统计待续......</div>

	<script src="../js/echarts-all.js"></script>
</body>
</html>

