package kumagai.concert;

import java.sql.*;

/**
 * コンサート情報。日付のずれた開場・開演情報表示用。
 * @author kumagai
 */
public class Concert3
{
	private final int id;
	private final Date date1;
	private final Time date2;
	private final Date kaijou1;
	private final Time kaijou2;
	private final Date kaien1;
	private final Time kaien2;

	/**
	 * コンサートID取得。
	 * @return コンサートID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 日付１取得。
	 * @return 日付１
	 */
	public Date getDate1()
	{
		return date1;
	}

	/**
	 * 日付２取得。
	 * @return 日付２
	 */
	public Time getDate2()
	{
		return date2;
	}

	/**
	 * 会場時刻１取得。
	 * @return 会場時刻１
	 */
	public Date getKaijou1()
	{
		return kaijou1;
	}

	/**
	 * 会場時刻２取得。
	 * @return 会場時刻２
	 */
	public Time getKaijou2()
	{
		return kaijou2;
	}

	/**
	 * 開演時刻１取得。
	 * @return 開演時刻１
	 */
	public Date getKaien1()
	{
		return kaien1;
	}

	/**
	 * 開演時刻２取得。
	 * @return 開演時刻２
	 */
	public Time getKaien2()
	{
		return kaien2;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Concert3(ResultSet result)
		throws SQLException
	{
		id = result.getInt("id");
		date1 = result.getDate("date");
		date2 = result.getTime("date");
		kaijou1 = result.getDate("kaijou");
		kaijou2 = result.getTime("kaijou");
		kaien1 = result.getDate("kaien");
		kaien2 = result.getTime("kaien");
	}
}
