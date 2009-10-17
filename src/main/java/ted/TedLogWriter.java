/**
 * 
 */
package ted;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the logwriter for ted
 * This class writes lines of the log to a logfile (log.txt)
 * 
 * @author Roel
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
public class TedLogWriter
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	String fileName = TedSystemInfo.getUserDirectory() + "log.txt";
	File logFile = new File(fileName);
	PrintWriter fileWriter;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Constructs a TedLogWriter
	 */
	public TedLogWriter()
	{
		try
		{
			if (!logFile.exists())
			{
				logFile.createNewFile();
				
			}
			fileWriter =  new PrintWriter(logFile);
			TedLog.setWriteToFile(true);
		} 
		catch (IOException e)
		{
			TedLog.error(e, "Error opening logfile");
			TedLog.setWriteToFile(false);
		}			
	}
	
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/	
	/**
	 * Write one line to the logfile
	 * @param line The line to write to the logfile
	 * @throws IOException
	 */
	public void addLine (String line) throws IOException
	{
		// append line to logfile
		fileWriter.write(line);
		fileWriter.flush();
	}
	
	/**
	 * Clear (delete) the logfile
	 */
	public void resetLogFile ()
	{
		// delete current logfile
		logFile.delete();
		try 
		{
			logFile.createNewFile();
			fileWriter =  new PrintWriter(logFile);
			TedLog.setWriteToFile(true);
		} 
		catch (IOException e) 
		{
			TedLog.error(e, "Error opening logfile");
			TedLog.setWriteToFile(false);
		}
		
	}
	
	public String getLocationLog()
	{
		return this.fileName;
	}
}
