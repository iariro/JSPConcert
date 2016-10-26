package kumagai.sql;

import javax.servlet.http.*;

/**
 * キーと値２個の組のデータ。
 */
public class KeyAnd2Values
	extends KeyAndValue
{
	protected String value1, value2;

	/**
	 * オブジェクトの構築とともにメンバの初期化，条件指定の判断を行う。
	 * @param key キー
	 * @param value1 値１
	 * @param value2 値２
	 */
	public KeyAnd2Values(String key, String value1, String value2)
	{
		super(key);

		this.value1 = value1;
		this.value2 = value2;

		if (value1 != null && value2 != null)
		{
			// nullではない。

			if (value1.length() > 0 && value2.length() > 0)
			{
				// 空文字列ではない。

				valid = true;
			}
		}
	}

	/**
	 * オブジェクトの構築とともにSevletリクエストによりクラスの初期化を行う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName1 値１用パラメータ名
	 * @param parameterName2 値２用パラメータ名
	 */
	public KeyAnd2Values(String key, HttpServletRequest request,
		String parameterName1, String parameterName2)
	{
		this(
			key,
			request.getParameter(parameterName1),
			request.getParameter(parameterName2));
	}
}
