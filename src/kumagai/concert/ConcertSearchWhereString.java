package kumagai.concert;

import kumagai.sql.*;
import kumagai.concert.struts2.*;

/**
 * コンサートテーブル用WHERE句コレクション。
 * @author kumagai
 */
public class ConcertSearchWhereString
	extends WhereString
{
	/**
	 * 指定の値からWHERE句コレクションを構築する。
	 * @param player 演奏者
	 * @param composer 作曲家
	 * @param title 曲名
	 * @param hall ホール名
	 * @param address ホール住所
	 * @param startDate 検索範囲開始日
	 * @param endDate 検索範囲終了日
	 * @param listenerId 来場者ID
	 */
	public ConcertSearchWhereString(String player, String composer,
		String title, String hall, String address, String startDate,
		String endDate, int listenerId)
	{
		add(new KeyLikeValue("Player.name", player));
		add(new KeyLikeValue("Composer.name", composer));
		add(new KeyLikeValue("Kyokumoku.title", title));
		add(new KeyLikeValue("Hall.name", hall));
		add(new KeyLikeValue("Hall.address", address));
		add(new Between("Concert.date", startDate, endDate));
		add(new KeyEqualValue("Listener.id", Integer.toString(listenerId)));
	}

	/**
	 * Struts2のリクエストからWHERE句コレクションを構築する。
	 * @param action コンサート検索アクションオブジェクト
	 */
	public ConcertSearchWhereString(ConcertSearch2Action action)
	{
		add(new KeyLikeValue("Player.name", action.player));
		add(new KeyLikeValue("Composer.name", action.composer));
		add(new KeyLikeValue("Kyokumoku.title", action.title));
		add(new KeyLikeValue("Hall.name", action.hall));
		add(new KeyLikeValue("Hall.address", action.address));
		add(new Between("Concert.date", action.startDate, action.endDate));
		add(new KeyEqualValue("Listener.id", action.listenerId));
	}
}
