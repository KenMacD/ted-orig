package ted;

import java.util.Locale;


/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * TedConfig stores all the configuration variables of ted
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
public class TedConfig //implements Serializable 

{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 2199026019828977965L;
	public static final int NEVER = 0;
	public static final int ASK = 1;
	public static final int ALWAYS = 2;
	public static final int DOWNLOADMINIMUMSEEDERS = 0;
	public static final int DOWNLOADMOSTSEEDERS = 1;
	private static int RefreshTime = 600;
	private static String Directory = System.getProperty("user.dir");
	private static boolean ShowErrors = false;
	private static boolean ShowHurray = true;
	private static boolean OpenTorrent = true;
	private static boolean StartMinimized = false;
	private static boolean CheckVersion = true;
	private static boolean downloadNewSeason = true;
	private static boolean parseAtStart = true;
	private static int autoUpdateFeedList = ASK;
	private static int autoAdjustFeeds = ASK;
	private static int width = 550;
	private static int height = 400;
	private static int x = 0;
	private static int y = 0;
	private static int RSSVersion = 0;
	private static int TimeOutInSecs = 10;
	private static int SeederSetting = 0;
	private static Locale tedLocale = Locale.getDefault();
	private static boolean addSysTray = TedSystemInfo.osSupportsTray();
	private static boolean getCompressed = true;
	private static String filterExtensions = "zip, rar, r01";
	private static int timesParsedSinceLastCheck = 0; 
	private static boolean allowLogging = true;
	private static boolean logToFile = true;


	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a TedConfig with some default values
	 */
	public TedConfig()
	{
		/*// set default config values
		RefreshTime = 600;
		Directory = "";
		ShowErrors = true;
		ShowHurray = true;
		width = 550;
		height = 400;
		downloadNewSeason = true;
		x = 0;
		y = 0;
		RSSVersion = 0;
		TimeOutInSecs = 10;
		autoUpdateFeedList = ASK;
		autoAdjustFeeds = ASK;
		SeederSetting = 0;
		tedLocale = Locale.getDefault();
		addSysTray = TedSystemInfo.osSupportsTray();
		
		if (TedSystemInfo.osIsWindows() || TedSystemInfo.osIsMac())
		{
			OpenTorrent = true;
		}
		else
		{
			OpenTorrent = false;
		}
		StartMinimized = false;
		CheckVersion = true;
		parseAtStart = true;
		getCompressed = true;
		filterExtensions = "zip, rar, r01";*/
	}
		
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/

	/**
	 * @return Returns the directory where ted has to save the torrents
	 */
	public static String getDirectory() 
	{
		return Directory;
	}
	/**
	 * Sets the directory where ted has to save the torrents he downloads
	 * @param directory
	 */
	public static void setDirectory(String directory) 
	{
		Directory = directory;
	}
	/**
	 * @return Returns the time (in seconds) between the parser intervals
	 */
	public static int getRefreshTime() 
	{
		// convert to minutes
		return RefreshTime;
	}
	/**
	 * Set the time (in seconds) between two parser rounds
	 * @param refreshTime
	 */
	public static void setRefreshTime(int refreshTime) 
	{
		// in minutes
		RefreshTime = refreshTime;
	}

	/**
	 * @return Returns if the user wants to see the errors
	 */
	public static boolean isShowErrors() 
	{
		return ShowErrors;
	}

	/**
	 * Set if the user wants to see messages when errors occur
	 * @param showErrors
	 */
	public static void setShowErrors(boolean showErrors) 
	{
		ShowErrors = showErrors;
	}

	/**
	 * @return Returns if the user wants to see hurray messages
	 */
	public static boolean isShowHurray() 
	{
		return ShowHurray;
	}

	/**
	 * Set if the user wants to see hurray messages
	 * @param showHurray
	 */
	public static void setShowHurray(boolean showHurray) 
	{
		ShowHurray = showHurray;
	}

	/**
	 * @return Returns if the user wants ted to open downloaded torrents in a default torrent client
	 */
	public static boolean isOpenTorrent() 
	{
		return OpenTorrent;
	}

	/**
	 * Set if the user wants ted to open a downloaded torrent
	 * @param openTorrent
	 */
	public static void setOpenTorrent(boolean openTorrent) 
	{
		OpenTorrent = openTorrent;
	}

	/**
	 * @return Returns if the user wants ted to check his version at startup
	 */
	public static boolean isCheckVersion() 
	{
		return CheckVersion;
	}

	/**
	 * Set if the user wants ted to check his version at startup
	 * @param checkVersion
	 */
	public static void setCheckVersion(boolean checkVersion) 
	{
		CheckVersion = checkVersion;
	}

	/**
	 * @return Returns the user wants ted to start minimized
	 */
	public static boolean isStartMinimized() 
	{
		return StartMinimized;
	}

	/**
	 * Set if the user wants ted to start minimized
	 * @param startMinimized
	 */
	public static void setStartMinimized(boolean startMinimized) 
	{
		StartMinimized = startMinimized;
	}

	/**
	 * @return Returns the stored height of the mainwindow.
	 */
	public static int getHeight() 
	{
		return height;
	}

	/**
	 * @param h The height of the mainwindow to set.
	 */
	public static void setHeight(int h) 
	{
		height = h;
	}

	/**
	 * @return Returns the width of the mainwindow.
	 */
	public static int getWidth()
	{
		return width;
	}

	/**
	 * @param w The width of the mainwindow to set.
	 */
	public static void setWidth(int w) 
	{
		width = w;
	}

	/**
	 * @return Returns the x of the mainwindow.
	 */
	public static int getX() 
	{
		return x;
	}

	/**
	 * @param x_pos The x of the mainwindow to set.
	 */
	public static void setX(int x_pos) 
	{
		x = x_pos;
	}

	/**
	 * @return Returns the y of the mainwindow.
	 */
	public static int getY() 
	{
		return y;
	}

	/**
	 * @param y_pos The y of the mainwindow to set.
	 */
	public static void setY(int y_pos) 
	{
		y = y_pos;
	}

	/**
	 * @return Returns if the user wants to download a new season when ted encounters one
	 */
	public static boolean isDownloadNewSeason() 
	{
		return downloadNewSeason;
	}

	/**
	 * Set if the user wants to download a new season when ted encounters one
	 * @param download
	 */
	public static void setDownloadNewSeason(boolean download) 
	{
		downloadNewSeason = download;
	}
	
	/**
	 * @return Returns the number of the latest downloaded RSS feeds.
	 */
	public static int getRSSVersion() 
	{
		return RSSVersion;
	}

	/**
	 * @param version The RSSVersion of the latest downloaded RSS feeds.
	 */
	public static void setRSSVersion(int version) 
	{
		RSSVersion = version;
	}

	public static boolean isAutoAdjustFeeds() 
	{
		return (autoAdjustFeeds == ALWAYS);
	}
	public static boolean askAutoAdjustFeeds() 
	{
		return (autoAdjustFeeds == ASK);
	}

	public static void setAutoAdjustFeeds(int adjust) 
	{
		autoAdjustFeeds = adjust;
	}

	public static boolean isAutoUpdateFeedList() 
	{
		return (autoUpdateFeedList == ALWAYS);
	}
	public static boolean askAutoUpdateFeedList() 
	{
		return (autoUpdateFeedList == ASK);
	}

	public static void setAutoUpdateFeedList(int update) 
	{
		autoUpdateFeedList = update;
	}
	
	public static int getAutoUpdateFeedList()
	{
		return autoUpdateFeedList;
	}
	
	public static int getAutoAdjustFeeds()
	{
		return autoAdjustFeeds;
	}

	/**
	 * @return Returns the timeOutInSecs.
	 */
	public static int getTimeOutInSecs()
	{
		return TimeOutInSecs;
	}

	/**
	 * @param timeOutInSecs The timeOutInSecs to set.
	 */
	public static void setTimeOutInSecs(int timeOutInSecs)
	{
		TimeOutInSecs = timeOutInSecs;
	}
	
	/**
	 * @return Returns the seederSetting.
	 */
	public static int getSeederSetting()
	{
		return SeederSetting;
	}

	/**
	 * @param seederSetting The seederSetting to set.
	 */
	public static void setSeederSetting(int seederSetting)
	{
		SeederSetting = seederSetting;
	}

	/**
	 * @return Returns the current locale.
	 */
	public static Locale getLocale()
	{
		return tedLocale;
	}
	
	/**
	 * @return Returns current language code (eg en for english)
	 */
	public static String getLanguage()
	{
		return tedLocale.getLanguage();
	}
	
	/**
	 * @return current country code (eg US for United States)
	 */
	public static String getCountry()
	{
		return tedLocale.getCountry();
	}

	/**
	 * @param language The language to set.
	 */
	public static void setLocale(Locale language)
	{
		tedLocale = language;
	}
	
	/**
	 * @param country The country
	 * @param language The language
	 */
	public static void setLocale(String country, String language)
	{
		tedLocale = new Locale(language, country);
	}
	
	public static void setParseAtStart(boolean b)
	{
		parseAtStart = b;
	}
	
	/**
	 * @return Should ted parse at startup?
	 */
	public static boolean isParseAtStart()
	{
		return parseAtStart;
	}

	/**
	 * @return Should ted add a systray?
	 */
	public static boolean isAddSysTray()
	{
		return addSysTray;
	}
	
	public static void setAddSysTray(boolean b)
	{
		addSysTray = b;
	}

	public static boolean getDoNotDownloadCompressed() 
	{
		return getCompressed;
	}
	
	public static void setDoNotDownloadCompressed(boolean b)
	{
		getCompressed = b;
	}

	public static void setFilterExtensions(String text) 
	{
		filterExtensions = text;
	}
	
	public static String getFilterExtensions()
	{
		return filterExtensions;
	}
	

	public static int getTimesParsedSinceLastCheck() 
	{
		return timesParsedSinceLastCheck;
	}

	public static void setTimesParsedSinceLastCheck(int timesParsed) 
	{
		timesParsedSinceLastCheck = timesParsed;
	}

	public static void setAllowLogging(boolean allowLog) 
	{
		allowLogging = allowLog;
		TedLog.setAllowLogging(allowLog);
	}

	public static void setLogToFile(boolean logToFile2) 
	{
		logToFile = logToFile2;
		TedLog.setWriteToFile(logToFile2);
	}

	public static boolean isAllowLogging() 
	{
		return allowLogging;
	}
	
	public static boolean isLogToFile() 
	{
		return logToFile;
	}
}
