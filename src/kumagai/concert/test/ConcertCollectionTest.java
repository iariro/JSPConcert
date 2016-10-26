package kumagai.concert.test;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import kumagai.concert.*;

public class ConcertCollectionTest
{
	public static void main(String[] args)
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			java.sql.DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		KyokumokuCollection.deleteTitle(connection, 4983);
		ShutsuenCollection.deleteShutsuen(connection, 4983);
		ConcertCollection.delete(connection, 4983);
	}
}
