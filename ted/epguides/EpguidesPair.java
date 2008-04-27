package ted.epguides;

import java.io.Serializable;

import ted.datastructures.SeasonEpisode;

public class EpguidesPair implements Comparable<EpguidesPair>, Serializable
{
	private static final long serialVersionUID = 1L;
	private SeasonEpisode seasonEpisode;
	private boolean isDoubleEpisode;
		
	public EpguidesPair()
	{
		this.seasonEpisode  = null;
		this.isDoubleEpisode = false;
	}
	
	public EpguidesPair(SeasonEpisode first, boolean second)
	{
		this.seasonEpisode  = first;
		this.isDoubleEpisode = second;
	}
	
	public SeasonEpisode getSeasonEpisode() 
	{
		return seasonEpisode;
	}
	public void setSeasonEpisode(SeasonEpisode first) 
	{
		this.seasonEpisode = first;
	}
	public boolean getIsDoubleEpisode() 
	{
		return isDoubleEpisode;
	}
	public void setIsDoubleEpisode(boolean second) 
	{
		this.isDoubleEpisode = second;
	}

	public int compareTo(EpguidesPair arg0) 
	{
		return this.getSeasonEpisode().compareTo(arg0.getSeasonEpisode());
	}
}
