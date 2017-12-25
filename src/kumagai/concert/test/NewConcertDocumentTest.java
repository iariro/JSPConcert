package kumagai.concert.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import ktool.io.StringListFromFile;
import kumagai.concert.crawler.ConcertInformation;
import kumagai.concert.crawler.ConcertSchemaDocument;
import kumagai.concert.crawler.NewConcertDocument;

public class NewConcertDocumentTest
	extends TestCase
{
	private String [] halls;
	private String [] playerNames;
	private String [] partNames;
	private String [] composers;

	@Override
	protected void setUp()
		throws Exception
	{
		ConcertSchemaDocument schemaDocument = new ConcertSchemaDocument("testdata/ConcertSchema.xsd");
		halls = schemaDocument.getHalls();
		playerNames = schemaDocument.getPlayerNames();
		partNames = schemaDocument.getPartNames();
		composers = schemaDocument.getComposerNames();
	}

	public void testLeSquare201711()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_LeSquare201711.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "ル スコアール管弦楽団", lines, halls, composers, partNames, playerNames);

		assertEquals("第43回演奏会", concert.name);
		assertEquals("2017/11/19", concert.date);
		assertEquals("13:30", concert.getKaijou());
		assertEquals("14:00", concert.getKaien());
		assertEquals("すみだトリフォニーホール", concert.hall);
		assertTrue(concert.ryoukin.indexOf("全席自由 1,000円 当日券あり") >= 0);
		assertEquals(1, concert.composerNameAndTitles.size());
		assertEquals("マーラー", concert.composerNameAndTitles.get(0).string1);
		assertEquals("交響曲第3番", concert.composerNameAndTitles.get(0).string2);
		assertEquals(1, concert.partAndPlayers.size());
		assertEquals("ル スコアール管弦楽団", concert.partAndPlayers.get(0).string2);
	}

	public void testTraum2017()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_Traum201810.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "xxxオーケストラ", lines, halls, composers, partNames, playerNames);

		assertEquals("第6回演奏会", concert.name);
		assertEquals("2018/10/8", concert.date);
		assertEquals("14:30", concert.getKaijou());
		assertEquals("15:00", concert.getKaien());
		assertEquals("パルテノン多摩", concert.hall);
		assertNull(concert.ryoukin);
		assertEquals(3, concert.composerNameAndTitles.size());
		assertEquals("ドヴォルザーク", concert.composerNameAndTitles.get(0).string1);
		assertEquals("『スラブ舞曲集』 (B.147)より第2番「ドゥムカ」", concert.composerNameAndTitles.get(0).string2);
		assertEquals("ドヴォルザーク", concert.composerNameAndTitles.get(1).string1);
		assertEquals("交響曲第8番 ト長調 (B.163)", concert.composerNameAndTitles.get(1).string2);
		assertEquals("ブラームス", concert.composerNameAndTitles.get(2).string1);
		assertEquals("交響曲第2番 ニ長調", concert.composerNameAndTitles.get(2).string2);
		assertEquals(2, concert.partAndPlayers.size());
		assertEquals("管弦楽", concert.partAndPlayers.get(0).string1);
		assertEquals("xxxオーケストラ", concert.partAndPlayers.get(0).string2);
		assertEquals("指揮", concert.partAndPlayers.get(1).string1);
		assertEquals("石川 智己", concert.partAndPlayers.get(1).string2);
	}

	public void testHikarigaoka201806()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_Hikarigaoka201806.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "xxxオーケストラ", lines, halls, composers, partNames, playerNames);

		assertEquals("第48回定期演奏会", concert.name);
		assertEquals("2018/6/17", concert.date);
		assertEquals("13:45", concert.getKaijou());
		assertEquals("14:30", concert.getKaien());
		assertEquals("光が丘IMAホール", concert.hall);
		assertEquals("全自由席　1000円", concert.ryoukin);
		assertEquals(3, concert.composerNameAndTitles.size());
		assertEquals("モーツァルト", concert.composerNameAndTitles.get(0).string1);
		assertEquals("歌劇「ドン・ジョヴァンニ」序曲", concert.composerNameAndTitles.get(0).string2);
		assertEquals("モーツァルト", concert.composerNameAndTitles.get(1).string1);
		assertEquals("交響曲第35番ニ長調「ハフナー」", concert.composerNameAndTitles.get(1).string2);
		assertEquals("モーツァルト", concert.composerNameAndTitles.get(2).string1);
		assertEquals("　交響曲第40番ト短調", concert.composerNameAndTitles.get(2).string2);
		assertEquals(2, concert.partAndPlayers.size());
		assertEquals("管弦楽", concert.partAndPlayers.get(0).string1);
		assertEquals("xxxオーケストラ", concert.partAndPlayers.get(0).string2);
		assertEquals("指揮", concert.partAndPlayers.get(1).string1);
		assertEquals("喜古 恵理香", concert.partAndPlayers.get(1).string2);
	}

	public void testKoutouCity201806()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_KoutouCity201806.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "江東シティオーケストラ", lines, halls, composers, partNames, playerNames);

		assertEquals("第48回定期演奏会", concert.name);
		assertEquals("2018/6/10", concert.date);
		assertEquals("12:00", concert.getKaijou());
		assertEquals("12:00", concert.getKaien());
		assertEquals(null, concert.hall);
		assertEquals(null, concert.ryoukin);
		assertEquals(1, concert.composerNameAndTitles.size());
		assertEquals("ブラームス", concert.composerNameAndTitles.get(0).string1);
		assertEquals("交響曲第１番 ほか", concert.composerNameAndTitles.get(0).string2);
		assertEquals(1, concert.partAndPlayers.size());
		assertEquals("管弦楽", concert.partAndPlayers.get(0).string1);
		assertEquals("江東シティオーケストラ", concert.partAndPlayers.get(0).string2);
	}

	public void testGakushuuin201805()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_Gakushuuin201805.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "学習院輔仁会音楽部", lines, halls, composers, partNames, playerNames);

		assertEquals("第61回定期演奏会", concert.name);
		assertEquals("2018/5/13", concert.date);
		assertEquals("12:00", concert.getKaijou());
		assertEquals("12:00", concert.getKaien());
		assertEquals("すみだトリフォニーホール", concert.hall);
		assertEquals(null, concert.ryoukin);
		assertEquals(1, concert.partAndPlayers.size());
		assertEquals("管弦楽", concert.partAndPlayers.get(0).string1);
		assertEquals("学習院輔仁会音楽部", concert.partAndPlayers.get(0).string2);
	}

	public void testAprico201802()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concert_aprico201802.txt").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, "アプリコ・シンフォニー・オーケストラ", lines, halls, composers, partNames, playerNames);

		assertEquals("第29回定期演奏会", concert.name);
		assertEquals("2018/02/12", concert.date);
		assertEquals("13:30", concert.getKaijou());
		assertEquals("14:00", concert.getKaien());
		assertEquals("当日券１０００円", concert.ryoukin);
		assertEquals(2, concert.partAndPlayers.size());
		assertEquals("管弦楽", concert.partAndPlayers.get(0).string1);
		assertEquals("アプリコ・シンフォニー・オーケストラ", concert.partAndPlayers.get(0).string2);
		assertEquals("指揮", concert.partAndPlayers.get(1).string1);
		//assertEquals("横島 勝人", concert.partAndPlayers.get(1).string2);
		assertEquals(4, concert.composerNameAndTitles.size());
		assertEquals("ブラームス", concert.composerNameAndTitles.get(0).string1);
		assertEquals("交響曲第3番", concert.composerNameAndTitles.get(0).string2);
		assertEquals("R.シュトラウス", concert.composerNameAndTitles.get(1).string1);
		assertEquals("交響詩「死と変容」", concert.composerNameAndTitles.get(1).string2);
		assertEquals("ブラームス", concert.composerNameAndTitles.get(2).string1);
		assertEquals("悲劇的序曲", concert.composerNameAndTitles.get(2).string2);
		assertEquals("ラフマニノフ", concert.composerNameAndTitles.get(3).string1);
		assertEquals("交響曲第２番", concert.composerNameAndTitles.get(3).string2);
	}
}
