package kumagai.sql;

/**
 * キーと値の組のデータ。
 * @author kumagai
 */
public class KeyAndValue
{
	protected boolean valid;
	protected String key;

	/**
	 * オブジェクトの構築とともにメンバの初期化を行う。
	 * @param key キー
	 */
	public KeyAndValue(String key)
	{
		this.key = key;
		this.valid = false;
	}
}
