package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * The episode listener reacts on actions of users in the episode dialog
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

public class TedEpisodeListener implements ActionListener
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private TedEpisodeDialog TedED;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a new episode dialog listener
	 * @param dialog TedEpisodeDialog we listen to
	 */
	public TedEpisodeListener(TedEpisodeDialog dialog) 
	{
		this.TedED = dialog;

	}

	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	public void actionPerformed(ActionEvent arg0) 
	{
		// handle events on the episode dialog
		String action = arg0.getActionCommand();
		TedED.stopEditing();
		
		if (action.equals("chooseserie"))
		{
			TedED.choose_Serie(arg0);
		}
		else if (action.equals("save"))
		{
			TedED.saveShow();
		}
		else if (action.equals("cancel"))
		{
			TedED.closeDialog();
		}
		else if (action.equals("getlatest"))
		{
			TedED.getLatestSandE();
		}
		else if (action.equals("openfeed"))
		{
			TedED.openRSSFeed();
		}
		else if (action.equals("resetdate"))
		{
			TedED.resetSelectedDate();
		}
		else if (action.equals("downloadall"))
		{
			TedED.downloadAllUpdate(false);
		}
		else if (action.equals("schedule"))
		{
			TedED.scheduleUpdate();
		}
		else if (action.equals("breakschedule"))
		{
			TedED.breakUpdate();
		}
		else if (action.equals("addfeed"))
		{
			TedED.addFeed();
		}
		else if (action.equals("deletefeed"))
		{
			TedED.deleteSelectedFeed();
		}
		else if (action.equals("movefeedup"))
		{
			TedED.moveSelectedFeedUp();
		}
		else if (action.equals("movefeeddown"))
		{
			TedED.moveSelectedFeedDown();
		}		
	}
}
