package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import ktool.datetime.DateTime;
import kumagai.concert.ConcertCollection;

/**
 * 更新日ごとの登録件数表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/concertcountgraph.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class ConcertCountAction
{
	public LinkedHashMap<DateTime, Integer> concertCount;
	public String message;

	/**
	 * hiChartsグラフ用座標文字列を取得
	 * @return hiChartsグラフ用座標文字列
	 */
	public String getConcertCountPoints()
	{
		StringBuffer highChartsString = new StringBuffer();
		highChartsString.append("{name: '件数',data: [");
		int count = 0;

		for (Map.Entry<DateTime, Integer> entry : concertCount.entrySet())
		{
			if (count > 0)
			{
				// ２個目以降

				highChartsString.append(",");
			}
			highChartsString.append(String.format("[Date.parse('%s'), %d]", entry.getKey(), entry.getValue()));
			count++;
		}
		highChartsString.append("]}");

		return highChartsString.toString();
	}

	/**
	 * 更新日ごとの登録件数表示アクション。
	 * @return 処理結果
	 */
	@Action("concertcount")
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
				concertCount = ConcertCollection.getConcertCountPerUpdateDate(connection, statement);
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
