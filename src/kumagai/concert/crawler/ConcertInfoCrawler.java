package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

public class ConcertInfoCrawler
{
	public static void main(String[] args) throws IOException
	{
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy-cb");
		System.setProperty("proxyPort", "8080");

		String[] htmlLines = ConcertInfoServer.getHtmlLines("pastorchestra.htm");
		HashMap<String, String> urlAndNames = ConcertInfoServer.getUrls(htmlLines);
		for (Entry<String, String> urlAndName : urlAndNames.entrySet())
		{
			URL url = new URL(urlAndName.getValue());

			InputStream in = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.indexOf("演奏会") >= 0)
				{
					;
				}
			}

			in.close();
		}
	}

}
