package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * ホールランキング表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/hallrank.jsp")
public class HallRankAction
{
	public ArrayList<HallRanking> ranking;

	/**
	 * ランキング情報件数を取得。
	 * @return ランキング情報件数
	 */
	public int getSize()
	{
		return ranking.size();
	}

	/**
	 * ホールランキング表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("hallrank")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		ranking = new HallRankingCollection(statement);

		statement.close();
		connection.close();

		return "success";
	}
}
