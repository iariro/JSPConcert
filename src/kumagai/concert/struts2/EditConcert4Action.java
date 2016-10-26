package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集 - 出演情報追加アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert4.jsp")
public class EditConcert4Action
{
	public int concertId;
	public int playerId;
	public int partId;

	/**
	 * コンサート編集 - 出演情報追加アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert4")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ShutsuenCollection.insertShutsuen
			(connection, concertId, playerId, partId);

		connection.close();

		return "success";
	}
}
