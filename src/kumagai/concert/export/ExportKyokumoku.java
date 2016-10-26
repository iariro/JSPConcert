package kumagai.concert.export;

import java.io.*;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import kumagai.concert.*;

public class ExportKyokumoku
{
	/**
	 * 全曲目情報をCSV形式で取得。
	 * @param args [0]=出力ファイル名
	 */
	public static void main(String[] args)
		throws SQLException, IOException
	{
		if (args.length >= 1)
		{
			DriverManager.registerDriver(new SQLServerDriver());

			Connection connection =
				java.sql.DriverManager.getConnection
					("jdbc:sqlserver://localhost:2144;DatabaseName=Concert;User=sa;Password=p@ssw0rd;");

			String [] csv = KyokumokuCollection.getAllKyokumoku(connection);

			connection.close();

			FileOutputStream stream = new FileOutputStream(args[0]);
			PrintWriter writer = new PrintWriter(stream);
			for (int i=0 ; i<csv.length ; i++)
			{
				writer.println(csv[i]);
			}
			stream.close();
		}
	}
}
