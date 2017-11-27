<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>更新日ごとの登録件数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>更新日ごとの登録件数</h1>

		<div class=hatena-body>
		<div class=main>

			<table>
				<tr><th>登録日</th><th>件数</th></tr>
				<s:iterator value="concertCount">
					<tr>
						<td><s:property value="key" /></td>
						<td><s:property value="value" /></td>
					</tr>
				</s:iterator>
			</table>

		</div>
		</div>

	</body>
</html>
