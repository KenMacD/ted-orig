package ted.datastructures;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ted.Lang;

public class StandardStructure 
{
	int quality = 0;
	protected Date publishDate; // date torrent was published online
	protected Date airDate; // date episode was aired on tv
	protected String title; // episode title
	protected String summaryURL = "";
	
	/**
	 * @return Returns the quality.
	 */
	public int getQuality()
	{
		return quality;
	}
	/**
	 * @param quality The quality to set.
	 */
	public void setQuality(int quality)
	{
		this.quality = quality;
	}
	
	/**
	 * @return Returns the publishDate.
	 */
	public Date getPublishDate()
	{
		return publishDate;
	}
	
	/**
	 * @return Returns the publishDate.
	 */
	public String getFormattedPublishDate()
	{
		if (this.publishDate != null)
		{
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			return df.format(this.publishDate);
		}
		else
		{
			return Lang.getString("TedAddShowDialog.EpisodesTable.Upcoming");
		}
	}
	
	/**
	 * @param publishDate The publishDate to set.
	 */
	public void setPublishDate(long publishDate)
	{
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(publishDate);
		this.publishDate = c.getTime();
	}
	/**
	 * @param publishDate The Pubish date
	 */
	public void setPublishDate(Date publishDate)
	{
		this.publishDate = publishDate;
	}
	
	public void setSummaryURL(String url)
	{
		this.summaryURL = url;
	}
	
	public URL getSummaryURL() throws MalformedURLException
	{
		return new URL(this.summaryURL);
	}
	public String getSummaryURLString()
	{
		return this.summaryURL;
	}
	
	public Date getAirDate() 
	{
		return airDate;
	}
	public void setAirDate(Date airDate) 
	{
		this.airDate = airDate;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	public String getFormattedAirDate() 
	{
		if (this.airDate != null)
		{
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			return df.format(this.airDate);
		}
		else
		{
			return "-";
		}
	}
}
