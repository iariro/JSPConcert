<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>コンサート情報検索</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>コンサート情報検索</h1>

		<div class=hatena-body>
		<div class=main>

		<s:form action="concertsearch2" theme="simple">
			<table cellpadding="5">
				<tr bgcolor="#eeeeff"><td>演奏者</td><td><input type="text" name="player"></td></tr>
				<tr bgcolor="#eeeeff"><td>作曲家</td><td><input type="text" name="composer"></td></tr>
				<tr bgcolor="#eeeeff"><td>曲名</td><td><input type="text" name="title"></td></tr>
				<tr bgcolor="#eeeeff"><td>ホール名</td><td><input type="text" name="hall"></td></tr>
				<tr bgcolor="#eeeeff"><td>ホール住所</td><td><input type="text" name="address"></td></tr>

				<tr bgcolor="#eeeeff"><td>コンサート日付範囲</td>
				<td><input type="text" name="startDate" size="15" value="<s:property value="startDay" />" >
				～
				<input type="text" name="endDate" size="15" value="<s:property value="endDay" />">
				</td></tr>
				<tr bgcolor="#eeeeff">
				<td>来場者</td>
				<td>
					<s:select name="listenerId" list="listeners" listKey="string2" listValue="string1" />
				</td>
				</tr>
				<tr bgcolor="#eeeeff">
				<td>作曲家ランク</td>
				<td>
					<s:select name="composerRank" list="#{ '1':'絞り込みなし','2':'まあまあ興味あり以上','3':'興味ありのみ' }"/>
				</td>
				</tr>
				<tr><td colspan="2" align="center"><s:submit value="検索" /></td></tr>
			</table>
		</s:form>

		</div>
		</div>

	</body>
</html>
