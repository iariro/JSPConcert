package kumagai.concert;

import java.util.*;

/**
 * 汎用的なID値のコレクション。
 * @author kumagai
 */
public class IdCollection
	extends ArrayList<Integer>
{
	/**
	 * @see java.util.AbstractCollection#toString()
	 */
	public String toString()
	{
		int i = 0;
		String ret = "(";

		for (int id : this)
		{
			if (i > 0)
			{
				// ２個目以降。

				ret += ", ";
			}

			ret += String.valueOf(id);
			i++;
		}

		ret += ")";

		return ret;
	}
}
