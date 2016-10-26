<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>UMNチェック</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>UMNチェック</h1>

		<div class=hatena-body>
		<div class=main>

		「<s:property value="umnFilePath" />」とDBを比較<br>
		<s:property value="result.size" />件
		<div class=day>

		<h2>URL違い</h2>
		<ul>
			<s:iterator value="result.diffUrl">
				<li><s:property />
			</s:iterator>
		</ul>

		<h2>DBにURLなし</h2>
		<ul>
			<s:iterator value="result.dbNoUrl">
				<li><s:property />
			</s:iterator>
		</ul>

		<h2>UMNにエントリなし</h2>
		<ul>
			<s:iterator value="result.noUmn">
				<li><s:property />
			</s:iterator>
		</ul>

		<h2>DBにエントリなし</h2>
		<ul>
			<s:iterator value="result.noDb">
				<li><s:property />
			</s:iterator>
		</ul>

		<h2>１年以上アクセスなし</h2>
		<s:if test="%{oldCount>0}">
			<table>
				<tr><th>フォルダ</th><th>アクセス日時</th><th>タイトル</th></tr>
				<s:iterator value="result.old">
					<tr>
						<td><s:property value="folderPath" /></td>
						<td><s:property value="watch" /></td>
						<td><s:property value="title" /></td>
					</tr>
				</s:iterator>
			</table>
		</s:if>

		</div>

		</div>
		</div>

	</body>
</html>
