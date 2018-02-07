<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv=Content-Style-Type content=text/css>
		<title>concert</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<script type="text/javascript" src="http://code.highcharts.com/highcharts-more.js"></script>
	</head>

	<body>

		<div class=hatena-body>

		<div id="container" style="width:1300px; height:700px"></div>
		<script type="text/javascript">
		function dateFormat(date)
		{
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();

			if (m < 10)
			{
				m = '0' + m;
			}
			if (d < 10)
			{
				d = '0' + d;
			}

			return y + '/' + m + '/' + d;
		}

		Highcharts.chart('container', {
		    chart: { type: 'columnrange', inverted: true },
		    title: { text: 'コンサート期間' },
		    yAxis: { title: { text: 'Date' }, type:'datetime' },
		    tooltip: { formatter: function () { return '<b>' + this.point.name + '</b><br/>' + dateFormat(new Date(this.point.low)) + ' - ' + dateFormat(new Date(this.point.high));}},
		    series:
		    [{
		        data:[ <s:property value="concertRanges" /> ]
		    }]
		});
		</script>

		</div>

	</body>
</html>
