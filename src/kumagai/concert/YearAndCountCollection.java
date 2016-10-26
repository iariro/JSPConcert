package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * 年とカウント情報のコレクション。
 * @author kumagai
 */
public class YearAndCountCollection
	extends ArrayList<YearAndCount>
{
	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param results DB取得レコード
	 * @throws SQLException
	 */
	public YearAndCountCollection(ResultSet results)
		throws SQLException
	{
		int year2 = 0;

		while (results.next())
		{
			YearAndCount yearAndCount = new YearAndCount(results);

			if (year2 > 0)
			{
				// ２回目以降。

				// 間が空くのを０件で補間する。
				for (int i=year2 ; i<yearAndCount.getYear() - 1 ; i++)
				{
					add(new YearAndCount(i));
				}
			}

			add(yearAndCount);

			year2 = yearAndCount.getYear();
		}

		results.close();
	}
}
