package kumagai.sql;

import javax.servlet.http.*;

/**
 * key=valueの条件。
 * @author kumagai
 */
public class KeyEqualValue
	extends KeyAnd1Value
{
	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName 値用パラメータ名
	 */
	public KeyEqualValue
		(String key, HttpServletRequest request, String parameterName)
	{
		super(key, request, parameterName);
	}

	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param value 値
	 */
	public KeyEqualValue(String key, Object value)
	{
		super(key, value.toString());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return valid ? String.format("%s = \'%s\'", key, value) : new String();
	}
}
