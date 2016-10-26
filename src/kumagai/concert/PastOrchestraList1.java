package kumagai.concert;

import java.sql.*;
import java.util.ArrayList;

/**
 * 出演が何もないオーケストラのリスト情報。
 * @author kumagai
 */
public class PastOrchestraList1
	extends ArrayList<StringAndStringAndDate>
{
	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public PastOrchestraList1(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery(
				"select Player.name, Player.siteurl from Player where id not in (select playerId from Shutsuen) and active=1");

		while (result.next())
		{
			add(
				new StringAndStringAndDate(
					result.getString("name"),
					result.getString("siteurl"),
					new Date(0)));
		}

		result.close();
	}
}
