package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサート編集アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editconcert5.jsp")
public class EditConcert5Action
{
	public int concertId;
	public int hallId;
	public String date;
	public String kaijou;
	public String kaien;
	public String ryoukin;

	/**
	 * コンサート編集アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editconcert5")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		ConcertCollection.update
			(connection, concertId, date, kaijou, kaien, hallId, ryoukin);

		connection.close();

		return "success";
	}
}
