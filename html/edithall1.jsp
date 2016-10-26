<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>ホール情報編集</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>ホール情報編集</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2><span class=title>ホールID=<s:property value="hallId" /></span></h2>

		<div class=body>
			<s:form action="edithall2" theme="simple">
				<input type="hidden" name="hallId" value="<s:property value="hallId" />">

				名前：
				<input name="name" value="<s:property value="name" />" size="50"><br>

				住所：
				<input name="address" value="<s:property value="address" />" size="50"><br>

				席数：
				<input name="capacity" value="<s:property value="capacity" />"><br>
				<s:submit value="更新" />
			</s:form>
		</div>

		</div>
		</div>
		</div>

	</body>
</html>
