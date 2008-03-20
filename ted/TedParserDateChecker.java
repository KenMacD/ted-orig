package ted;
/****************************************************
 * IMPORTS
 ****************************************************/
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.sun.cnpi.rss.elements.Item;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * The TedParserDateChecker reads the date from an item and returns if we already 
 * checked it
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

public class TedParserDateChecker 
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private long thisParseDate;
	private long lastParseDate;
	
	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	/**
	 * Read the date from an item
	 * @param item Item 
	 * @return Date of item (in millisecs)
	 */
	public long getItemDate(Item item)
	{
		Calendar c = new GregorianCalendar();
		int year, month, day, hour, minute, second;
		try
		{
			String dateStr = item.getPubDate().toString().toLowerCase();
		
			StringTokenizer st = new StringTokenizer(dateStr, " ,:");
			st.nextToken();
			day = Integer.parseInt(st.nextToken().trim());
			month = getMonth(st.nextToken().trim());
			year = Integer.parseInt(st.nextToken().trim());
			hour = Integer.parseInt(st.nextToken().trim());
			minute = Integer.parseInt(st.nextToken().trim());
			second = Integer.parseInt(st.nextToken().trim());
		
			c.set(year, month, day, hour, minute, second);
		}
		catch (Exception e)
		{
			// error loading / parsing the date from the feed, return current date?
		}
		return c.getTimeInMillis();
	}
		
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	/**
	 * Have we already checked this item?
	 * @param item Item
	 * @param serie Show the item is from
	 * @return If ted already checked this item in the past
	 */
	public boolean alreadyChecked(Item item, TedSerieFeed feed)
	{
		long date = getItemDate(item);
		if(date > feed.getDate())
		{
			//setting the parse date happens after finding the torrent
			thisParseDate = date;
			lastParseDate = date;
			return false;
		}
		
		return true;
	}
	
	public long newestEntryInFeed(Item item)
	{
		long date = getItemDate(item);		
		return date;
	}
	
	/**
	 * Gets the int from a month string
	 * @param s String like jan, feb
	 * @return Int like 0, 1
	 */
	public int getMonth(String s)
	{
		s.toLowerCase();
		
        if(s.equals("jan") || s.equals("january"))
        	return 0;
		if(s.equals("feb") || s.equals("february"))
        	return 1;
		if(s.equals("mar") || s.equals("march"))
        	return 2;
		if(s.equals("apr") || s.equals("april"))
        	return 3;
		if(s.equals("may"))
        	return 4;
		if(s.equals("jun") || s.equals("june"))
        	return 5;
		if(s.equals("jul") || s.equals("july"))
        	return 6;
		if(s.equals("aug") || s.equals("august"))
        	return 7;
		if(s.equals("sep") || s.equals("september"))
        	return 8;
		if(s.equals("oct") || s.equals("october"))
        	return 9;
		if(s.equals("nov") || s.equals("november"))
        	return 10;
		if(s.equals("dec") || s.equals("december"))
        	return 11;
		
		//will hopefully never happen
		return -1; 
	}
	
	
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	/**
	 * @return Returns the lastParseDate.
	 */
	public long getLastParseDate() 
	{
		return lastParseDate;
	}

	/**
	 * @param lastParseDate The lastParseDate to set.
	 */
	public void setLastParseDate(long lastParseDate)
	{
		this.lastParseDate = lastParseDate;
	}

	/**
	 * @return Returns the thisParseDate.
	 */
	public long getThisParseDate() 
	{
		return thisParseDate;
	}

	/**
	 * @param thisParseDate The thisParseDate to set.
	 */
	public void setThisParseDate(long thisParseDate) 
	{
		this.thisParseDate = thisParseDate;
	}
}
