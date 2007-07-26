package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
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
public class TedConfigListener implements ActionListener 
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private TedConfigDialog TedCD;

	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new ConfigListener
	 * @param dialog The dialog to listen to
	 */
	public TedConfigListener(TedConfigDialog dialog) 
	{
		TedCD = dialog;
	}

	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		if(action.equals("Save"))
		{
			TedCD.saveConfig();
		}
		else if(action.equals("Cancel"))
		{
			TedCD.setVisible(false);
		}
		else if(action.equals("Help"))
		{
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/wiki/index.php/Config_General"); //$NON-NLS-1$
			} 
			catch (Exception err)
			{
				
			}
		}
		
	}

}
