package kumagai.concert.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import ktool.datetime.DateTime;

public class PastOrchestraListServlet
	extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		try
		{
			DriverManager.registerDriver(new SQLServerDriver());
			Connection connection = DriverManager.getConnection(getInitParameter("ConcertSqlserverUrl"));
			String sql = "select Player.name as title, Player.siteurl as url, Player.siteencoding, Player.active, max(Concert.date) as lastdate from Concert " +
				"join Shutsuen on Shutsuen.concertId=Concert.id " +
				"join Player on Player.id=Shutsuen.playerId " +
				"where Shutsuen.partId=1 and len(Player.siteurl)>0 and Player.active=1 " +
				"group by player.id, Player.name, Player.siteurl, Player.siteencoding, Player.active " +
				"having max(Concert.date) < getdate() order by max(Concert.date)";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();

			request.setCharacterEncoding("utf-8");
			response.setContentType("text/plain; charset=UTF-8");

			PrintWriter writer = response.getWriter();
			while (result.next())
			{
				String title = result.getString("title");
				String url = result.getString("url");
				String siteencoding = result.getString("siteencoding");
				int active = result.getInt("active");
				DateTime lastdate = new DateTime(result.getDate("lastdate"));
				writer.printf("%s,%s,%s,%d,%s\n", title, url, siteencoding, active, lastdate.toString());
			}
		}
		catch (SQLException e)
		{
			PrintWriter writer = response.getWriter();
			writer.println(e.toString());
			e.printStackTrace();
		}
	}
}
