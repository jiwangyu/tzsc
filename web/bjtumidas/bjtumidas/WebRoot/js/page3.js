$(document).ready(function() {

	page3_jContainer1_DataGrids = new Array("page3_jDataGrid1", "49");
	page3_jContainer1_obj = $('#page3_jContainer1_container').layout({
		onresize : function() {
			Vjjq.resizegrid(page3_jContainer1_DataGrids);
		},
		center__paneSelector : '.page3_jContainer1_center',
		north__paneSelector : '.page3_jContainer1_north',
		north__size : 31,
		north__spacing_open : 0
	});
});

jQuery()
		.ready(
				function() {
					jQuery('#page3_jDataGrid1_table')
							.jqGrid(
									{
										datatype : 'json',
										mtype : 'post',
										url : 'UsersServlet?type=query',
										multiselect : true,
										viewrecords : true,
										grouping : true,
										groupingView : {
											groupField : [ 'uGrade' ],
											groupColumnShow : [ true ],
											groupText : [ '<b><font color="#0000FF" size="3px">{0}                人数：{uTime}</font></b>' ],
											groupCollapse : false,
											groupOrder : [ 'asc' ],
											groupSummary : [ true ],
											groupDataSorted : true
										},
										colNames : [ '编号', "年级", '昵称', '学号',
												'手机号', '性别', '注册时间' ],
										colModel : [
												{
													name : 'uId',
													index : 'uId',
													align : "center",
													width : 32
												},
												{
													name : 'uGrade',
													index : 'uGrade',
													align : "center",
													width : 60
												},
												{
													name : 'uNickName',
													index : 'uNickName',
													align : "center",
													width : 258
												},
												{
													name : 'uNo',
													index : 'uNO',
													align : "center",
													width : 138
												},
												{
													name : 'uPhone',
													index : 'uPhone',
													align : "center",
													width : 140

												},
												{
													name : 'uSex',
													index : 'uSex',
													align : "center",
													width : 49
												},
												{
													name : 'uTime',
													index : 'uTime',
													align : "center",
													summaryType : 'count',
													summaryTpl : '<b><font color="#0000FF">小计:{0} 人</font></b>',
													width : 300
												} ],
										loadComplete : function(xhr) {
											var rowNum = parseInt($(this)
													.getGridParam("records"),
													10);
											if (rowNum <= 0) {
												alert("无记录！");
											}
										},
										width : '100%',
										height : '100%',
										rowNum : 10000,
										pager : jQuery('#page3_jDataGrid1_pager')
									}).navGrid('#page3_jDataGrid1_pager', {
								edit : false,
								add : false,
								del : false
							});
					$('#page3_jDataGrid1_table').closest('.ui-jqgrid-bdiv')
							.css({
								'overflow-y' : 'auto'
							});
					$('#page3_jDataGrid1_table').trigger('reloadGrid');
				})

$(function() {
	$("#page3_jDialog1").dialog({
		autoOpen : false,
		height : 202,
		width : 346,
		resizable : false
	});
});

$(document).ready(function() {
	page3_jContainer1_obj.resizeAll();
});
