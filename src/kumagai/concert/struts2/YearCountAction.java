package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 年ごとのコンサート件数カウントアクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/monthlycountview.jsp")
public class YearCountAction
{
	public ArrayList<MonthlyCountPerYear> monthCountCollection;

	/**
	 * 年ごとのコンサート件数カウントアクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("yearcount")
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
			"select year(concert.date) as year, month(concert.date) as month, count(distinct concert.id) as count from concert join listen on concert.id=listen.concertid group by year(concert.date), month(concert.date) order by year(concert.date) desc";

		monthCountCollection = new MonthlyCountCollection(statement, sql);

		statement.close();
		connection.close();

		return "success";
	}
}
