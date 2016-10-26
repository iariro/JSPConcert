package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * 出演情報コレクション。
 * @author kumagai
 */
public class ShutsuenCollection
	extends ArrayList<Shutsuen>
{
	/**
	 * コンサート出演情報追加。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @param playerId 演奏者ID
	 * @param partId パートId
	 * @throws SQLException
	 */
	static public void insertShutsuen
		(Connection connection, int concertId, int playerId, int partId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into shutsuen values (?, ?, ?)");

		statement.setInt(1, concertId);
		statement.setInt(2, playerId);
		statement.setInt(3, partId);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * コンサート出演情報削除。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @throws SQLException
	 */
	static public void deleteShutsuen(Connection connection, int concertId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement("delete shutsuen where concertId=?");

		statement.setInt(1, concertId);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * コンサート出演情報削除。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @param partId パートID
	 * @param playerId 演奏者ID
	 * @throws SQLException
	 */
	static public void deleteShutsuen
		(Connection connection, int concertId, int partId, int playerId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"delete shutsuen where concertId=? and partId=? and playerId=?");

		statement.setInt(1, concertId);
		statement.setInt(2, partId);
		statement.setInt(3, playerId);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @throws SQLException
	 */
	public ShutsuenCollection(Connection connection, int concertId)
		throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"select Part.name as partname, Player.name as playername, Part.id as partid, Player.id as playerid from Shutsuen join Part on Part.id=Shutsuen.partId join Player on Player.id=Shutsuen.playerId where Shutsuen.concertId=?");

		statement.setInt(1, concertId);

		ResultSet result = statement.executeQuery();

		while (result.next())
		{
			add(new Shutsuen(result));
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

		for (Shutsuen shutsuen : this)
		{
			ret += "\t" + shutsuen + "\n";
		}

		return ret;
	}
}
