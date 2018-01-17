package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.concert.ConcertCollection;
import kumagai.concert.ConcertRangeCollection;

/**
 * 全オーケストラの全コンサートの期間表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/concertchronology.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class ConcertChronologyGraphAction
{
	public String concertRanges;
	public String message;

	/**
	 * 全オーケストラの全コンサートの期間表示アクション。
	 * @return 処理結果
	 */
	@Action("concertchronology")
	public String execute()
		throws Exception
	{
		try
		{
			ServletContext context = ServletActionContext.getServletContext();

			String dbUrl = context.getInitParameter("ConcertSqlserverUrl");
			if (dbUrl != null)
			{
				// パラメータあり

				DriverManager.registerDriver(new SQLServerDriver());
				Connection connection = DriverManager.getConnection(dbUrl);
				Statement statement = connection.createStatement();
				ConcertRangeCollection concertRanges =
					ConcertCollection.getAllConcertRange(connection, statement);
				this.concertRanges = concertRanges.generateHighchartsSeries();
				statement.close();
				connection.close();

				return "success";
			}
			else
			{
				// パラメータなし

				message = "ConcertSqlserverUrl定義なし";
			}
		}
		catch (Exception exception)
		{
			// 何もしない

			message = exception.toString();
		}

		return "error";
	}
}
