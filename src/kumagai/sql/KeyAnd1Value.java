package kumagai.sql;

import javax.servlet.http.*;

/**
 * キーと値１個の組のデータ。
 */
public abstract class KeyAnd1Value
	extends KeyAndValue
{
	protected String value;

	/**
	 * オブジェクトの構築とともにメンバの初期化，条件指定の判断を行う。
	 * @param key キー
	 * @param value 値
	 */
	public KeyAnd1Value(String key, String value)
	{
		super(key);

		this.value = value;

		if (this.value != null)
		{
			// nullではない。

			if (this.value.length() > 0)
			{
				// 空文字列ではない。

				valid = true;
			}
		}
	}

	/**
	 * オブジェクトの構築とともにSevletリクエストによりクラスの初期化を行う。
	 * @param key キー名
	 * @param request リクエスト
	 * @param parameterName 値用パラメータ名
	 */
	public KeyAnd1Value
		(String key, HttpServletRequest request, String parameterName)
	{
		this(key, request.getParameter(parameterName));
	}
}
