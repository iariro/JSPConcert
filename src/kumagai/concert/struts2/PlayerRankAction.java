package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 演奏者ランキング表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/playerrank.jsp")
public class PlayerRankAction
{
	public ArrayList<StringAndNumber> ranking;

	/**
	 * ランキング情報件数を取得。
	 * @return ランキング情報件数
	 */
	public int getSize()
	{
		return ranking.size();
	}

	/**
	 * 演奏者ランキング表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("playerrank")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		ranking = new PlayerRanking(statement);

		statement.close();
		connection.close();

		return "success";
	}
}
