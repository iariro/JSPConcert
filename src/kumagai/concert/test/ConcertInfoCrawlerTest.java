package kumagai.concert.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import kumagai.concert.crawler.ConcertInfoCrawler;

public class ConcertInfoCrawlerTest
{
	public static void main(String[] args)
		throws IOException
	{
		String html =
			"<h1>今後の予定</h1>\n" +
			"1\n" +
			"2\n" +
			"3\n" +
			"4\n" +
			"5\n" +
			"6\n" +
			"7\n" +
			"8\n" +
			"9\n" +
			"10\n" +
			"<h2>第１０回演奏会</h2>\n" +
			"<li>日時：2017年10月10日\n" +
			"<li>開演：14:00\n" +
			"<li>場所：すみだトリフォニーホール\n" +
			"<li>演目：ｘｘｘ：交響曲\n" +
			"1\n" +
			"2\n" +
			"3\n" +
			"4\n" +
			"5\n" +
			"6\n" +
			"7\n" +
			"8\n" +
			"9\n" +
			"10\n" +
			"11\n" +
			"12\n" +
			"13\n" +
			"14\n" +
			"15\n" +
			"16\n" +
			"17\n" +
			"18\n" +
			"19\n" +
			"20\n" +
			"<a>戻る</a>";

		byte [] bytes = html.getBytes();
		ByteInputStream stream = new ByteInputStream(bytes, 0, bytes.length);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String [] lines = ConcertInfoCrawler.getConcertInfo(reader);
		for (String line : lines)
		{
			System.out.println(line);
		}
		stream.close();
	}
}
