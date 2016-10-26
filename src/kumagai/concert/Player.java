package kumagai.concert;

import java.sql.*;

/**
 * 演奏者情報。
 * @author kumagai
 */
public class Player
{
	public int id;
	public String name;
	public String siteurl;
	public boolean active;

	/**
	 * DB取得値をメンバーに割り当て。
	 * @param results DBレコード
	 * @throws SQLException
	 */
	public Player(ResultSet results)
		throws SQLException
	{
		this.id = results.getInt("id");
		this.name = results.getString("name");
		this.siteurl = results.getString("siteurl");
		this.active = results.getInt("active") > 0;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%d:%s %s %s", id, name, siteurl, active);
	}
}
