package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 演奏者リスト表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/playerlist.jsp")
public class PlayerListAction
{
	public ArrayList<Player> playerList;

	/**
	 * 演奏者リストのサイズを取得。
	 * @return 演奏者リストのサイズ
	 */
	public int getSize()
	{
		return playerList.size();
	}

	/**
	 * 演奏者リスト表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("playerlist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		playerList = new PlayerList(statement);

		statement.close();
		connection.close();

		return "success";
	}
}
