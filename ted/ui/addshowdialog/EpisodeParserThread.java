package ted.ui.addshowdialog;

import java.util.Vector;

import ted.TedParser;
import ted.TedSerie;
import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;

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
	private SubscribeOptionsPanel subscribeOptionsPanel = null;
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new counter
	 * @param subscribeOptionsPanel 
	 * @param m the TedMainDialog
	 * @param tc the current config
	 */
	public EpisodeParserThread(EpisodeChooserPanel ecp, TedSerie ts, SubscribeOptionsPanel subscribeOptionsPanel)
	{
		this.episodeChooserPanel = ecp;
		this.selectedSerie = ts;
		this.subscribeOptionsPanel = subscribeOptionsPanel;
	}
	
	public EpisodeParserThread(EpisodeChooserPanel episodeChooserPanel2,
			TedSerie show)
	{
		this.episodeChooserPanel = episodeChooserPanel2;
		this.selectedSerie = show;
	}

	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	public void run()
	{
		this.episodeChooserPanel.setActivityStatus(true);
		this.episodeChooserPanel.clear();
		if (this.subscribeOptionsPanel != null)
		{
			this.subscribeOptionsPanel.clear();
		}
		if(selectedSerie!=null)
		{		
			// add vector to chooser panel
			this.episodeChooserPanel.setSeasonEpisodes(selectedSerie.getPubishedAndAiredEpisodes());
			if (this.subscribeOptionsPanel != null)
			{
				this.subscribeOptionsPanel.setSeasonEpisodes(selectedSerie.getPubishedAndAiredEpisodes());
			}
		}
		
		// disable avtivity image
		this.episodeChooserPanel.setActivityStatus(false);
		this.episodeChooserPanel.selectEpisode();
	}
}
