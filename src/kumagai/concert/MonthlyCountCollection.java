package kumagai.concert;

import java.sql.*;
import java.util.*;

/**
 * 月毎のコンサート回数情報。
 */
public class MonthlyCountCollection
	extends ArrayList<MonthlyCountPerYear>
{
	/**
	 * 月毎のコンサート回数情報を構築。
	 * @param statement DBステートメント
	 * @param sql SQL
	 * @throws SQLException
	 */
	public MonthlyCountCollection(Statement statement, String sql)
		throws SQLException
	{
		ResultSet result = statement.executeQuery(sql);

		int year2 = 0;
		MonthlyCountPerYear monthCount = null;

		while (result.next())
		{
			int year = result.getInt("year");
			int month = result.getInt("month");
			int count = result.getInt("count");

			if (year != year2)
			{
				// 次の年。

				if (year2 > 0)
				{
					// 初回以外。

					for (int i=year2-1 ; i>year ; i--)
					{
						add(new MonthlyCountPerYear(i));
					}
				}

				monthCount = new MonthlyCountPerYear(year);
				add(monthCount);
				monthCount.setCount(month, count);
			}
			else
			{
				// 同じ年。

				monthCount.setCount(month, count);
			}

			year2 = year;
		}

		result.close();
	}
}
