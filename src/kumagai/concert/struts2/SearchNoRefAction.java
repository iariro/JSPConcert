package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 参照されていないデータの検索アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/searchnoref.jsp")
public class SearchNoRefAction
{
	// 01 何も演奏しないコンサート
	public ArrayList<Concert2> noKyokumokuConcerts;

	// 02 コンサートがない曲
	public ArrayList<Kyokumoku2> noConcertKyokumoku;

	// 03 作曲家がいない曲
	public ArrayList<Kyokumoku2> noComposeKyokumoku;

	// 04 重複した曲目
	public ArrayList<Kyokumoku2> duplicateKyokumoku;

	// 05 だれも演奏しない作曲家
	public ArrayList<StringAndNumber> noPlayComposer;

	// 06 重複した作曲家
	public ArrayList<String> duplicateComposer;

	// 07 だれも演奏しないコンサート
	public ArrayList<Concert2> noShutsuenConcerts;

	// 08 日付のずれた開場・開演
	public ArrayList<Concert3> invalidDateConcert;

	// 09 重複したコンサート
	public ArrayList<Concert4> duplicateConcert;

	// 10 コンサートがない出演
	public ArrayList<Shutsuen2> noConcertShutsuen;

	// 11 演奏者がない出演
	public ArrayList<Shutsuen2> noPlayerShutsuen;

	// 12 演奏しない演奏者
	public ArrayList<StringAndNumber> noPlayPlayer;

	// 13 重複した演奏者
	public ArrayList<String> duplicatePlayer;

	// 14 パートがない出演
	public ArrayList<Shutsuen2> noPartShutsuen;

	// 15 重複した出演
	public ArrayList<Shutsuen2> duplicateShutsuen;

	// 16 担当がいないパート
	public ArrayList<StringAndNumber> noShutsuenPart;

	// 17 ホールがないコンサート
	public ArrayList<StringAndNumber> noHallConcert;

	// 18 使用されないホール
	public ArrayList<StringAndNumber> noConcertHall;

	// 19 重複したホール
	public ArrayList<StringAndNumber> duplicateHall;

	// 20 管弦楽がないコンサート
	public ArrayList<Concert2> noOrchestraConcerts;

	// 21 半角かな - タイトル
	public ArrayList<StringAndNumber> hankakuTitle;

	// 22 半角かな - 出演者
	public ArrayList<StringAndNumber> hankakuPlayer;

	// 23 「作品」を含む曲目
	public ArrayList<StringAndNumber> sakuhinTitle;

	// 24 スペースを含む曲目
	public ArrayList<StringAndNumber> spaceTitle;

	// 25 住所が同じホール
	public ArrayList<StringAndString> sameAddressHall;

	/**
	 * 参照されていないデータの検索アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("searchnoref")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		// 01 何も演奏しないコンサート
		ResultSet result = statement.executeQuery(
			"select id, name, date from Concert where id not in (select concertId from Kyokumoku)");

		noKyokumokuConcerts = new ArrayList<Concert2>();

		while (result.next())
		{
			noKyokumokuConcerts.add(new Concert2(result));
		}
		result.close();

		// 02 コンサートがない曲
		result = statement.executeQuery(
			"select concertId, composerId, title from Kyokumoku where concertId not in (select id from Concert)");

		noConcertKyokumoku = new ArrayList<Kyokumoku2>();

		while (result.next())
		{
			noConcertKyokumoku.add(new Kyokumoku2(result));
		}
		result.close();

		// 03 作曲家がいない曲
		noComposeKyokumoku = new ArrayList<Kyokumoku2>();

		result = statement.executeQuery(
			"select concertId, composerId, title from Kyokumoku where composerId not in (select id from Composer)");

		while (result.next())
		{
			noComposeKyokumoku.add(new Kyokumoku2(result));
		}
		result.close();

		// 04 重複した曲目
		result = statement.executeQuery(
			"select concertId, composerId, title from Kyokumoku group by concertId, composerId, title having count (*) >= 2");

		duplicateKyokumoku = new ArrayList<Kyokumoku2>();

		while (result.next())
		{
			duplicateKyokumoku.add(new Kyokumoku2(result));
		}
		result.close();

		// 05 だれも演奏しない作曲家
		result = statement.executeQuery(
			"select id, name from Composer where id not in (select composerId from Kyokumoku)");

		noPlayComposer = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			noPlayComposer.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 06 重複した作曲家
		result = statement.executeQuery(
			"select name from Composer group by name having count(*) >= 2");

		duplicateComposer = new ArrayList<String>();

		while (result.next())
		{
			duplicateComposer.add(result.getString("name"));
		}
		result.close();

		// 07 だれも演奏しないコンサート
		result = statement.executeQuery(
			"select id, name, date from Concert where id not in (select concertId from Shutsuen)");

		noShutsuenConcerts = new ArrayList<Concert2>();

		while (result.next())
		{
			noShutsuenConcerts.add(new Concert2(result));
		}
		result.close();

		// 08 日付のずれた開場・開演
		result = statement.executeQuery(
			"select id, date, kaijou, kaien from Concert where datename(yyyy, date) <> datename(yyyy, kaijou) or datename(mm, date) <> datename(mm, kaijou) or datename(dd, date) <> datename(dd, kaijou) or datename(yyyy, date) <> datename(yyyy, kaien) or datename(mm, date) <> datename(mm, kaien) or datename(dd, date) <> datename(dd, kaien)");

		invalidDateConcert = new ArrayList<Concert3>();

		while (result.next())
		{
			invalidDateConcert.add(new Concert3(result));
		}
		result.close();

		// 09 重複したコンサート
		result = statement.executeQuery(
			"select hallId, hall.name as hallName, date, kaien from concert join hall on hall.id=concert.hallid group by hallId, hall.name, date, kaien having count(*) >= 2 order by date");

		duplicateConcert = new ArrayList<Concert4>();

		while (result.next())
		{
			duplicateConcert.add(new Concert4(result));
		}
		result.close();

		// 10 コンサートがない出演
		result = statement.executeQuery(
			"select concertId, playerId, partId from Shutsuen where concertId not in (select id from Concert)");

		noConcertShutsuen = new ArrayList<Shutsuen2>();

		while (result.next())
		{
			noConcertShutsuen.add(new Shutsuen2(result));
		}
		result.close();

		// 11 演奏者がない出演
		result = statement.executeQuery(
			"select concertId, playerId, partId from Shutsuen where playerId not in (select id from Player)");

		noPlayerShutsuen = new ArrayList<Shutsuen2>();

		while (result.next())
		{
			noPlayerShutsuen.add(new Shutsuen2(result));
		}
		result.close();

		// 12 演奏しない演奏者
		result = statement.executeQuery(
			"select id, name from Player where id not in (select playerId from Shutsuen)");

		noPlayPlayer = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			noPlayPlayer.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 13 重複した演奏者
		result = statement.executeQuery(
			"select name from Player group by name having count(*) >= 2");

		duplicatePlayer = new ArrayList<String>();

		while (result.next())
		{
			duplicatePlayer.add(result.getString("name"));
		}
		result.close();

		// 14 パートがない出演
		result = statement.executeQuery(
			"select concertId, playerId, partId from Shutsuen where partId not in (select id from Part)");

		noPartShutsuen = new ArrayList<Shutsuen2>();

		while (result.next())
		{
			noPartShutsuen.add(new Shutsuen2(result));
		}
		result.close();

		// 15 重複した出演
		result = statement.executeQuery(
			"select concertId, playerId, partId from Shutsuen group by concertId, playerId, partId having count(*) >= 2");

		duplicateShutsuen = new ArrayList<Shutsuen2>();

		while (result.next())
		{
			duplicateShutsuen.add(new Shutsuen2(result));
		}
		result.close();

		// 16 担当がいないパート
		result = statement.executeQuery(
			"select id, name from Part where id not in (select partId from Shutsuen)");

		noShutsuenPart = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			noShutsuenPart.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 17 ホールがないコンサート
		result = statement.executeQuery(
			"select id, name from Concert where hallId not in (select id from Hall)");

		noHallConcert = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			noHallConcert.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 18 使用されないホール
		result = statement.executeQuery(
			"select id, name from Hall where id not in (select hallId from Concert)");

		noConcertHall = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			noConcertHall.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 19 重複したホール
		result = statement.executeQuery(
			"select id, name from Hall group by id, name having count(*) >= 2");

		duplicateHall = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			duplicateHall.add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}
		result.close();

		// 20 管弦楽がないコンサート
		result = statement.executeQuery(
			"select id, name, date from Concert where id not in (select concertId from Shutsuen where partId=1)");

		noOrchestraConcerts = new ArrayList<Concert2>();

		while (result.next())
		{
			noOrchestraConcerts.add(new Concert2(result));
		}
		result.close();

		// 21 半角かな - タイトル
		result =
			statement.executeQuery("select concertid, title from Kyokumoku");

		hankakuTitle = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			String title = result.getString("title");

			if (title.indexOf("｢") >= 0 ||
				title.indexOf("｣") >= 0 ||
				title.indexOf("･") >= 0)
			{
				// 半角カナを含む。

				hankakuTitle.add(
					new StringAndNumber(title, result.getInt("concertid")));
			}
		}
		result.close();

		// 22 半角かな - 出演者
		result = statement.executeQuery("select id, name from player");

		hankakuPlayer = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			String title = result.getString("name");

			if (title.indexOf("｢") >= 0 ||
				title.indexOf("｣") >= 0 ||
				title.indexOf("･") >= 0)
			{
				// 半角カナを含む。

				hankakuPlayer.add(
					new StringAndNumber(title, result.getInt("id")));
			}
		}
		result.close();

		// 23 作品
		result = statement.executeQuery(
			"select concertid, title from Kyokumoku where title like '%作品%'");

		sakuhinTitle = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			sakuhinTitle.add(
				new StringAndNumber(
					result.getString("title"),
					result.getInt("concertid")));
		}
		result.close();

		// 24 スペース
		result = statement.executeQuery(
			"select concertid, title from Kyokumoku where title like '% '");

		spaceTitle = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			spaceTitle.add(
				new StringAndNumber(
					result.getString("title"),
					result.getInt("concertid")));
		}
		result.close();

		// 25 住所が同じホール
		result = statement.executeQuery(
			"select name, address from hall where address in (select address from hall group by address having count(*) > 1)");

		sameAddressHall = new ArrayList<StringAndString>();

		while (result.next())
		{
			sameAddressHall.add(
				new StringAndString(
					result.getString("name"),
					result.getString("address")));
		}
		result.close();

		statement.close();
		connection.close();

		return "success";
	}
}
