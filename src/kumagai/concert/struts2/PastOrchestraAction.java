package kumagai.concert.struts2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import ktool.datetime.DateTime;
import kumagai.concert.PastOrchestraList1;
import kumagai.concert.PastOrchestraList2;
import kumagai.concert.crawler.PastConcertInfo;

/**
 * コンサートが終了したオーケストラのリスト表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/pastorchestra.jsp")
public class PastOrchestraAction
{
	public ArrayList<PastConcertInfo> pastOrchestraList1;
	public ArrayList<PastConcertInfo> pastOrchestraList2;
	public ArrayList<PastConcertInfo> pastOrchestraList3;
	public String today;

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

		DateTime today = new DateTime();
		this.today = today.toFullString();

		return "success";
	}
}
