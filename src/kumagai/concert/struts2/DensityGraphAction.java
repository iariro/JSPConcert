package kumagai.concert.struts2;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.xml.transform.*;
import com.microsoft.sqlserver.jdbc.*;
import org.apache.struts2.*;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.convention.annotation.Result;
import ktool.datetime.*;
import ktool.xml.*;
import kumagai.concert.*;

/**
 * コンサート密度グラフ表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/densitygraph.jsp")
public class DensityGraphAction
{
	private KDocument densityDocument;

	/**
	 * 開始年を取得。
	 * @return 開始年
	 */
	public int getStartYear()
	{
		return 1993;
	}

	/**
	 * 終了年を取得。
	 * @return 終了年
	 */
	public int getEndYear()
	{
		return new DateTime().getYear() + 1;
	}

	/**
	 * グラフSVGドキュメントを文字列として取得。
	 * @return 文字列によるグラフSVGドキュメント
	 */
	public String getXml()
		throws TransformerFactoryConfigurationError, TransformerException
	{
		// XML書き出し準備。
		Transformer transformer =
			TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

		StringWriter writer = new StringWriter();

		// XML書き出し。
		densityDocument.write(transformer, writer);

		return writer.toString();
	}

	/**
	 * コンサート密度グラフ表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("densitygraph")
	public String execute()
		throws Exception
	{
		ServletContext context = ServletActionContext.getServletContext();

		DriverManager.registerDriver(new SQLServerDriver());

		Connection connection =
			DriverManager.getConnection
				(context.getInitParameter("ConcertSqlserverUrl"));

		Statement statement = connection.createStatement();

		ResultSet result =
			statement.executeQuery
				("select date, count(*) as count from Concert group by date");

		// 計算結果をグラフ化。
		densityDocument =
			new DensityDocument(result, getStartYear(), getEndYear());

		result.close();
		statement.close();
		connection.close();

		return "success";
	}
}
