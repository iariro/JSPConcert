package kumagai.sql;

import javax.servlet.http.*;

/**
 * ILIKEによる条件。
 * @author kumagai
 */
public class KeyILikeValue
	extends KeyAnd1Value
{
	/**
	 * オブジェクトの構築とともにServletリクエストにより基底クラスの初期化を行
	 * う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName 値用パラメータ名
	 */
	public KeyILikeValue
		(String key, HttpServletRequest request, String parameterName)
	{
		super(key, request, parameterName);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%s ilike \'%%%s%%\'", key, value);
	}
}
