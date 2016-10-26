package kumagai.concert;

import java.sql.*;
import kumagai.sql.*;

/**
 * コンサートIDのコレクション。
 * @author kumagai
 */
public class ConcertIdCollection
	extends IdCollection
{
	/**
	 * DB取得情報からコンサートIDコレクションを構築。
	 * @param statement DBステートメント
	 * @param join JOIN句オブジェクト
	 * @param where WHERE句オブジェクト
	 * @throws SQLException
	 */
	public ConcertIdCollection(Statement statement, JoinCollection join,
		WhereString where)
		throws SQLException
	{
		String sql =
			String.format(
				"select Concert.id from Concert %s %s group by concert.id, concert.date order by concert.date",
				join,
				where);

		ResultSet result = statement.executeQuery(sql);

		while (result.next())
		{
			add(result.getInt("id"));
		}

		result.close();
	}
}
