package kumagai.concert.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import kumagai.concert.crawler.ConcertInfoServer;
import kumagai.concert.crawler.PastConcertInfo;

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

		ArrayList<PastConcertInfo> urls = ConcertInfoServer.getUrls(lines);
		for (PastConcertInfo url : urls)
		{
			System.out.println(url);
		}
	}
}
