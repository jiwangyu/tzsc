$(document).ready(function() {

	page4_jContainer1_DataGrids = new Array("page4_jDataGrid1", "49");
	page4_jContainer1_obj = $('#page4_jContainer1_container').layout({
		onresize : function() {
			Vjjq.resizegrid(page4_jContainer1_DataGrids);
		},
		center__paneSelector : '.page4_jContainer1_center',
		north__paneSelector : '.page4_jContainer1_north',
		north__size : 31,
		north__spacing_open : 0
	});
});

jQuery()
		.ready(
				function() {
					jQuery('#page4_jDataGrid1_table')
							.jqGrid(
									{
										datatype : 'json',
										mtype : 'post',
										url : 'MGoodsServlet?type=query',
										multiselect : true,
										viewrecords : true,
										grouping : true,
										groupingView : {
											groupField : [ 'uInfo' ],
											groupColumnShow : [ true ],
											groupText : [ '<b><font color="#0000FF" size="3px">{0}                总数：{gMaxPrice}件</font></b>' ],
											groupCollapse : false,
											groupOrder : [ 'asc' ],
											groupSummary : [ true ],
											groupDataSorted : true
										},
										colNames : [ '编号', '卖家', '商品标题',
												'商品描述', '价格', '发布时间', '浏览量',
												'商品类型', '发布类型', '当前最高的出价' ],
										colModel : [
												{
													name : 'gId',
													index : 'gId',
													align : "center",
													width : 33
												},
												{
													name : 'uInfo',
													align : "center",
													index : 'uInfo',
													width : 100
												},
												{
													name : 'gTitle',
													align : "center",
													index : 'gTitle',
													width : 132
												},
												{
													name : 'gDesc',
													index : 'gDesc',
													align : "center",
													width : 177
												},
												{
													name : 'gPrice',
													index : 'gPrice',
													align : "center",
													width : 64
												},
												{
													name : 'gTime',
													index : 'gTime',
													align : "center",
													width : 64
												},
												{
													name : 'gBrowCount',
													index : 'gBrowCount',
													align : "center",
													width : 64
												},
												{
													name : 'gType',
													index : 'gType',
													align : "center",
													width : 64
												},
												{
													name : 'gPublishType',
													index : 'gPublishType',
													align : "center",
													width : 64
												},
												{
													name : 'gMaxPrice',
													index : 'gMaxPrice',
													align : "center",
													summaryType : 'count',
													summaryTpl : '<b><font color="#0000FF">小计:{0} 件</font></b>',
													width : 88
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
										rowNum : 1000,
										pager : jQuery('#page4_jDataGrid1_pager')
									}).navGrid('#page4_jDataGrid1_pager', {
								edit : false,
								add : false,
								del : false
							});
					$('#page4_jDataGrid1_table').closest('.ui-jqgrid-bdiv')
							.css({
								'overflow-y' : 'auto'
							});
					$('#page4_jDataGrid1_table').trigger('reloadGrid');
				})

$(document).ready(function() {
	page4_jContainer1_obj.resizeAll();
});
