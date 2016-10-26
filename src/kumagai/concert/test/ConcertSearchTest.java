package kumagai.concert.test;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import kumagai.concert.*;

/**
 * コンサート情報検索のテスト。
 * @author kumagai
 */
public class ConcertSearchTest
{
	/**
	 * @param args 未使用
	 * @throws SQLException
	 */
	public static void main(String[] args)
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			java.sql.DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		Statement statement1 = connection.createStatement();

		IdCollection idCollection =
			new ConcertIdCollection(
				statement1,
				new ConcertJoinCollection(true, true),
				new ConcertSearchWhereString
					(null, "ワーグナー", null, null, null, null, null, 1));

		if (idCollection.size() > 0)
		{
			// １件でもある。

			ConcertCollection concertCollection =
				new ConcertCollection(connection, statement1, idCollection);

			for (Concert concert : concertCollection)
			{
				System.out.println(concert);
			}
		}
		else
		{
			// １件もない。

			System.out.println("０件");
		}

		connection.close();
	}
}
