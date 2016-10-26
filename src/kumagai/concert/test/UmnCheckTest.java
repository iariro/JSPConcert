package kumagai.concert.test;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import kumagai.concert.*;

public class UmnCheckTest
{
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)
		throws Exception
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		UmnCheckResult result = new UmnCheckResult(connection, args[0]);

		System.out.println(result.diffUrl.size());

		for (String entry : result.diffUrl)
		{
			System.out.println(entry);
		}

		connection.close();
	}
}
