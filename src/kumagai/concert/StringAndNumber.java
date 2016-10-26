package kumagai.concert;

/**
 * 文字列と数字の対からなる汎用型。
 * @author kumagai
 */
public class StringAndNumber
{
	public final String string;
	public final int number;

	/**
	 * 文字列を取得。
	 * @return 文字列
	 */
	public String getString()
	{
		return string;
	}

	/**
	 * 数値を取得。
	 * @return 数値
	 */
	public int getNumber()
	{
		return number;
	}

	/**
	 * 指定の値を割り当てオブジェクトを構築する。
	 * @param string 文字列
	 * @param number 数値
	 */
	public StringAndNumber(String string, int number)
	{
		this.string = string;
		this.number = number;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return String.format("%d:%s", number, string);
	}
}
