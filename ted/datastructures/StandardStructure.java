package ted.datastructures;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StandardStructure 
{
	int quality = 0;
	private Date publishDate;
	
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
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		return df.format(this.publishDate);
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
	public void setPublishDate(Date publishDate)
	{
		this.publishDate = publishDate;
	}
}
