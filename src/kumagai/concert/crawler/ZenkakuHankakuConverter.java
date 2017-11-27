package kumagai.concert.crawler;

import java.util.HashMap;

/**
 * 全角→半角変換処理
 */
public class ZenkakuHankakuConverter
{
	static private final HashMap<Character, Character> table;

	static
	{
		table = new HashMap<Character, Character>();
		table.put('：', ':');
	}

	/**
	 * 全角→半角変換。
	 * @param zenkaku zenkaku 全角文字列
	 * @return 半角文字列
	 */
	static public String ConvertZenkakuToHankaku(String zenkaku)
	{
		if (zenkaku == null)
		{
			// nullが指定された。

			return null;
		}

		StringBuffer builder = new StringBuffer();

		for (int i = 0; i < zenkaku.length(); i++)
		{
			if (table.containsKey(zenkaku.charAt(i)))
			{
				// テーブル分の変換対象。

				builder.append(table.get(zenkaku.charAt(i)));
			}
			else
			{
				// テーブル分の変換対象以外。

				if (zenkaku.charAt(i) >= '０' && zenkaku.charAt(i) <= '９')
				{
					// 全角数字。

					builder.append((char)('0' + zenkaku.charAt(i) - '０'));
				}
				else
				{
					// それ以外。

					builder.append(zenkaku.charAt(i));
				}
			}
		}

		return builder.toString();
	}
}
