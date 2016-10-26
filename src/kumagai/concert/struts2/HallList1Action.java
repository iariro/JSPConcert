package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * ホールリスト表示アクション。住所順。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/halllist.jsp")
public class HallList1Action
{
	public ArrayList<Hall> halls;

	/**
	 * ホール情報数取得。
	 * @return ホール情報数
	 */
	public int getSize()
	{
		return halls.size();
	}

	/**
	 * ホールリスト表示アクション。住所順。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("halllist1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		halls = new HallList(statement, 1);

		statement.close();
		connection.close();

		return "success";
	}
}
