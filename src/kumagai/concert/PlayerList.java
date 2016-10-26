package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * 演奏者情報コレクション。
 * @author kumagai
 */
public class PlayerList
	extends ArrayList<Player>
{
	/**
	 * 演奏者情報の更新。
	 * @param connection DB接続オブジェクト
	 * @param id 演奏者ID
	 * @param name 演奏者名
	 * @param siteurl URL
	 * @param active true=有効／false=無効
	 * @throws SQLException
	 */
	static public void update(Connection connection, int id, String name,
		String siteurl, boolean active)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("update player set name=?, siteurl=?, active=? where id=?");

		statement.setString(1, name);
		statement.setString(2, siteurl);
		statement.setInt(3, active ? 1 : 0);
		statement.setInt(4, id);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * 全演奏者情報を取得する。
	 * @param statement DBステートメント
	 * @return 全演奏者情報
	 * @throws SQLException
	 */
	static public ArrayList<StringAndNumber> getAll(Statement statement)
		throws SQLException
	{
		ArrayList<StringAndNumber> players = new ArrayList<StringAndNumber>();

		ResultSet result =
			statement.executeQuery("select id, name from Player order by name");

		while (result.next())
		{
			players.add(
				new StringAndNumber
					(result.getString("name"), result.getInt("id")));
		}

		result.close();

		return players;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public PlayerList(Statement statement)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery(
				"select id, name, siteurl, active from Player join Shutsuen on Shutsuen.playerId = Player.id where Shutsuen.partId = 1 group by id, name, siteurl, active order by name");

		while (result.next())
		{
			add(new Player(result));
		}

		result.close();
	}
}
