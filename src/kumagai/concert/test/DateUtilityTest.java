package kumagai.concert.test;

import kumagai.concert.crawler.DateUtility;

public class DateUtilityTest
{
	public static void main(String[] args)
	{
		System.out.println(DateUtility.trimDate("2017/01/02"));
		System.out.println(DateUtility.trimDate("2017年3月4日"));
		System.out.println(DateUtility.trimDate("２０１７年５月６日"));
		System.out.println(DateUtility.trimDate("２０１7年７月８日"));
		System.out.println(DateUtility.trimDate("平成29年9月10日"));
		System.out.println(DateUtility.trimDate("平成２９年１１月１２日"));
	}
}
