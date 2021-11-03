package kumagai.concert;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import ktool.db.KConnection;
import ktool.db.postgre.PostgreConnection;
import ktool.db.postgre.PostgreDbUrl;
import ktool.xml.KDocument;

/**
 * 日ごとのコンサートの分量をSVG形式でグラフ化。
 */
public class DensityDocument
	extends KDocument
{
	/**
	 * １日を表すますのサイズ。
	 */
	static private final Dimension size = new Dimension(2, 15);

	/**
	 * 表の左上隅の座標。
	 */
	static private final Point location = new Point(70, 20);

	/**
	 * DensityDocumentクラスのテスト。
	 * @param args 未使用
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 */
	static public void main(String [] args)
		throws SQLException,
		IOException,
		ParserConfigurationException,
		TransformerConfigurationException,
		TransformerException,
		TransformerFactoryConfigurationError
	{
		KConnection connection = new PostgreConnection();
		connection.connect(
			new PostgreDbUrl("castalia", "concert"),
			"kumagai",
			"kumagai");
		Statement statement = connection.createStatement();

		ResultSet result =
			statement.executeQuery(
				"select date, count(date) as count from concert group by date");

		new DensityDocument(result, 2005, 2007).write(
			new FileWriter("density.svg"));

		result.close();
		statement.close();
		connection.close();
	}

	/**
	 * 指定の計算結果を受けSVG文書を構築。
	 * @param result 日ごとのコンサートの分量
	 * @param start 開始年
	 * @param end 終了年
	 * @throws ParserConfigurationException
	 */
	public DensityDocument(ResultSet result, int start, int end)
		throws ParserConfigurationException,
		TransformerConfigurationException,
		SQLException
	{
		// トップ要素。
		Element top = createElement("svg");
		appendChild(top);

		top.setAttribute("xmlns", "http://www.w3.org/2000/svg");

		Element element = createElement("title");
		top.appendChild(element);

		Text text = createTextNode("コンサート密度グラフ");
		element.appendChild(text);

		// 枠線。
		element = createElement("rect");
		element.setAttribute
			("x", String.valueOf(location.x - 1));
		element.setAttribute
			("y", String.valueOf(location.y - 1));
		element.setAttribute
			("width", String.valueOf(size.width * 366 + 2));
		element.setAttribute
			("height", String.valueOf(size.height * (end - start + 1) + 2));

		element.setAttribute("fill", "none");
		element.setAttribute("stroke", "black");
		top.appendChild(element);

		for (int i=0 ; i<=end-start ; i++)
		{
			// 年数。
			element = createElement("text");
			element.setAttribute
				("x", String.valueOf(location.x - 60));
			element.setAttribute
				("y", String.valueOf(location.y + 13 + size.height * i));
			element.appendChild
				(createTextNode(String.format("%d年", start + i)));
			top.appendChild(element);
		}

		GregorianCalendar calendar = new GregorianCalendar();

		int count, year, dayOfYear;

		while (result.next())
		{
			calendar.setTime(result.getDate("date"));
			count = result.getInt("count");

			year = calendar.get(Calendar.YEAR);
			dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

			if (year >= start && year<=end)
			{
				// 範囲内である。

				element = createElement("rect");

				element.setAttribute(
					"x",
					String.valueOf
						(location.x + size.width * dayOfYear));

				element.setAttribute(
					"y",
					String.valueOf
						(location.y + size.height * (year - start)));

				element.setAttribute
					("width", String.valueOf(size.width));
				element.setAttribute
					("height", String.valueOf(size.height));

				if (count >= 4)
				{
					// ４件以上。

					element.setAttribute("fill", "#0000aa");
				}

				if (count >= 3)
				{
					// ３件以上。

					element.setAttribute("fill", "#0000cc");
				}
				else if (count >= 2)
				{
					// ２件以上。

					element.setAttribute("fill", "#7777ff");
				}
				else
				{
					// １件。

					element.setAttribute("fill", "#ccccff");
				}

				top.appendChild(element);
			}
		}
	}
}
