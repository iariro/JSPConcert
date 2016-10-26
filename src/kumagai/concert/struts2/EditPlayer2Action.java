package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 演奏者情報編集アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editplayer2.jsp")
public class EditPlayer2Action
{
	public int id;
	public String name;
	public String siteurl;
	public boolean active;

	/**
	 * 演奏者情報編集アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editplayer2")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		PlayerList.update(connection, id, name, siteurl, active);

		connection.close();

		return "success";
	}
}
