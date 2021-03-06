package kumagai.concert.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import ktool.io.StringListFromFile;
import ktool.xml.KDocument;
import kumagai.concert.StringAndString;

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
	static private final Pattern patternEnsoukai1 = Pattern.compile(".*(第.*回演奏会).*");
	static private final Pattern patternEnsoukai2 = Pattern.compile(".*(第.*回定期演奏会).*");
	static private final Pattern patternDate1 = Pattern.compile("[^0-9]*([0-9０-９]{4})年[ 　]*([0-9０-９]*)月[ 　]*([0-9０-９]*)日.*");
	//static private final Pattern patternDate2 = Pattern.compile(".*[０-９]{4}年[０-９]*月[０-９]*日.*");
	static private final Pattern patternDate3 = Pattern.compile("[^0-9]*[0-9]{2}[/.][0-9]*[/.][0-9]*.*");
	static private final Pattern patternDate4 = Pattern.compile("[^0-9]*([0-9]{4})/([0-9]*)/([0-9]*).*");
	static private final Pattern patternKaijouKaien1 = Pattern.compile(".*[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開場[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開演.*");
	static private final Pattern patternKaijouKaien2 = Pattern.compile(".*開場[ 　]*：* *[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９][ 　]*開演[ 　]*[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９].*");
	static private final Pattern patternKaijou1 = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９])開場.*");
	static private final Pattern patternKaijou2 = Pattern.compile(".*開場[ 　]*：* *([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*");
	static private final Pattern patternKaijou3 = Pattern.compile(".*([0-9０-９][0-9０-９])時([0-9０-９][0-9０-９])分開場.*");
	static private final Pattern patternKaien1 = Pattern.compile(".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]) *開演.*");
	static private final Pattern patternKaien2 = Pattern.compile(".*開演[ 　]*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*");
	static private final Pattern patternKaien4 = Pattern.compile(".*([0-9０-９][0-9０-９])時開演.*");
	static private final Pattern patternKaien5 = Pattern.compile(".*午後([0-9０-９])時開演.*");
	static private final Pattern patternKaien6 = Pattern.compile(".*開演[ 　]*：* *([0-9][0-9])[:：]([0-9][0-9]).*");
	static private final Pattern patternRyoukin1 = Pattern.compile("[ 　]*入場料[:：]* *(.*)");
	static private final Pattern patternRyoukin2 = Pattern.compile("料金[:：]* *");

	static public void main(String[] args)
		throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, IOException, SAXException, XPathExpressionException
	{
		ConcertSchemaDocument schemaDocument = new ConcertSchemaDocument("testdata/ConcertSchema.xsd");
		String [] halls = schemaDocument.getHalls();
		String [] playerNames = schemaDocument.getPlayerNames();
		String [] partNames = schemaDocument.getPartNames();
		String [] composers = schemaDocument.getComposerNames();
		String [] lines = new StringListFromFile("testdata/concertLeSquare2017.txt", "utf-8").toArray(new String[]{});
		ConcertInformation concertInformation =
			trimConcertInfo(0, "xxxオーケストラ", lines, halls, composers, partNames, playerNames);
		concertInformation.dump();
		ArrayList<ConcertInformation> concertInformations = new ArrayList<ConcertInformation>();
		concertInformations.add(concertInformation);

		NewConcertDocument document = new NewConcertDocument(concertInformations);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		document.write(transformer, new OutputStreamWriter(new FileOutputStream(new File("..", "NewConcert.xml")), "utf-8"));
	}

	/**
	 * テキストからコンサート情報を生成
	 * @param index インデックス
	 * @param orchestraName 管弦楽団の名前
	 * @param lines 加工対象
	 * @param halls ホール一覧
	 * @param composers 作曲者一覧
	 * @param partNames パート名一覧
	 * @param playerNames 演奏者一覧
	 * @return コンサート情報
	 */ 
	static public ConcertInformation trimConcertInfo(int index,
		String orchestraName, String [] lines, String [] halls,
		String [] composers, String [] partNames, String [] playerNames)
	{
		ConcertInformation concert = new ConcertInformation(index);

		concert.addPart("管弦楽");
		concert.setPlayer(orchestraName);
		concert.orchestraOk = true;

		LineType lineType = LineType.None;
		String composer = null;
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

						if (!concert.nameOk &&
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

									if (!concert.orchestraOk)
									{
										concert.addPart("管弦楽");
										concert.setPlayer(playerNames[j]);
									}

									line = Pattern.compile(playerNames[j] + "　*").matcher(line).replaceAll(empty);
									break;
								}
							}
							line = line.replace("のお知らせ", empty);
							Matcher matcher = patternEnsoukai1.matcher(line);
							if (matcher.matches())
							{
								line = matcher.replaceAll("$1");
								concert.nameOk = true;
							}
							matcher = patternEnsoukai2.matcher(line);
							if (matcher.matches())
							{
								line = matcher.replaceAll("$1");
								concert.nameOk = true;
							}

							concert.name = line;
							continue;
						}

						if (Pattern.matches("曲目[:：]*$", line) || line.equals("プログラム"))
						{
							// 不要な行と判断。

							continue;
						}

						if (patternDate1.matcher(line).matches())
						{
							// yyyy年mm月dd日

							concert.setDate(patternDate1.matcher(line).replaceAll("$1/$2/$3"));
							kakutei = true;
						}
						/*
						else if (patternDate2.matcher(line).matches())
						{
							// ｙｙｙｙ年ｍｍ月ｄｄ日

							concert.setDate(patternDate2.matcher(line).replaceAll("$1/$2/$3"));
							kakutei = true;
						}
						*/
						else if (patternDate3.matcher(line).matches())
						{
							// yy/mm/dd or yy.mm.dd

							concert.setDate(patternDate3.matcher(line).replaceAll("20$1/$2/$3"));
							kakutei = true;
						}
						else if (line.indexOf("http") < 0 && patternDate4.matcher(line).matches())
						{
							// yyyy/mm/dd

							concert.setDate(patternDate4.matcher(line).replaceAll("$1/$2/$3"));
							kakutei = true;
						}

						if (patternKaijouKaien1.matcher(line).matches())
						{
							// 「13:30開場 14:00開演」の形式の開場・開演時刻を含む。

							concert.kaijou = patternKaijou1.matcher(line).replaceAll("$1:$2");
							concert.kaien = patternKaien1.matcher(line).replaceAll("$1:$2");
							kakutei = true;
						}
						else if (patternKaijouKaien2.matcher(line).matches())
						{
							// 「開場13:30 開演14:00」の形式の開場・開演時刻を含む。

							concert.kaijou = patternKaijou2.matcher(line).replaceAll("$1:$2");
							concert.kaien = patternKaien2.matcher(line).replaceAll("$1:$2");
							kakutei = true;
						}
						else
						{
							if (patternKaijou1.matcher(line).matches())
							{
								// 「13:30開場」の形式の開場時刻を含む。

								concert.kaijou = patternKaijou1.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (patternKaijou3.matcher(line).matches())
							{
								// 「13時30分開場」の形式の開場時刻を含む。

								concert.kaijou = patternKaijou3.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (patternKaijou2.matcher(line).matches())
							{
								// 「開場：13:30」の形式の開場時刻を含む。

								concert.kaijou = patternKaijou2.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}

							if (patternKaien1.matcher(line).matches())
							{
								// 「14:00開演」の形式の開演時刻を含む。

								concert.kaien = patternKaien1.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (patternKaijou3.matcher(line).matches())
							{
								// 「14時00分開演」の形式の開演時刻を含む。

								concert.kaien = patternKaijou3.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (patternKaien4.matcher(line).matches())
							{
								// 「14時開演」の形式の開演時刻を含む。

								concert.kaien = patternKaien4.matcher(line).replaceAll("$1:00");
								kakutei = true;
							}
							else if (patternKaien5.matcher(line).matches())
							{
								// 「午後2時開演」の形式の開演時刻を含む。

								String hour12 = patternKaien5.matcher(line).replaceAll("$1");
								concert.kaien = String.format("%d:00", Integer.valueOf(ZenkakuHankakuConverter.ConvertZenkakuToHankaku(hour12)) + 12);
								kakutei = true;
							}
							else if (patternKaien2.matcher(line).matches())
							{
								// 「開演14:00」の形式の開演時刻を含む。

								concert.kaien = patternKaien2.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
							else if (patternKaien6.matcher(line).matches())
							{
								// 「開演：14:00」の形式の開演時刻を含む。

								concert.kaien = patternKaien6.matcher(line).replaceAll("$1:$2");
								kakutei = true;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						if (line.endsWith("無料"))
						{
							// 料金情報を含む。

							concert.ryoukin = "入場無料";
							continue;
						}
						else if (line.indexOf("全席自由") >= 0)
						{
							// 料金情報を含む。

							concert.ryoukin = line;
							continue;
						}
						else if (line.indexOf("入場料") >= 0)
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
									patternRyoukin1.matcher(line).replaceAll("$1");
								continue;
							}
						}
						else if (line.startsWith("料金"))
						{
							// 料金情報を含む。

							concert.ryoukin =
								patternRyoukin2.matcher(line).replaceAll(empty);
							continue;
						}
						else if (line.endsWith("円"))
						{
							// 料金情報を含む。

							concert.ryoukin = line;
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

						if (Pattern.matches(".*駅.*口", line))
						{
							// 会場案内を含む。

							continue;
						}

						for (int j = 0; j < composerSurrogates.length; j++)
						{
							if (Pattern.matches(composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*", line))
							{
								// 作曲家：曲名の形式。

								line = Pattern.compile(composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*").matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目：").matcher(line).replaceAll(empty);

								concert.addComposer(composerSurrogates[j][1]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(composerSurrogates[j][0] + "作曲「.*」", line))
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
							if (Pattern.matches(".*" + composers[j] + "[ 　\t]*[/:／：…　][ \t]*.*", line))
							{
								// 作曲家：曲名の形式。

								line = Pattern.compile(".*" + composers[j] + "[ 　\t]*[/:／：…　][ \t]*").matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目：").matcher(line).replaceAll(empty);

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(composers[j] + "  *.*", line))
							{
								// 作曲家 曲名の形式。

								line = Pattern.compile(composers[j] + "  *(.*)").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(composers[j] + "作曲「.*」", line))
							{
								// 作曲家作曲「曲名」の形式。

								line = Pattern.compile(composers[j] + "作曲「(.*)」").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(composers[j] + "作曲[ 　].*", line))
							{
								// 作曲家作曲 曲名の形式。

								line = Pattern.compile(composers[j] + "作曲[ 　](.*)").matcher(line).replaceAll("$1");

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(composers[j] + "「.*」", line))
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
							if (Pattern.matches(".*[ 　]*[/／].*" + composers[j], line))
							{
								// 曲名／作曲家の形式。

								line = Pattern.compile("[ 　]*[/／].*" + composers[j]).matcher(line).replaceAll(empty);
								line = Pattern.compile("曲　*目[：　]*").matcher(line).replaceAll(empty);

								concert.addComposer(composers[j]);
								concert.setTitle(line);
								kakutei = true;
								break;
							}

							if (Pattern.matches(".*[(（]" + composers[j], line))
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
							if (line.equals(composers[j]) ||
								line.equals(composers[j] + "作曲") ||
								line.indexOf("オール" + composers[j] + "プログラム") >= 0 ||
								line.indexOf("オール・" + composers[j] + "・プログラム") >= 0)
							{
								// 作曲家名の行である。

								composer  = composers[j];
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
							if (!concert.orchestraOk &&
								line.equals(playerNames[j]) &&
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
								concert.orchestraOk = true;
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

						if (line.indexOf("交響曲") >= 0 ||
							line.indexOf("序曲") >= 0 ||
							line.indexOf("バレエ音楽") >= 0)
						{
							// 曲目に見える

							concert.addComposer(composer);
							concert.setTitle(line);
							kakutei = true;
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

							if (Pattern.matches(partNames[j] + "[ 　:：]", line) ||
								Pattern.matches(partNames[j] + "ソロ[ 　:：]", line) ||
								Pattern.matches(partNames[j] + "独奏[ 　:：]", line))
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

	/**
	 * コンサート情報から登録用XMLを構築
	 * @param concertInformations コンサート情報
	 */
	public NewConcertDocument(ArrayList<ConcertInformation> concertInformations)
		throws ParserConfigurationException, TransformerConfigurationException, TransformerFactoryConfigurationError
	{
		Element top = createElement("c:concertCollection");
		appendChild(top);
		top.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		top.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:c", "concert");
		top.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "concert Concert.xsd");

		for (ConcertInformation concertInformation : concertInformations)
		{
			Element concert = createElement("concert");
			top.appendChild(concert);
			concert.setAttribute("name", concertInformation.name);
			concert.setAttribute("date", concertInformation.date);
			concert.setAttribute("kaijou", concertInformation.getKaijou());
			concert.setAttribute("kaien", concertInformation.getKaien());
			concert.setAttribute("hall", concertInformation.hall);
			concert.setAttribute("ryoukin", concertInformation.ryoukin);

			Element kyokuCollection = createElement("kyokuCollection");
			concert.appendChild(kyokuCollection);
			for (StringAndString composerNameAndTitle : concertInformation.composerNameAndTitles)
			{
				Element kyoku = createElement("kyoku");
				kyokuCollection.appendChild(kyoku);
				kyoku.setAttribute("composer", composerNameAndTitle.string1);
				kyoku.setAttribute("title", composerNameAndTitle.string2);
			}

			Element playerCollection = createElement("playerCollection");
			concert.appendChild(playerCollection);
			for (StringAndString partAndPlayer : concertInformation.partAndPlayers)
			{
				Element player = createElement("player");
				playerCollection.appendChild(player);
				player.setAttribute("part", partAndPlayer.string1);
				player.setAttribute("name", partAndPlayer.string2);
			}
		}

		// 手作業編集分の空要素を追加
		Element concert = createElement("concert");
		top.appendChild(concert);
		concert.setAttribute("name", empty);
		concert.setAttribute("date", empty);
		concert.setAttribute("kaijou", "12:00");
		concert.setAttribute("kaien", "12:00");
		concert.setAttribute("hall", empty);
		concert.setAttribute("ryoukin", new String());

		Element kyokuCollection = createElement("kyokuCollection");
		concert.appendChild(kyokuCollection);
		for (int i=0 ; i<3 ; i++)
		{
			Element kyoku = createElement("kyoku");
			kyokuCollection.appendChild(kyoku);
			kyoku.setAttribute("composer", "");
			kyoku.setAttribute("title", "");
		}

		Element playerCollection = createElement("playerCollection");
		concert.appendChild(playerCollection);
		Element player = createElement("player");
		playerCollection.appendChild(player);
		player.setAttribute("name", "");
		player.setAttribute("part", "管弦楽");
		player = createElement("player");
		playerCollection.appendChild(player);
		player.setAttribute("name", "");
		player.setAttribute("part", "指揮");
	}
}
