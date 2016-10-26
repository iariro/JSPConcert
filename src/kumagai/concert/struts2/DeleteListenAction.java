package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 来場情報の削除アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/deletelisten.jsp")
public class DeleteListenAction
{
	public String concertId;
	public String listenerId;

	/**
	 * 来場情報の削除アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("deletelisten")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ListenCollection.deleteListen(
			connection,
			Integer.valueOf(concertId),
			Integer.valueOf(listenerId));

		connection.close();

		return "success";
	}
}
