package kumagai.concert;

import java.sql.*;

/**
 * 年とカウント情報。
 * @author kumagai
 */
public class YearAndCount
{
	private final int year;
	private final int count;

	/**
	 * 年を取得。
	 * @return 年
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * カウントを取得。
	 * @return カウント
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param results DB取得レコード
	 * @throws SQLException
	 */
	public YearAndCount(ResultSet results)
		throws SQLException
	{
		this.year = results.getInt("year");
		this.count = results.getInt("count");
	}

	/**
	 * カウント＝０のオブジェクトを構築する。
	 * @param year 年
	 * @throws SQLException
	 */
	public YearAndCount(int year)
		throws SQLException
	{
		this.year = year;
		this.count = 0;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%d:%d", year, count);
	}
}
