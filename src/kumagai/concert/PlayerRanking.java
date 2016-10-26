package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * 演奏者の演奏会来場回数ランキング情報。
 * @author kumagai
 */
public class PlayerRanking
	extends ArrayList<StringAndNumber>
{
	/**
	 * DB取得値からランキング情報構築。
	 * @param statement DB接続オブジェクト
	 * @throws SQLException
	 */
	public PlayerRanking(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery(
				"select Player.name, count(distinct Listen.concertId) as count from Concert join Shutsuen on Shutsuen.concertId=Concert.id join Player on Player.id=Shutsuen.playerId join Listen on Listen.concertId=Concert.id where Shutsuen.partId=1 group by Player.name order by count(Listen.concertId) desc");

		while (result.next())
		{
			add(
				new StringAndNumber
					(result.getString("name"), result.getInt("count")));
		}

		result.close();
	}
}
