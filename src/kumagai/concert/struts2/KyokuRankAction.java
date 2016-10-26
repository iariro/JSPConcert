package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 曲を聴いた回数ランキング表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/kyokurank.jsp")
public class KyokuRankAction
{
	public ArrayList<StringAndNumber> ranking;

	/**
	 * 現在日時を取得。
	 * @return 現在日時
	 */
	public String getDateTime()
	{
		return new Date().toString();
	}

	/**
	 * 曲を聴いた回数ランキング表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("kyokurank")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ranking = new KyokumokuRanking(connection, 2);

		connection.close();

		return "success";
	}
}
