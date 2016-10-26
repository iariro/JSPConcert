package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集 - 曲目編集アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert7.jsp")
public class EditConcert7Action
{
	public int concertId;
	public int composerId;
	public String concert;
	public String composer;
	public String title1;
	public String title2;

	/**
	 * コンサート編集 - 曲目編集アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert7")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		KyokumokuCollection.updateTitle
			(connection, concertId, composerId, title1, title2);

		connection.close();

		return "success";
	}
}
