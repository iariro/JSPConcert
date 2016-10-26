package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集 - 出演情報削除アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert6.jsp")
public class EditConcert6Action
{
	public int concertId;
	public int partId;
	public int playerId;

	/**
	 * コンサート編集 - 出演情報削除アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert6")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ShutsuenCollection.deleteShutsuen
			(connection, concertId, partId, playerId);

		connection.close();

		return "success";
	}
}
