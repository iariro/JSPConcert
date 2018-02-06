package kumagai.concert.crawler;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ktool.xml.KDocument;

/**
 * コンサートスキーマXML
 */
public class ConcertSchemaDocument
	extends KDocument
{
	static private final XPath xpath = createXPath("xsd", "http://www.w3.org/2001/XMLSchema");

	/**
	 * テストコード
	 * @param args 未使用
	 */
	public static void main(String[] args)
		throws TransformerConfigurationException, ParserConfigurationException, TransformerFactoryConfigurationError, IOException, SAXException, XPathExpressionException
	{
		ConcertSchemaDocument document = new ConcertSchemaDocument("testdata/ConcertSchema.xsd");

		String [] halls = document.getHalls();
		for (String hall : halls)
		{
			System.out.println(hall);
		}
		System.out.println();

		String [] players = document.getPlayerNames();
		for (String player : players)
		{
			System.out.println(player);
		}
		System.out.println();

		String [] parts = document.getPartNames();
		for (String part : parts)
		{
			System.out.println(part);
		}
		System.out.println();

		String [] composers = document.getComposerNames();
		for (String composer : composers)
		{
			System.out.println(composer);
		}
		System.out.println();
	}

	/**
	 * 指定のファイルを読み込む
	 * @param filename ファイル名
	 */
	public ConcertSchemaDocument(String filename)
		throws ParserConfigurationException, TransformerConfigurationException, TransformerFactoryConfigurationError, IOException, SAXException
	{
		super(filename);
	}

	/**
	 * ホール名一覧を取得
	 * @return ホール名一覧
	 */
	public String[] getHalls()
		throws XPathExpressionException
	{
		NodeList hallNodes =
			(NodeList)xpath.evaluate(
				"//xsd:schema/xsd:simpleType[@name='hallName']/xsd:restriction/xsd:enumeration",
				getDocument(),
				XPathConstants.NODESET);

		ArrayList<String> halls = new ArrayList<String>();

		for (int i=0;i<hallNodes.getLength();i++)
		{
			halls.add(((Element)hallNodes.item(i)).getAttribute("value"));
		}

		return halls.toArray(new String [] {});
	}

	/**
	 * 演奏者名一覧を取得
	 * @return 演奏者名一覧
	 */
	public String[] getPlayerNames()
		throws XPathExpressionException
	{
		NodeList playerNodes =
			(NodeList)xpath.evaluate(
	   			"/xsd:schema/xsd:simpleType[@name='playerName']/xsd:restriction/xsd:enumeration",
				getDocument(),
				XPathConstants.NODESET);

		ArrayList<String> playerNames = new ArrayList<String>();

		for (int i=0;i<playerNodes.getLength();i++)
		{
			playerNames.add(((Element)playerNodes.item(i)).getAttribute("value"));
		}

		return playerNames.toArray(new String [] {});
	}

	/**
	 * パート名一覧を取得
	 * @return パート名一覧
	 */
	public String[] getPartNames()
		throws XPathExpressionException
	{
		NodeList partNodes =
			(NodeList)xpath.evaluate(
	   			"/xsd:schema/xsd:simpleType[@name='partName']/xsd:restriction/xsd:enumeration",
				getDocument(),
				XPathConstants.NODESET);

		ArrayList<String> partNames = new ArrayList<String>();

		for (int i=0;i<partNodes.getLength();i++)
		{
			partNames.add(((Element)partNodes.item(i)).getAttribute("value"));
		}

		return partNames.toArray(new String [] {});
	}

	/**
	 * 作曲家名一覧を取得
	 * @return 作曲家名一覧
	 */
	public String[] getComposerNames()
		throws XPathExpressionException
	{
		NodeList composerNameNodes =
			(NodeList)xpath.evaluate(
	   			"/xsd:schema/xsd:simpleType[@name='composerName']/xsd:restriction/xsd:enumeration",
				getDocument(),
				XPathConstants.NODESET);

		ArrayList<String> composerNames = new ArrayList<String>();

		for (int i=0;i<composerNameNodes.getLength();i++)
		{
			composerNames.add(((Element)composerNameNodes.item(i)).getAttribute("value"));
		}

		return composerNames.toArray(new String [] {});
	}
}
