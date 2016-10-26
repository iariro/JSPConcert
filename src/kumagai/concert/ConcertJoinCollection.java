package kumagai.concert;

import kumagai.sql.*;

/**
 * コンサートテーブル用JOIN句コレクション。
 * @author kumagai
 */
public class ConcertJoinCollection
	extends JoinCollection
{
	/**
	 * コンサートテーブル用JOIN句コレクションを構築する。
	 * @param kyokumoku true=曲目テーブルを結合する／false=しない
	 * @param listen true=来場者テーブルを結合する／false=しない
	 */
	public ConcertJoinCollection(boolean kyokumoku, boolean listen)
	{
		add(new Join("Shutsuen", "concertId", "Concert", "id"));
		add(new Join("Player", "id", "Shutsuen", "playerId"));

		if (kyokumoku)
		{
			// 曲目の結合あり。

			add(new Join("Kyokumoku", "concertId", "Concert", "id"));
			add(new Join("Composer", "id", "Kyokumoku", "composerId"));
		}

		add(new Join("Hall", "id", "Concert", "hallId"));

		if (listen)
		{
			// 来場者の結合あり。

			add(new Join("Listen", "concertId", "Concert", "id"));
			add(new Join("Listener", "id", "Listen", "listenerId"));
		}
	}
}
