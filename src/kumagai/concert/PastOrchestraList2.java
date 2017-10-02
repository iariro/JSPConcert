package kumagai.concert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ktool.datetime.DateTime;
import kumagai.concert.crawler.PastConcertInfo;

/**
 * 演奏会がすべて終わったオーケストラのリスト情報。
 * @author kumagai
 */
public class PastOrchestraList2
	extends ArrayList<PastConcertInfo>
{
	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param active true=有効／false=無効
	 */
	public PastOrchestraList2(Connection connection, boolean active)
		throws SQLException
	{
		String sql =
			"select Player.name, Player.siteurl, max(Concert.date) as date from Concert join Shutsuen on Shutsuen.concertId=Concert.id join Player on Player.id=Shutsuen.playerId where Shutsuen.partId=1 and active=? group by Player.name, Player.siteurl having max(Concert.date) < getdate() order by max(Concert.date)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, active ? 1 : 0);

		ResultSet result = statement.executeQuery();

		while (result.next())
		{
			add(
				new PastConcertInfo(
					result.getString("name"),
					result.getString("siteurl").length() > 0 ? result.getString("siteurl") : null,
					new DateTime(result.getDate("date")).toFullString()));
		}

		result.close();
	}
}
