$(document).ready(function() {

	page7_jContainer1_DataGrids = new Array("page7_jDataGrid1", "49");
	page7_jContainer1_obj = $('#page7_jContainer1_container').layout({
		onresize : function() {
			Vjjq.resizegrid(page7_jContainer1_DataGrids);
		},
		center__paneSelector : '.page7_jContainer1_center',
		north__paneSelector : '.page7_jContainer1_north',
		north__size : 31,
		north__spacing_open : 0
	});
});

jQuery()
		.ready(
				function() {
					jQuery('#page7_jDataGrid1_table')
							.jqGrid(
									{
										datatype : 'json',
										mtype : 'post',
										url : 'MCommentServlet?type=query',
										multiselect : true,
										viewrecords : true,
										grouping : true,
										groupingView : {
											groupField : [ 'gId' ],
											groupColumnShow : [ true ],
											groupText : [ '<b><font color="#0000FF" size="3px">{0}                总数：{cContent}条</font></b>' ],
											groupCollapse : false,
											groupOrder : [ 'asc' ],
											groupSummary : [ true ],
											groupDataSorted : true
										},
										colNames : [ '编号', '商品编号', '评论时间',
												'评论人', '评论内容' ],
										colModel : [
												{
													name : 'cId',
													index : 'cId',
													align : "center",
													width : 48
												},
												{
													name : 'gId',
													index : 'gId',
													align : "center",
													width : 64
												},
												{
													name : 'cTime',
													index : 'cTime',
													align : "center",
													width : 64
												},
												{
													name : 'uInfo',
													index : 'uInfo',
													align : "center",
													width : 64
												},
												{
													name : 'cContent',
													index : 'cContent',
													align : "center",
													summaryType : 'count',
													summaryTpl : '<b><font color="#0000FF">小计:{0} 条</font></b>',
													width : 575
												} ],
										width : '100%',
										height : '100%',
										rowNum : 20,
										pager : jQuery('#page7_jDataGrid1_pager')
									}).navGrid('#page7_jDataGrid1_pager', {
								edit : false,
								add : false,
								del : false
							});

					$('#page7_jDataGrid1_table').trigger('reloadGrid');
				})

$(document).ready(function() {
	page7_jContainer1_obj.resizeAll();
});
