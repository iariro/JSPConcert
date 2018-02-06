package kumagai.concert;

import java.util.ArrayList;

/**
 * 全オーケストラの全コンサートの期間
 */
public class ConcertRangeCollection
	extends ArrayList<ConcertRange>
{
	/**
	 * Highcharts向けのSeries文字列を生成する
	 * @param today 今日の日付
	 * @return Highcharts向けのSeries文字列
	 */
	public String generateHighchartsSeries(long today)
	{
		StringBuffer buffer = new StringBuffer();
		for (int i=0 ; i<size() ; i++)
		{
			if (i > 0)
			{
				buffer.append(",");
			}
			buffer.append(
				String.format(
					"{low:%d,high:%d, color:'%s', name:'%s'}",
					get(i).minDate,
					get(i).maxDate,
					get(i).maxDate >= today ? "lightblue" : "gray",
					get(i).orchestra));
		}
		return buffer.toString();
	}
}
