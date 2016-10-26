package kumagai.concert;

import java.sql.*;
import java.util.*;

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
