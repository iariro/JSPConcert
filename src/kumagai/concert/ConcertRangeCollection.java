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
	 * @return Highcharts向けのSeries文字列
	 */
	public String generateHighchartsSeries()
	{
		StringBuffer buffer = new StringBuffer();
		for (int i=0 ; i<size() ; i++)
		{
			if (i > 0)
			{
				buffer.append(",");
			}
			buffer.append(String.format("[%d,%d]", get(i).minDate, get(i).maxDate));
		}
		return buffer.toString();
	}
}
