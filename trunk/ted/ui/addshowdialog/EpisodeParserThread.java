package ted.ui.addshowdialog;

import java.util.Vector;

import ted.TedParser;
import ted.TedSerie;
import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the counter that counts down to the next parse round of ted
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
public class EpisodeParserThread extends Thread 
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private EpisodeChooserPanel episodeChooserPanel;
	private TedSerie selectedSerie;
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new counter
	 * @param m the TedMainDialog
	 * @param tc the current config
	 */
	public EpisodeParserThread(EpisodeChooserPanel ecp, TedSerie ts)
	{
		this.episodeChooserPanel = ecp;
		this.selectedSerie = ts;
		this.episodeChooserPanel.setActivityStatus(true);
		this.episodeChooserPanel.clear();
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	public void run()
	{
		if(selectedSerie!=null)
		{	
			// parse all feeds in the show
			TedParser showParser = new TedParser();
			Vector seasonEpisodes = showParser.getItems(selectedSerie);
			
			// add the next upcoming episode	
			if (seasonEpisodes.size() > 0)
			{
				if (selectedSerie.isDaily())
				{
					DailyDate sRow = (DailyDate)seasonEpisodes.get(0);
					DailyDate upcoming = new DailyDate();
					upcoming.setDate(sRow.getDate());
					upcoming.setDay(upcoming.getDay()+1);
					upcoming.setQuality(0);
					upcoming.setPublishDate(null);
					
					seasonEpisodes.add(0, upcoming);
					
				}
				else
				{
					SeasonEpisode sRow = (SeasonEpisode) seasonEpisodes.get(0);
					SeasonEpisode upcoming = new SeasonEpisode();
					upcoming.setEpisode(sRow.getEpisode()+1);
					upcoming.setSeason(sRow.getSeason());
					upcoming.setQuality(0);
					upcoming.setPublishDate(null);
					
					seasonEpisodes.add(0, upcoming);
				}
			}
				
			// add vector to chooser panel
			this.episodeChooserPanel.setSeasonEpisodes(seasonEpisodes);
		}
		
		// disable avtivity image
		this.episodeChooserPanel.setActivityStatus(false);
	}
}
