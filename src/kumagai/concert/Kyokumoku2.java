package kumagai.concert;

import java.sql.*;

/**
 * 曲目情報。コンサート・作曲家がない曲目，重複した曲目情報表示用。
 * @author kumagai
 */
public class Kyokumoku2
{
	private final int concertId;
	private final int composerId;
	private final String title;

	/**
	 * コンサートIDを取得。
	 * @return コンサートID
	 */
	public int getConcertId()
	{
		return concertId;
	}

	/**
	 * 作曲家IDを取得。
	 * @return 作曲家ID
	 */
	public int getComposerId()
	{
		return composerId;
	}

	/**
	 * タイトルを取得。
	 * @return タイトル
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
	public Kyokumoku2(ResultSet result)
		throws SQLException
	{
		concertId = result.getInt("concertId");
		composerId = result.getInt("composerId");
		title = result.getString("title");
	}
}
