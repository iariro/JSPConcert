<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>コンサート情報</title>
		<link rel="stylesheet" type="text/css" href="hatena.css">
	</head>

	<body>
		<h1>コンサート情報</h1>

		<div class=hatena-body>
		<div class=main>
		<div class=day>

		<h2>間近のコンサート</h2>
		<table>
			<s:iterator value="concerts">
			<tr>
				<td><s:property value="date"/><br></td>
				<td><s:property value="orchestra"/></td>
				<td><s:property value="title"/></td>
				<td>
				<s:property value="lastNumber.composer"/>
				<s:property value="lastNumber.title"/>
				</td>
			</tr>
			</s:iterator>
		</table>
		<s:form action="recentconcert" theme="simple">
			<s:submit value="詳細表示" />
		</s:form>

		<h2>メニュー</h2>

		<table>
			<tr>
			<th>検索</th>
			<th>来場情報</th>
			<th>マスター</th>
			<th>情報入力用</th>
			<th>エラーチェック</th>
			<td>

			<tr>
			<td>
				<s:form action="concertsearch1" theme="simple">
					<s:submit value="コンサート検索" />
				</s:form>

				<s:form action="kongoconcert" theme="simple">
					<s:select name="composerRank" list="#{ '1':'絞り込みなし','2':'まあまあ興味あり以上','3':'興味ありのみ' }"/>
					<s:submit value="今後のコンサート" />
				</s:form>

				<s:form action="densitygraph" theme="simple">
					<s:submit value="密度" />
				</s:form>

				<s:form action="monthlycountview" theme="simple">
					<s:submit value="月毎回数一覧" />
				</s:form>

				<s:form action="concertchronology" theme="simple">
					<s:submit value="全コンサートの期間表示" />
				</s:form>

			</td>
			<td>

				<s:form action="yearcount" theme="simple">
					<s:submit value="年ごと来場件数" />
				</s:form>

				<s:form action="playerrank" theme="simple">
					<s:submit value="演奏者ランキング" />
				</s:form>

				<s:form action="hallrank" theme="simple">
					<s:submit value="ホールランキング" />
				</s:form>

				<s:form action="kyokurank" theme="simple">
					<s:submit value="聴いた曲ランキング" />
				</s:form>

			</td>
			<td>

				<s:form action="composerlist" theme="simple">
					<s:submit value="作曲家一覧" />
				</s:form>

				<s:form action="playerlist" theme="simple">
					<s:submit value="演奏者一覧" />
				</s:form>

				<s:form action="partlist" theme="simple">
					<s:submit value="パート一覧" />
				</s:form>

				<s:form action="halllist1" theme="simple">
					<s:submit value="ホール一覧 - 住所順" />
				</s:form>

				<s:form action="halllist2" theme="simple">
					<s:submit value="ホール一覧 - 席数順" />
				</s:form>

			</td>
			<td>

				<s:form action="pastorchestra" theme="simple">
					<s:submit value="コンサートが終わった演奏者" />
				</s:form>

				<s:form action="concertcount" theme="simple">
					<s:submit value="コンサート登録数" />
				</s:form>

				<s:form action="recentaddedconcert" theme="simple">
					<s:submit value="直近登録コンサート情報" />
				</s:form>

			</td>
			<td>

				<s:form action="searchnoref" theme="simple">
					<s:submit value="参照されてないデータを検索" />
				</s:form>

			</td>
			</tr>
		</table>

		</div>
		</div>
		</div>

	</body>
</html>
