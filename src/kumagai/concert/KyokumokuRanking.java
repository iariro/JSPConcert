package kumagai.concert;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * 曲目の聴いた回数ランキング情報。
 * @author kumagai
 */
public class KyokumokuRanking
	extends ArrayList<StringAndNumber>
{
	/**
	 * ランキング情報取得テスト。
	 * @param args 未使用
	 * @throws SQLException
	 */
	public static void main(String[] args)
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		KyokumokuRanking ranking = new KyokumokuRanking(connection, 2);

		for (StringAndNumber element : ranking)
		{
			System.out.println(element.string + " " + element.number);
		}

		connection.close();
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param count カウント閾値
	 * @throws SQLException
	 */
	public KyokumokuRanking(Connection connection, int count)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"select Composer.name, Kyokumoku.title, count(*) as count from Kyokumoku join Composer on Kyokumoku.composerId=Composer.id join Concert on Kyokumoku.concertId=Concert.id where Kyokumoku.concertId in (select concertId from Listen) and Concert.date <= getdate() group by Composer.name, Kyokumoku.title having count(*)>=? order by count(*) desc, Composer.name");

		statement.setInt(1, count);

		ResultSet result = statement.executeQuery();

		while (result.next())
		{
			add(
				new StringAndNumber(
					result.getString("name") + " " + result.getString("title"),
					result.getInt("count")));
		}

		result.close();
		statement.close();
	}
}
