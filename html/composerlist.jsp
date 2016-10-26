<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>作曲家一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>作曲家一覧</h1>

		<div class=hatena-body>
		<div class=main>

		<s:property value="size" />件
		<div class=day>
		<table>

			<tr>
				<th>ID</th>
				<th>名前</th>
				<th>カウント</th>
			</tr>
			<s:iterator value="composers">
				<tr>
					<td align="right"><s:property value="id" /></td>
					<td><s:property value="name" /></td>
					<td align="right"><s:property value="count" /></td>
				</tr>
			</s:iterator>

		</table>
		</div>

		</div>
		</div>

	</body>
</html>
