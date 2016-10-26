<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>パート一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>パート一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>
			<s:iterator value="partList">
				<tr>
					<td><s:property value="string" /></td>
					<td align="right"><s:property value="number" />件</td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
