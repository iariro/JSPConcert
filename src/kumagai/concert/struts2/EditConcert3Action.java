package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集 - 曲目追加アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert3.jsp")
public class EditConcert3Action
{
	public int concertId;
	public int composerId;
	public String title;

	/**
	 * コンサート編集 - 曲目追加アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert3")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		KyokumokuCollection.insertKyokumoku
			(connection, concertId, composerId, title);

		connection.close();

		return "success";
	}
}
