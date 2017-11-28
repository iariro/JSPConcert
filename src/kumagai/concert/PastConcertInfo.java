package kumagai.concert;

/**
 * コンサート終了オーケストラ情報
 * @author kumagai
 */
public class PastConcertInfo
{
	public final String orchestra;
	public final String url;
	public final String date;
	public final String encode;

	/**
	 * 指定の値をメンバーに割り当て
	 * @param orchestra 楽団名
	 * @param url URL
	 * @param date 日付
	 * @param encode エンコード名
	 */
	public PastConcertInfo(String orchestra, String url, String date, String encode)
	{
		this.orchestra = orchestra;
		this.url = url;
		this.date = date;
		this.encode = encode;
	}

	@Override
	public String toString()
	{
		return String.format("%s %s %s", orchestra, date, url);
	}
}
