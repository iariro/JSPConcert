package kumagai.concert.test;

import junit.framework.TestCase;
import kumagai.concert.crawler.ZenkakuHankakuConverter;

public class HankakuZenkakuConvertTest
	extends TestCase
{
	public void test1()
	{
		String zenkaku = "１２：３４";

		assertEquals("12:34", ZenkakuHankakuConverter.ConvertZenkakuToHankaku(zenkaku));
	}
}
