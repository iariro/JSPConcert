package kumagai.concert;

/**
 * ２つの文字列からなる汎用型。
 * @author kumagai
 */
public class StringAndString
{
	public final String string1;
	public String string2;

	/**
	 * 文字列１を取得。
	 * @return 文字列１
	 */
	public String getString1()
	{
		return string1;
	}

	/**
	 * 文字列２を取得。
	 * @return 文字列２
	 */
	public String getString2()
	{
		return string2;
	}

	/**
	 * 指定の値を割り当てオブジェクトを構築する。
	 * @param string1 文字列１
	 * @param string2 文字列２
	 */
	public StringAndString(String string1, String string2)
	{
		this.string1 = string1;
		this.string2 = string2;
	}
}
