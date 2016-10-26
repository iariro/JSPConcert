package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.sql.*;
import kumagai.concert.*;

/**
 * トップページ表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/index.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class IndexAction
{
	public ArrayList<Concert> concerts;
	public Exception exception;

	/**
	 * トップページ表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("index")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		try
		{
			Connection connection =
				DriverManager.getConnection
					(context.getInitParameter("ConcertSqlserverUrl"));

			Statement statement1 = connection.createStatement();

			WhereString where = new WhereString();
			where.add(new KeyEqualValue("shutsuen.partid", "1"));
			where.add(new Between("date-getdate()", 0, 30));

			JoinCollection join = new ConcertJoinCollection(false, true);

			IdCollection idCollection =
				new ConcertIdCollection(statement1, join, where);

			concerts =
				new ConcertCollection(connection, statement1, idCollection);

			statement1.close();
			connection.close();

			return "success";
		}
		catch (SQLServerException exception)
		{
			this.exception = exception;

			return "error";
		}
	}
}
