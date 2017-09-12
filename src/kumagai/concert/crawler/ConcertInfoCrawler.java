package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * コンサート情報収集処理
 * @author kumagai
 */
public class ConcertInfoCrawler
{
	static private final String [] encodes = { "UTF-8", "Shift_JIS", "EUC-JP" };

	/**
	 * コンサート情報収集処理
	 * @param args 未使用
	 */
	static public void main(String[] args)
		throws IOException
	{
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy-cb");
		System.setProperty("proxyPort", "8080");

		String[] htmlLines = ConcertInfoServer.getHtmlLines("pastorchestra.htm");
		ArrayList<PastConcertInfo> urlAndNames = ConcertInfoServer.getUrls(htmlLines);
		PrintWriter fileWriter = new PrintWriter("NewConcert.txt");
		for (int i=0 ; i<urlAndNames.size() ; i++)
		{
			PastConcertInfo urlAndName = urlAndNames.get(i);

			System.out.printf("(%d/%d) %s\n", i+1, urlAndNames.size(), urlAndName);

			try
			{
				fileWriter.println("-----------------------------------------------");

				if (urlAndName.url.indexOf("facebook.com") >= 0)
				{
					fileWriter.printf("%s facebook\n", urlAndName.orchestra);
					continue;
				}

				boolean find = false;
				for (String encode : encodes)
				{
					URL url = new URL(urlAndName.url);

					URLConnection connection = url.openConnection();
					connection.setRequestProperty("User-agent", "ConcertCrawler");
					connection.setReadTimeout(10000);

					InputStream inputStream = url.openStream();
					BufferedReader reader =
						new BufferedReader(new InputStreamReader(inputStream, encode));

					int lineCount = 30;
					if (urlAndName.url.indexOf("okesen") >= 0)
					{
						lineCount = 50;
					}

					String[] concertInfo = getConcertInfo(reader, lineCount);
					if (concertInfo != null)
					{
						// 情報あり

						for (String line : concertInfo)
						{
							if (line.length() > 200)
							{
								// 長すぎる行＝スクリプト対策

								line = line.substring(0, 100);
							}

							fileWriter.println(line);
						}

						fileWriter.printf("%s %s\n", urlAndName.orchestra, encode);
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
					fileWriter.printf("%s none\n", urlAndName.orchestra);
				}
			}
			catch (IOException exception)
			{
				fileWriter.printf("%s %s\n", urlAndName.orchestra, exception.getMessage());
			}
		}
		fileWriter.close();
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
			if (line.length() <= 1 ||
				line.equals("-->") ||
				line.equals("&nbsp;") ||
				line.equals("// <![CDATA["))
			{
				// 空行

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
}
