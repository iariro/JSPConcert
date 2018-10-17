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

		<h2>直近に登録したコンサート情報</h2>
		<s:iterator value="concerts">
			<h3><s:property value="key"/></h3>
			<table>
				<s:iterator value="value">
				<tr>
					<td><s:property value="date"/><br></td>
					<td><s:property value="orchestra"/></td>
					<td><s:property value="title"/></td>
					<td>
					<s:property value="lastNumber.composer"/>
					<s:property value="lastNumber.title"/>
					</td>
				</tr>
				</s:iterator>
			</table>
		</s:iterator>

		</div>
		</div>
		</div>

	</body>
</html>
