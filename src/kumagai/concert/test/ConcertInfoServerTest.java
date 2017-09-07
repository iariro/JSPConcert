package kumagai.concert.test;

import java.util.HashMap;
import java.util.Map.Entry;

import junit.framework.TestCase;
import kumagai.concert.crawler.ConcertInfoServer;

public class ConcertInfoServerTest
	extends TestCase
{
	public void test1()
	{
		String [] lines =
			{
					"						",
					"						",
					"<a href=\"http://orchestra.musicinfo.co.jp/~akane-phil/\">",
					"						",
					"						明音交響楽団",
					"						",
					"						</a>"
			};

		HashMap<String, String> urls = ConcertInfoServer.getUrls(lines);
		for (Entry<String, String> url : urls.entrySet())
		{
			System.out.println(url);
		}
	}
}
