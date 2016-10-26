<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>edit concert</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>来場情報削除</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		コンサート
		<s:property value="concertId" />
		に対する来場者
		<s:property value="listenerId" />
		を削除しました。

		</div>
		</div>
		</div>

	</body>
</html>
