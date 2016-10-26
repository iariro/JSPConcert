<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>演奏者一覧</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>演奏者一覧</h1>

		<div class=hatena-body>
		<div class=main>

		<s:property value="size" />件
		<div class=day>
		<table>

			<s:iterator value="playerList">
				<tr>
				<td>
					<s:if test="%{siteurl.length()>0}">
						<a href="<s:property value="siteurl" /> ">
					</s:if>
					<s:property value="name" />
					<s:if test="%{siteurl.length>0}">
						</a>
					</s:if>
				</td>
				<td>
					<s:form action="editplayer1" theme="simple">
						<input type="hidden" name="id" value="<s:property value="id" />">
						<input type="hidden" name="name" value="<s:property value="name" />">
						<input type="hidden" name="siteurl" value="<s:property value="siteurl" />">
						<input type="hidden" name="active" value="<s:property value="active" />">
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
