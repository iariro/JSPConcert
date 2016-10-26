package kumagai.concert;

import java.sql.*;

/**
 * コンサート来場情報操作。
 * @author kumagai
 */
public class ListenCollection
{
	/**
	 * コンサート来場情報削除。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @param listenerId 来場者ID
	 * @throws SQLException
	 */
	static public void deleteListen
		(Connection connection, int concertId, int listenerId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("delete listen where concertId=? and listenerId=?");

		statement.setInt(1, concertId);
		statement.setInt(2, listenerId);

		statement.executeUpdate();
		statement.close();
	}

	/**
	 * コンサート来場情報追加。
	 * @param connection DB接続オブジェクト
	 * @param concertId コンサートID
	 * @param listenerId 来場者ID
	 * @throws SQLException
	 */
	static public void insertListen
		(Connection connection, int concertId, int listenerId)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement(
				"insert into listen (concertId, listenerId) values (?, ?)");

		statement.setInt(1, concertId);
		statement.setInt(2, listenerId);

		statement.executeUpdate();
		statement.close();
	}
}
