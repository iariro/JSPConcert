package kumagai.concert;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * 作曲家情報コレクション。
 * @author kumagai
 */
public class ComposerCollection
	extends ArrayList<StringAndNumber>
{
	/**
	 * テストコード。
	 * @param args 未使用
	 * @throws SQLException
	 */
	static public void main(String [] args)
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		Statement statement1 = connection.createStatement();

		ArrayList<StringAndNumber> listenerList =
			new ComposerCollection(statement1);

		statement1.close();
		connection.close();

		for (StringAndNumber listener : listenerList)
		{
			System.out.println(listener);
		}
	}

	/**
	 * 作曲家と登場回数のコレクションを取得。
	 * @param statement DBステートメント
	 * @return 作曲家と登場回数のコレクション
	 * @throws SQLException
	 */
	static public ArrayList<ComposerIdNameCount> getComposerAndCount
		(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery
				("select Composer.id, Composer.name, Count(*) as count from Composer join Kyokumoku on Kyokumoku.composerId=Composer.id group by composer.id, composer.name order by name");

		ArrayList<ComposerIdNameCount> composerAndCountCollection =
			new ArrayList<ComposerIdNameCount>();

		while (result.next())
		{
			composerAndCountCollection.add(
				new ComposerIdNameCount(
					result.getInt("id"),
					result.getString("name"),
					result.getInt("count")));
		}

		result.close();

		return composerAndCountCollection;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public ComposerCollection(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery
				("select id, name from Composer order by name");

		while (result.next())
		{
			add(
				new StringAndNumber(
					result.getString("name"),
					result.getInt("id")));
		}

		result.close();
	}
}
