<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>演奏者ランキング</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>演奏者ランキング</h1>

		<div class=hatena-body>
		<div class=main>

		<s:property value="size" />件
		<div class=day>

		<table>
			<s:iterator value="ranking">
				<tr>
					<td><s:property value="string" /></td>
					<td align="right"><s:property value="number" /></td>
				</tr>
			</s:iterator>
		</table>

		</div>

		</div>
		</div>

	</body>
</html>
