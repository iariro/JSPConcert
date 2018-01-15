package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.concert.Concert;
import kumagai.concert.ConcertCollection;
import kumagai.concert.ConcertIdCollection;
import kumagai.concert.ConcertJoinCollection;
import kumagai.concert.IdCollection;
import kumagai.sql.Between;
import kumagai.sql.JoinCollection;
import kumagai.sql.KeyEqualValue;
import kumagai.sql.WhereString;

/**
 * 直近の来場予定コンサート表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/recentconcert.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class RecentConcertAction
{
	public ArrayList<Concert> concerts;
	public String message;

	/**
	 * 直近の来場予定コンサート表示アクション。
	 * @return 処理結果
	 */
	@Action("recentconcert")
	public String execute()
		throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String dbUrl = context.getInitParameter("ConcertSqlserverUrl");
			if (dbUrl != null)
			{
				// パラメータあり

				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection(dbUrl);
				Statement statement = connection.createStatement();

				WhereString where = new WhereString();
				where.add(new KeyEqualValue("shutsuen.partid", "1"));
				where.add(new Between("date-getdate()", 0, 30));

				JoinCollection join = new ConcertJoinCollection(false, true);

				IdCollection idCollection =
					new ConcertIdCollection(statement, join, where);

				concerts = new ConcertCollection(connection, statement, idCollection);

				statement.close();
				connection.close();

				return "success";
			}
			else
			{
				// パラメータなし

				message = "ConcertSqlserverUrl定義なし";
			}
		}
		catch (Exception exception)
		{
			// 何もしない

			message = exception.toString();
		}

		return "error";
	}
}
