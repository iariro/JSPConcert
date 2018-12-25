package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import kumagai.concert.ConcertCollection;
import kumagai.concert.KyokumokuCollection;
import kumagai.concert.ShutsuenCollection;

/**
 * コンサート削除アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Results
({
	@Result(name="success", location="/concert/deleteconcert.jsp"),
	@Result(name="error", location="/concert/error.jsp")
})
public class DeleteConcertAction
{
	public boolean sure;
	public int concertId;

	public String message;

	/**
	 * コンサート削除アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("deleteconcert")
	public String execute()
		throws Exception
	{
		if (!sure)
		{
			message = "「本当に削除する」をチェックして再度実行してください";
			return "error";
		}

		try
		{
			ServletContext context = ServletActionContext.getServletContext();
			String url = context.getInitParameter("ConcertSqlserverUrl");

			if (url != null)
			{
				// パラメータあり

				DriverManager.registerDriver(new SQLServerDriver());

				Connection connection = DriverManager.getConnection (url);

				KyokumokuCollection.deleteTitle(connection, concertId);
				ShutsuenCollection.deleteShutsuen(connection, concertId);
				ConcertCollection.delete(connection, concertId);

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
			message = exception.toString();
		}

		return "error";
	}
}
