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

	static ConcertInformation trimConcertInfo(int index, String [] lines, String [] halls, com.sun.org.apache.xpath.internal.operations.String [] composers, String [] playerNames)
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
/*
						if (Regex.IsMatch(line, "曲目[:：]*$") || line == "プログラム")
						{
							// 不要な行と判断。

							continue;
						}

						if (Regex.IsMatch(line, "[0-9]{4}年[ 　]*[0-9]*月[ 　]*[0-9]*日"))
						{
							// 日付を含む。

							concert.date =
								Regex.Replace(line, ".*([0-9]{4})年[ 　]*([0-9]*)月[ 　]*([0-9]*)日.*", "$1/$2/$3");
							kakutei = true;
						}
						else if (Regex.IsMatch(line, "[０-９]{4}年[０-９]*月[０-９]*日"))
						{
							// 日付を含む。

							concert.date =
								Regex.Replace(line, ".*([０-９]{4})年([０-９]*)月([０-９]*)日.*", "$1/$2/$3");
							kakutei = true;
						}
						else if (Regex.IsMatch(line, "[0-9]{2}[/.][0-9]*[/.][0-9]*"))
						{
							// 日付を含む。

							concert.date =
								Regex.Replace(line, ".*([0-9]{2})[/.]([0-9]*)[/.]([0-9]*).*", "20$1/$2/$3");
							kakutei = true;
						}
						else if (Regex.IsMatch(line, "[0-9]{4}/[0-9]* /[0-9]*")) スペース消すこと
						{
							// 日付を含む。

							concert.date =
								Regex.Replace(line, ".*([0-9]{4})/([0-9]*)/([0-9]*).*", "$1/$2/$3");
							kakutei = true;
						}

						if (Regex.IsMatch(line, "[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開場[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開演"))
						{
							// 「13:30開場 14:00開演」の形式の開場・開演時刻を含む。

							concert.kaijou = Regex.Replace(line, ".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９])開場.*", "$1:$2");
							concert.kaien = Regex.Replace(line, ".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]) *開演.*", "$1:$2");
							kakutei = true;
						}
						else if (Regex.IsMatch(line, "開場[ 　]*：* *[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９][ 　]*開演[ 　]*[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９]"))
						{
							// 「開場13:30 開演14:00」の形式の開場・開演時刻を含む。

							concert.kaijou = Regex.Replace(line, ".*開場[ 　]*：* *([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*", "$1:$2");
							concert.kaien = Regex.Replace(line, ".*開演[ 　]*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]).*", "$1:$2");
							kakutei = true;
						}
						else
						{
							if (Regex.IsMatch(line, "[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９]開場"))
							{
								// 「13:30開場」の形式の開場時刻を含む。

								concert.kaijou = Regex.Replace(line, ".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９])開場.*", "$1:$2");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "[0-9０-９][0-9０-９]時[0-9０-９][0-9０-９]分開場"))
							{
								// 「13時30分開場」の形式の開場時刻を含む。

								concert.kaijou = Regex.Replace(line, ".*([0-9０-９][0-9０-９])時([0-9０-９][0-9０-９])分開場.*", "$1:$2");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "開場[ 　]*：* *[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開場：13:30」の形式の開場時刻を含む。

								concert.kaijou = Regex.Replace(line, ".*開場[ 　]*：* *([0-9][0-9])[:：]([0-9][0-9]).*", "$1:$2");
								kakutei = true;
							}

							if (Regex.IsMatch(line, "[0-9０-９][0-9０-９][:：][0-9０-９][0-9０-９] *開演"))
							{
								// 「14:00開演」の形式の開演時刻を含む。

								concert.kaien = Regex.Replace(line, ".*([0-9０-９][0-9０-９])[:：]([0-9０-９][0-9０-９]) *開演.*", "$1:$2");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "[0-9０-９][0-9０-９]時[0-9０-９][0-9０-９]分 *開演"))
							{
								// 「14時00分開演」の形式の開演時刻を含む。

								concert.kaien = Regex.Replace(line, ".*([0-9０-９][0-9０-９])時([0-9０-９][0-9０-９])分 *開演.*", "$1:$2");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "[0-9０-９][0-9０-９]時開演"))
							{
								// 「14時開演」の形式の開演時刻を含む。

								concert.kaien = Regex.Replace(line, ".*([0-9０-９][0-9０-９])時開演.*", "$1:00");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "午後[0-9０-９]時開演"))
							{
								// 「午後2時開演」の形式の開演時刻を含む。

								string hour12 = Regex.Replace(line, ".*午後([0-9０-９])時開演.*", "$1");
								concert.kaien = string.Format("{0}:00", int.Parse(ZenkakuHankakuConverter.ConvertZenkakuToHankaku(hour12)) + 12);
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "開演[ 　]*[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開演14:00」の形式の開演時刻を含む。

								concert.kaien = Regex.Replace(line, ".*開演[ 　]*([0-9][0-9])[:：]([0-9][0-9]).*", "$1:$2");
								kakutei = true;
							}
							else if (Regex.IsMatch(line, "開演[ 　]*：* *[0-9][0-9][:：][0-9][0-9]"))
							{
								// 「開演：14:00」の形式の開演時刻を含む。

								concert.kaien = Regex.Replace(line, ".*開演[ 　]*：* *([0-9][0-9])[:：]([0-9][0-9]).*", "$1:$2");
								kakutei = true;
							}
						}
						*/

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

						/*
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
									Regex.Replace(line, "入場料[:：]* *", string.Empty);
								continue;
							}
						}

						if (line.startsWith("料金"))
						{
							// 料金情報を含む。

							concert.ryoukin =
								Regex.Replace(line, "料金[:：]* *", string.Empty);
							continue;
						}
						*/

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

						/*
						if (Regex.IsMatch(line, ".*駅.*口"))
						{
							// 会場案内を含む。

							continue;
						}
						*/

						for (int j = 0; j < composerSurrogates.length; j++)
						{
							/*
							if (Regex.IsMatch(line, composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*"))
							{
								// 作曲家：曲名の形式。

								line = Regex.Replace(line, composerSurrogates[j][0] + "[ 　]*[/:／：][ 　]*", string.Empty);
								line = Regex.Replace(line, "曲　*目：", string.Empty);

								concert.AddComposer(composerSurrogates[j][1]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, composerSurrogates[j][0] + "作曲「.*」"))
							{
								// 作曲家作曲「曲名」の形式。

								line = Regex.Replace(line, composerSurrogates[j][0] + "作曲「(.*)」", "$1");

								concert.AddComposer(composerSurrogates[j][1]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}
							*/
						}

						for (int j = 0; j < composers.length; j++)
						{
							/*
							if (Regex.IsMatch(line, composers[j] + "[ \t]*[/:／：　][ \t]*"))
							{
								// 作曲家：曲名の形式。

								line = Regex.Replace(line, composers[j] + "[ \t]*[/:／：　][ \t]*", string.Empty);
								line = Regex.Replace(line, "曲　*目：", string.Empty);

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, composers[j] + "  *.*"))
							{
								// 作曲家 曲名の形式。

								line = Regex.Replace(line, composers[j] + "  *(.*)", "$1");

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, composers[j] + "作曲「.*」"))
							{
								// 作曲家作曲「曲名」の形式。

								line = Regex.Replace(line, composers[j] + "作曲「(.*)」", "$1");

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, composers[j] + "作曲[ 　].*"))
							{
								// 作曲家作曲 曲名の形式。

								line = Regex.Replace(line, composers[j] + "作曲[ 　](.*)", "$1");

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, composers[j] + "「.*」"))
							{
								// 作曲家「曲名」の形式。

								line = Regex.Replace(line, composers[j] + "「(.*)」", "$1");

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}
							*/
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						/*
						for (int j = 0; j < composers.length; j++)
						{
							if (Regex.IsMatch(line, ".* *[/／] *" + composers[j]))
							{
								// 曲名／作曲家の形式。

								line = Regex.Replace(line, " *[/／] *" + composers[j], string.Empty);
								line = Regex.Replace(line, "曲　*目[：　]*", string.Empty);

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}

							if (Regex.IsMatch(line, ".*[(（]" + composers[j]))
							{
								// 曲名（作曲家）の形式。

								line = Regex.Replace(line, "[(（]" + composers[j], string.Empty);

								concert.AddComposer(composers[j]);
								concert.SetTitle(line);
								kakutei = true;
								break;
							}
						}

						if (kakutei)
						{
							// 行内容確定。

							continue;
						}

						for (int j = 0; j < composers.Length; j++)
						{
							if (line == composers[j] ||
								line == composers[j] + "作曲" ||
								line.IndexOf("オール" + composers[j] + "プログラム") >= 0)
							{
								// 作曲家名の行である。

								concert.AddComposer(composers[j]);
								kakutei = true;
								lineType = LineType.Composer;
								break;
							}
						}

						for (int j = 0; j < composerSurrogates.Length; j++)
						{
							if (line.EndsWith(composerSurrogates[j][0]) ||
								line.IndexOf("オール" + composerSurrogates[j][0] + "プログラム") >= 0)
							{
								// 作曲家名の行である。

								concert.AddComposer(composerSurrogates[j][1]);
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

						for (int j = 0; j < playerNames.Length && !kakutei; j++)
						{
							if (line == playerNames[j] &&
								(line.StartsWith("Ensemble") ||
								line.EndsWith("楽団") ||
								line.StartsWith("オーケストラ") ||
								line.EndsWith("オーケストラ") ||
								line.EndsWith("シンフォニエッタ") ||
								line.EndsWith("Orchestra")))
							{
								// オーケストラ名の行と判断する。

								concert.AddPart("管弦楽");
								concert.SetPlayer(playerNames[j]);
								kakutei = true;
								break;
							}

							for (int k = 0; k < partNames.Length && !kakutei; k++)
							{
								if ((line.StartsWith(partNames[k]) ||
									line.StartsWith(partNames[k].Insert(1, "　"))) &&
									(line.IndexOf(playerNames[j]) >= 0 ||
									line.IndexOf(playerNames[j].Replace(' ', '　')) >= 0 ||
									line.IndexOf(playerNames[j].Replace(" ", string.Empty)) >= 0))
								{
									// パート名＋演奏者名の行である。

									concert.AddPart(partNames[k]);
									concert.SetPlayer(playerNames[j]);
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
						for (int j = 0; j < partNames.Length; j++)
						{
							if (line.StartsWith("合唱指揮"))
							{
								// 合唱指揮情報である。

								break;
							}

							if (Regex.IsMatch(line, partNames[j] + "[ 　:：]") ||
								Regex.IsMatch(line, partNames[j] + "ソロ[ 　:：]") ||
								Regex.IsMatch(line, partNames[j] + "独奏[ 　:：]"))
							{
								// パート名から始まっている。

								string player =
									Regex.Replace(
										line,
										partNames[j] + "[^ 　\t:：]*[ 　\t:：]*",
										string.Empty);
								player = player.Replace("　", " ");

								concert.AddPart(partNames[j]);
								concert.SetPlayer(player);
								kakutei = true;
								break;
							}
						}
						*/

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
