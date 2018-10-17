package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.concert.Concert;
import kumagai.concert.ConcertCollection;

/**
 * 直近追加のコンサート情報表示アクション
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/recentaddedconcert.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class RecentAddedConcertAction
{
	public LinkedHashMap<String,ArrayList<Concert>> concerts;
	public String message;

	/**
	 * 直近追加のコンサート情報表示アクション
	 * @return 処理結果
	 */
	@Action("recentaddedconcert")
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
				concerts = ConcertCollection.getRecentAddedConcerts(connection, 3);
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
