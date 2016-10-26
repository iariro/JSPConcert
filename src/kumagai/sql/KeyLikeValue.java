package kumagai.sql;

import javax.servlet.http.*;

/**
 * LIKEによる条件。
 * @author kumagai
 */
public class KeyLikeValue
	extends KeyAnd1Value
{
	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param value 値
	 */
	public KeyLikeValue(String key, String value)
	{
		super(key, value);
	}

	/**
	 * オブジェクトの構築とともにServletリクエストにより基底クラスの初期化を行
	 * う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName パラメータ名
	 */
	public KeyLikeValue
		(String key, HttpServletRequest request, String parameterName)
	{
		super(key, request, parameterName);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%s like \'%%%s%%\'", key, value);
	}
}
