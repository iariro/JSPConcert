package kumagai.sql;

import java.util.*;

/**
 * WHERE句。
 */
public class WhereString
	extends ArrayList<KeyAndValue>
{
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		int count = 0;
		String ret = new String();

		for (KeyAndValue value : this)
		{
			if (value.valid)
			{
				// 条件指定する。

				if (count >= 1)
				{
					// ２個目以降である。

					ret += " and ";
				}

				ret += value.toString();
				count++;
			}
		}

		if (count > 0)
		{
			// 条件が１個でもある。

			ret = "where " + ret;
		}

		return ret;
	}
}
