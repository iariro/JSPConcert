package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

/**
 * コンサート情報収集処理
 * @author kumagai
 */
public class ConcertInfoCrawler
{
	static private final String [] encodes = { "UTF-8", "Shift_JIS", "EUC-JP" };

	static public void main(String[] args)
		throws IOException
	{
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy-cb");
		System.setProperty("proxyPort", "8080");

		String[] htmlLines = ConcertInfoServer.getHtmlLines("pastorchestra.htm");
		ArrayList<PastConcertInfo> urlAndNames = ConcertInfoServer.getUrls(htmlLines);
		PrintWriter fileWriter = new PrintWriter("NewConcert.txt");
		for (PastConcertInfo urlAndName : urlAndNames)
		{
			System.out.println(urlAndName);

			for (String encode : encodes)
			{
				try
				{
					URL url = new URL(urlAndName.url);

					InputStream in = url.openStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));

					fileWriter.println(urlAndName.orchestra);
					fileWriter.println("-----------------------------------------------");
					String[] concertInfo = getConcertInfo(reader);
					if (concertInfo != null)
					{
						// 情報あり

						for (String line : concertInfo)
						{
							if (line.length() > 100)
							{
								line = line.substring(0, 100);
							}

							fileWriter.println(line);
						}

						break;
					}

					in.close();
				}
				catch (IOException exception)
				{
					fileWriter.println(exception.getMessage());
				}
			}
		}
		fileWriter.close();
	}

	/**
	 * HTMLからコンサート情報を取得
	 * @param reader HTML読み込みオブジェクト
	 * @return コンサート情報
	 */
	static public String [] getConcertInfo(BufferedReader reader)
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
			if (line.length() <= 0)
			{
				// 空行

				continue;
			}

			if (!find || count > 0)
			{
				// 演奏会情報の前or演奏会情報の範囲内

				lines.add(line);
				if (lines.size() > 30)
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

				if (line.indexOf("演奏会") >= 0)
				{
					// 演奏会の文字を含む

					find = true;
					count = 20;
				}
			}
		}

		return find ? lines.toArray(new String [] {}) : null;
	}
}
