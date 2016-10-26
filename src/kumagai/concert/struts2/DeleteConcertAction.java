package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート削除アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/deleteconcert.jsp")
public class DeleteConcertAction
{
	public int id;

	/**
	 * コンサート削除アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert8")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		KyokumokuCollection.deleteTitle(connection, id);
		ShutsuenCollection.deleteShutsuen(connection, id);
		ConcertCollection.delete(connection, id);

		return "success";
	}
}
