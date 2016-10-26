package kumagai.concert;

import java.net.*;
import java.sql.*;
import java.util.*;
import ktool.datetime.*;
import kumagai.urlmanager.*;

/**
 * UMNチェック結果。
 * @author kumagai
 */
public class UmnCheckResult
{
	public ArrayList<String> diffUrl;
	public ArrayList<String> dbNoUrl;
	public ArrayList<String> noUmn;
	public ArrayList<String> noDb;
	public ArrayList<UmnItem> old;

	/**
	 * UMNチェックを行い結果オブジェクトを構築する。
	 * @param connection DB接続オブジェクト
	 * @param umnFilePath UMNファイルパス
	 */
	public UmnCheckResult(Connection connection, String umnFilePath)
		throws Exception
	{
		Statement statement = connection.createStatement();
		ArrayList<Player> playerList = new PlayerList(statement);

		UmnXmlDocument document = new UmnXmlDocument(umnFilePath);

		ArrayList<UmnItem> items = document.getUmnItem("Hobby/Music/Classic");

		diffUrl = new ArrayList<String>();
		dbNoUrl = new ArrayList<String>();
		noUmn = new ArrayList<String>();
		noDb = new ArrayList<String>();
		old = new ArrayList<UmnItem>();

		for (Player player : playerList)
		{
			boolean find = false;

			for (UmnItem item : items)
			{
				if (player.name.equals(item.getTitle()))
				{
					// オーケストラ名一致。

					if (player.siteurl != null)
					{
						// URLはある。

						// %80のような表現をデコード。
						String urlDb =
							URLDecoder.decode(player.siteurl, "utf-8");
						String urlUmn =
							URLDecoder.decode(item.getUrl(), "utf-8");

						if (! urlDb.equals(urlUmn))
						{
							// URL不一致。

							diffUrl.add(player.name);
						}
					}
					else
					{
						// DBにURLなし。

						dbNoUrl.add(player.name);
					}

					find = true;
					break;
				}
			}

			if (! find)
			{
				// UMNに見つからなかった。

				noUmn.add(player.name);
			}
		}

		DateTime now = new DateTime();

		for (UmnItem item : items)
		{
			DateTime datetime = DateTime.parseDateString(item.getWatch());

			if (now.diff(datetime).getDay() >= 365)
			{
				// １年以上アクセスなし。

				old.add(item);
			}
		}

		// UMN上のフォルダパスでソート。
		Collections.sort(
			old,
			new Comparator<UmnItem>()
			{
				public int compare(UmnItem item1, UmnItem item2)
				{
					return item1.getFolderPath().compareTo
						(item2.getFolderPath());
				}
			});

		for (UmnItem item : items)
		{
			boolean find = false;

			for (Player player : playerList)
			{
				if (player.name.equals(item.getTitle()))
				{
					// オーケストラ名一致。

					find = true;
					break;
				}
			}

			if (! find)
			{
				// DBに見つからなかった。

				noDb.add(item.getTitle());
			}
		}
	}

	/**
	 * 全エラー項目の件数を取得。
	 * @return 全エラー項目の件数
	 */
	public int getSize()
	{
		return diffUrl.size() + dbNoUrl.size() + noUmn.size() + noDb.size();
	}

	/**
	 * １年以上アクセスがないエントリの個数を取得。
	 * @return １年以上アクセスがないエントリの個数を取得。
	 */
	public int getOldCount()
	{
		return old.size();
	}
}
