package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * ホール来場回数ランキング情報コレクション。
 * @author kumagai
 */
public class HallRankingCollection
	extends ArrayList<HallRanking>
{
	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public HallRankingCollection(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery(
				"select Hall.id, Hall.name, count(distinct Listen.concertId) as count, Hall.capacity from Concert join Hall on Hall.id=Concert.hallId join Listen on Listen.concertId=Concert.id group by Hall.id, Hall.name, Hall.capacity order by count(Listen.concertId) desc");

		while (result.next())
		{
			add(new HallRanking(result));
		}

		result.close();
	}
}
