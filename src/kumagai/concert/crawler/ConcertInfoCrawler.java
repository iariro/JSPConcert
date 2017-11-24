package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import ktool.datetime.DateTime;
import ktool.datetime.TimeSpan;
import ktool.io.StringListFromFile;
import kumagai.concert.PastConcertInfo;
import kumagai.concert.PastOrchestraList2;

/**
 * コンサート情報収集処理
 * @author kumagai
 */
public class ConcertInfoCrawler
{
	static private final String separateLine = "-----------------------------------------------";
	static private final String [] encodes = { "UTF-8", "Shift_JIS", "EUC-JP" };
	static private final String [] skipLines =
		{
			".*演奏会の情報を追加しました",
			"^<!--$",
			"^-->$",
			"^//[A-z ->]*$",
			"^&nbsp;$",
			"\"[A-z]*\" : \"[A-z0-9#]*\".*",
			"^// <!\\[CDATA\\[$",
			"^function *[A-z0-9]*.*$",
			"^if *\\(.*\\).*",
			"^onMouse[DUO].*",
			"^var [A-z]*.*",
			"^window\\.[A-z]*.*",
			"^<img [A-z]*.*",
			"^<A HREF.*",
			"^<a href.*"
		};
	static private final Pattern [] patternSkipLines;

	static
	{
		patternSkipLines = new Pattern[skipLines.length];
		for (int i=0 ; i<skipLines.length ; i++)
		{
			patternSkipLines[i] = Pattern.compile(skipLines[i]);
		}
	}

	/**
	 * コンサート情報収集処理
	 * @param args db|net outdir
	 */
	static public void main(String[] args)
		throws IOException, SQLException, ParserConfigurationException, TransformerFactoryConfigurationError, SAXException, XPathExpressionException, TransformerException
	{
		String source = null;
		if (args.length < 2)
		{
			// no arg

			System.out.println("Usage: db|net outdir");
			return;
		}
		source = args[0];
		String outdir = args[1];

		ArrayList<PastConcertInfo> urlAndNames;
		if (source.equals("net"))
		{
			// net

			System.setProperty("proxySet", "true");
			System.setProperty("proxyHost", "proxy-cb");
			System.setProperty("proxyPort", "8080");

			String[] htmlLines = ConcertInfoServer.getHtmlLines("pastorchestra.htm");
			urlAndNames = ConcertInfoServer.getUrls(htmlLines);
		}
		else if (source.equals("db"))
		{
			// DB

			DriverManager.registerDriver(new SQLServerDriver());
			Connection dbconnection =
				DriverManager.getConnection
					("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");
			urlAndNames = new PastOrchestraList2(dbconnection, true);
			dbconnection.close();
		}
		else
		{
			// else

			System.out.println("Usage: db|net");
			return;
		}
		ConcertSchemaDocument schemaDocument = new ConcertSchemaDocument("testdata/ConcertSchema.xsd");
		String [] halls = schemaDocument.getHalls();
		String [] playerNames = schemaDocument.getPlayerNames();
		String [] partNames = schemaDocument.getPartNames();
		String [] composers = schemaDocument.getComposerNames();
		String [] lines = new StringListFromFile("testdata/concert.txt", "utf-8").toArray(new String[]{});

		PrintWriter fileNew = new PrintWriter(new File(outdir, "Concert_new.txt"));
		PrintWriter fileOld = new PrintWriter(new File(outdir, "Concert_old.txt"));
		PrintWriter fileError = new PrintWriter(new File(outdir, "Concert_error.txt"));

		String maxspanOrchestra = null;
		DateTime start = new DateTime();
		TimeSpan maxspan = null;
		ArrayList<ConcertInformation> concertInformations = new ArrayList<ConcertInformation>();
		for (int i=0 ; i<urlAndNames.size() ; i++)
		{
			DateTime split1 = new DateTime();

			PastConcertInfo urlAndName = urlAndNames.get(i);

			System.out.printf("(%d/%d) %s\n", i+1, urlAndNames.size(), urlAndName.orchestra);

			try
			{
				if (urlAndName.url == null)
				{
					// URLなし

					fileError.printf("%s\ndb=%s no url\n", urlAndName.orchestra, urlAndName.date);
					continue;
				}

				if (urlAndName.url.indexOf("facebook.com") >= 0)
				{
					// facebookは対象外とする

					fileError.println(separateLine);
					fileError.printf("%s\ndb=%s facebook\n", urlAndName.orchestra, urlAndName.date);
					continue;
				}

				boolean find = false;
				for (String encode : encodes)
				{
					URL url = new URL(urlAndName.url);

					URLConnection connection = url.openConnection();
					// httpsエラー対策
					connection.setRequestProperty("User-agent", "ConcertCrawler");
					// タイムアウト設定
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(10000);

					InputStream inputStream = url.openStream();
					BufferedReader reader =
						new BufferedReader(new InputStreamReader(inputStream, encode));

					int lineCount = 30;
					if (urlAndName.url.indexOf("okesen") >= 0)
					{
						// オケ専の場合はたくさん読むように

						lineCount = 50;
					}

					String[] concertInfo = getConcertInfo(reader, lineCount);
					if (concertInfo != null)
					{
						// 情報あり

						ConcertInformation concertInformation =
							NewConcertDocument.trimConcertInfo(0, lines, halls, composers, partNames, playerNames);
						concertInformations.add(concertInformation);

						PrintWriter file;
						String date = ConcertInfoCrawler.extractDate(concertInfo);
						if (date == null)
						{
							// 日付なし

							file = fileError;
						}
						else if (date.compareTo(urlAndName.date) <= 0)
						{
							// 既知の情報と同じor古い情報

							file = fileOld;
						}
						else
						{
							// 新しい情報

							file = fileNew;
						}

						file.println(separateLine);
						for (String line : concertInfo)
						{
							if (line.length() > 200)
							{
								// 長すぎる行＝スクリプト対策

								line = line.substring(0, 100);
							}

							file.println(line);
						}

						file.println(urlAndName.orchestra);
						file.println(String.format("db=%s:now=%s %s", urlAndName.date, date, encode));
						find = true;
					}

					inputStream.close();

					if (find)
					{
						// 情報を見つけた＝このエンコードで当たり

						break;
					}
				}

				if (!find)
				{
					// 見つからなかった

					fileError.println(separateLine);
					fileError.printf("%s\ndb=%s:now=none\n", urlAndName.orchestra, urlAndName.date);
				}
			}
			catch (IOException exception)
			{
				// URLなし・サーバエラー

				fileError.println(separateLine);
				fileError.printf("%s %s\n", urlAndName.orchestra, exception.getMessage());
			}
			DateTime split2 = new DateTime();
			TimeSpan span = split2.diff(split1);
			if (maxspan == null || span.compare(maxspan) > 0)
			{
				// 最大値更新

				maxspan = span;
				maxspanOrchestra = urlAndName.orchestra;
			}
		}

		fileOld.close();
		fileNew.close();
		fileError.close();

		NewConcertDocument document = new NewConcertDocument(concertInformations);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		document.write(transformer, new FileWriter(new File(outdir, "NewConcert.xml")));

		DateTime end = new DateTime();
		System.out.printf("%s -> %s = %s\n", start.toFullString(), end.toFullString(), end.diff(start));
		System.out.printf("%s %s\n", maxspanOrchestra, maxspan);
	}

	/**
	 * HTMLからコンサート情報を取得
	 * @param reader HTML読み込みオブジェクト
	 * @return コンサート情報
	 */
	static public String [] getConcertInfo(BufferedReader reader, int lineCount)
		throws IOException
	{
		int count = 0;
		boolean find = false;
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null)
		{
			line = line.replaceAll("<.+?>", "");
			line = line.trim();
			if (line.length() <= 1)
			{
				// 空行

				continue;
			}

			boolean skip = false;
			for (Pattern patternSkipLine : patternSkipLines)
			{
				Matcher matcher = patternSkipLine.matcher(line);
				if (matcher.matches())
				{
					skip = true;
					break;
				}
			}

			if (skip)
			{
				continue;
			}

			if (!find || count > 0)
			{
				// 演奏会情報の前or演奏会情報の範囲内

				lines.add(line);
				if (lines.size() > 40)
				{
					// 最大サイズを超えている

					lines.remove(0);
				}
			}

			if (find)
			{
				// 演奏会情報の範囲内

				count--;
			}
			else
			{
				// 演奏会情報の前

				if (line.indexOf("演奏会") >= 0 ||
					line.indexOf("公演概要") >= 0)
				{
					// 演奏会の文字を含む

					if (line.indexOf("演奏会記録") < 0)
					{
						// 演奏会記録ではない

						find = true;
						count = lineCount;
					}
				}
			}
		}

		return find ? lines.toArray(new String [] {}) : null;
	}

	/**
	 * コンサート情報から日付を抽出する
	 * 複数ある場合は新しい日付を優先する
	 * @param lines コンサート情報文字列
	 * @return 日付
	 */
	static public String extractDate(String [] lines)
	{
		String lastDate = null;

		for (String line : lines)
		{
			if (DateUtility.patternSeirekiHalf.matcher(line).find() ||
				DateUtility.patternSeirekiFull.matcher(line).find() ||
				DateUtility.patternWarekiHalf.matcher(line).find() ||
				DateUtility.patternWarekiFull.matcher(line).find())
			{
				// 日付を含む行

				String date = DateUtility.trimDate(line);

				if (date != null)
				{
					// 日付を取り出し

					if (lastDate == null || lastDate.compareTo(date) < 0)
					{
						// 新規または新しい日付あり

						lastDate = date;
					}
				}
			}
		}
		return lastDate;
	}
}
