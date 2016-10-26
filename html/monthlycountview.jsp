<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>月毎回数一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>月毎回数一覧</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<table>

			<tr>
				<th align="left">年</th>
				<s:iterator begin="1" end="12">
					<th><s:property />月</th>
				</s:iterator>
				<th>合計</th>
			</tr>
			<s:iterator value="monthCountCollection">
				<tr>
					<th><s:property value="year" /></th>
					<s:iterator value="count" var="c">
						<td align="right">
							<s:if test="#c>0">
								<s:property />
							</s:if>
						</td>
					</s:iterator>
					<td align="right"><s:property value="totalCount" /></th>
				</tr>
			</s:iterator>

		</table>

		</div>
		</div>
		</div>

	</body>
</html>
