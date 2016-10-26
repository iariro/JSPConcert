package kumagai.sql;

import javax.servlet.http.*;

/**
 * BETWEEN句。
 */
public class Between
	extends KeyAnd2Values
{
	private final boolean quote;

	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param start 値１
	 * @param end 値２
	 */
	public Between(String key, String start, String end)
	{
		super(key, start, end);

		quote = true;
	}

	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param start 値１
	 * @param end 値２
	 */
	public Between(String key, int start, int end)
	{
		super(key, Integer.toString(start), Integer.toString(end));

		this.quote = false;
	}

	/**
	 * オブジェクトの構築とともにServletリクエストにより基底クラスの初期化を行う。
	 * @param key キー
	 * @param request リクエスト
	 * @param parameterName1 値１用パラメータ名
	 * @param parameterName2 値２用パラメータ名
	 */
	public Between(String key, HttpServletRequest request,
		String parameterName1, String parameterName2)
	{
		super(key, request, parameterName1, parameterName2);

		this.quote = true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if (valid)
		{
			// 有効。

			if (quote)
			{
				// クオートを付加する。

				return String.format("%s between \'%s\' and \'%s\'", key, value1, value2);
			}
			else
			{
				// クオートを付加しない。

				return String.format("%s between %s and %s", key, value1, value2);
			}
		}
		else
		{
			// 無効。

			return new String();
		}
	}
}
