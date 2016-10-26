package kumagai.sql;

/**
 * key > valueの条件。
 * @author kumagai
 */
public class KeyGreaterEqualValue
	extends KeyAnd1Value
{
	/**
	 * オブジェクトの構築とともに基底クラスの初期化を行う。
	 * @param key キー
	 * @param value 値
	 */
	public KeyGreaterEqualValue(String key, String value)
	{
		super(key, value);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return valid ? String.format("%s >= %s", key, value) : null;
	}
}
