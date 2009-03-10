package ted;

import java.awt.Color;
import java.util.Locale;

import javax.swing.JFileChooser;


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
public class TedConfig

{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private final long serialVersionUID = 2199026019828977965L;
	
	public static final int NEVER = 0;
	public static final int ASK = 1;
	public static final int ALWAYS = 2;
	public static final int DOWNLOADMINIMUMSEEDERS = 0;
	public static final int DOWNLOADMOSTSEEDERS = 1;
	public static final int SORT_OFF = 0;
	public static final int SORT_NAME = 1;
	public static final int SORT_STATUS = 2;
	public static final int SORT_ASCENDING = 0;
	public static final int SORT_DESCENDING = 1;
	
	// create some default settings
	private int RefreshTime = 3600;
	private String Directory = "";
	private boolean ShowErrors = false;
	private boolean ShowHurray = true;
	private boolean OpenTorrent = true;
	private boolean StartMinimized = false;
	private boolean CheckVersion = true;
	private boolean downloadNewSeason = true;
	private boolean parseAtStart = true;
	private int autoUpdateFeedList = ALWAYS;
	private int autoAdjustFeeds = ALWAYS;
	private int width = 400;
	private int height = 550;
	private int x = 0;
	private int y = 0;
	private int RSSVersion = 0;
	private int TimeOutInSecs = 10;
	private int SeederSetting = 0;
	private Locale tedLocale = Locale.getDefault();
	private boolean addSysTray = TedSystemInfo.osSupportsTray();
	private boolean getCompressed = true;
	private String filterExtensions = "zip, rar, r01";
	private int timesParsedSinceLastCheck = 0; 
	private boolean allowLogging = true;
	private boolean logToFile = true;
	private int timeZoneOffset = -1;
	private boolean useAutoSchedule = true;
	private int sortType = SORT_STATUS;
	private int sortDirection = SORT_ASCENDING;
	private String hdKeywords = "720p & HD";
	private boolean hdDownloadPreference = false;
	private boolean useProxy = false;
	private boolean useProxyAuth = false;
	private String proxyUsername = "";
	private String proxyPassword = "";
	private String proxyHost = "";
	private String proxyPort = "";
	
	private final Color defaultEvenRowColor = Color.WHITE;
	private final Color defaultOddRowColor  = new Color(236,243,254);
	
	private Color evenRowColor     	= Color.WHITE;
	private Color oddRowColor      	= new Color(236,243,254);
	private Color selectedRowColor 	= new Color(61, 128, 223);  
	private Color gridColor 			= new Color(205,205,205);

	private static TedConfig configSingleton = null;

	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a TedConfig with some default values
	 */
	private TedConfig()
	{
	}
	
	// Handle multi threading problems. Only allow one singleton to be made.
    private synchronized static void createInstance() 
    {
        if (configSingleton == null) 
        {
        	configSingleton = new TedConfig();
        }
    }
 
    public static TedConfig getInstance() 
    {
        if (configSingleton == null) 
        {
        	createInstance();
        }
        
        return configSingleton;
    }
    
    // Prevent cloning.
    public Object clone() throws CloneNotSupportedException 
    {
    	throw new CloneNotSupportedException();
    }

		
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/

	/**
	 * @return Returns the directory where ted has to save the torrents
	 */
	public String getDirectory() 
	{
		if (Directory.equals(""))
		{
			// init object
			Directory = new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath();
			String seperator = System.getProperty("file.separator");
			Directory += seperator + "ted";
		}
		return Directory;
	}
	/**
	 * Sets the directory where ted has to save the torrents he downloads
	 * @param directory
	 */
	public void setDirectory(String directory) 
	{
		Directory = directory;
	}
	/**
	 * @return Returns the time (in seconds) between the parser intervals
	 */
	public int getRefreshTime() 
	{
		// convert to minutes
		return RefreshTime;
	}
	/**
	 * Set the time (in seconds) between two parser rounds
	 * @param refreshTime
	 */
	public void setRefreshTime(int refreshTime) 
	{
		// in minutes
		RefreshTime = refreshTime;
	}

	/**
	 * @return Returns if the user wants to see the errors
	 */
	public boolean isShowErrors() 
	{
		return ShowErrors;
	}

	/**
	 * Set if the user wants to see messages when errors occur
	 * @param showErrors
	 */
	public void setShowErrors(boolean showErrors) 
	{
		ShowErrors = showErrors;
	}

	/**
	 * @return Returns if the user wants to see hurray messages
	 */
	public boolean isShowHurray() 
	{
		return ShowHurray;
	}

	/**
	 * Set if the user wants to see hurray messages
	 * @param showHurray
	 */
	public void setShowHurray(boolean showHurray) 
	{
		ShowHurray = showHurray;
	}

	/**
	 * @return Returns if the user wants ted to open downloaded torrents in a default torrent client
	 */
	public boolean isOpenTorrent() 
	{
		return OpenTorrent;
	}

	/**
	 * Set if the user wants ted to open a downloaded torrent
	 * @param openTorrent
	 */
	public void setOpenTorrent(boolean openTorrent) 
	{
		OpenTorrent = openTorrent;
	}

	/**
	 * @return Returns if the user wants ted to check his version at startup
	 */
	public boolean isCheckVersion() 
	{
		return CheckVersion;
	}

	/**
	 * Set if the user wants ted to check his version at startup
	 * @param checkVersion
	 */
	public void setCheckVersion(boolean checkVersion) 
	{
		CheckVersion = checkVersion;
	}

	/**
	 * @return Returns the user wants ted to start minimized
	 */
	public boolean isStartMinimized() 
	{
		return StartMinimized;
	}

	/**
	 * Set if the user wants ted to start minimized
	 * @param startMinimized
	 */
	public void setStartMinimized(boolean startMinimized) 
	{
		StartMinimized = startMinimized;
	}

	/**
	 * @return Returns the stored height of the mainwindow.
	 */
	public int getHeight() 
	{
		return height;
	}

	/**
	 * @param h The height of the mainwindow to set.
	 */
	public void setHeight(int h) 
	{
		height = h;
	}

	/**
	 * @return Returns the width of the mainwindow.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param w The width of the mainwindow to set.
	 */
	public void setWidth(int w) 
	{
		width = w;
	}

	/**
	 * @return Returns the x of the mainwindow.
	 */
	public int getX() 
	{
		return x;
	}

	/**
	 * @param x_pos The x of the mainwindow to set.
	 */
	public void setX(int x_pos) 
	{
		x = x_pos;
	}

	/**
	 * @return Returns the y of the mainwindow.
	 */
	public int getY() 
	{
		return y;
	}

	/**
	 * @param y_pos The y of the mainwindow to set.
	 */
	public void setY(int y_pos) 
	{
		y = y_pos;
	}

	/**
	 * @return Returns if the user wants to download a new season when ted encounters one
	 */
	public boolean isDownloadNewSeason() 
	{
		return downloadNewSeason;
	}

	/**
	 * Set if the user wants to download a new season when ted encounters one
	 * @param download
	 */
	public void setDownloadNewSeason(boolean download) 
	{
		downloadNewSeason = download;
	}
	
	/**
	 * @return Returns the number of the latest downloaded RSS feeds.
	 */
	public int getRSSVersion() 
	{
		return RSSVersion;
	}

	/**
	 * @param version The RSSVersion of the latest downloaded RSS feeds.
	 */
	public void setRSSVersion(int version) 
	{
		RSSVersion = version;
	}

	/**
	 * @return If the feeds should be auto-adjusted
	 */
	public boolean isAutoAdjustFeeds() 
	{
		return (autoAdjustFeeds == ALWAYS);
	}
	/**
	 * @return If the user wants to be asked before autoadjustement of the feeds
	 */
	public boolean askAutoAdjustFeeds() 
	{
		return (autoAdjustFeeds == ASK);
	}

	/**
	 * Set the auto-adjustment of feeds
	 * @param adjust
	 */
	public void setAutoAdjustFeeds(int adjust) 
	{
		autoAdjustFeeds = adjust;
	}

	/**
	 * @return If the feed list should be auto-updated
	 */
	public boolean isAutoUpdateFeedList() 
	{
		return (autoUpdateFeedList == ALWAYS);
	}
	
	/**
	 * @return If the user wants to be asked before the feedslist is updated
	 */
	public boolean askAutoUpdateFeedList() 
	{
		return (autoUpdateFeedList == ASK);
	}

	/**
	 * Set the auto-update of the feedlist
	 * @param update
	 */
	public void setAutoUpdateFeedList(int update) 
	{
		autoUpdateFeedList = update;
	}
	
	/**
	 * @return Auto-update of the feedlist
	 */
	public int getAutoUpdateFeedList()
	{
		return autoUpdateFeedList;
	}
	
	/**
	 * @return If the feeds should be auto-adjusted
	 */
	public int getAutoAdjustFeeds()
	{
		return autoAdjustFeeds;
	}

	/**
	 * @return Returns the timeOutInSecs.
	 */
	public int getTimeOutInSecs()
	{
		return TimeOutInSecs;
	}

	/**
	 * @param timeOutInSecs The timeOutInSecs to set.
	 */
	public void setTimeOutInSecs(int timeOutInSecs)
	{
		TimeOutInSecs = timeOutInSecs;
	}
	
	/**
	 * @return Returns the seederSetting.
	 */
	public int getSeederSetting()
	{
		return SeederSetting;
	}

	/**
	 * @param seederSetting The seederSetting to set.
	 */
	public void setSeederSetting(int seederSetting)
	{
		SeederSetting = seederSetting;
	}

	/**
	 * @return Returns the current locale.
	 */
	public Locale getLocale()
	{
		return tedLocale;
	}
	
	/**
	 * @return Returns current language code (eg en for english)
	 */
	public String getLanguage()
	{
		return tedLocale.getLanguage();
	}
	
	/**
	 * @return current country code (eg US for United States)
	 */
	public String getCountry()
	{
		return tedLocale.getCountry();
	}

	/**
	 * @param language The language to set.
	 */
	public void setLocale(Locale language)
	{
		tedLocale = language;
	}
	
	/**
	 * @param country The country
	 * @param language The language
	 */
	public void setLocale(String country, String language)
	{
		tedLocale = new Locale(language, country);
	}
	
	public void setParseAtStart(boolean b)
	{
		parseAtStart = b;
	}
	
	/**
	 * @return Should ted parse at startup?
	 */
	public boolean isParseAtStart()
	{
		return parseAtStart;
	}

	/**
	 * @return Should ted add a systray?
	 */
	public boolean isAddSysTray()
	{
		return addSysTray;
	}
	
	/**
	 * Set the add systray setting
	 * @param b
	 */
	public void setAddSysTray(boolean b)
	{
		addSysTray = b;
	}

	/**
	 * @return The download torrents with compressed files setting
	 */
	public boolean getDoNotDownloadCompressed() 
	{
		return getCompressed;
	}
	
	/**
	 * Set the download torrents with compressed files settings
	 * @param b
	 */
	public void setDoNotDownloadCompressed(boolean b)
	{
		getCompressed = b;
	}

	/**
	 * Set the extensions used in the filtering of torrents with compressed files
	 * @param text
	 */
	public void setFilterExtensions(String text) 
	{
		filterExtensions = text;
	}
	
	/**
	 * Get the extensions set by the user to filter torrents with compressed files
	 * @return
	 */
	public String getFilterExtensions()
	{
		return filterExtensions;
	}
	

	/**
	 * Get the number of times that ted has searched for new episodes after the last update check
	 * @return
	 */
	public int getTimesParsedSinceLastCheck() 
	{
		return timesParsedSinceLastCheck;
	}

	/**
	 * Set the number of times that ted searched for new shows after the last update check
	 * @param timesParsed
	 */
	public void setTimesParsedSinceLastCheck(int timesParsed) 
	{
		timesParsedSinceLastCheck = timesParsed;
	}

	/**
	 * Set the log setting
	 * @param allowLog
	 */
	public void setAllowLogging(boolean allowLog) 
	{
		allowLogging = allowLog;
		TedLog.setAllowLogging(allowLog);
	}

	/**
	 * Set the log to file setting
	 * @param logToFile2
	 */
	public void setLogToFile(boolean logToFile2) 
	{
		logToFile = logToFile2;
		TedLog.setWriteToFile(logToFile2);
	}

	/**
	 * @return If ted should keep a log
	 */
	public boolean isAllowLogging() 
	{
		return allowLogging;
	}
	
	/**
	 * @return If ted should write the log to a file
	 */
	public boolean isLogToFile() 
	{
		return logToFile;
	}

	public Color getEvenRowColor()
	{
		return evenRowColor;
	}

	public void setEvenRowColor(Color evenRowColor) 
	{
		this.evenRowColor = evenRowColor;
	}

	public Color getOddRowColor() 
	{
		return oddRowColor;
	}

	public void setOddRowColor(Color oddRowColor) 
	{
		this.oddRowColor = oddRowColor;
	}

	public Color getSelectedRowColor()
	{
		return selectedRowColor;
	}

	public void setSelectedRowColor(Color selectedRowColor)
	{
		this.selectedRowColor = selectedRowColor;
	}
	
	public Color getGridColor()
	{
		return gridColor;
	}
	
	public void restoreDefaultColors()
	{
		setEvenRowColor(defaultEvenRowColor);
		setOddRowColor (defaultOddRowColor );
	}

	public void setTimeZoneOffset(int timezoneOffset) 
	{
		this.timeZoneOffset = timezoneOffset;
	}
	
	public int getTimeZoneOffset()
	{
		return this.timeZoneOffset;
	}

	public boolean isUseAutoSchedule() 
	{
		return useAutoSchedule;
	}

	public void setUseAutoSchedule(boolean useAutoSchedule) 
	{
		this.useAutoSchedule = useAutoSchedule;
	}
	
	/**
	 * @return The type of field the maintable should be sorted on.
	 * 0 = no sort, 1 = on name, 2 = on status and airdate
	 */
	public int getSortType()
	{
		return sortType;
	}
	
	/**
	 * @param sortType Type of sort that should be applied to the maintable
	 */
	public void setSortType(int sortType)
	{
		this.sortType = sortType;
	}
	
	/**
	 * @return The direction of sorting for the maintable
	 * 0 = ascensing, 1 = descending
	 */
	public int getSortDirection()
	{
		return sortDirection;
	}
	
	/**
	 * @param direction Direction of sort for the maintable
	 */
	public void setSortDirection (int direction)
	{
		this.sortDirection = direction;
	}
	
	public String getHDKeywords()
	{
		return hdKeywords;		
	}
	
	public void setHDKeywords(String keywords)
	{
		if (!keywords.equals(""))
		{
			this.hdKeywords = keywords;
		}
	}

	public void setHDDownloadPreference(boolean hdDownloadPreference) 
	{
		this.hdDownloadPreference = hdDownloadPreference;
	}

	public boolean isHDDownloadPreference() 
	{
		return hdDownloadPreference;
	}
	
	public void setUseProxy(boolean useProxy)
	{
		this.useProxy = useProxy;
	}
	
	public boolean getUseProxy()
	{
		return this.useProxy;
	}
	
	public void setUseProxyAuth(boolean useProxyAuth)
	{
		this.useProxyAuth = useProxyAuth;
	}

	public String getProxyUsername()
	{
		return proxyUsername;
	}

	public void setProxyUsername(String proxyUsername)
	{
		this.proxyUsername = proxyUsername;
	}

	public String getProxyPassword()
	{
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword)
	{
		this.proxyPassword = proxyPassword;
	}

	public boolean getUseProxyAuth()
	{
		return useProxyAuth;
	}

	public String getProxyHost()
	{
		return proxyHost;
	}

	public void setProxyHost(String proxyHost)
	{
		this.proxyHost = proxyHost;
	}

	public String getProxyPort()
	{
		return proxyPort;
	}

	public void setProxyPort(String proxyPort)
	{
		this.proxyPort = proxyPort;
	}
	
	
}
