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
		<div class=main>
		<div class=day>

		<div id="container" style="width:1300px; height:700px"></div>
		<script type="text/javascript">
		Highcharts.chart('container', {
		    chart: { type: 'columnrange', inverted: true },
		    title: { text: 'コンサート期間' },
		    yAxis: { title: { text: 'Date' }, type:'datetime' },
		    series:
		    [{
		        data:[ <s:property value="concertRanges" /> ]
		    }]
		});
		</script>

		</div>
		</div>
		</div>

	</body>
</html>
