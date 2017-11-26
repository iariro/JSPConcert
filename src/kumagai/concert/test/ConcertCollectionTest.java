package kumagai.concert.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import junit.framework.TestCase;
import ktool.datetime.DateTime;
import kumagai.concert.ConcertCollection;
import kumagai.concert.KyokumokuCollection;
import kumagai.concert.ShutsuenCollection;

public class ConcertCollectionTest
	extends TestCase
{
	static private final String connectionString = "jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;";

	public void testDelete()
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection = java.sql.DriverManager.getConnection(connectionString);

		KyokumokuCollection.deleteTitle(connection, 4983);
		ShutsuenCollection.deleteShutsuen(connection, 4983);
		ConcertCollection.delete(connection, 4983);
	}

	public void testgetConcertCountPerUpdateDate() throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());
		Connection connection = java.sql.DriverManager.getConnection(connectionString);
		Statement statement = connection.createStatement();
		TreeMap<DateTime,Integer> concertCount = ConcertCollection.getConcertCountPerUpdateDate(connection, statement);
		for (Entry<DateTime, Integer> entry : concertCount.entrySet())
		{
			System.out.printf("%s:%d", entry.getKey(), entry.getValue());
			System.out.println();
		}
	}
}
