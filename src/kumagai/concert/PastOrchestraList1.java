package kumagai.concert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 出演が何もないオーケストラのリスト情報。
 * @author kumagai
 */
public class PastOrchestraList1
	extends ArrayList<PastConcertInfo>
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
				"select Player.id, Player.name, Player.siteurl, Player.siteencoding from Player where id not in (select playerId from Shutsuen) and active=1");

		while (result.next())
		{
			add(
				new PastConcertInfo(
					result.getInt("id"),
					result.getString("name"),
					result.getString("siteurl"),
					null,
					result.getString("siteencoding")));
		}

		result.close();
	}
}
