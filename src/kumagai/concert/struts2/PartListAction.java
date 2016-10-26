package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * パートリスト表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/partlist.jsp")
public class PartListAction
{
	public ArrayList<StringAndNumber> partList;

	/**
	 * パートリスト表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("partlist")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		ResultSet results =
			statement.executeQuery(
				"select Part.name, count(*) as count from Part join Shutsuen on partid=Part.id group by Part.name order by count(*) desc");

		partList = new ArrayList<StringAndNumber>();

		while (results.next())
		{
			partList.add(
				new StringAndNumber(
					results.getString("name"),
					results.getInt("count")));
		}

		results.close();
		statement.close();
		connection.close();

		return "success";
	}
}
