package kumagai.concert.struts2;

import org.apache.struts2.convention.annotation.*;

/**
 * 演奏者情報編集ページ表示アクション。
 * @author kumagai
 */
@Namespace("/concert")
@Result(name="success", location="/concert/editplayer1.jsp")
public class EditPlayer1Action
{
	public int id;
	public String name;
	public String siteurl;
	public boolean active;

	/**
	 * 演奏者情報編集ページ表示アクション。
	 * @return 処理結果
	 * @throws Exception
	 */
	@Action("editplayer1")
	public String execute()
		throws Exception
	{
		return "success";
	}
}
