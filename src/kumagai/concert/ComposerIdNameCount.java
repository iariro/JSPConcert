package kumagai.concert;

/**
 * 作曲家のID・名前・カウント情報。
 * @author kumagai
 */
public class ComposerIdNameCount
{
	public final int id;
	public final String name;
	public final int count;

	/**
	 * 指定の値をメンバーに割り当てる。
	 * @param id ID
	 * @param name 名前
	 * @param count カウント
	 */
	public ComposerIdNameCount(int id, String name, int count)
	{
		this.id = id;
		this.name = name;
		this.count = count;
	}
}
