package kumagai.concert.crawler;

/**
 * コンサート終了オーケストラ情報
 * @author kumagai
 */
public class PastConcertInfo
{
	public final String orchestra;
	public final String url;
	public final String date;

	/**
	 * 指定の値をメンバーに割り当て
	 * @param orchestra 楽団名
	 * @param url URL
	 * @param date 日付
	 */
	public PastConcertInfo(String orchestra, String url, String date)
	{
		this.orchestra = orchestra;
		this.url = url;
		this.date = date;
	}

	@Override
	public String toString()
	{
		return String.format("%s %s %s", orchestra, date, url);
	}
}
