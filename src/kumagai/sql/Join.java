package kumagai.sql;

/**
 * JOIN句。
 * @author kumagai
 */
public class Join
{
	private final String table1;
	private final String column1;
	private final String table2;
	private final String column2;

	/**
	 * オブジェクトの構築とともにメンバの初期化を行う。
	 * @param table1 テーブル１
	 * @param column1 列１
	 * @param table2 テーブル２
	 * @param column2 列２
	 */
	public Join(String table1, String column1, String table2, String column2)
	{
		this.table1 = table1;
		this.column1 = column1;
		this.table2 = table2;
		this.column2 = column2;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return
			String.format(
				"join %s on %s.%s = %s.%s",
				table1,
				table1,
				column1,
				table2,
				column2);
	}
}
