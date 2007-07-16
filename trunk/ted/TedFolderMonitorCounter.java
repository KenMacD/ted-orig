package ted;

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
public class TedFolderMonitorCounter extends Thread 
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private int count;
	private int thread_seconds;
	private TedFolderMonitor monitor;
	
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new counter
	 * @param m the TedMainDialog
	 * @param tc the current config
	 */
	public TedFolderMonitorCounter(TedFolderMonitor m, int seconds)
	{
		monitor = m;
		thread_seconds = seconds;
		count = seconds;
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	public void run()
	{
		while (count > 0)
		{
			if (count % 60 == 0)
			{
				// check if a minute passed
				count --;
			}
			try
			{
				// sleep for a sec
				count --;
				sleep(1000);
			}
			catch (Exception e)
			{
				TedLog.error(e, "folder monitor counter error");
			}
		}
		
		if (count == 0)
		{
			// do something
			monitor.updateStatus();
			count = thread_seconds;
			this.run();
		}
	}
	
	/**
	 * Update the conter to the refreshtime that is currently set in the config
	 */
	public void updateRefreshTime()
	{
		this.count = thread_seconds;
	}
	
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	
	/**
	 * Set the counter
	 * @param i
	 */
	public void setCount(int i)
	{
		count = i;
	}
	
	/**
	 * @return Current count
	 */
	public int getCount()
	{
		return count;
	}
}
