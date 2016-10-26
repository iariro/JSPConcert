package kumagai.sql;

import java.util.*;

/**
 * JOIN句のコレクション。
 * @author kumagai
 *
 */
public class JoinCollection
	extends ArrayList<Join>
{
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String ret = new String();

		for (Join j : this)
		{
			ret += j + " \n";
		}

		return ret;
	}
}
