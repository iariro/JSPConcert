package kumagai.concert;

import java.sql.*;

/**
 * 曲目情報。
 * @author kumagai
 */
public class Kyokumoku
{
	public final int composerId;
	public final String composer;
	public final int rank;
	public final String title;

	/**
	 * 作曲家IDを取得。
	 * @return 作曲家ID
	 */
	public int getComposerId()
	{
		return composerId;
	}

	/**
	 * 作曲家名を取得。
	 * @return 作曲家名
	 */
	public String getComposer()
	{
		return composer;
	}

	/**
	 * 曲名を取得。
	 * @return 曲名
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Kyokumoku(ResultSet result)
		throws SQLException
	{
		composerId = result.getInt("id");
		composer = result.getString("name");
		rank = result.getInt("rank");
		title = result.getString("title");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return composer + " " + title;
	}
}
