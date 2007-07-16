package ted;

import java.util.GregorianCalendar;

import ted.datastructures.DailyDate;

public class TedDailySerie extends TedSerie
{

	/**
	 * TED: Torrent Episode Downloader (2005 - 2007)
	 * 
	 * This is the about dialog of ted, it shows some trivial information
	 * 
	 * @author Joost
	 * 
	 * ted License:
	 * This file is part of ted. ted and all of it's parts are licensed
	 * under GNU General Public License (GPL) version 2.0
	 * 
	 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
	 *
	 */
	private static final long serialVersionUID = -4861614508271085128L;
	private GregorianCalendar latestParseDate;
	private GregorianCalendar latestDownloadDate;
	private int maxDownloads;
	
	public TedDailySerie()
	{
		this.isDaily = true;
		latestParseDate = new GregorianCalendar();
		//latestParseDate.setTimeInMillis(0);
		latestDownloadDate = new GregorianCalendar();
		//latestDownloadDate.setTimeInMillis(0);
		maxDownloads = 0;
	}
	
	
	/**
	 * 
	 * @return the latest downloaded airdate for this show in milliseconds 
	 */
	public long getLatestDownloadDateInMillis()
	{
		return latestDownloadDate.getTimeInMillis();
	}
	
	/**
	 * 
	 * @return the latest date this show has been parsed
	 */
	public long getLatestParseDateInMillis()
	{
		return latestParseDate.getTimeInMillis();
	}
	
	/**
	 * 
	 * Set the latest airdate of the item which has been downloaded for this show
	 * @param day day
	 * @param month month, is 0-based
	 * @param year year
	 */
	public void setLatestDownloadDate(int day, int month, int year)
	{
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		latestDownloadDate = d;
	}
	
	/**
	 * 
	 * Set the latest airdate of the item which has been downloaded for this show
	 * @param millis the date expressed in milliseconds
	 */
	public void setLatestDownloadDate(long millis)
	{
		GregorianCalendar d = new GregorianCalendar();
		d.setTimeInMillis(millis);
		latestDownloadDate = d;
	}
	
	/**
	 * Set the latest parse date of this show
	 * @param day day
	 * @param month month, 0-based
	 * @param year year
	 */
	public void setLatestParseDate(int day, int month, int year)
	{
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		latestParseDate = d;
	}
	
	/**
	 * Returns -1 as this is a daily show
	 */
	public int getCurrentSeason()
	{
		return -1;
	}
	
	/**
	 * Returns -1 as this is a daily show
	 */
	public int getCurrentEpisode()
	{
		return -1;
	}
	
	/**
	 * Overrides the original setCurrentSeason as this is a daily show
	 */
	public void setCurrentSeason(int i)
	{
		super.currentSeason = -1;
	}
	
	/**
	 * Overrides the original setCurrentEpisode as this is a daily show
	 */
	public void setCurrentEpisode(int i)
	{
		super.currentEpisode = -1;
	}

	/**
	 * Returns an integer representing the number of entries ted should download at most
	 * @return 0 represents "all", other integers are 'normal'
	 */
	public int getMaxDownloads() 
	{
		return maxDownloads;
	}

	/**
	 * Sets an integer representing the number of entries ted should download at most
	 * @param maxDownloads 0 0 represents "all", other integers are 'normal'
	 */
	public void setMaxDownloads(int maxDownloads) 
	{
		this.maxDownloads = maxDownloads;
	}
	
	public String getSearchForString() 
	{
		DailyDate dd = new DailyDate();
		dd.setDate(this.latestDownloadDate);
		
		String text;
		if (this.getMaxDownloads() == 1)
		{
			text = "TedTableModel.LabelDailySingle";
		}
		
		else
		{
			text = "TedTableModel.LabelDailyMultiple";
		}
		
		return Lang.getString(text) +" " + dd.toString();
	}
}
