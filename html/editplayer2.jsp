<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>演奏者情報編集</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>演奏者情報編集</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		以下のように編集しました。<br>
		<br>
		プレイヤーID：<s:property value="id" /><br>
		演奏者名：<s:property value="name" /><br>
		URL：<s:property value="siteurl" /><br>
		有効・無効：<s:property value="active" /><br>

		</div>
		</div>
		</div>

	</body>
</html>
