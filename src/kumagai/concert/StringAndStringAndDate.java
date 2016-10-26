package kumagai.concert;

import java.sql.*;
import java.text.*;

/**
 * 文字列・文字列・日付の組。
 * @author kumagai
 */
public class StringAndStringAndDate
{
	static private final SimpleDateFormat formatDate;

	/**
	 * 日付フォーマット情報生成。
	 */
	static
	{
		formatDate = new SimpleDateFormat();
		formatDate.applyPattern("yyyy/MM/dd");
	}

	public final String string1;
	public final String string2;
	public final Date date;

	/**
	 * 文字列１を取得。
	 * @return 文字列１
	 */
	public String getString1()
	{
		return string1;
	}

	/**
	 * 文字列２を取得。
	 * @return 文字列２
	 */
	public String getString2()
	{
		return string2;
	}

	/**
	 * 日付を取得。
	 * @return 日付
	 */
	public String getDate()
	{
		return formatDate.format(date);
	}

	/**
	 * 指定の値を割り当てオブジェクトを構築する。
	 * @param string1 文字列１
	 * @param string2 文字列２
	 * @param date 日付
	 */
	public StringAndStringAndDate(String string1, String string2, Date date)
	{
		this.string1 = string1;
		this.string2 = string2;
		this.date = date;
	}
}
