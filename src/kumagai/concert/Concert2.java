package kumagai.concert;

import java.sql.*;

/**
 * コンサート情報。何も演奏しないコンサート情報表示用。
 * @author kumagai
 */
public class Concert2
{
	private final int id;
	private final String name;
	private final Date date;

	/**
	 * コンサートID取得。
	 * @return コンサートID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * コンサート名取得。
	 * @return コンサート名
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 日付取得。
	 * @return 日付
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Concert2(ResultSet result)
		throws SQLException
	{
		id = result.getInt("id");
		name = result.getString("name");
		date = result.getDate("date");
	}
}
