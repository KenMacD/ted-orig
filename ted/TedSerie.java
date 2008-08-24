 package ted;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.w3c.dom.Element;

import ted.SeasonEpisodeScheduler.NoEpisodeFoundException;
import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;
import ted.datastructures.StandardStructure.AirDateUnknownException;
import ted.ui.editshowdialog.FeedPopupItem;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the Serie class of Ted. This class stores a show and keeps
 * track of the current episode and season that ted found.
 * It also contains other data that ted uses to parse and check episodes.
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
public class TedSerie implements Serializable
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	static final long serialVersionUID= 7210007788942770687L;
	protected int currentEpisode;
	protected int currentSeason;
	protected String currentEpisodeSearchString;
	protected boolean currentEpisodeIsDouble;
	protected Date currentEpisodeAirDate;
	private int minSize;
	private int maxSize;
	private String name;
	private String url;
	private String keywords;
	private long checkDate;
	private boolean [] days = this.initDays();
	private int lastWeekChecked;
	private int lastDayChecked;
	private int lastYearChecked;
	private boolean downloadAll;
	public final static int STATUS_HIATUS = 3;
	public final static int STATUS_HOLD = 2;
	public final static int STATUS_PAUSE = 1;
	public final static int STATUS_CHECK = 0;
	public final static int STATUS_DISABLED = 4;
	public final static int IS_PARSING = 1;
	public final static int IS_IDLE = 0;
	private int activity = 0;
	private int weeklyInterval;
	protected int status = 0;
	private boolean useEpisodeSchedule;
	private boolean useBreakSchedule;
	private boolean useBreakScheduleEpisode;
	private boolean useBreakScheduleFrom;
	private boolean useAutoSchedule;
	private long breakUntil = 0;
	private long breakFrom = 0;
	private int breakEpisode;
	private Vector<TedSerieFeed> feeds = new Vector<TedSerieFeed>();
	private int minNumOfSeeders;
	private String statusString;
	private int progress = 0;
	private boolean usePresets;
	private String tvCom;
	protected boolean isDaily = false;
	private String searchName = "";
	protected SeasonEpisodeScheduler scheduler;
	private String epguidesName;
	private int timeZone = -1;

	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Constructs a new TedSerie with some predefined settings
	 * @param ce Current Episode
	 * @param cs Current Season
	 * @param name Name of the Show
	 * @param url URL of the RSS Feed
	 * @param min Minimum size of torrent contents
	 * @param max Maximum size of torrent contents
	 * @param key Keywords to check torrent titles
	 */
	public TedSerie(int ce, int cs, String name, String url, int min, int max, String key, int minNumSeeders, String sName)
	{
		this.currentEpisode = ce;
		this.currentSeason = cs;
		this.name = name;
		this.url = url;
		this.checkDate = 0;
		this.minSize = min;
		this.maxSize = max;
		this.keywords = key;
		this.setStatus(TedSerie.STATUS_CHECK);
		this.lastWeekChecked = -1;
		this.lastDayChecked = 0;
		this.lastYearChecked = 0;
		this.weeklyInterval = 1;
		this.downloadAll = false;
		this.useEpisodeSchedule = false;
		this.useBreakSchedule = false;
		this.useBreakScheduleEpisode = false;
		this.useBreakScheduleFrom = false;
		this.useAutoSchedule = true;
		this.breakEpisode = 1;
		this.breakFrom  = 0;
		this.breakUntil = 0;
		this.minNumOfSeeders = minNumSeeders;
		this.statusString = Lang.getString("TedSerie.Idle"); //$NON-NLS-1$
		this.usePresets = true;
		this.searchName = sName;
		currentEpisodeIsDouble = false;
	}
	
	/**
	 * Creates empty TedSerie
	 */
	public TedSerie()
	{
		this.currentEpisode = 0;
		this.currentSeason = 0;
		this.name = "";
		this.url = "";
		this.checkDate = 0;
		this.minSize = 0;
		this.maxSize = 0;
		this.keywords = "";
		this.status = TedSerie.STATUS_CHECK;
		this.lastWeekChecked = -1;
		this.lastDayChecked = 0;
		this.lastYearChecked = 0;
		this.weeklyInterval = 1;
		this.downloadAll = false;
		this.useEpisodeSchedule = false;
		this.useBreakSchedule = false;
		this.useBreakScheduleEpisode = false;
		this.useBreakScheduleFrom = false;
		this.useAutoSchedule = true;
		this.breakEpisode = 1;
		this.breakFrom  = 0;
		this.breakUntil = 0;
		this.minNumOfSeeders = 0;
		this.statusString = Lang.getString("TedSerie.Idle"); //$NON-NLS-1$
		this.usePresets = true;
		this.searchName = "";
		currentEpisodeIsDouble = false;
	}
	
	
	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	/**
	 * @return Initialized array for the checkdays
	 * From sunday (=0) to saturday (= 6)
	 */
	private boolean[] initDays() 
	{
		boolean days [] = new boolean [7];
		for (int i = 0; i < days.length; i++)
		{
			days[i] = false;
		}
		
		return days;
	}
	
	/**
	 * @param a
	 * @param b
	 * @return If arrays are equal in size and in contents
	 */
	private boolean arraysEqual(boolean[] a, boolean[] b) 
	{
		// arrays are equal sized
		for (int i = 0; i < a.length; i++)
		{
			if (a[i] != b[i])
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param firstday
	 * @param lastday
	 * @return If there is a true in the days array between the first and the lastday
	 */
	boolean checkDay(int firstday, int lastday) 
	{
		// return true if any entry in array days on index larger than firstday and smaller or equal to i is true
		for (int j = firstday; j<=lastday; j++)
		{
			if (days[j] == true)
			{
				return true;
			}
		}
		return false;
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	

	/**
	 * Toggle the status of the show from hold, pause to check
	 */
	public void toggleStatus() 
	{
		// toggle the status, from hold, pause to check
		if (this.status != TedSerie.STATUS_HOLD)
		{
			this.setStatus(this.status + 1);
		}
		else
		{
			this.setStatus(TedSerie.STATUS_CHECK);
		}
		
		if (this.status == TedSerie.STATUS_PAUSE)
		{
			this.setLastDatesToToday();
		}
	}

	/**
	 * @param currentDay Day of today
	 * @return the next day that is checked in the days list
	 */
	public int getNextCheckDay(int currentDay)
	{
		int result = 0;
		boolean [] days = this.getDays();
		
		for (int i = currentDay; i < days.length; i++)
		{
			if (days[i])
			{
				return i;
			}
		}
		for (int i = 0; i < currentDay; i++)
		{
			if (days[i])
			{
				return i;
			}
		}	
		return result;
	}
	
	
	/**
	 * @param ep Current Episode
	 * @return If the show has to be set on break
	 */
	public boolean checkBreakEpisode(int ep)
	{
		if (this.useBreakSchedule && this.isUseBreakScheduleEpisode() && 
				ep == this.breakEpisode)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public boolean checkBreakDate()
	{
		Calendar date = new GregorianCalendar();
		if(this.useBreakSchedule && (this.getBreakFrom() < date.getTimeInMillis()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void AutoFillInPresets(TedSerie XMLserie)
	{
		if (XMLserie != null)
		{
			this.setName(XMLserie.getName());
			
			Vector<TedSerieFeed> feeds = new Vector<TedSerieFeed>();
			feeds = XMLserie.getFeeds();
			feeds.addAll(this.getSelfmadeFeeds());
			this.setFeeds(feeds);
						
			if(this.isUsePresets())
			{
				if(XMLserie.getMinSize()!=0)
					this.setMinSize(XMLserie.getMinSize());
				
				if(XMLserie.getMaxSize()!=0)
					this.setMaxSize(XMLserie.getMaxSize());
				
				if(XMLserie.getMinNumOfSeeders()!=0)
					this.setMinNumOfSeeders(XMLserie.getMinNumOfSeeders());
				
				//add the new keywords if they didn't already existed
				//leave user defined keywords alone
				String userKeywords = this.getKeywords();
				String xmlKeywords  = XMLserie.getKeywords();
				if(userKeywords == null)
				{
					if(xmlKeywords != null)
						this.setKeywords(xmlKeywords);
				}
				else if(xmlKeywords == null)
				{
					if(userKeywords != null)
						this.setKeywords(userKeywords);
				}
				else if(!userKeywords.contains(xmlKeywords))
				{
					if(userKeywords.equals("")) //$NON-NLS-1$
						this.setKeywords(xmlKeywords);
					else if(xmlKeywords.equals("")) //$NON-NLS-1$
						this.setKeywords(userKeywords);
					else
						this.setKeywords("(" + userKeywords + " | " + xmlKeywords + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				//else the xmlKeywords are already defined in the userKeywords
				
				//when the show has to be put on hold
				if(XMLserie.isUseBreakSchedule())
				{
					this.setUseBreakSchedule(XMLserie.isUseBreakSchedule());
					this.setUseBreakScheduleEpisode(XMLserie.isUseBreakScheduleEpisode());
					this.setBreakEpisode(XMLserie.getBreakEpisode());
					this.setUseBreakScheduleFrom(XMLserie.isUseBreakScheduleFrom());
					this.setBreakFrom(XMLserie.getBreakFrom());
					this.setBreakUntil(XMLserie.getBreakUntil());
				}
				
				//when the show has to pause
				if(XMLserie.isUseEpisodeSchedule())
					this.setEpisodeSchedule(XMLserie.isUseEpisodeSchedule(), XMLserie.getDays());
			}
		}
	}
	
	/**
	 * This method reads shows.xml and generates feed urls from
	 * the automatic feeds located in the xml file.
	 */
	public void generateFeedLocations() 
	{
		// read shows.xml
		// get the details of the show
		TedXMLParser parser = new TedXMLParser();
		Element series = parser.readXMLFromFile(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
		
		// add auto-generated search based feeds to the show
		Vector<FeedPopupItem> items = new Vector<FeedPopupItem>();
		items = parser.getAutoFeedLocations(series);
		
		this.generateFeedLocations(items);
	}
	
	/**
	 * Generate feed locations from a vector of search based feeds
	 * @param items
	 */
	public void generateFeedLocations(Vector<FeedPopupItem> items) 
	{		
		this.removeAllPredefinedFeeds();
		
		for(int i=0; i<items.size(); i++)
		{
			FeedPopupItem item = items.get(i);
			this.autoGenerateAndAddFeedURL(item.getUrl());
		}	
	}
	
	/**
	 * Removes all predefined feeds from this show
	 */
	public void removeAllPredefinedFeeds()
	{
		// backup the user defined feeds
		Vector<TedSerieFeed> userFeeds = new Vector<TedSerieFeed>();
		userFeeds.addAll(this.getSelfmadeFeeds());
		
		// clear all feeds
		this.removeAllFeeds();
		
		this.setFeeds(userFeeds);
	}
	
	/**
	 * Removes all feeds from this show
	 */
	public void removeAllFeeds()
	{
		feeds.clear();
	}

	/**
	 * Generate a feed url from a search based feed
	 * @param url2 url of a search based feed
	 */
	public void autoGenerateAndAddFeedURL(String url2) 
	{
		if (url2.contains("#NAME#"))
		{
			url2 = url2.replace("#NAME#", this.getSearchName());
			this.addPredefinedFeed(url2);
		}
	}

	/**
	 * Add a predefined feed to this TedSerie
	 * @param url2 the Url of the feed
	 */
	private void addPredefinedFeed(String url2) 
	{
		TedSerieFeed newFeed = new TedSerieFeed(url2, false);
		this.feeds.add(newFeed);	
	}
	
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	
	/**
	 * Set the latest dates (used for the episode schedule) to today
	 */
	public void setLastDatesToToday()
	{
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);

		// convert day
		int day = c.get(Calendar.DAY_OF_WEEK);
		day --;
		
		this.lastDayChecked = day;
		this.lastWeekChecked =(c.get(Calendar.WEEK_OF_YEAR));
		this.lastYearChecked = (c.get(Calendar.YEAR));		
	}

	/**
	 * @return The current episode of the show
	 */
	public int getCurrentEpisode() 
	{
		return currentEpisode;
	}
	
	/**
	 * @return The current season of the show
	 */
	public int getCurrentSeason() 
	{
		return currentSeason;
	}
	
	/**
	 * @return The name of the show
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Set the name of the show
	 * @param name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * @return Vector with feeds for this show
	 */
	public Vector<TedSerieFeed> getFeeds()
	{
		if (feeds == null)
		{
			feeds = new Vector<TedSerieFeed>();
			TedSerieFeed tsf = new TedSerieFeed(url, checkDate);
			feeds.add(tsf);
		}
		return feeds;
	}
	
	
	/**
	 * @return The minimum size for torrent contents
	 */
	public int getMinSize() 
	{
		return this.minSize;
	}
	
	/**
	 * Set the minimum size for the torrent contents
	 * @param size
	 */
	public void setMinSize(int size) 
	{
		this.minSize = size;
	}

	/**
	 * Set the maximum size for the torrent contents
	 * @param size
	 */
	public void setMaxSize(int size) 
	{
		this.maxSize = size;
	}
	
	/**
	 * @return The max size for the torrent contents
	 */
	public int getMaxSize() 
	{		
		return this.maxSize;
	}
	
	/**
	 * Set the keywords for the torrent titles
	 * @param s
	 */
	public void setKeywords(String s) 
	{
		this.keywords = s;
	}
	
	/**
	 * @return Keywords for the torrent titles
	 */
	public String getKeywords() 
	{
		return this.keywords;
	}
	
	/**
	 * @return The last week we checked if the show has to get another status
	 */
	public int getLastWeekChecked() 
	{
		return lastWeekChecked;
	}

	/**
	 * Set if ted has to download all shows from the feed
	 * @param b
	 */
	public void setDownloadAll(boolean b) 
	{
		this.downloadAll = b;
	}
	
	/**
	 * @return If ted has to download all shows from the feed
	 */
	public boolean isDownloadAll()
	{
		return this.downloadAll;
	}

	/**
	 * Set the status of the show
	 * @param status The status to set.
	 */
	public boolean setStatus(int status) 
	{
		if ( ( 	status == TedSerie.STATUS_CHECK || 
				status == TedSerie.STATUS_HOLD || 
				status == TedSerie.STATUS_PAUSE ||
				status == TedSerie.STATUS_HIATUS ||
				status == TedSerie.STATUS_DISABLED
				)
				&& status != this.status
			)
		{
			// disable break schedule if show is put on a different status as hold
			if(this.status == TedSerie.STATUS_HOLD && status != TedSerie.STATUS_HOLD)
			{
				if(!(this.isUseBreakScheduleEpisode() || this.isUseBreakScheduleFrom()))
					this.setUseBreakSchedule(false);
			}
			
			// set status
			this.status = status;				
			this.resetStatus(true);
			
			return true;
		}
		return false;
	}
	
	/**
	 * @return If the show is paused
	 */
	public boolean isPaused()
	{
		return this.status == TedSerie.STATUS_PAUSE;
	}
	/**
	 * @return If the show is on hold
	 */
	public boolean isHold()
	{
		return this.status == TedSerie.STATUS_HOLD;
	}
	/**
	 * @return If the status of the show is CHECK
	 */
	public boolean isCheck()
	{
		return this.status == TedSerie.STATUS_CHECK;
	}
	
	boolean isHiatus() 
	{
		return this.status == TedSerie.STATUS_HIATUS;
	}
	
	
	boolean isDisabled() 
	{
		return this.status == TedSerie.STATUS_DISABLED;
	}

	/**
	 * @return Returns if ted has to use the episode scheduler
	 */
	public boolean isUseEpisodeSchedule() 
	{
		return useEpisodeSchedule;
	}

	/**
	 * Set if ted has to use the episode scheduler
	 * @param useSchedule
	 * @param newdays Days ted has to check for new episodes
	 */
	public void setEpisodeSchedule(boolean useSchedule, boolean[] newdays) 
	{
		// if someone changed the schedule, set this week as last week
		// unless the show is on hold
		if (useSchedule && !arraysEqual(newdays, this.days) && (!this.isHold() && ! this.isHiatus()))
		{
			this.setLastDatesToToday();
		}
		this.useEpisodeSchedule = useSchedule;
		this.days = newdays;
		this.resetStatus(true);
	}

	/**
	 * @return The days ticked by the user in the scheduler
	 */
	public boolean[] getDays() 
	{
		if (days != null)
		{
			return days;
		}
		else
		{
			days = this.initDays();
			return days;
		}
	}
	
	/**
	 * Set the days used by the scheduler
	 * @param newDays
	 */
	public void setDays(boolean[] newDays)
	{
		this.days = newDays;
	}

	/**
	 * @return Returns the weeklyInterval.
	 */
	public int getWeeklyInterval() 
	{
		return weeklyInterval;
	}

	/**
	 * @param weeklyInterval The weeklyInterval to set.
	 */
	public void setWeeklyInterval(int weeklyInterval) 
	{
		this.weeklyInterval = weeklyInterval;
	}

	/**
	 * @return If ted has to use the break scheduler
	 */
	public boolean isUseBreakSchedule() 
	{
		return useBreakSchedule;
	}

	/**
	 * Set if ted has to use the breakscheduler
	 * @param useBreakSchedule
	 */
	public void setUseBreakSchedule(boolean useBreakSchedule) 
	{
		this.useBreakSchedule = useBreakSchedule;
		this.resetStatus(true);
	}

	/**
	 * @return Until when the show has a break
	 */
	public long getBreakUntil() 
	{
		return breakUntil;
	}
	
	/**
	 * @return When when the show has a break
	 */
	public long getBreakFrom() 
	{
		return breakFrom;
	}

	/**
	 * Set untill when the show has a break
	 * @param day
	 * @param month
	 * @param year
	 */
	public void setBreakUntil(int day, int month, int year) 
	{
		Calendar c = new GregorianCalendar();
		c.set(year, month, day);
		this.breakUntil = c.getTimeInMillis();
	}
	
	public void setBreakUntil(long date)
	{
		this.breakUntil = date;
	}
	
	/**
	 * Set when when the show has a break
	 * @param day
	 * @param month
	 * @param year
	 */
	public void setBreakFrom(int day, int month, int year) 
	{
		Calendar c = new GregorianCalendar();
		c.set(year, month, day);
		this.breakFrom = c.getTimeInMillis();
	}
	
	public void setBreakFrom(long date)
	{
		this.breakFrom = date;
	}
	
	/**
	 * @return The episode the user wants the break to start
	 */
	public int getBreakEpisode()
	{
		return this.breakEpisode;
	}

	/**
	 * Set the episode number the user wants the break to start
	 * @param i
	 */
	public void setBreakEpisode (int i)
	{
		this.breakEpisode = i;
	}
	
	/**
	 * Set the feeds for this serie
	 * @param serieFeeds
	 */
	public void setFeeds(Vector<TedSerieFeed> serieFeeds)
	{	
		this.feeds = serieFeeds;
	}
	
	public Vector<TedSerieFeed> getSelfmadeFeeds()
	{
		Vector<TedSerieFeed> tempFeeds = this.getFeeds(); 
		Vector<TedSerieFeed> selfmadeFeeds = new Vector<TedSerieFeed>();
		TedSerieFeed temp;
		
		for (int i = 0; i < tempFeeds.size(); i++)
		{
			temp = this.feeds.get(i);

			if (temp.getSelfmade())
			{
				selfmadeFeeds.addElement(temp);
			}
		}
		
		return selfmadeFeeds;
	}

	/**
	 * @return Returns the activity.
	 */
	public int getActivity()
	{
		return activity;
	}

	/**
	 * @param activity The activity to set.
	 */
	public void setActivity(int activity)
	{
		this.activity = activity;
	}

	/**
	 * @return Returns the minNumOfSeeders.
	 */
	public int getMinNumOfSeeders()
	{
		return minNumOfSeeders;
	}

	/**
	 * @param minNumOfSeeders The minNumOfSeeders to set.
	 */
	public void setMinNumOfSeeders(int minNumOfSeeders)
	{
		this.minNumOfSeeders = minNumOfSeeders;
	}

	/**
	 * @return Returns the statusString.
	 */
	public String getStatusString()
	{
		return statusString;
	}

	/**
	 * @param statusString The statusString to set.
	 * @param mainDialog 
	 */
	public void setStatusString(String statusString, TedMainDialog mainDialog)
	{
		this.statusString = statusString;
		mainDialog.repaint();
	}

	/**
	 * @return Returns the progress.
	 */
	public int getProgress()
	{
		return progress;
	}

	/**
	 * @param progress The progress to set.
	 * @param mainDialog 
	 */
	public void setProgress(int progress, TedMainDialog mainDialog)
	{
		this.progress = progress;
		mainDialog.repaint();
	}

	/**
	 * Reset progress, activity and statusstring to default values
	 */
	public void resetStatus(Boolean reset)
	{
		this.progress = 0;
		this.activity = TedSerie.IS_IDLE;
		if (reset)
		{
			this.statusString = this.makeDefaultStatusString();	
		}
	}

	/**
	 * @return Default status string according to status of show
	 */
	private String makeDefaultStatusString()
	{		
		if (this.isHold())
		{
			// if we use the breakschedule: return next date we put the show on check again
			if (this.useBreakSchedule)
			{			
				DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
				
				return Lang.getString("TedSerie.StatusOnHoldUntill") + " " + df.format(breakUntil); //$NON-NLS-1$
			}
			else
			{
				return Lang.getString("TedSerie.StatusOnHold"); //$NON-NLS-1$
			}
			
		}
		else if (this.isPaused())
		{
			// if we use the episode schedule: return next day the show will be on check again
			if (this.useEpisodeSchedule)
			{
				String[] dayNames = {Lang.getString("TedSerie.Sunday"), Lang.getString("TedSerie.Monday"), Lang.getString("TedSerie.Tuesday"), Lang.getString("TedSerie.Wednesday"), Lang.getString("TedSerie.Thursday"), Lang.getString("TedSerie.Friday"), Lang.getString("TedSerie.Saturday")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
				// display "waiting until next tuesday"
				Calendar c = new GregorianCalendar();
				int today = c.get(Calendar.DAY_OF_WEEK);
				// find next day in days array that is on true
				int nextDay = this.getNextCheckDay(today);
				return Lang.getString("TedSerie.OnPauseTillNext")+ " " + dayNames[nextDay]; //$NON-NLS-1$
			}
			else
			{
				return Lang.getString("TedSerie.OnPause"); //$NON-NLS-1$
			}
			
		}
		else if (this.isHiatus())
		{
			return Lang.getString("TedSerie.Hiatus");
		}
		else if (this.isDisabled())
		{
			return Lang.getString("TedSerie.Disabled");
		}
		else
		{
			return Lang.getString("TedSerie.Idle"); //$NON-NLS-1$
		}
	}
	
	

	public boolean isUseBreakScheduleEpisode() {
		return useBreakScheduleEpisode;
	}

	public void setUseBreakScheduleEpisode(boolean useBreakScheduleEpisode) {
		this.useBreakScheduleEpisode = useBreakScheduleEpisode;
	}

	public boolean isUseBreakScheduleFrom() {
		return useBreakScheduleFrom;
	}

	public void setUseBreakScheduleFrom(boolean useBreakScheduleFrom) {
		this.useBreakScheduleFrom = useBreakScheduleFrom;
	}
	
	public void setUsePresets(boolean usePresets)
	{
		this.usePresets = usePresets;
	}
	
	public boolean isUsePresets()
	{
		return usePresets;
	}

	public void setTVcom(String tempS)
	{
		this.tvCom = tempS;
		
	}
	
	public String getTVcom()
	{
		return this.tvCom;
		
	}
	
	public boolean isDaily()
	{
		return this.isDaily;
	}

	/**
	 * @return A text that displays where this show is searching for
	 */
	public String getSearchForString() 
	{	
		if (this.currentEpisodeSearchString == "" || this.currentEpisodeSearchString == null)
		{
			// no search for string found, probably first time this show is loaded in new ted
			try 
			{
				// get the se from the scheduler
				SeasonEpisode se = (SeasonEpisode) this.getScheduler().getEpisode(new SeasonEpisode(this.currentSeason, this.currentEpisode));
				this.currentEpisodeIsDouble = se.isDouble();
				this.currentEpisodeSearchString = se.getSearchStringWithTitle();
			} 
			catch (NoEpisodeFoundException e) 
			{
				// nothing found in scheduler, generate from current season/episode
				SeasonEpisode se = new SeasonEpisode();
				se.setSeason(this.currentSeason);
				se.setEpisode(this.currentEpisode);
				this.currentEpisodeSearchString =  se.toString();
			}	
		}
		return this.currentEpisodeSearchString;
	}
	
	/**
	 * @return the name of the show that can be used to search on torrent sites
	 */
	public String getSearchName()
	{
		if (!(searchName == null) && !searchName.equals(""))
		{
			return this.searchName;
		}
		else
		{
			// remove weird chars from name
			String result = this.getName();
			result = result.replaceAll("[/:&*?|\"\\\\]", " ");
			// replacing one of these chars by a space could lead to multiple spaces
			// so we replace 2 or more spaces with a single space
			result = result.replaceAll(" {2,}", " ");
			return result;
		}
	}
	
	public void setSearchName(String sName)
	{
		this.searchName = sName;
	}
	
	/**
	 * @return the name of the show that can be used to query epguides.com
	 */
	public String getEpguidesName()
	{
		if (!(epguidesName == null) && !epguidesName.equals(""))
		{
			return this.epguidesName;
		}
		else
		{
			// remove weird chars from name
			String result = this.getName().toLowerCase();
			result = result.replaceAll("[/:&*?|\"\\\\]", " ");
			// remove all spaces
			result = result.replaceAll(" ", "");
			// remove any leading "the "
			if(result.startsWith("the"))
			{
				result = result.substring(3);
			}
			return result;
		}
	}
	

	
	/**
	 * Will increment the season/episode of the show to the episode that is
	 * scheduled after the params.
	 * When the show is not on hiatus and no episode is found, the currentEpisode number
	 * will be increased with 1.
	 * @param season
	 * @param episode
	 */
	public void goToNextEpisode(int season, int episode)
	{
		// If the current episode is a double episode you want to increase
		// the episode number by 2 instead of 1.
		boolean doubleEpisode = this.isDoubleEpisode(); // separate boolean for the catch block
		if (doubleEpisode)
		{
			++episode;
		}
		
		try
		{
			SeasonEpisode nextSE = (SeasonEpisode) this.getScheduler().getNextEpisode(new SeasonEpisode(season, episode));
			this.setCurrentEpisode(nextSE);
		}
		catch (NoEpisodeFoundException e) 
		{
			// if no next SE is found, put ted on hiatus and increment episode with 1
			if (this.status != TedSerie.STATUS_HIATUS)
			{
				int newSeason = season;
				int newEpisode = episode + 1;
				
				SeasonEpisode nextSE = new SeasonEpisode (newSeason, newEpisode);
				this.setCurrentEpisode(nextSE);
				//this.setStatus(TedSerie.STATUS_HIATUS);
			}
		}
	}
	
	public boolean isDoubleEpisode()
	{
		return this.currentEpisodeIsDouble;
	}

	/**
	 * @param episode
	 */
	public void updateStatus(int episode) 
	{
		this.getScheduler().updateStatus(episode);		
	}

	/**
	 * Checks the status of the show with the current date
	 */
	public void updateShowStatus() 
	{
		this.getScheduler().updateShowStatus();	
	}

	/**
	 * Checks the status of the show with the scheduled airdate
	 */
	private void checkAirDate() 
	{
		this.getScheduler().checkAirDate();	
	}
	
	public SeasonEpisodeScheduler getScheduler()
	{
		// scheduler can be null if this serie is from an older ted version
		if (this.scheduler == null)
		{
			this.scheduler = new SeasonEpisodeScheduler(this);
			this.useAutoSchedule = true;
		}
		return this.scheduler;
	}

	/**
	 * @return All aired and published episodes for this show
	 */
	public Vector<StandardStructure> getPubishedAndAiredEpisodes(boolean noScheduledEpisodes) 
	{
		return this.getScheduler().getPubishedAndAiredEpisodes(noScheduledEpisodes);
	}

	public int getLastDayChecked() 
	{
		return this.lastDayChecked;
	}

	public int getLastYearChecked() 
	{
		return this.lastYearChecked;
	}

	public void setLastDateChecked(int day, int week, int year) 
	{
		this.lastDayChecked = day;
		this.lastWeekChecked = week;
		this.lastYearChecked = year;	
	}

	public boolean isUseAutoSchedule() 
	{
		return useAutoSchedule;
	}

	public void setUseAutoSchedule(boolean useAutoSchedule) 
	{
		this.useAutoSchedule = useAutoSchedule;
		
		if (useAutoSchedule)
		{
			this.setStatus(STATUS_HIATUS);
		}
		else
		{
			this.setStatus(STATUS_CHECK);
		}
		
		this.updateShowStatus();
	}

	public void setEpguidesName(String text)
	{
		if (!text.equals(this.getEpguidesName()))
		{
			this.epguidesName = text;
			// update scheduler
			this.getScheduler().forceScheduleUpdate();
		}	
	}

	/**
	 * @return The next to air episode. Either based on the schedule (if there is one) or generated
	 * from known (published) episodes
	 */
	public StandardStructure getNextEpisode() 
	{
		return this.getScheduler().getNextToAirEpisode();
	}
	
	public void setTimeZone(int timeZone)
	{
		this.timeZone = timeZone;
	}
	
	public int getTimeZone()
	{
		return this.timeZone;
	}

	/**
	 * Cache the values in the Serie so we don't need to update them from the schedule every time
	 * Now they are pushed when the schedule is updated.
	 * @param se
	 */
	public void setCurrentEpisode(StandardStructure se) 
	{
		SeasonEpisode episode;
		try 
		{
			episode = (SeasonEpisode) this.scheduler.getEpisode(se);
		} 
		catch (NoEpisodeFoundException e1) 
		{
			episode = (SeasonEpisode)se;
		}
		
		this.currentEpisode = episode.getEpisode();
		this.currentSeason = episode.getSeason();
		try 
		{
			this.currentEpisodeAirDate = episode.getAirDate();
		} 
		catch (AirDateUnknownException e) 
		{
			this.currentEpisodeAirDate = null;
		}
		this.currentEpisodeSearchString = episode.getSearchStringWithTitle();
		this.currentEpisodeIsDouble = episode.isDouble();
		this.updateShowStatus();
	}

	public void setStatusString(String string) 
	{
		this.statusString = string;		
	}

	/**
	 * Checks the schedule whether the current season/episode are known.
	 * If not, checks the schedule if there is another episode known after the previous episode.
	 */
	public void checkIfCurrentEpisodeIsScheduled() 
	{
		try 
		{
			StandardStructure result = scheduler.getEpisode(new SeasonEpisode(currentSeason, currentEpisode));
		} 
		catch (NoEpisodeFoundException e) 
		{
			// no episode found in schedule, try to see if there is a next episode known
			goToNextEpisode(currentSeason, currentEpisode);
		}		
	}
	
	public StandardStructure getCurrentStandardStructure()
	{
		StandardStructure temp = new SeasonEpisode(currentSeason, currentEpisode);
		StandardStructure result;
		try
		{
			result = scheduler.getEpisode(temp);
		}
		catch (NoEpisodeFoundException e) 
		{
			result = temp;
		}	
		
		return result;
	}

	public void setScheduler(SeasonEpisodeScheduler scheduler2) 
	{
		this.scheduler = scheduler2;		
	}
}
