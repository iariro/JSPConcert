package kumagai.concert;

import kumagai.sql.*;

/**
 * 過去のコンサート一覧取得用where句文字列。
 * @author kumagai
 */
public class KakoConcertWhereString
	extends WhereString
{
	/**
	 * where句文字列を追加しオブジェクト構築。
	 */
	public KakoConcertWhereString()
	{
		add(new KeyLessThanValue("Concert.date", "getdate()"));
	}
}
