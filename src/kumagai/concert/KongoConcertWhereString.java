package kumagai.concert;

import kumagai.sql.*;

/**
 * 今後のコンサート一覧取得用where句文字列。
 * @author kumagai
 */
public class KongoConcertWhereString
	extends WhereString
{
	/**
	 * where句文字列を追加しオブジェクト構築。
	 */
	public KongoConcertWhereString()
	{
		add(new KeyGreaterEqualValue("Concert.date", "getdate()-1"));
	}
}
