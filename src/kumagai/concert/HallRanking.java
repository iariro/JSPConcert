package kumagai.concert;

import java.sql.*;

/**
 * ホール来場回数ランキング情報。
 */
public class HallRanking
{
	public final int id;
	public final String name;
	public final int count;
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
	 * 来場回数を取得。
	 * @return 来場回数
	 */
	public int getCount()
	{
		return count;
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
	public HallRanking(ResultSet result)
		throws SQLException
	{
		id = result.getInt("id");
		name = result.getString("name");
		count = result.getInt("count");
		capacity = result.getInt("capacity");
	}
}
