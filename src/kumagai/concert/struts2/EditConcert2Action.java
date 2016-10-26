package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集 - 来場情報追加アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert2.jsp")
public class EditConcert2Action
{
	public int concertId;
	public int listenerId;

	/**
	 * コンサート編集 - 来場情報追加アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ListenCollection.insertListen(connection, concertId, listenerId);

		connection.close();

		return "success";
	}
}
