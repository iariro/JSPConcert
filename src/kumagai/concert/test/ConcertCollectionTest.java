package kumagai.concert.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import junit.framework.TestCase;
import ktool.datetime.DateTime;
import kumagai.concert.Concert;
import kumagai.concert.ConcertCollection;
import kumagai.concert.KyokumokuCollection;
import kumagai.concert.ShutsuenCollection;

public class ConcertCollectionTest
	extends TestCase
{
	static private final String connectionString = "jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;";

	public void _testDelete()
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection = java.sql.DriverManager.getConnection(connectionString);

		KyokumokuCollection.deleteTitle(connection, 4983);
		ShutsuenCollection.deleteShutsuen(connection, 4983);
		ConcertCollection.delete(connection, 4983);
	}

	public void _testGetConcertCountPerUpdateDate() throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());
		Connection connection = java.sql.DriverManager.getConnection(connectionString);
		LinkedHashMap<DateTime, Integer> concertCount = ConcertCollection.getConcertCountPerUpdateDate(connection);
		System.out.println(concertCount.size());
		for (Entry<DateTime, Integer> entry : concertCount.entrySet())
		{
			System.out.printf("%s:%d", entry.getKey(), entry.getValue());
			System.out.println();
		}
	}

	public void testGetRecentAddedConcerts()
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());
		Connection connection = java.sql.DriverManager.getConnection(connectionString);
		LinkedHashMap<String,ArrayList<Concert>> concerts = ConcertCollection.getRecentAddedConcerts(connection, 3);
		for (Entry<String, ArrayList<Concert>> concerts2 : concerts.entrySet())
		{
			for (Concert concert : concerts2.getValue())
			{
				System.out.println(concert);
			}
		}
	}
}
