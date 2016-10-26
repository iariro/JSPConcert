package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * ホール情報編集アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/edithall2.jsp")
public class EditHall2Action
{
	public int hallId;
	public String name;
	public String address;
	public int capacity;

	/**
	 * ホール情報編集アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("edithall2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		HallList.updateTitle(connection, hallId, name, address, capacity);

		connection.close();

		return "success";
	}
}
