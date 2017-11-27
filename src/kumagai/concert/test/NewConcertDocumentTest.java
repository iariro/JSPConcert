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

	public void testLeSquare2017()
		throws FileNotFoundException, IOException
	{
		String [] lines = new StringListFromFile("testdata/concertLeSquare2017.txt", "utf-8").toArray(new String[]{});
		ConcertInformation concert =
			NewConcertDocument.trimConcertInfo(0, lines, halls, composers, partNames, playerNames);

		assertEquals("2017年演奏会", concert.name);
		assertEquals("2017/11/19", concert.date);
		assertEquals("13:30", concert.getKaijou());
		assertEquals("14:00", concert.getKaien());
		assertEquals("すみだトリフォニーホール", concert.hall);
		assertTrue(concert.ryoukin.indexOf("全席自由 1,000円 当日券あり") >= 0);
	}
}
