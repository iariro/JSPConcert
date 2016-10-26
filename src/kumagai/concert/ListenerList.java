package kumagai.concert;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * 来場者情報コレクション。
 * @author kumagai
 */
public class ListenerList
	extends ArrayList<StringAndNumber>
{
	/**
	 * テストコード。
	 * @param args なし
	 * @throws SQLException
	 */
	static public void main(String [] args)
		throws SQLException
	{
		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			java.sql.DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		Statement statement1 = connection.createStatement();

		ArrayList<StringAndNumber> listenerList = new ListenerList(statement1);

		for (StringAndNumber listener : listenerList)
		{
			System.out.println(listener);
		}

		ArrayList<StringAndNumber> list =
			getListenerIdByConcertId(connection, 3209);

		for (StringAndNumber stringAndNumber : list)
		{
			System.out.println(stringAndNumber);
		}

		statement1.close();
		connection.close();
	}

	/**
	 * 子弟のコンサートの来場者を取得する。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @return 来場者リスト
	 * @throws SQLException
	 */
	static public ArrayList<StringAndNumber> getListenerIdByConcertId
		(Connection connection, int concertId)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"select Listener.Id, Listener.name from concert join Listen on concert.id=concertId join Listener on Listen.listenerId=Listener.id where Concert.id=?");

		statement.setInt(1, concertId);

		ResultSet result = statement.executeQuery();

		ArrayList<StringAndNumber> list = new ArrayList<StringAndNumber>();

		while (result.next())
		{
			list.add(
				new StringAndNumber
					(result.getString("name"), result.getInt("id")));
		}

		result.close();
		statement.close();

		return list;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public ListenerList(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery("select id, name from Listener");

		while (result.next())
		{
			add(
				new StringAndNumber
					(result.getString("name"), result.getInt("id")));
		}

		result.close();
	}
}
