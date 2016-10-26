<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>ホール一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>ホール一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<s:property value="size" />件
		<div class=day>

		<table>
			<tr>
				<th>ID</th>
				<th>ホール名</th>
				<th>住所</th>
				<th>席数</th>
			</tr>
			<s:iterator value="halls">
				<tr>
					<td align="right"><s:property value="id" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="address" /></td>
					<td align="right"><s:property value="capacity" /></td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
