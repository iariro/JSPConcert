package kumagai.concert;

import java.sql.*;

/**
 * ホール情報。
 * @author kumagai
 */
public class Hall
{
	public final int id;
	public final String name;
	public final String address;
	public final int capacity;

	/**
	 * ホールIDを取得。
	 * @return ホールID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * ホール名を取得。
	 * @return ホール名
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 住所を取得。
	 * @return 住所
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * 席数を取得。
	 * @return 席数
	 */
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Hall(ResultSet result)
		throws SQLException
	{
		id = result.getInt("id");
		name = result.getString("name");
		address = result.getString("address");
		capacity = result.getInt("capacity");
	}
}
