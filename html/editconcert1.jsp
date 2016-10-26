<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>コンサート情報編集</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>

		<h1>コンサート情報編集</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>
			<span class=title>
			<s:property value="concert.orchestra" />
			<s:property value="concert.title" />
			(<s:property value="id" />)
			</span>
		</h2>

		<s:if test="%{concert.listen}">
			<div class=body2>
		</s:if>
		<s:else>
			<div class=body>
		</s:else>

		<div class=section>
			<s:property value="concert.date" /><br>
			<s:property value="concert.hall" /><br>

			<s:property value="concert.kaijou" />開場
			<s:property value="concert.kaien" />開演<br>
		</div>

		<ul>
			<table>
			<s:iterator value="concert.kyokumoku">
				<tr>
				<s:form action="editconcert7" theme="simple">
					<td><s:property value="composer" /></td>
					<input type="hidden" name="concertId" value="<s:property value="id" />">
					<input type="hidden" name="composerId" value="<s:property value="composerId" />">
					<input type="hidden" name="title1" value="<s:property value="title" />">
					<td><input name="title2" value="<s:property value="title" />" size="60"></td>
					<td><s:submit value="変更" /></td>
				</s:form>
				<s:form action="editconcert8" theme="simple">
					<input type="hidden" name="concertId" value="<s:property value="id" />">
					<input type="hidden" name="composerId" value="<s:property value="composerId" />">
					<input type="hidden" name="title" value="<s:property value="title" />">
					<td><s:submit value="削除" /></td>
				</s:form>
				</tr>
			</s:iterator>
			</table>
		</ul>

		<ul>
			<s:iterator value="concert.shutsuen">
				<s:form action="editconcert6" theme="simple">
					<li><s:property />
					<input type="hidden" name="concertId" value="<s:property value="id" />">
					<input type="hidden" name="partId" value="<s:property value="partId" />">
					<input type="hidden" name="playerId" value="<s:property value="playerId" />">
					<s:submit value="削除" />
				</s:form>
			</s:iterator>
		</ul>

		</div>
		</div>
		</div>
		</div>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2><span class=title>コンサート情報編集</span></h2>

		<div class=body>
			<s:form action="editconcert5" theme="simple">
				<input type="hidden" name="concertId" value="<s:property value="id" />">

				開催日：<input name="date" value="<s:property value="concert.date2" />"><br>

				ホール：
				<s:select name="hallId" list="halls" listKey="id" listValue="name" value="%{concert.hallId}" />
				<br>

				開場：<input name="kaijou" value="<s:property value="concert.kaijou" />"><br>
				開演：<input name="kaien" value="<s:property value="concert.kaien" />"><br>
				料金：<input name="ryoukin" value="<s:property value="concert.ryoukin" />" size="40"><br>
				<s:submit value="更新" />
			</s:form>
		</div>

		</div>
		</div>
		</div>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2><span class=title>曲目追加</span></h2>

		<div class=body>

			<s:form action="editconcert3" theme="simple">
				<input type="hidden" name="concertId" value="<s:property value="id" />">

				作曲家：
				<s:select name="composerId" list="composers" listKey="number" listValue="string" />
				<br>

				曲目：
				<input name="title" value=""><br>
				<s:submit value="追加" />
			</s:form>

		</div>
		</div>
		</div>
		</div>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2><span class=title>演奏者追加</span></h2>

		<div class=body>

			<s:form action="editconcert4" theme="simple">
				<input type="hidden" name="concertId" value="<s:property value="id" />">

				パート：
				<s:select name="partId" list="parts" listKey="number" listValue="string" />
				<br>

				演奏者：
				<s:select name="playerId" list="players" listKey="number" listValue="string" />
				<br>

				<s:submit value="追加" />
			</s:form>

		</div>
		</div>
		</div>
		</div>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2><span class=title>来場者追加</span></h2>

		<div class=body>

			<s:form action="editconcert2" theme="simple">
				<input type="hidden" name="concertId" value="<s:property value="id" />">

				来場者：
				<s:select name="listenerId" list="allListeners" listKey="number" listValue="string" />
				<br>

				<s:submit value="追加" />
			</s:form>

		</div>
		</div>
		</div>
		</div>

		<s:if test="%{listenersLength>0}">
			<div class=hatena-body>
			<div class=main>
			<div class=day>
			<h2><span class=title>来場者削除</span></h2>
			<div class=body>

				<s:form action="deletelisten" theme="simple">
					<input type="hidden" name="concertId" value="<s:property value="id" />">

					来場者：
					<s:select name="listenerId" list="listeners" listKey="number" listValue="string" />
					<br>

					<s:submit value="削除" />
				</s:form>

			</div>
			</div>
			</div>
			</div>
		</s:if>

	</body>
</html>
