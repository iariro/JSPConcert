package kumagai.concert;

import java.sql.*;

/**
 * 出演情報。
 * @author kumagai
 */
public class Shutsuen
{
	public final String part;
	public final String player;
	private final int partId;
	private final int playerId;

	/**
	 * パートIDを取得。
	 * @return パートID
	 */
	public int getPartId()
	{
		return partId;
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
	 * DB取得値からオブジェクトを構築する。
	 * @param result DB取得レコード
	 */
	public Shutsuen(ResultSet result)
		throws SQLException
	{
		part = result.getString("partname");
		player = result.getString("playername");
		partId = result.getInt("partid");
		playerId = result.getInt("playerid");
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return part + " " + player;
	}
}
