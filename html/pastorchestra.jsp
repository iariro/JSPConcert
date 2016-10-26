<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>今後のコンサート情報がない楽団</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>今後のコンサート情報がない楽団</h1>
		<div class=hatena-body>
		<div class=main>

		<div class=day>

			<h2>コンサート情報がない楽団</h2>
			<s:property value="size1" />件あります。
			<table>
				<s:iterator value="pastOrchestraList1">
				<tr>
				<td>
					<s:if test="%{string2.length()>0}">
						<a href="<s:property value="string2" />">
					</s:if>
					<s:property value="string1" />
					<s:if  test="%{string2.length()>0}">
						</a>
					</s:if>
				</td>
				<td><s:property value="date" /></td>
				</tr>
				</s:iterator>
			</table>

			<h2>活動が見られない楽団</h2>
			<s:property value="size2" />件あります。
			<table>
				<s:iterator value="pastOrchestraList2">
				<tr>
				<td>
					<s:if test="%{string2.length()>0}">
						<a href="<s:property value="string2" />">
					</s:if>
					<s:property value="string1" />
					<s:if  test="%{string2.length()>0}">
						</a>
					</s:if>
				</td>
				<td><s:property value="date" /></td>
				</tr>
				</s:iterator>
			</table>

			<h2>活動中の楽団</h2>
			<s:property value="size3" />件あります。
			<table>
				<s:iterator value="pastOrchestraList3">
				<tr>
				<td>
					<s:if test="%{string2.length()>0}">
						<a href="<s:property value="string2" />">
					</s:if>
					<s:property value="string1" />
					<s:if  test="%{string2.length()>0}">
						</a>
					</s:if>
				</td>
				<td><s:property value="date" /></td>
				</tr>
				</s:iterator>
			</table>
		</div>

		</div>
		</div>

	</body>
</html>
