package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 月毎のコンサート回数表示用アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/monthlycountview.jsp")
public class MonthlyCountViewAction
{
	public ArrayList<MonthlyCountPerYear> monthCountCollection;

	/**
	 * 月毎のコンサート回数表示用アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("monthlycountview")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		String sql =
			"select year(date) as year, month(date) as month, count(*) as count from concert group by year(date), month(date) order by year(date) desc";

		monthCountCollection = new MonthlyCountCollection(statement, sql);

		statement.close();
		connection.close();

		return "success";
	}
}
