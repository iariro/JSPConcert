package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * ホール情報コレクション。
 * @author kumagai
 */
public class HallList
	extends ArrayList<Hall>
{
	/**
	 * ホール情報変更。
	 * @param connection DB接続
	 * @param hallId ホールID
	 * @param name ホール名
	 * @param address 住所
	 * @param capacity 席数
	 * @throws SQLException
	 */
	static public void updateTitle(Connection connection, int hallId,
		String name, String address, int capacity)
			throws SQLException
	{
		PreparedStatement statement =
			connection.prepareStatement
				("update hall set name=?, address=?, capacity=? where id=?");

		statement.setString(1, name);
		statement.setString(2, address);
		statement.setInt(3, capacity);
		statement.setInt(4, hallId);

		statement.executeUpdate();

		statement.close();
	}

	/**
	 * 指定のIDのホール情報を取得。
	 * @param statement DBステートメント
	 * @param id ホールID
	 * @return ホール情報
	 */
	static public Hall getHallById(Statement statement, int id)
		throws SQLException
	{
		ResultSet result =
			statement.executeQuery(
				"select id, name, address, capacity from Hall where id=" + id);

		Hall hall = null;

		if (result.next())
		{
			// 取得成功。

			hall = new Hall(result);
		}

		result.close();

		return hall;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param statement DBステートメント
	 * @param order ソート順指定 0=ホール名／1=住所／2=席数（降順）
	 * @throws SQLException
	 */
	public HallList(Statement statement, int order)
		throws SQLException
	{
		String ordersql = null;

		switch (order)
		{
			case 0:
				ordersql = " order by name";
				break;
			case 1:
				ordersql = " order by address";
				break;
			case 2:
				ordersql = " order by capacity desc";
				break;
		}

		ResultSet result =
			statement.executeQuery(
				"select id, name, address, capacity from Hall" + ordersql);

		while (result.next())
		{
			add(new Hall(result));
		}

		result.close();
	}
}
