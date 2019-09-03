$(document).ready(function(){
	
	var page8_jChart1_chart = echarts.init(document.getElementById('page8_jChart1'));
	page8_jChart1_chart.setOption({
		legend: {
			data:['人数']
		},
		xAxis: [
			{
				type: 'category',
				data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			}
		],
		yAxis: [
			{
				type: 'value'
			}
		],
		series : [
			{
				name:'人数',
				type:'line',
				data:[267, 306, 220, 330, 360, 0, 0, 0, 0, 0, 0, 0]
			}
		]
	});
	
	var page8_jChart2_chart = echarts.init(document.getElementById('page8_jChart2'));
	page8_jChart2_chart.setOption({
		legend: {
			data:['男','女']
		},
		xAxis: [
			{
				type: 'category',
				data: ['2015级','2016级','2017级','2018级']
			}
		],
		yAxis: [
			{
				type: 'value'
			}
		],
		series : [
			{
				name:'男',
				type:'bar',
				data:[220, 330, 360, 23]
			},
			{
				name:'女',
				type:'bar',
				data:[144, 122, 211, 144]
			}
		]
	});
	
	var page8_jChart3_chart = echarts.init(document.getElementById('page8_jChart3'));
	page8_jChart3_chart.setOption({
		legend: {
			data:['总人数']
		},
		series : [
			{
				type:'pie',
				radius: '55%',
				center: ['50%', '60%'],
				data:[{value:306, name:'2015级'},
					{value:220, name:'2016级'},
					{value:330, name:'2017级'},
					{value:360, name:'2018级'}
				]
			}
		]
	});
});
