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

		<h2><span class=title>演奏者ID=<s:property value="id" /></span></h2>

		<div class=body>
			<s:form action="editplayer2" theme="simple">
				<input type="hidden" name="id" value="<s:property value="id" />">

				名前：
				<input name="name" value="<s:property value="name" />" size="50"><br>

				URL：
				<input name="siteurl" value="<s:property value="siteurl" />" size="50"><br>
				
				有効・無効：
				<s:checkbox name="active" label="有効・無効" />

				<s:submit value="更新" />
			</s:form>
		</div>

		</div>
		</div>
		</div>

	</body>
</html>
