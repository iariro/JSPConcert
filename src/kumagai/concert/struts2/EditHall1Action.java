package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * ホール情報編集ページ表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/edithall1.jsp")
public class EditHall1Action
{
	public int hallId;
	public String name;
	public String address;
	public int capacity;

	/**
	 * ホール情報編集ページ表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("edithall1")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		Hall hall = HallList.getHallById(statement, hallId);

		statement.close();
		connection.close();

		name = hall.name;
		address = hall.address;
		capacity = hall.capacity;

		return "success";
	}
}
