package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * 作曲家リスト表示用アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/composerlist.jsp")
public class ComposerListAction
{
	public ArrayList<ComposerIdNameCount> composers;

	/**
	 * 作曲家リストのサイズを取得。
	 * @return 作曲家リストのサイズ
	 */
	public int getSize()
	{
		return composers.size();
	}

	/**
	 * コンサート検索ページ表示用アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("composerlist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		composers = ComposerCollection.getComposerAndCount(statement);

		statement.close();
		connection.close();

		return "success";
	}
}
