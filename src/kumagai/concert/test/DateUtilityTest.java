package kumagai.concert.test;

import junit.framework.TestCase;
import kumagai.concert.crawler.DateUtility;

public class DateUtilityTest
	extends TestCase
{
	public void testTrimDate()
	{
		assertEquals("2015/08/15", DateUtility.trimDate("'15/08/15"));
		assertEquals("2017/07/28", DateUtility.trimDate("2017.7.28"));
		assertEquals("2017/01/02", DateUtility.trimDate("2017/01/02"));
		assertEquals("2017/03/04", DateUtility.trimDate("2017年3月4日"));
		assertEquals("2013/05/26", DateUtility.trimDate("2013 年 5 月 26 日"));
		assertEquals("2017/07/23", DateUtility.trimDate("2017年７月23日"));
		assertEquals("2017/05/05", DateUtility.trimDate("2017年 5月 5日"));
		assertEquals("2017/05/06", DateUtility.trimDate("２０１７年５月６日"));
		assertEquals("2017/07/08", DateUtility.trimDate("２０１7年７月８日"));
		assertEquals("2017/09/10", DateUtility.trimDate("平成29年9月10日"));
		assertEquals("2017/11/12", DateUtility.trimDate("平成２９年１１月１２日"));
		assertEquals("2016/01/10", DateUtility.trimDate("平成２８年（2016年）1月10日"));
	}
}
