package ted;

import java.util.GregorianCalendar;

public class TedDailySerie extends TedSerie
{

	/**
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
		latestParseDate.setTimeInMillis(0);
		latestDownloadDate = new GregorianCalendar();
		latestDownloadDate.setTimeInMillis(0);
		maxDownloads = 1;
	}
	
	public long getLatestDownloadDateInMillis()
	{
		return latestDownloadDate.getTimeInMillis();
	}
	
	public long getLatestParseDateInMillis()
	{
		return latestParseDate.getTimeInMillis();
	}
	
	public void setLatestDownloadDate(int day, int month, int year)
	{
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		latestDownloadDate = d;
	}
	
	public void setLatestDownloadDateInMillis(long millis)
	{
		GregorianCalendar d = new GregorianCalendar();
		d.setTimeInMillis(millis);
		latestDownloadDate = d;
	}
	
	public void setLatestParseDate(int day, int month, int year)
	{
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		latestParseDate = d;
	}
	
	public int getCurrentSeason()
	{
		return -1;
	}
	
	public int getCurrentEpisode()
	{
		return -1;
	}
	
	public void setCurrentSeason(int i)
	{
		super.currentSeason = -1;
	}
	
	public void setCurrentEpisode(int i)
	{
		super.currentEpisode = -1;
	}

	public int getMaxDownloads() 
	{
		return maxDownloads;
	}

	public void setMaxDownloads(int maxDownloads) 
	{
		this.maxDownloads = maxDownloads;
	}
}
