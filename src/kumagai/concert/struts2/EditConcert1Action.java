package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集ページ表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert1.jsp")
public class EditConcert1Action
{
	public int id;
	public Concert concert;
	public ArrayList<Hall> halls;
	public ArrayList<StringAndNumber> allListeners;
	public ArrayList<StringAndNumber> listeners;
	public ArrayList<StringAndNumber> composers;
	public ArrayList<StringAndNumber> parts;
	public ArrayList<StringAndNumber> players;
	public int listenersLength;

	/**
	 * コンサート編集ページ表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement1 = connection.createStatement();

		IdCollection idCollection = new IdCollection();

		idCollection.add(Integer.valueOf(id));

		ConcertCollection concertCollection =
			new ConcertCollection(connection, statement1, idCollection);

		if (concertCollection.size() == 1)
		{
			// コンサート情報は１件。

			concert = concertCollection.get(0);

			listeners =
				ListenerList.getListenerIdByConcertId
					(connection, concertCollection.get(0).id);

			listenersLength = listeners.size();
		}

		halls = new HallList(statement1, 0);
		allListeners = new ListenerList(statement1);
		composers = new ComposerCollection(statement1);
		parts = new PartCollection(statement1);
		players = PlayerList.getAll(statement1);

		statement1.close();
		connection.close();

		return "success";
	}
}
