package kumagai.concert.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import junit.framework.TestCase;
import kumagai.concert.crawler.ConcertInfoCrawler;

public class ConcertInfoCrawlerTest
	extends TestCase
{
	public void testGetConcertInfo(String[] args)
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
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes, 0, bytes.length);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String [] lines = ConcertInfoCrawler.getConcertInfo(reader, 20);
		for (String line : lines)
		{
			System.out.println(line);
		}
		stream.close();
	}

	public void testGetDate()
		throws IOException
	{
		FileInputStream stream = new FileInputStream("../NewConcert.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		ArrayList<String> lines = null;
		String line;
		while ((line = reader.readLine()) != null)
		{
			if (line.equals("-----------------------------------------------"))
			{
				if (lines != null)
				{
					String [] lines2 = lines.toArray(new String [] {});
					String date = ConcertInfoCrawler.extractDate(lines2);
					System.out.printf("\t-> %s\n", date);
				}
				lines = new ArrayList<String>();
			}
			else
			{
				lines.add(line);
			}
			System.out.println(line);
		}

		stream.close();
	}
}
