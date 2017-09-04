package kumagai.concert.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列ユーティリティ
 * @author kumagai
 */
public class DateUtility
{
	static private final Pattern pattermSeireki = Pattern.compile("([0-9]{4})[/／年]([0-9]{1,2})[/／月]([0-9]{1,2})[／日]*");
	static private final Pattern pattermWareki = Pattern.compile("平成([0-9]*)[/／年]([0-9]{1,2})[/／月]([0-9]{1,2})[／日]*");

	/**
	 * 日付文字列の整形
	 * @param date 対象の文字列
	 * @return 整形した日付文字列
	 */
	static public String trimDate(String date)
	{
		date = changeNumFullToHalf(date);

		Matcher matcher = pattermSeireki.matcher(date);
		if (matcher.matches())
		{
			return changeNumFullToHalf(String.format("%s/%s/%s", matcher.group(1), matcher.group(2), matcher.group(3)));
		}
		matcher = pattermWareki.matcher(date);
		if (matcher.matches())
		{
			int year = Integer.valueOf(matcher.group(1));
			return changeNumFullToHalf(String.format("%s/%s/%s", year + 1988, matcher.group(2), matcher.group(3)));
		}
		return null;
	}

	/**
	 * 全角数字を半角数字に変換
	 * @param str 変換元文字列
	 * @return 変換済み文字列
	 */
	public static String changeNumFullToHalf(String str)
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
