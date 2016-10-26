package kumagai.sql;

import javax.servlet.http.*;

/**
 * 月日の条件指定。
 * @author kumagai
 */
public class MonthAndDay
	extends KeyAnd2Values
{
	/**
	 * オブジェクトの構築とともにServletリクエストにより基底クラスの初期化を行
	 * う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName1 値１用パラメータ
	 * @param parameterName2 値２用パラメータ
	 */
	public MonthAndDay(String key, HttpServletRequest request,
		String parameterName1, String parameterName2)
	{
		super(key, request, parameterName1, parameterName2);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return valid ?
			String.format(
				"extract (month from %s) = %s and extract (day from %s) = %s",
				key,
				value1,
				key,
				value2) :
			new String();
	}
}
