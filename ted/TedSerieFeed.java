package ted;

import java.io.Serializable;
/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This class will hold data for one feed
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

public class TedSerieFeed implements Serializable
{
	private static final long serialVersionUID = 8455093524452410549L;
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private long checkdate;
	private String url;
	private boolean selfMade = false;
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	public TedSerieFeed (String u, long date)
	{
		this.setDate(date);
		this.setUrl(u);
		this.setSelfMade(false);
	}
	
	public TedSerieFeed (String u, long date, boolean b)
	{
		this.setDate(date);
		this.setUrl(u);
		this.setSelfMade(b);
	}
	
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/

	public TedSerieFeed(String url2, boolean b) 
	{
		this.setDate(0);
		this.setUrl(url2);
		this.setSelfMade(b);
	}

	/**
	 * @return Returns the checkdate.
	 */
	public long getDate()
	{
		return checkdate;
	}

	/**
	 * @param checkdate The checkdate to set.
	 */
	public void setDate(long checkdate)
	{
		this.checkdate = checkdate;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl()
	{
		this.url = this.url.replace(" ", "%20");
		return url;
	}

	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url)
	{
		this.url = url.replace(" ", "%20");
	}
	
	public boolean getSelfmade()
	{
		return this.selfMade;
	}
	
	private void setSelfMade(boolean b)
	{
		this.selfMade = b;
	}
}
