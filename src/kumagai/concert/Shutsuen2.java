package kumagai.concert;

import java.sql.*;

/**
 * 出演情報。参照の不正な出演情報表示用。
 * @author kumagai
 */
public class Shutsuen2
{
	private final int concertId;
	private final int playerId;
	private final int partId;

	/**
	 * コンサートIDを取得。
	 * @return コンサートID
	 */
	public int getConcertId()
	{
		return concertId;
	}

	/**
	 * 演奏者IDを取得。
	 * @return 演奏者ID
	 */
	public int getPlayerId()
	{
		return playerId;
	}

	/**
	 * パートIDを取得。
	 * @return パートID
	 */
	public int getPartId()
	{
		return partId;
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Shutsuen2(ResultSet result)
		throws SQLException
	{
		concertId = result.getInt("concertId");
		playerId = result.getInt("playerId");
		partId = result.getInt("partId ");
	}
}
