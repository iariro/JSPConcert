package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * パート情報コレクション。
 * @author kumagai
 */
public class PartCollection
	extends ArrayList<StringAndNumber>
{
	/**
	 * 全パート情報コレクションを構築する。
	 * @param statement DBステートメント
	 * @throws SQLException
	 */
	public PartCollection(Statement statement)
		throws SQLException
	{
		ResultSet result = statement.executeQuery("select id, name from Part");

		while (result.next())
		{
			add(
				new StringAndNumber
					(result.getString("name"), result.getInt("id")));
		}

		result.close();
	}
}
