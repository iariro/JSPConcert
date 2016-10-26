package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import ktool.datetime.*;
import kumagai.concert.*;

/**
 * コンサート検索ページ表示用アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/concertsearch1.jsp")
public class ConcertSearch1Action
{
	public String startDay;
	public String endDay;
	public ArrayList<StringAndString> listeners;

	/**
	 * コンサート検索ページ表示用アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("concertsearch1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		DateTime start = new DateTime();
		DateTime end = new DateTime();

		start.setMonth(1);
		start.setDay(1);

		end.setMonth(12);
		end.setDay(31);

		startDay = start.toString();
		endDay = end.toString();

		Statement statement = connection.createStatement();
		ArrayList<StringAndNumber> listeners = new ListenerList(statement);
		statement.close();
		connection.close();

		this.listeners = new ArrayList<StringAndString>();

		this.listeners.add(new StringAndString("-", ""));

		for (StringAndNumber stringAndNumber : listeners)
		{
			this.listeners.add(
				new StringAndString(
					stringAndNumber.string,
					Integer.toString(stringAndNumber.number)));
		}

		return "success";
	}
}
