<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>edit concert</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>コンサート情報編集</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<s:property value="concertId" />
		に対する曲目
		<s:property value="composerId" />
		「<s:property value="title" />」
		を追加しました。

		</div>
		</div>
		</div>

	</body>
</html>
