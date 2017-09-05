package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
						<a href="http://orchestra.musicinfo.co.jp/~akane-phil/">
					
					明音交響楽団
					
						</a>
 */
public class ConcertInfoServer
{
	static private final String urlBase = "http://www2.gol.com/users/ip0601170243/private/web/concert/%s";

	public static void main(String[] args) throws IOException
	{
		execute("pastorchestra.htm");
	}

	/**
	 * HTML読み込み。
	 * @param filename ファイル名 
	 * @throws IOException 
	 */
	static public String [] execute(String filename) throws IOException
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
