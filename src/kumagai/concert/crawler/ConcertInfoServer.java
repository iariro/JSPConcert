package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kumagai.concert.PastConcertInfo;

/**
 * コンサート情報Webページ
 */
public class ConcertInfoServer
{
	private static final String empty = "";
	static private final String urlBase = "http://www2.gol.com/users/ip0601170243/private/web/concert/%s";
	static private final Pattern pattern = Pattern.compile(".*<a href=\"(.*)\".*");

	/**
	 * pastorchestra.htmの内容からURLと楽団名を取得
	 * @param lines HTML行データ
	 * @return URLと楽団名リスト
	 */
	static public ArrayList<PastConcertInfo> getUrls(String[] lines)
	{
		ArrayList<PastConcertInfo> urls = new ArrayList<PastConcertInfo>();
		String url = null;
		String orchestra = null;
		for (String line : lines)
		{
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches())
			{
				// <a>行

				url = matcher.group(1);
			}
			else
			{
				// それ以外

				line = line.replaceAll("<.+?>", empty);
				if (url != null)
				{
					// URL確定

					line = line.trim();
					if (line.length() > 0)
					{
						if (orchestra == null)
						{
							// 楽団名がまだ→楽団名

							orchestra = line;
						}
						else
						{
							// 楽団名あり→日付

							urls.add(new PastConcertInfo(null, orchestra, url, line, null));
							url = null;
							orchestra = null;
						}
					}
				}
			}
		}
		return urls;
	}

	/**
	 * HTML読み込み。
	 * @param filename ファイル名
	 */
	static public String [] getHtmlLines(String filename) throws IOException
	{
		URL url = new URL(String.format(urlBase, filename));

		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));

		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()) != null)
		{
			lines.add(line);
		}

		in.close();

		return lines.toArray(new String [] {});
	}
}
