package ted;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the Serie class of Ted. This class stores a show and keeps
 * track of the current episode and season that ted found.
 * It also contains other data that ted uses to parse and check episodes.
 * 
 * @author Roel
 * @author Joost
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
public class TedWatcher extends TedSerie{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487495895819393L;
	public final static int STATUS_DELETE = 3;
	private int afterStatus;

	public TedWatcher(String name, String keywords, int max, int min, String url)
	{
		this.setName(name);
		this.setKeywords(keywords);
		this.setMaxSize(max);
		this.setMinSize(min);
		//this.setUrl(url);
		this.setDownloadAll(true);
	}
	
	public void setAfterStatus(int i)
	{
		this.afterStatus = i; 
	}
	
	public int getAfterStatus()
	{
		return this.afterStatus;
	}
}
;