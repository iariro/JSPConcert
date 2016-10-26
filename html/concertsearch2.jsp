<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>コンサート検索</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

	<h1>コンサート検索</h1>
	<div class=hatena-body>
	<div class=main>

	<s:property value="size" /> ／
	<s:property value="originalSize" />件です。

	<s:iterator value="concertCollection">
		<div class=day>
		<h2>
			<span class=title>
			<s:property value="orchestra" />
			<s:property value="title" /> (<s:property value="id" />)</span>
		</h2>

		<s:if test="%{listen}">
			<div class=body2>
		</s:if>
		<s:else>
			<div class=body>
		</s:else>

		<div class=section>

			<s:form action="editconcert1" theme="simple">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<s:submit value="編集" />
			</s:form>

			<s:property value="date" /><br>
			<s:property value="hall" /><br>

			<s:property value="kaijou" />開場
			<s:property value="kaien" />開演<br>

			料金：<s:property value="ryoukin" />

			<ul>
				<s:iterator value="kyokumoku">
					<li><s:property />
				</s:iterator>
			</ul>

			<ul>
				<s:iterator value="shutsuen">
					<li><s:property />
				</s:iterator>
			</ul>

		</div>
		</div>
		</div>
	</s:iterator>

	</div>
	</div>

</body>
</html>
