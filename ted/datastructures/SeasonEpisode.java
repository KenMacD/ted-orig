package ted.datastructures;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SeasonEpisode extends StandardStructure implements Comparable
{
	private int season = 0;
	private int episode = 0;
	/**
	 * @return Returns the episode.
	 */
	public int getEpisode()
	{
		return episode;
	}
	/**
	 * @param episode The episode to set.
	 */
	public void setEpisode(int episode)
	{
		this.episode = episode;
	}
	
	/**
	 * @return Returns the season.
	 */
	public int getSeason()
	{
		return season;
	}
	/**
	 * @param season The season to set.
	 */
	public void setSeason(int season)
	{
		this.season = season;
	}
	public int compareTo(Object arg0)
	{
		SeasonEpisode second = (SeasonEpisode) arg0;
		
		if (this.getSeason() < second.getSeason())
		{
			return 1;
		}
		else if (this.getSeason() > second.getSeason())
		{
			return -1;
		}
		else if (this.getEpisode() < second.getEpisode())
		{
			return 1;
		}
		else if (this.getEpisode() > second.getEpisode())
		{
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * @return Returns the publishDate.
	 */
	public String getFormattedPublishDate()
	{
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return df.format(this.getPublishDate());
	}
	
	public String toString()
	{
		return "Season: "+ this.season + ", Episode: " + this.episode;
	}
}
