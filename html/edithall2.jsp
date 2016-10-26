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

		以下のように編集しました。<br>
		<br>
		ホールID：<s:property value="hallId" /><br>
		ホール名：<s:property value="name" /><br>
		住所：<s:property value="address" /><br>
		席数：<s:property value="capacity" /><br>

		</div>
		</div>
		</div>

	</body>
</html>
