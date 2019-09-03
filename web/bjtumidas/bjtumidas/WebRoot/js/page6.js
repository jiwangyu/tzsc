$(document).ready(function() {

	page6_jContainer1_DataGrids = new Array("page6_jDataGrid1", "49");
	page6_jContainer1_obj = $('#page6_jContainer1_container').layout({
		onresize : function() {
			Vjjq.resizegrid(page6_jContainer1_DataGrids);
		},
		center__paneSelector : '.page6_jContainer1_center',
		north__paneSelector : '.page6_jContainer1_north',
		north__size : 31,
		north__spacing_open : 0
	});
});

jQuery().ready(function() {
	jQuery('#page6_jDataGrid1_table').jqGrid({
		datatype : 'json',
		mtype : 'post',
		url : 'MGoodsTypeServlet?type=query',
		multiselect : true,
		viewrecords : true,
		colNames : [ '类型编号', '类型名' ],
		colModel : [ {
			name : 'tId',
			index : 'tId',
			align : "center",
			width : 64
		}, {
			name : 'tName',
			index : 'tName',
			align : "center",
			width : 700
		} ],
		width : '100%',
		height : '100%',
		rowNum : 20,
		pager : jQuery('#page6_jDataGrid1_pager')
	}).navGrid('#page6_jDataGrid1_pager', {
		edit : false,
		add : false,
		del : false
	});
	$('#page6_jDataGrid1_table').closest('.ui-jqgrid-bdiv').css({
		'overflow-y' : 'auto'
	});
	$('#page6_jDataGrid1_table').trigger('reloadGrid');
})

$(function() {
	$("#page6_jDialog1").dialog({
		autoOpen : false,
		height : 202,
		width : 345,
		resizable : false
	});
});

$(document).ready(function() {
	page6_jContainer1_obj.resizeAll();
});
