package kumagai.concert.struts2;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.concert.*;

/**
 * コンサートが終了したオーケストラのリスト表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/pastorchestra.jsp")
public class PastOrchestraAction
{
	public ArrayList<StringAndStringAndDate> pastOrchestraList1;
	public ArrayList<StringAndStringAndDate> pastOrchestraList2;
	public ArrayList<StringAndStringAndDate> pastOrchestraList3;

	/**
	 * リスト１のサイズを取得。
	 * @return リストのサイズ
	 */
	public int getSize1()
	{
		return pastOrchestraList1.size();
	}

	/**
	 * リスト２のサイズを取得。
	 * @return リストのサイズ
	 */
	public int getSize2()
	{
		return pastOrchestraList2.size();
	}

	/**
	 * リスト３のサイズを取得。
	 * @return リストのサイズ
	 */
	public int getSize3()
	{
		return pastOrchestraList3.size();
	}

	/**
	 * コンサートが終了したオーケストラのリスト表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("pastorchestra")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		pastOrchestraList1 = new PastOrchestraList1(statement);
		pastOrchestraList2 = new PastOrchestraList2(connection, false);
		pastOrchestraList3 = new PastOrchestraList2(connection, true);

		statement.close();
		connection.close();

		return "success";
	}
}
