package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.sql.*;
import kumagai.concert.*;

/**
 * 今後開催予定コンサート表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/concertsearch2.jsp")
public class KongoConcertAction
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
	 * コンサート検索結果件数を取得。
	 * @return コンサート検索結果件数
	 */
	public int getSize()
	{
		return concertCollection.size();
	}

	/**
	 * 今後開催予定コンサート表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("kongoconcert")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement1 = connection.createStatement();

		WhereString where = new KongoConcertWhereString();
		JoinCollection join = new ConcertJoinCollection(true, false);

		IdCollection idCollection =
			new ConcertIdCollection(statement1, join, where);
		concertCollection =
			new ConcertCollection(connection, statement1, idCollection);

		statement1.close();
		connection.close();

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
