package kumagai.concert.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ConcertInfoCrawler
{
	static public void main(String[] args) throws IOException
	{
		System.setProperty("proxySet", "true");
		System.setProperty("proxyHost", "proxy-cb");
		System.setProperty("proxyPort", "8080");

		String[] htmlLines = ConcertInfoServer.getHtmlLines("pastorchestra.htm");
		ArrayList<PastConcertInfo> urlAndNames = ConcertInfoServer.getUrls(htmlLines);
		for (PastConcertInfo urlAndName : urlAndNames)
		{
			System.out.println(urlAndName);

			URL url = new URL(urlAndName.url);

			InputStream in = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			getConcertInfo(br);

			in.close();
		}
	}

	static public String [] getConcertInfo(BufferedReader reader)
		throws IOException
	{
		int count = 0;
		boolean range = false;
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null)
		{
			line = line.replaceAll("<.+?>", "");
			if (!range || count > 0)
			{
				lines.add(line);
				if (lines.size() > 30)
				{
					lines.remove(0);
				}
			}

			if (range)
			{
				count--;
			}
			else
			{
				if (line.indexOf("演奏会") >= 0)
				{
					range = true;
					count = 20;
				}
			}
		}
		
		return lines.toArray(new String [] {});
	}
}
