package ted.datastructures;

import java.util.Date;

import ted.Lang;

public class SeasonEpisode extends StandardStructure implements Comparable<SeasonEpisode>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 933251042852089885L;
	private int season = 0;
	private int episode = 0;
	
	public SeasonEpisode(int season2, int episode2, Date airdate2, String title2) 
	{
		this.season = season2;
		this.episode = episode2;
		this.airDate = airdate2;
		this.title = title2;
		this.quality = 0;
	}
	
	public SeasonEpisode() {
		// TODO Auto-generated constructor stub
	}

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
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SeasonEpisode arg0)
	{
		SeasonEpisode second = arg0;
		
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
	
	
	public String toString()
	{
		return Lang.getString("TedTableModel.Season")+": "+ this.season + ", " + Lang.getString("TedTableModel.Episode")+": "+ this.episode;
	}
}
