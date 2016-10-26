package kumagai.concert.struts2;

import java.sql.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * URLマネージャデータファイルとSQL Server DBとの照合アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/umncheck.jsp")
public class UmnCheckAction
{
	public String umnFilePath;
	public UmnCheckResult result;

	/**
	 * URLマネージャデータファイルとSQL Server DBとの照合アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("umncheck")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		umnFilePath = context.getInitParameter("commonUmnPath");

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		result = new UmnCheckResult(connection, umnFilePath);

		connection.close();

		return "success";
	}
}
