package kumagai.concert;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * １オーケストラの全コンサートの期間
 */
public class ConcertRange
{
	public final String orchestra;
	public final long minDate;
	public final long maxDate;

	/**
	 * DB取得値からオブジェクトを構築する
	 * @param result DB取得値
	 */
	public ConcertRange(ResultSet result)
		throws SQLException
	{
		this.orchestra = result.getString("name");
		this.minDate = result.getDate("mindate").getTime();
		this.maxDate = result.getDate("maxdate").getTime();
	}
}
