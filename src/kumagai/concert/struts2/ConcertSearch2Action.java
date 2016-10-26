package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.sql.*;
import kumagai.concert.*;

/**
 * コンサート検索アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/concertsearch2.jsp")
public class ConcertSearch2Action
{
	public String player;
	public String composer;
	public String title;
	public String hall;
	public String address;
	public String startDate;
	public String endDate;
	public String listenerId;
	public int composerRank;
	public int originalSize;

	public ConcertCollection concertCollection;

	/**
	 * 作曲家の指定の有無を取得。
	 * @return true=指定あり／false=なし
	 */
	public boolean hasComposer()
	{
		if (composer != null)
		{
			// 作曲家の指定あり。

			return composer.length() > 0;
		}
		else
		{
			// 作曲家の指定なし。

			return false;
		}
	}

	/**
	 * タイトルの指定の有無を取得。
	 * @return true=指定あり／false=なし
	 */
	public boolean hasTitle()
	{
		if (title != null)
		{
			// タイトルの指定あり。

			return title.length() > 0;
		}
		else
		{
			// タイトルの指定あり。

			return false;
		}
	}

	/**
	 * 来場者の指定の有無を取得。
	 * @return true=指定あり／false=なし
	 */
	public boolean hasListener()
	{
		if (listenerId != null)
		{
			// 指定されている。

			return listenerId.length() > 0;
		}
		else
		{
			// 指定されていない。

			return false;
		}
	}

	/**
	 * コンサート検索結果件数を取得。
	 * @return コンサート検索結果件数
	 */
	public int getSize()
	{
		return concertCollection.size();
	}

	/**
	 * コンサート検索アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("concertsearch2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement1 = connection.createStatement();

		WhereString where = new ConcertSearchWhereString(this);

		JoinCollection join =
			new ConcertJoinCollection
				(hasComposer() || hasTitle(), hasListener());

		IdCollection idCollection =
			new ConcertIdCollection(statement1, join, where);

		concertCollection =
			new ConcertCollection(connection, statement1, idCollection);

		originalSize = concertCollection.size();

		for (int i=concertCollection.size()-1 ; i>=0 ; i--)
		{
			int maxRank = 0;

			for (Kyokumoku kyoku : concertCollection.get(i).kyokumoku)
			{
				if (kyoku.rank > maxRank)
				{
					// 現状の最大を上回る

					maxRank = kyoku.rank;
				}
			}

			if (maxRank < composerRank)
			{
				// 指定のランクに満たない

				concertCollection.remove(i);
			}
		}

		return "success";
	}
}
