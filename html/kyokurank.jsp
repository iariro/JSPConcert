<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>曲ランキング</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>曲ランキング</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>
			<tr>
				<th>曲目</th>
				<th>聞いた回数</th>
			</tr>
			<s:iterator value="ranking">
				<tr>
					<td><s:property value="string" /></td>
					<td align="right"><s:property value="number" /></td>
				</tr>
			</s:iterator>
		</table>

		</div>
		<div align="right">
		<s:property value="dateTime" />
		</div>

		</div>
		</div>

	</body>
</html>
