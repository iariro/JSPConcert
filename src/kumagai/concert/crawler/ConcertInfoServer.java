package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class ConcertInfoServer
{
	static private final String urlBase = "http://www2.gol.com/users/ip0601170243/private/web/concert/%s";
	static private final Pattern pattern = Pattern.compile(".*<a href=\"(.*)\".*");

	public static void main(String[] args) throws IOException
	{
		String[] htmlLines = getHtmlLines("pastorchestra.htm");
	}
	
	/**
	 * pastorchestra.htmの内容からURLと楽団名を取得
	 * @param lines HTML行データ
	 * @return URLと楽団名リスト
	 */
	static public HashMap<String, String> getUrls(String[] lines)
	{
		HashMap<String, String> urls = new HashMap<String, String>();
		String url = null;
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

				if (url != null)
				{
					// URL確定

					line = line.trim();
					if (line.length() > 0)
					{
						urls.put(line, url);
						url = null;
					}
				}
			}
		}
		return urls;
	}

	/**
	 * HTML読み込み。
	 * @param filename ファイル名 
	 * @throws IOException 
	 */
	static public String [] getHtmlLines(String filename) throws IOException
	{
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy-cb");
		System.setProperty("proxyPort", "8080");

		URL url = new URL(String.format(urlBase, filename));

		InputStream in = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = br.readLine()) != null)
		{
			System.out.println(line);
		}

		in.close();
		
		return null;
	}
}
