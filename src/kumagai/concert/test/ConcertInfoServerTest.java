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

	public void test2()
	{
		String line = "<h1 class=\"\" id=\"cc-m-header-10881515489\">演奏会情報</h1>";
		System.out.println(line.replace("<.+?>", ""));
	}
}
