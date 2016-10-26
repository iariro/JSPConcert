package kumagai.concert;

import java.sql.*;
import java.text.*;

/**
 * コンサート情報。
 * @author kumagai
 */
public class Concert
{
	static private final SimpleDateFormat formatDate;
	static private final SimpleDateFormat formatDate2;
	static private final SimpleDateFormat formatTime;

	/**
	 * 日付書式オブジェクトを構築。
	 */
	static
	{
		formatDate = new SimpleDateFormat();
		formatDate2 = new SimpleDateFormat();
		formatTime = new SimpleDateFormat();

		formatDate.applyPattern("yyyy/MM/dd(E)");
		formatDate2.applyPattern("yyyy/MM/dd");
		formatTime.applyPattern("HH:mm");
	}

	public final int id;
	public final String orchestra;
	public final String title;
	public final Date date;
	public final Time kaijou;
	public final Time kaien;
	public final int hallId;
	public final String hall;
	public final String ryoukin;
	public final KyokumokuCollection kyokumoku;
	public final ShutsuenCollection shutsuen;
	public final boolean listen;

	/**
	 * コンサートID取得。
	 * @return コンサートID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * オーケストラ名取得。
	 * @return オーケストラ名
	 */
	public String getOrchestra()
	{
		return orchestra;
	}

	/**
	 * コンサート名取得。
	 * @return コンサート名
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * 表示用日付取得。
	 * @return 日付
	 */
	public String getDate()
	{
		return formatDate.format(date);
	}

	/**
	 * 編集用日付取得。
	 * @return 日付
	 */
	public String getDate2()
	{
		return formatDate2.format(date);
	}

	/**
	 * 開場時間取得。
	 * @return 開場時間
	 */
	public String getKaijou()
	{
		return formatTime.format(kaijou);
	}

	/**
	 * 開演時間取得。
	 * @return 開演時間
	 */
	public String getKaien()
	{
		return formatTime.format(kaien);
	}

	/**
	 * ホールID取得。
	 * @return ホールID
	 */
	public int getHallId()
	{
		return hallId;
	}

	/**
	 * ホール名取得。
	 * @return ホール名
	 */
	public String getHall()
	{
		return hall;
	}

	/**
	 * 曲目情報取得。
	 * @return 曲目情報
	 */
	public KyokumokuCollection getKyokumoku()
	{
		return kyokumoku;
	}

	/**
	 * 出演情報取得。
	 * @return 出演情報
	 */
	public ShutsuenCollection getShutsuen()
	{
		return shutsuen;
	}

	/**
	 * コンサートを聴いているかを取得
	 * @return true=聴いている／false=いない
	 */
	public boolean isListen()
	{
		return listen;
	}

	/**
	 * ラスト曲を取得。
	 * @return ラスト曲
	 */
	public Kyokumoku getLastNumber()
	{
		return kyokumoku.get(kyokumoku.size() - 1);
	}

	/**
	 * DB取得値からオブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param result DB取得レコード
	 * @throws SQLException
	 */
	public Concert(Connection connection, ResultSet result)
		throws SQLException
	{
		id = result.getInt("concertid");
		orchestra = result.getString("playername");
		title = result.getString("concertname");
		date = result.getDate("date");
		kaijou = result.getTime("kaijou");
		kaien = result.getTime("kaien");
		hallId = result.getInt("hallid");
		hall = result.getString("hallname");
		ryoukin = result.getString("ryoukin");
		listen = result.getInt("listencount") > 0;

		kyokumoku = new KyokumokuCollection(connection, id);
		shutsuen = new ShutsuenCollection(connection, id);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return
			String.format(
				"%s %s %s %s %s %s %s %s\n%s\n%s",
				orchestra,
				title,
				date,
				kaijou,
				kaien,
				hall,
				ryoukin,
				listen,
				kyokumoku,
				shutsuen);
	}
}
