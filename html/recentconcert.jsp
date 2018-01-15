<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンサート情報</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>コンサート情報</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>直近の来場予定コンサート</h2>


		<s:iterator value="concerts">
		<div class=day>
		<h2>
			<span class=title>
			<s:property value="orchestra" />
			<s:property value="title" /> (<s:property value="id" />)</span>
		</h2>

		<div class=body2>
		<div class=section>

			<s:property value="date" /><br>
			<s:property value="hall" /><br>
			<s:property value="kaijou" />開場
			<s:property value="kaien" />開演<br>

			料金：<s:property value="ryoukin" />

			<ul>
				<s:iterator value="kyokumoku">
					<li><s:property />
				</s:iterator>
			</ul>

			<ul>
				<s:iterator value="shutsuen">
					<li><s:property />
				</s:iterator>
			</ul>

		</div>
		</div>
		</div>
		</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
