package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import ktool.datetime.DateTime;
import kumagai.concert.ConcertCollection;

/**
 * 更新日ごとの登録件数表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/concertcount.jsp")
public class ConcertCountAction
{
	public TreeMap<DateTime,Integer> concertCount;

	/**
	 * 更新日ごとの登録件数表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("concertcount")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));
		Statement statement = connection.createStatement();
		concertCount = ConcertCollection.getConcertCountPerUpdateDate(connection, statement);
		statement.close();
		connection.close();

		return "success";
	}
}
