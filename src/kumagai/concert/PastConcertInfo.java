package kumagai.concert;

/**
 * コンサート終了オーケストラ情報
 * @author kumagai
 */
public class PastConcertInfo
{
	public final Integer playerId;
	public final String orchestra;
	public final String url;
	public final String date;
	public final String encoding;

	/**
	 * 指定の値をメンバーに割り当て
	 * @param playerId 演奏者ID
	 * @param orchestra 楽団名
	 * @param url URL
	 * @param date 日付
	 * @param encoding エンコード名
	 */
	public PastConcertInfo(Integer playerId, String orchestra, String url, String date, String encoding)
	{
		this.playerId = playerId;
		this.orchestra = orchestra;
		this.url = url;
		this.date = date;
		this.encoding = encoding;
	}

	@Override
	public String toString()
	{
		return String.format("%s %s %s %s", orchestra, date, url, encoding);
	}
}
