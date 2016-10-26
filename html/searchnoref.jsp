<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
	<title>参照されてないデータを検索</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta http-equiv=Content-Style-Type content=text/css>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>参照されてないデータ</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>何も演奏しないコンサート</h2>
		<table>
			<s:iterator value="noKyokumokuConcerts">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="date" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>コンサートがない曲</h2>
		<table>
			<s:iterator value="noConcertKyokumoku">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="composerId" /></td>
					<td><s:property value="title" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>作曲家がいない曲</h2>
		<table>
			<s:iterator value="noComposeKyokumoku">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="composerId" /></td>
					<td><s:property value="title" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複した曲目</h2>
		<table>
			<s:iterator value="duplicateKyokumoku">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="composerId" /></td>
					<td><s:property value="title" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>だれも演奏しない作曲家</h2>
		<table>
			<s:iterator value="noPlayComposer">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複した作曲家</h2>
		<table>
			<s:iterator value="duplicateComposer">
				<tr>
					<td><s:property /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>だれも演奏しないコンサート</h2>
		<table>
			<s:iterator value="noShutsuenConcerts">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="date" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>日付のずれた開場・開演</h2>
		<table>
			<s:iterator value="invalidDateConcert">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="date1" /></td>
					<td><s:property value="date2" /></td>
					<td><s:property value="kaijou1" /></td>
					<td><s:property value="kaijou2" /></td>
					<td><s:property value="kaien1" /></td>
					<td><s:property value="kaien2" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複したコンサート</h2>
		<table>
			<s:iterator value="duplicateConcert">
				<tr>
					<td><s:property value="hallId" /></td>
					<td><s:property value="hallName" /></td>
					<td><s:property value="date" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>コンサートがない出演</h2>
		<table>
			<s:iterator value="noConcertShutsuen">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="playerId" /></td>
					<td><s:property value="partId" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>演奏者がない出演</h2>
		<table>
			<s:iterator value="noPlayerShutsuen">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="playerId" /></td>
					<td><s:property value="partId" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>演奏しない演奏者</h2>
		<table>
			<s:iterator value="noPlayPlayer">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複した演奏者</h2>
		<table>
			<s:iterator value="duplicatePlayer">
				<tr>
					<td><s:property /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>パートがない出演</h2>
		<table>
			<s:iterator value="noPartShutsuen">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="playerId" /></td>
					<td><s:property value="partId" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複した出演</h2>
		<table>
			<s:iterator value="duplicateShutsuen">
				<tr>
					<td><s:property value="concertId" /></td>
					<td><s:property value="playerId" /></td>
					<td><s:property value="partId" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>担当がいないパート</h2>
		<table>
			<s:iterator value="noShutsuenPart">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>ホールがないコンサート</h2>
		<table>
			<s:iterator value="noHallConcert">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>使用されないホール</h2>
		<table>
			<s:iterator value="noConcertHall">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>重複したホール</h2>
		<table>
			<s:iterator value="duplicateHall">
				<tr>
					<td><s:property value="number" /></td>
					<td><s:property value="string" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>管弦楽がないコンサート</h2>
		<table>
			<s:iterator value="noOrchestraConcerts">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="date" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>半角かな - タイトル</h2>
		<table>
			<s:iterator value="hankakuTitle">
				<tr>
					<td><s:property value="string" /></td>
					<td><s:property value="number" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>半角かな - 出演者</h2>
		<table>
			<s:iterator value="hankakuPlayer">
				<tr>
					<td><s:property value="string" /></td>
					<td><s:property value="number" /></td>
				</tr>
			</s:iterator>
		</table>

		<h2>作品</h2>
		<table>
			<s:iterator value="sakuhinTitle">
				<tr>
					<td><s:property value="number" /></td>
					<td>「<s:property value="string" />」</td>
				</tr>
			</s:iterator>
		</table>

		<h2>スペース</h2>
		<table>
			<s:iterator value="spaceTitle">
				<tr>
					<td>「<s:property value="string" />」</td>

					<td>
						<s:form action="editconcert1" theme="simple">
							<input type="hidden" name="id" value="<s:property value="number" />">
							<s:submit value="編集" />
						</s:form>
					</td>
				</tr>
			</s:iterator>
		</table>

		<h2>住所が同じホール</h2>
		<table>
			<s:iterator value="sameAddressHall">
				<tr>
					<td><s:property value="string1" /></td>
					<td><s:property value="string2" /></td>
				</tr>
			</s:iterator>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
