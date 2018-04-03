package kumagai.concert;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import ktool.datetime.DateTime;

/**
 * Concertクラスによるコンサート情報のコレクション。
 * @author kumagai
 */
public class ConcertCollection
	extends ArrayList<Concert>
{
	/**
	 * コンサート情報更新。
	 * @param connection DB接続
	 * @param concertId コンサートID
	 * @param date 開催日
	 * @param kaijou 開場時刻
	 * @param kaien 開演時刻
	 * @param hallId ホールID
	 * @param ryoukin 料金
	 * @throws SQLException
	 */
	static public void update(Connection connection, int concertId, String date,
		String kaijou, String kaien, int hallId, String ryoukin)
		throws SQLException
	{
		String sql =
			"update Concert set date=?, kaijou=?, kaien=?, hallId=?, ryoukin=? where id=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, date);
		statement.setString(2, String.format("%s %s", date, kaijou));
		statement.setString(3, String.format("%s %s", date, kaien));
		statement.setInt(4, hallId);
		statement.setString(5, ryoukin);
		statement.setInt(6, concertId);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * コンサート情報削除。
	 *
	 * @param connection DB接続
	 * @param concertId コンサートID
	 */
	static public void delete(Connection connection, int concertId)
		throws SQLException
	{
		String sql = "delete Concert where id=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, concertId);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 更新日ごとの登録件数を取得
	 * @param connection DB接続オブジェクト
	 * @param statement DBステートメント
	 * @return 更新日ごとの登録件数コレクション
	 */
	static public LinkedHashMap<DateTime, Integer> getConcertCountPerUpdateDate(Connection connection, Statement statement)
		throws SQLException
	{
		String sql = "select convert(date,createdate) as createdate, count(*) as count from concert group by convert(date,createdate) order by convert(date,createdate)";

		ResultSet result = statement.executeQuery(sql);

		LinkedHashMap<DateTime, Integer> concertCounts = new LinkedHashMap<DateTime, Integer>();
		int count = 0;
		int startCount = 0;
		while (result.next())
		{
			Date date = result.getDate("createdate");
			if (date != null)
			{
				// nullではない

				count += result.getInt("count");
				concertCounts.put(new DateTime(date), count);
			}
			else
			{
				// null＝日付なしすべて

				startCount = result.getInt("count");
			}
		}
		result.close();

		for (Entry<DateTime, Integer> entry : concertCounts.entrySet())
		{
			entry.setValue(entry.getValue() + startCount);
		}

		return concertCounts;
	}

	/**
	 * コンサート情報更新。
	 * @param connection DB接続
	 * @param encoding 文字コード名
	 * @param playerId ホールID
	 */
	static public void updateSiteEncoding(Connection connection, int playerId, String encoding)
		throws SQLException
	{
		String sql = "update Player set siteencoding=? where id=?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, encoding);
		statement.setInt(2, playerId);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 全オーケストラの全コンサートの期間を取得
	 * @param connection DB接続オブジェクト
	 * @param statement DBステートメント
	 * @return 全オーケストラの全コンサートの期間
	 */
	static public ConcertRangeCollection getAllConcertRange(Connection connection, Statement statement)
		throws SQLException
	{
		String sql = "select player.name, MIN(concert.date) as mindate, MAX(concert.date) as maxdate from Concert join Shutsuen on Shutsuen.concertId=Concert.id join Player on Player.id=Shutsuen.playerId where partId=1 group by Player.name order by max(concert.date)";

		ResultSet result = statement.executeQuery(sql);
		ConcertRangeCollection concertRanges = new ConcertRangeCollection();
		while (result.next())
		{
			concertRanges.add(new ConcertRange(result));
		}
		result.close();

		return concertRanges;
	}

	/**
	 * DBからコンサート情報を取得しコレクションを生成する。
	 * @param connection DB接続オブジェクト
	 * @param statement1 DBステートメント１
	 * @param idCollection コンサートIDのコレクション
	 * @throws SQLException
	 */
	public ConcertCollection
		(Connection connection, Statement statement1, IdCollection idCollection)
		throws SQLException
	{
		if (idCollection.size() > 0)
		{
			// １件でもある。

			String sql =
				String.format(
					"select Concert.id as concertid, Player.name as playername, Concert.name as concertname, Concert.date, Concert.kaijou, Concert.kaien, Hall.id as hallid, Hall.name as hallname, Concert.ryoukin, (select count(*) from Listen where concertid=Concert.id) as listencount from Concert join Shutsuen on Shutsuen.concertId=Concert.id join Player on Player.id=Shutsuen.playerId join Hall on Hall.id=Concert.hallid where Shutsuen.partId=1 and Concert.id in %s order by concert.date",
					idCollection);

			ResultSet result = statement1.executeQuery(sql);

			while (result.next())
			{
				add(new Concert(connection, result));
			}

			result.close();
		}
	}
}
