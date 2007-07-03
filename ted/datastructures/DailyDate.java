package ted.datastructures;

import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DailyDate  extends StandardStructure implements Comparable
{
	private int Year=0;
	private int Month=0;
	private int Day=0;
	
	private int seeders;
	private URL url;
	
	public DailyDate()
	{
		
	}
	
	public void setYear(int i)
	{
		this.Year = i;
	}
	
	public void setMonth(int i)
	{
		this.Month = i;
	}
	
	public void setDay(int i)
	{
		this.Day = i;
	}
	
	public int getYear()
	{
		return this.Year;
	}
	
	public int getMonth()
	{
		return this.Month;
	}
	
	public int getDay()
	{
		return this.Day;
	}
	
	
	public GregorianCalendar getDate()
	{
		GregorianCalendar d = new GregorianCalendar(Year, Month, Day);
		return d;
	}
	
	public int compareTo(Object arg0)
	{
		DailyDate second = (DailyDate) arg0;
		
		if(this.getDate().getTimeInMillis()<second.getDate().getTimeInMillis())
		{
			return 1;
		}
		else if(this.getDate().getTimeInMillis()>second.getDate().getTimeInMillis())
		{
			return -1;
		}
		
		return 0;
	}
	
	public String toString()
	{
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		return df.format(this.getDate().getTime());
	}

	public int getSeeders() 
	{
		return seeders;
	}

	public void setSeeders(int seeders) 
	{
		this.seeders = seeders;
	}

	public URL getUrl() 
	{
		return url;
	}

	public void setUrl(URL url) 
	{
		this.url = url;
	}

	public void setDate(GregorianCalendar date) 
	{
		this.setDay(date.get(Calendar.DAY_OF_MONTH));
		this.setYear(date.get(Calendar.YEAR));
		this.setMonth(date.get(Calendar.MONTH));
		
	}
	
}
