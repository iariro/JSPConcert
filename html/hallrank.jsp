<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>ホールランキング</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>ホールランキング</h1>

		<div class=hatena-body>
		<div class=main>
		<s:property value="size" />件
		<div class=day>

		<table>
			<tr>
				<th>ホール名</th>
				<th>回数</th>
				<th>席数</th>
				<th>編集</th>
			</tr>
			<s:iterator value="ranking">
				<tr>
					<td><s:property value="name" /></td>
					<td align="right"><s:property value="count" /></td>
					<td align="right"><s:property value="capacity" /></td>
					<td>
					<s:form action="edithall1" theme="simple">
						<input type="hidden" name="hallId" value="<s:property value="id" />">
						<s:submit value="変更" />
					</s:form>
					</td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
