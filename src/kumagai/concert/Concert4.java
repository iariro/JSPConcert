package kumagai.concert;

import java.sql.*;
import java.text.*;

/**
 * コンサート情報。重複したコンサート情報表示用。
 * @author kumagai
 */
public class Concert4
{
	static private final SimpleDateFormat formatDate;

	/**
	 * 日付書式オブジェクトを構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();

		formatDate.applyPattern("yyyy/MM/dd");
	}

	private final int hallId;
	private final String hallName;
	private final Date date;

	/**
	 * ホールID取得。
	 * @return ホールID
	 */
	public int getHallId()
	{
		return hallId;
	}

	/**
	 * ホール名取得。
	 * @return ホール名
	 */
	public String getHallName()
	{
		return hallName;
	}

	/**
	 * 日付取得。
	 * @return 日付
	 */
	public String getDate()
	{
		return formatDate.format(date);
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Concert4(ResultSet result)
		throws SQLException
	{
		hallId = result.getInt("hallId");
		hallName = result.getString("hallName");
		date = result.getDate("date");
	}
}
