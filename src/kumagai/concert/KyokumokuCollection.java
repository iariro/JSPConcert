package kumagai.concert;

import java.sql.*;
import java.util.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * Kyokumokuによる曲目情報のコレクション。
 * @author kumagai
 */
public class KyokumokuCollection
	extends ArrayList<Kyokumoku>
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
			java.sql.DriverManager.getConnection
				("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

		ArrayList<Kyokumoku> kyokumokuCollection =
			new KyokumokuCollection(connection, 3161);

		connection.close();

		for (Kyokumoku kyokumoku : kyokumokuCollection)
		{
			System.out.println(kyokumoku);
		}
	}

	/**
	 * 曲目情報変更。
	 * @param connection DB接続
	 * @param concertId コンサートID
	 * @param composerId 作曲家ID
	 * @param title1 旧タイトル
	 * @param title2 新タイトル
	 * @throws SQLException
	 */
	static public void updateTitle(Connection connection, int concertId,
		int composerId, String title1, String title2)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("update kyokumoku set title=? where concertId=? and composerId=? and title=?");

		statement.setString(1, title2);
		statement.setInt(2, concertId);
		statement.setInt(3, composerId);
		statement.setString(4, title1);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * 曲目情報追加。
	 * @param connection DB接続
	 * @param concertId コンサートID
	 * @param composerId 作曲家ID
	 * @param title タイトル
	 * @throws SQLException
	 */
	static public void insertKyokumoku
		(Connection connection, int concertId, int composerId, String title)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("insert into kyokumoku values (?, ?, ?)");

		statement.setInt(1, concertId);
		statement.setInt(2, composerId);
		statement.setString(3, title);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * 曲目情報削除。
	 * @param connection DB接続
	 * @param concertId コンサートID
	 * @throws SQLException
	 */
	static public void deleteTitle(Connection connection, int concertId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("delete from kyokumoku where concertId=?");

		statement.setInt(1, concertId);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * 曲目情報削除。
	 * @param connection DB接続
	 * @param concertId コンサートID
	 * @param composerId 作曲家ID
	 * @param title タイトル
	 * @throws SQLException
	 */
	static public void deleteTitle
		(Connection connection, int concertId, int composerId, String title)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("delete from kyokumoku where concertId=? and composerId=? and title=?");

		statement.setInt(1, concertId);
		statement.setInt(2, composerId);
		statement.setString(3, title);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * 全曲目情報をCSV形式で取得。
	 * @param connection DB接続オブジェクト
	 * @return 全曲目情報
	 */
	static public String [] getAllKyokumoku(Connection connection)
		throws SQLException
	{
		String sql = "select player.name as playername, date, composer.name as composername, kyokumoku.title from concert join kyokumoku on kyokumoku.concertid=concert.id join composer on composer.id=composerid join shutsuen on shutsuen.concertid=kyokumoku.concertid join player on player.id=shutsuen.playerid where shutsuen.partid=1";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet result = statement.executeQuery();

		ArrayList<String> csv = new ArrayList<String>();

		while (result.next())
		{
			csv.add(
				String.format(
					"%s,%s,%s,%s",
					result.getString("playername"),
					result.getDate("date"),
					result.getString("composername"),
					result.getString("title")));
		}

		result.close();
		statement.close();

		return csv.toArray(new String [] {});
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @throws SQLException
	 */
	public KyokumokuCollection(Connection connection, int concertId)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"select Composer.id, Composer.name, Composer.rank, Kyokumoku.title from Kyokumoku join Composer on Composer.id=Kyokumoku.composerId where Kyokumoku.concertId=?");

		statement.setInt(1, concertId);

		ResultSet result = statement.executeQuery();

		while (result.next())
		{
			add(new Kyokumoku(result));
		}

		result.close();
		statement.close();
	}

	/**
	 * @see java.util.AbstractCollection#toString()
	 */
	public String toString()
	{
		String ret = new String();

		for (Kyokumoku kyokumoku : this)
		{
			ret += "\t" + kyokumoku + "\n";
		}

		return ret;
	}
}
