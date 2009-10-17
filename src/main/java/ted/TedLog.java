package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the way to add log messages to the TedLog log viewer
 * 
 * @author mlathe
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
public class TedLog
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = -8661705723352441097L;
    private static final int DEBUG_MESSAGE = 0;
    private static final int ERROR_MESSAGE = 1;
    private static final int SIMPLE_MESSAGE = 2;
    private static boolean writeToFile;
    private static boolean allowLogging = true;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Constructs a TedLogger
	 */
	private TedLog()
	{
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	/**
	 * Add an entry to the TedLog window
	 * @param s String to be added
     * @param level What log level this entry should be added at
	 */
	private static void addEntry(int level, String s)
	{
		if(allowLogging)
		{
			Calendar c = new GregorianCalendar();
			Date d = new Date(c.getTimeInMillis());
			DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
			DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
			String formattedDate = df.format(d);
			String formattedTime = tf.format(d);
	        String entry = 
	        	formattedDate + " @ " + 
	        	formattedTime + ": " + 
	        	s + 
	        	System.getProperty("line.separator");
	        
	        //just in case something goes wrong, at least we get a msg
	        try 
	        {
	            TedLogDialog t = TedLogDialog.getInstance();
	            t.addEntry(level, entry);
	        } 
	        catch (Exception e)
	        {
	            System.out.println(entry);
	        }
		} 
	}
    
    /**
     * Add a debug message
     * @param s String to be added
     */
    public static void debug(String s)
    {
        addEntry(DEBUG_MESSAGE, s);
    }
    
    /**
     * Add a error message
     * @param s String to be added
     */
    public static void error(String s)
    {
        addEntry(ERROR_MESSAGE, s);
    }
    
    /**
     * Add a error message
     * @param s String to be added
     */
    public static void error(Exception e, String s)
    {
        addEntry(ERROR_MESSAGE, s + " Exception message=[" + e.getMessage() + "]");
    }

    public static void simpleLog(String s)
    {
    	addEntry(SIMPLE_MESSAGE, s);
    }
    
	public static void setWriteToFile(boolean b) 
	{
		writeToFile = b;	
	}
	
	public static void setAllowLogging(boolean b)
	{
		allowLogging = b;
	}
	
	public static boolean isWriteToFile()
	{
		return writeToFile;	
	} 
}
