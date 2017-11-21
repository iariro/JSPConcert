package kumagai.concert.crawler;

import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Element;

import ktool.xml.KDocument;

/**
 * コンサート情報XML
 */
public class NewConcertDocument
	extends KDocument
{
	static private final String [][] composerSurrogates =
		{
			new String [] { "ベートーベン", "ベートーヴェン" },
			new String [] { "ショスタコーヴィッチ", "ショスタコーヴィチ" },
			new String [] { "ショスタコービッチ", "ショスタコーヴィチ" },
			new String [] { "ヴァーグナー", "ワーグナー" },
			new String [] { "サン＝サーンス", "サン=サーンス" }
		};
	static private final String empty = new String();

	/**
	 * テキストからコンサート情報を生成
	 * @param index インデックス
	 * @param lines 加工対象
	 * @param halls ホール一覧
	 * @param composers 作曲者一覧
	 * @param partNames パート名一覧
	 * @param playerNames 演奏者一覧
	 * @return コンサート情報
	 */ 
	static public ConcertInformation trimConcertInfo(int index, String [] lines, String [] halls, String [] composers, String [] partNames, String [] playerNames)
	{
		ConcertInformation concert = new ConcertInformation(index);
		LineType lineType = LineType.None;
		for (String line : lines)
		{
			line = line.trim();

			try
			{
				if (line.length() > 0)
				{
					// ホワイトスペースのみの行ではない。
					boolean kakutei = false;

					if (lineType == LineType.Composer)
					{
						// 直前は作曲家。

						concert.setTitle(line);
						lineType = LineType.None;
						kakutei = true;
						continue;
					}
					else
					{
						// 直前は何もなし。

						if (concert.name == null &&
							(line.indexOf("演奏会") >= 0 ||
							line.endsWith("コンサート") ||
							line.endsWith("のつどい") ||
							line.endsWith("Concert")))
						{
							// コンサート名と判断。

							for (int j = 0; j < playerNames.length; j++)
							{
								if (line.startsWith(playerNames[j]))
								{
									// オーケストラ名の行である。

									concert.addPart("管弦楽");
									concert.setPlayer(playerNames[j]);
									line = Pattern.compile(playerNames[j] + "　*").matcher(line).replaceAll(empty);
									break;
								}
							}
							line = line.replace("のお知らせ", empty);

							concert.name = line;
							continue;
						}

						if (Pattern.matches("曲目[:：]*$", line) || line.equals("プログラム"))
						{
							// 不要な行と判断。

							continue;
						}

						if (Pattern.matches("[0-9]{4}年[ 　]*[0-9]*月[ 　]*[0-9]*日", line))
						{
							// 日付を含む。

							concert.date =
								Pattern.compile(".*([0-9]{4})年[ 　]*([0-9]*)月[ 　]*([0-9]*)日.*").matcher(line).replaceAll("$1/$2/$3");
							kakutei = true;
						}
						else if (Pattern.matches("[０-９]{4}年[０-９]*月[０-９]*日", line))
						{
							// 日付を含む。

							concert.date =
								Pattern.compile(".*([０-９]{4})年([０-９]*)月([０-９]*)日.*").matcher(line).replaceAll("$1/$2/$3");
							kakutei = true;
						}
						else if (Pattern.matches("[0-9]{2}[/.][0-9]*[/.][0-9]*", line))
						{
							// 日付を含む。

							concert.date =
								Pattern.compile(".*([0-9]{2})[/.]([0-9]*)[/.]([0-9]*).*").matcher(line).replaceAll("20$1/$2/$3");
							kakutei = true;
						}
						else if (Pattern.matches("[0-9]{4}/[0-9]*/[0-9]*", line))
						{
							// 日付を含む。

							concert.date =
								Pattern.compile(".*([0-9]{4})/([0-9]*)/([0-9]*).*").matcher(line).replaceAll("$1/$2/$3");
							kakutei = true;
						}

						if (Pattern.matches("[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開場[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開演", line))
						{
							// 「13:30開場 14:00開演」の形式の開場・開演時刻を含む。

							concert.kaijou = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９])開場.*").matcher(line).replaceAll("$1:$2");
							concert.kaien = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]) *開演.*").matcher(line).replaceAll("$1:$2");
							kakutei = true;
						}
						else if (Pattern.matches("開場[ 　]*：* *[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９][ 　]*開演[ 　]*[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９]", line))
						{
							// 「開場13:30 開演14:00」の形式の開場・開演時刻を含む。

							concert.kaijou = Pattern.compile(".*開場[ 　]*：* *([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*").matcher(line).replaceAll("$1:$2");
							concert.kaien = Pattern.compile(".*開演[ 　]*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*").matcher(line).replaceAll("$1:$2");
							kakutei = true;
						}
						else
						{
							if (Pattern.matches("[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９]開場", line))
							{
								// 「13:30開場」の形式の開場時刻を含む。

								concert.kaijou = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９])開場.*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (Pattern.matches(line, "[0-9０-９][0-9０-９]時[0-9０-９][0-9０-９]分開場"))
							{
								// 「13時30分開場」の形式の開場時刻を含む。

								concert.kaijou = Pattern.compile(".*([0-9０-９][0-9０-９])時([0-9０-９][0-9０-９])分開場.*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (Pattern.matches(line, "開場[ 　]*：* *[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開場：13:30」の形式の開場時刻を含む。

								concert.kaijou = Pattern.compile(".*開場[ 　]*：* *([0-9][0-9])[:：]([0-9][0-9]).*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}

							if (Pattern.matches(line, "[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開演"))
							{
								// 「14:00開演」の形式の開演時刻を含む。

								concert.kaien = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]) *開演.*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (Pattern.matches(line, "[0-9０-９][0-9０-９]時[0-9０-９][0-9０-９]分 *開演"))
							{
								// 「14時00分開演」の形式の開演時刻を含む。

								concert.kaien = Pattern.compile(".*([0-9０-９][0-9０-９])時([0-9０-９][0-9０-９])分 *開演.*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (Pattern.matches(line, "[0-9０-９][0-9０-９]時開演"))
							{
								// 「14時開演」の形式の開演時刻を含む。

								concert.kaien = Pattern.compile(".*([0-9０-９][0-9０-９])時開演.*").matcher(line).replaceAll("$1:00");
								kakutei = true;
							}
							else if (Pattern.matches(line, "午後[0-9０-９]時開演"))
							{
								// 「午後2時開演」の形式の開演時刻を含む。

								String hour12 = Pattern.compile(".*午後([0-9０-９])時開演.*").matcher(line).replaceAll("$1");
								concert.kaien = String.format("%d:00", Integer.valueOf(ZenkakuHankakuConverter.ConvertZenkakuToHankaku(hour12)) + 12);
								kakutei = true;
							}
							else if (Pattern.matches(line, "開演[ 　]*[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開演14:00」の形式の開演時刻を含む。

								concert.kaien = Pattern.compile(".*開演[ 　]*([0-9][0-9])[:：]([0-9][0-9]).*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (Pattern.matches(line, "開演[ 　]*：* *[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開演：14:00」の形式の開演時刻を含む。

								concert.kaien = Pattern.compile(".*開演[ 　]*：* *([0-9][0-9])[:：]([0-9][0-9]).*").matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						if (line.endsWith("円"))
						{
							// 料金情報を含む。

							concert.ryoukin = line;
							continue;
						}

						if (line.endsWith("無料"))
						{
							// 料金情報を含む。

							concert.ryoukin = "入場無料";
							continue;
						}

						if (line.endsWith("全席自由"))
						{
							// 料金情報を含む。

							concert.ryoukin = line;
							continue;
						}

						if (line.startsWith("入場料"))
						{
							// 料金情報を含む。

							if (line.endsWith("未定"))
							{
								// 未定である。

								concert.ryoukin = "未定";
								continue;
							}
							else
							{
								// 料金情報を含む。

								concert.ryoukin =
									Pattern.compile("入場料[:：]* *").matcher(line).replaceAll(empty);
								continue;
							}
						}

						if (line.startsWith("料金"))
						{
							// 料金情報を含む。

							concert.ryoukin =
								Pattern.compile("料金[:：]* *").matcher(line).replaceAll(empty);
							continue;
						}

						for (int j = 0; j < halls.length; j++)
						{
							if (line.indexOf(halls[j]) >= 0 ||
								line.indexOf(halls[j].replace("音楽ホール", "・音楽ホール")) >= 0 ||
								line.indexOf(halls[j].replace("ホール", "大ホール")) >= 0 ||
								line.indexOf(halls[j].replace("ー", "-")) >= 0 ||
								line.indexOf(halls[j].replace("こうとう", "江東")) >= 0 ||
								line.indexOf(halls[j].replace("市", empty)) >= 0)
							{
								// ホール名を含む。

								concert.hall = halls[j];
								kakutei = true;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						if (Pattern.matches(line, ".*駅.*口"))
						{
							// 会場案内を含む。

							continue;
						}

						for (int j = 0; j < composerSurrogates.length; j++)
						{
							if (Pattern.matches(line, composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*"))
							{
								// 作曲家：曲名の形式。

								line = Pattern.compile(composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*").matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目：").matcher(line).replaceAll(empty);

								concert.addComposer(composerSurrogates[j][1]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, composerSurrogates[j][0] + "作曲「.*」"))
							{
								// 作曲家作曲「曲名」の形式。

								line = Pattern.compile(composerSurrogates[j][0] + "作曲「(.*)」").matcher(line).replaceAll("$1");

								concert.addComposer(composerSurrogates[j][1]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}
						}

						for (int j = 0; j < composers.length; j++)
						{
							if (Pattern.matches(line, composers[j] + "[ \t]*[/:／：　][ \t]*"))
							{
								// 作曲家：曲名の形式。

								line = Pattern.compile(composers[j] + "[ \t]*[/:／：　][ \t]*").matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目：").matcher(line).replaceAll(empty);

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, composers[j] + "  *.*"))
							{
								// 作曲家 曲名の形式。

								line = Pattern.compile(composers[j] + "  *(.*)").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, composers[j] + "作曲「.*」"))
							{
								// 作曲家作曲「曲名」の形式。

								line = Pattern.compile(composers[j] + "作曲「(.*)」").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, composers[j] + "作曲[ 　].*"))
							{
								// 作曲家作曲 曲名の形式。

								line = Pattern.compile(composers[j] + "作曲[ 　](.*)").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, composers[j] + "「.*」"))
							{
								// 作曲家「曲名」の形式。

								line = Pattern.compile(composers[j] + "「(.*)」").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						for (int j = 0; j < composers.length; j++)
						{
							if (Pattern.matches(line, ".* *[/／] *" + composers[j]))
							{
								// 曲名／作曲家の形式。

								line = Pattern.compile(" *[/／] *" + composers[j]).matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目[：　]*").matcher(line).replaceAll(empty);

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(line, ".*[(（]" + composers[j]))
							{
								// 曲名（作曲家）の形式。

								line = Pattern.compile("[(（]" + composers[j]).matcher(line).replaceAll(empty);

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						for (int j = 0; j < composers.length; j++)
						{
							if (line == composers[j] ||
								line == composers[j] + "作曲" ||
								line.indexOf("オール" + composers[j] + "プログラム") >= 0)
							{
								// 作曲家名の行である。

								concert.addComposer(composers[j]);
								kakutei = true;
								lineType = LineType.Composer;
								break;
							}
						}

						for (int j = 0; j < composerSurrogates.length; j++)
						{
							if (line.endsWith(composerSurrogates[j][0]) ||
								line.indexOf("オール" + composerSurrogates[j][0] + "プログラム") >= 0)
							{
								// 作曲家名の行である。

								concert.addComposer(composerSurrogates[j][1]);
								kakutei = true;
								lineType = LineType.Composer;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						for (int j = 0; j < playerNames.length && !kakutei; j++)
						{
							if (line == playerNames[j] &&
								(line.startsWith("Ensemble") ||
								line.endsWith("楽団") ||
								line.startsWith("オーケストラ") ||
								line.endsWith("オーケストラ") ||
								line.endsWith("シンフォニエッタ") ||
								line.endsWith("Orchestra")))
							{
								// オーケストラ名の行と判断する。

								concert.addPart("管弦楽");
								concert.setPlayer(playerNames[j]);
								kakutei = true;
								break;
							}

							for (int k = 0; k < partNames.length && !kakutei; k++)
							{
								if ((line.startsWith(partNames[k]) ||
									line.startsWith(partNames[k].substring(0, 1) + "　" + partNames[k].substring(1))) &&
									(line.indexOf(playerNames[j]) >= 0 ||
									line.indexOf(playerNames[j].replace(' ', '　')) >= 0 ||
									line.indexOf(playerNames[j].replace(" ", empty)) >= 0))
								{
									// パート名＋演奏者名の行である。

									concert.addPart(partNames[k]);
									concert.setPlayer(playerNames[j]);
									kakutei = true;
									break;
								}
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						// 未登録の演奏者。
						for (int j = 0; j < partNames.length; j++)
						{
							if (line.startsWith("合唱指揮"))
							{
								// 合唱指揮情報である。

								break;
							}

							if (Pattern.matches(line, partNames[j] + "[ 　:：]") ||
								Pattern.matches(line, partNames[j] + "ソロ[ 　:：]") ||
								Pattern.matches(line, partNames[j] + "独奏[ 　:：]"))
							{
								// パート名から始まっている。

								String player =
									Pattern.compile(partNames[j] + "[^ 　\t:：]*[ 　\t:：]*").matcher(line).replaceAll(empty);
								player = player.replace("　", " ");

								concert.addPart(partNames[j]);
								concert.setPlayer(player);
								kakutei = true;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}
					}
				}
			}
			catch (Exception exception)
			{
				;
			}
		}
		return concert;
	}

	static public void main(String[] args)
		throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException
	{
		NewConcertDocument document = new NewConcertDocument();
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        document.write(transformer, new PrintWriter(System.out));
	}

	public NewConcertDocument()
		throws ParserConfigurationException, TransformerConfigurationException, TransformerFactoryConfigurationError
	{
		Element top = createElement("c:concertCollection");
		appendChild(top);
		top.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		top.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:c", "concert");
		top.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "concert Concert.xsd");

		Element concert = createElement("concert");
		top.appendChild(concert);
		concert.setAttribute("name", "name");
		concert.setAttribute("date", "2017/11/11");
		concert.setAttribute("kaijou", "12:00");
		concert.setAttribute("kaien", "12:00");
		concert.setAttribute("hall", "hall");

		Element kyokuCollection = createElement("kyokuCollection");
		concert.appendChild(kyokuCollection);

		Element kyoku = createElement("kyoku");
		kyokuCollection.appendChild(kyoku);
		kyoku.setAttribute("composer", "composer");
		kyoku.setAttribute("title", "title");

		Element playerCollection = createElement("playerCollection");
		concert.appendChild(playerCollection);

		Element player = createElement("player");
		playerCollection.appendChild(player);
		player.setAttribute("name", "name");
		player.setAttribute("part", "part");
	}
}
