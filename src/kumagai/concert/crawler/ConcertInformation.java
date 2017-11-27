package kumagai.concert.crawler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import ktool.datetime.DateTime;
import kumagai.concert.StringAndString;

/**
 * コンサート情報
 */
public class ConcertInformation
{
	public int index;
	public String name;
	public String date;
	public String hall;
	public String ryoukin;
	public ArrayList<StringAndString> composerNameAndTitles;
	public ArrayList<StringAndString> partAndPlayers;
	public String kaijou;
	public String kaien;
	public boolean nameOk;

	/**
	 * メンバーの初期化。
	 * @param index 加工対象XML中のノードインデックス
	 */
	public ConcertInformation(int index)
	{
		this.index = index;
		this.composerNameAndTitles = new ArrayList<StringAndString>();
		this.partAndPlayers = new ArrayList<StringAndString>();
	}

	/**
	 * 日付を半角数字で取得する。
	 * @return 日付
	 */
	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
		throws ParseException
	{
		String date2 = ZenkakuHankakuConverter.ConvertZenkakuToHankaku(date);
		if (this.date == null ||
			DateTime.parseDateString(this.date).compareTo(DateTime.parseDateString(date2)) < 0)
		{
			this.date = date2;
		}
	}

	/**
	 * 開場時間を取得。半角数字による。無指定の場合は12:00。なお無指定の場合、開演時間の30分前とする。
	 * @return 開場時間
	 */
	public String getKaijou()
	{
		if (kaijou != null)
		{
			// 開場時間指定あり。

			return ZenkakuHankakuConverter.ConvertZenkakuToHankaku(kaijou);
		}
		else
		{
			// 開場時間指定なし。

			if (this.kaien != null)
			{
				// 開演時間の指定あり。

				String kaien = getKaien();

				int hour = Integer.valueOf(kaien.substring(0, 2));
				int minute = Integer.valueOf(kaien.substring(3, 5));

				if (minute >= 30)
				{
					// 繰り下がりなし。

					minute -= 30;
				}
				else
				{
					// 繰り下がりあり。

					minute += 30;
					hour--;
				}

				return String.format("%02d:%02d", hour, minute);
			}
			else
			{
				// 開演時間の指定なし。

				return "12:00";
			}
		}
	}

	/**
	 * 開演時間を取得。半角数字による。無指定の場合12:00。
	 * @return 開演時間
	 */
	public String getKaien()
	{
		if (kaien != null)
		{
			// 開演時間の指定あり。

			return ZenkakuHankakuConverter.ConvertZenkakuToHankaku(kaien);
		}
		else
		{
			// 開演時間の指定なし。

			return "12:00";
		}
	}

	/**
	 * kyoku要素を作曲家属性のみで追加。
	 * @param composer 作曲家名
	 */
	public void addComposer(String composer)
	{
		composerNameAndTitles.add(new StringAndString(composer, null));
	}

	/**
	 * 最新のkyoku要素のtitle属性をセット。
	 * @param title 曲タイトル
	 */
	public void setTitle(String title)
	{
		String empty = new String();
		title = Pattern.compile(" *[oO]p. *[0-9]*").matcher(title).replaceAll(empty);
		title = Pattern.compile("[ 　]*作品 *[0-9]*").matcher(title).replaceAll(empty);
		title = Pattern.compile("[ 　]など").matcher(title).replaceAll(empty);
		title = Pattern.compile("[ 　]等").matcher(title).replaceAll(empty);

		composerNameAndTitles.get(composerNameAndTitles.size() - 1).string2 = title;
	}

	/**
	 * player要素をパート属性のみで追加。
	 * @param part パート名
	 */
	public void addPart(String part)
	{
		partAndPlayers.add(new StringAndString(part, null));
	}

	/**
	 * 最新のplayer要素のplayer属性をセット。
	 * @param player 演奏者名
	 */
	public void setPlayer(String player)
	{
		String part = partAndPlayers.get(partAndPlayers.size() - 1).string1;

		if (player.indexOf(" ") < 0 && !part.equals("管弦楽"))
		{
			// 管弦楽以外でスペースなし。

			player = player.substring(0, player.length() / 2) + " " + player.substring(player.length() / 2);
		}

		partAndPlayers.get(partAndPlayers.size() - 1).string2 = player;
	}

	/**
	 * 「管弦楽」「指揮」枠を確保。
	 */
	public void secureRequiredPart()
	{
		for (String part : new String [] { "管弦楽", "指揮" })
		{
			boolean find = false;

			for (StringAndString kv : partAndPlayers)
			{
				if (kv.string1 == part)
				{
					find = true;
					break;
				}
			}

			if (!find)
			{
				partAndPlayers.add(new StringAndString(part, null));
			}
		}
	}

	/**
	 * パートをキーにソート。順番はスキーマファイル登場順とする。
	 * @param partNames パートマスター
	 */
	public void sortPlayerByPart(final String [] partNames)
	{
		Collections.sort(
				partAndPlayers,
				new Comparator<StringAndString>()
				{
					public int compare(StringAndString x, StringAndString y)
					{
						int xindex = Arrays.binarySearch(partNames, x.string1);
						int yindex = Arrays.binarySearch(partNames, y.string1);

						return Integer.compare(xindex, yindex);
					}					
				});
	}

	/**
	 * 標準出力にダンプ
	 */
	public void dump()
	{
		System.out.printf("name=%s date=%s kaijou=%s kaien=%s hall=%s ryoukin=%s", name, date, getKaijou(), kaien, hall, ryoukin);
		System.out.println();
		for (StringAndString composerNameAndTitle : composerNameAndTitles)
		{
			System.out.printf("%s:%s", composerNameAndTitle.string1, composerNameAndTitle.string2);
			System.out.println();
		}
		for (StringAndString partAndPlayer : partAndPlayers)
		{
			System.out.printf("%s:%s", partAndPlayer.string1, partAndPlayer.string2);
			System.out.println();
		}
	}
}
