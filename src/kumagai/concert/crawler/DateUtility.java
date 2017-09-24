package kumagai.concert.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列ユーティリティ
 * @author kumagai
 */
public class DateUtility
{
	static public final Pattern patternSeirekiHalf =
		Pattern.compile(".*([0-9]{4}) *[/／年\\.][ ）]*([0-9]{1,2}) *[/／月\\.] *([0-9]{1,2}) *[／日]*.*");
	static public final Pattern patternSeirekiFull =
		Pattern.compile(".*([0-9０-９]{4}) *[/／年\\.][ ）]*([0-9０-９]{1,2}) *[/／月\\.] *([0-9０-９]{1,2}) *[／日]*.*");
	static public final Pattern patternSeireki2keta =
		Pattern.compile(".*'([0-9]{2})/([0-9]{1,2})/([0-9]{1,2}).*");
	static public final Pattern patternWarekiHalf =
		Pattern.compile(".*平成([0-9]*)[/／年] *([0-9]{1,2})[/／月] *([0-9]{1,2})[／日]*.*");
	static public final Pattern patternWarekiFull =
		Pattern.compile(".*平成([０-９]*)[/／年] *([０-９]{1,2})[/／月] *([０-９]{1,2})[／日]*.*");

	/**
	 * 日付文字列の整形
	 * @param date 対象の文字列
	 * @return 整形した日付文字列
	 */
	static public String trimDate(String date)
	{
		date = changeNumFullToHalf(date);

		Matcher matcher = patternSeirekiHalf.matcher(date);
		if (matcher.matches())
		{
			int year = Integer.valueOf(matcher.group(1));
			int month = Integer.valueOf(matcher.group(2));
			int day = Integer.valueOf(matcher.group(3));
			return changeNumFullToHalf(String.format("%04d/%02d/%02d", year, month, day));
		}
		matcher = patternWarekiHalf.matcher(date);
		if (matcher.matches())
		{
			int year = Integer.valueOf(matcher.group(1));
			int month = Integer.valueOf(matcher.group(2));
			int day = Integer.valueOf(matcher.group(3));
			return changeNumFullToHalf(String.format("%04d/%02d/%02d", year + 1988, month, day));
		}
		matcher = patternSeireki2keta.matcher(date);
		if (matcher.matches())
		{
			int year = Integer.valueOf(matcher.group(1));
			int month = Integer.valueOf(matcher.group(2));
			int day = Integer.valueOf(matcher.group(3));
			return changeNumFullToHalf(String.format("%04d/%02d/%02d", year + 2000, month, day));
		}
		return null;
	}

	/**
	 * 全角数字を半角数字に変換
	 * @param str 変換元文字列
	 * @return 変換済み文字列
	 */
	static public String changeNumFullToHalf(String str)
	{
		String result = null;
		if (str != null)
		{
			StringBuilder sb = new StringBuilder(str);
			for (int i = 0; i < sb.length(); i++)
			{
				int c = (int)sb.charAt(i);
				if (c >= '０' && c <= '９')
				{
					sb.setCharAt(i, (char)(c - '０' + '0'));
				}
			}
			result = sb.toString();
		}
		return result;
	}
}
