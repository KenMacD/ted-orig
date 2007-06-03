package ted;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;

import net.sf.torrentsniffer.bencoding.BencodingException;
import net.sf.torrentsniffer.torrent.TorrentException;
import net.sf.torrentsniffer.torrent.TorrentFile;
import net.sf.torrentsniffer.torrent.TorrentImpl;
import net.sf.torrentsniffer.torrent.TorrentInfo;
import net.sf.torrentsniffer.torrent.TorrentState;

import com.sun.cnpi.rss.elements.Channel;
import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * The parser checks one entire show for new episodes
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
public class TedParser
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private TedMainDialog tMainDialog;
	private boolean foundTorrent;
	private TedParserKeywordChecker tPKeyChecker = new TedParserKeywordChecker();
	private TedParserDateChecker tPDateChecker = new TedParserDateChecker();
	private TorrentImpl bestTorrent;
	private TorrentState bestTorrentState;
	private TorrentInfo bestTorrentInfo;
	private URL bestTorrentUrl = null;
	private Vector dailyItems;
	
  	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a new tedparser
	 * @param serie Serie to parse
	 * @param main TedMainDialog to report to
	 */
	public void ParseSerie(TedSerie serie, TedMainDialog main)
	{		
		this.tMainDialog = main;
		// reset globals
		foundTorrent = false;
		double progress = 0;
		//int toDo = 0;
		this.bestTorrent = null;
		this.bestTorrentInfo = null;
		this.bestTorrentState = null;
		this.bestTorrentUrl = null;
		this.dailyItems = new Vector();
		
		Rss rss;
		// load xml
		// parse the rss feed of the serie
		RssParser parser;
		Vector feeds = serie.getFeeds();
		TedSerieFeed currentFeed;
		serie.setProgress(0, tMainDialog);
		// walk through all the feeds for this show
		//toDo += feeds.size();
		
		double progressPerFeed;
		if(feeds.size()==0)
			progressPerFeed = 0; //when a show has no feeds
		else
			progressPerFeed = 100 / feeds.size();
		
		for (int i = 0; i < feeds.size(); i++)
		{
			if (main.getStopParsing())
			{
				return;
			}
			//progress++;
			currentFeed = (TedSerieFeed) feeds.get(i);
			tPDateChecker.setLastParseDate(currentFeed.getDate());
			try 
			{
				URLConnection urlc;
				URL feedURL = new URL(currentFeed.getUrl());
				
				TedLog.debug("Loading feed from " + serie.getName() + " URL: " + feedURL); //$NON-NLS-1$ //$NON-NLS-2$
				serie.setStatusString(Lang.getString("TedParser.StatusLoading") + " " + feedURL, tMainDialog);		 //$NON-NLS-1$
				
				urlc = feedURL.openConnection();
				// timeout for connection
				urlc.setConnectTimeout(5000);
				
				// create an RSS parser
				parser = RssParserFactory.createDefault();
				//parser.
				
				rss = parser.parse(urlc.getInputStream());
				Channel channel = rss.getChannel();
		        Object[] items = channel.getItems().toArray();
		        

		        // walk through the different entries until we find a desired episode
		        // we walk in the wrong direction to get older torrents first (older = more seeders)
		       // toDo += items.length;
		        double progressPerItem = progressPerFeed / items.length;
		        int itemProgress = 0;
		        int itemLength = items.length;
		        for (int j = items.length - 1; j >= 0; j--)
		        {
		        	if (main.getStopParsing())
					{
						return;
					}
		        	progress += progressPerItem;
		        	itemProgress++;
		        	
		        	Item item = (Item)items[j];
		        	serie.setProgress((int) Math.abs(progress), tMainDialog);
		        	serie.setStatusString(Lang.getString("TedParser.StatusCheckingItem") + " " + itemProgress + "/" + itemLength , tMainDialog); //$NON-NLS-1$ //$NON-NLS-2$
		        	
		        	if(serie.isDaily || this.continueParsing())
		        	{
		        		if(tPKeyChecker.checkKeywords(item.getTitle().toString().toLowerCase(), serie.getKeywords().toLowerCase()))
		        		{
	        				if(!serie.isDaily)
	        				{
	        					this.ParseItem(item, serie, channel.getTitle().getText());
	        				}
	        				else
	        				{
	        					if((((TedDailySerie)serie).getLatestDownloadDateInMillis()) < 
	        						getDailyDateFromItem(item).getDate().getTimeInMillis())
	        					{
	        						this.addDailyItem(item, serie);	
	        					}
	        				}
			        	}
		        	}
		        	else
		        	{
		        		i = feeds.size();
		        		j = 0;
		        	}
		        }
			}
			catch (RssParserException e) 
			{
				String message = Lang.getString("TedParser.ErrorCreatingParser1") + " " + serie.getName() + //$NON-NLS-1$
				"\n" + Lang.getString("TedParser.ErrorCreatingParser2") + 
				"\n" + Lang.getString("TedParser.ErrorCreatingParser3") + " " + currentFeed.getUrl(); //$NON-NLS-1$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (MalformedURLException e) 
	        {
	        	String message = Lang.getString("TedParser.ErrorNotValidURL1") + " " +  serie.getName() + ". " + Lang.getString("TedParser.ErrorNotValidURL2") + //$NON-NLS-1$ //$NON-NLS-2$
				"\n" + currentFeed.getUrl();; //$NON-NLS-1$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (IOException e) 
			{
	        	String message = Lang.getString("TedParser.Error404Feed1") + " " + serie.getName() + 
	        	"\n" + Lang.getString("TedParser.Error404Feed2") + " "+currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (NullPointerException e)
	        {
	        	// no items in the feed
	        	String message = "ted could not find any items in the feed of " + serie.getName() + "\nFeed: " + currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				tMainDialog.displayError("ted Error!", message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				e.printStackTrace();
				//return;
	        }
	        catch (Exception e)
	        {
	        	TedLog.error(e, "Unkown exception while parsing RSS feed ("+currentFeed.getUrl()+")"); //$NON-NLS-1$ //$NON-NLS-2$
	        	//return;
	        }
	   
	       
		}
		try
		{
			serie.setProgress(100, tMainDialog);
			
			if(!serie.isDaily)
			{
				this.downloadBest(serie);
			}
			else
			{
				this.downloadBestDaily(serie);
			}
		}
		catch (Exception e)
		{
			// error while downloading best torrent
			e.printStackTrace();
		}
	}

	

	/**
	 * @return Whether the parser should continue parsing considering the found
	 * torrent and the user settings
	 */
	private boolean continueParsing()
	{
		if (TedConfig.getSeederSetting() == TedConfig.DOWNLOADMOSTSEEDERS)
		{
			return true;
		}
		else if (TedConfig.getSeederSetting() == TedConfig.DOWNLOADMINIMUMSEEDERS && this.bestTorrentUrl == null)
		{
			return true;
		}
		return false;
	}



	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	/**
	 * Parse one item from the RSS feed.
	 * Checks the contents, finds episode and seaon numbers and 
	 * takes specific actions (like saving the torrent or alerting the user)
	 * @param item Item to parse
	 * @param serie Serie this item is from
	 * @param source Source of the Feed
	 * @throws FileSizeException 
	 * @throws HeadlessException 
	 */
	private void ParseItem(Item item, TedSerie serie, String source)
	{
		int season = 0;
		int episode = 0;
		String sTitle = item.getTitle().toString();
		String sTitle_lower = sTitle.toLowerCase();
		
		// if the user doesnt want to download all or if we want to find the latest s + e
		// check the episode and season (a watcher is set to download all)
		if (!serie.isDownloadAll())
		{
			
			SeasonEpisode se = getSeasonEpisodeFromItem(item, serie, source, false);
			if (se != null)
			{
				season = se.getSeason();
				episode = se.getEpisode();
			}
			// used if user wants to get the latest season/episode from the feed
			// limit of 50 as maximum episode/season number
		}
			
		// if the season is the current season and episode is the next episode
		// or if the season is the next season and episode is the first episode
		// also download all from feed is so selected
		TedIO tIO = new TedIO();
		
		// translate the url from the feed to a download url
		String torrentUrl = item.getLink().toString();
			
		if ((season == serie.getCurrentSeason() && episode == serie.getCurrentEpisode()) 
				|| serie.isDownloadAll())
		{	
			torrentUrl = tIO.translateUrl(torrentUrl, sTitle, TedConfig.getTimeOutInSecs());
			
			TedLog.debug("check: " + sTitle); //$NON-NLS-1$
			serie.setStatusString(Lang.getString("TedSerie.Checking") + " " + sTitle, tMainDialog); //$NON-NLS-1$
			tMainDialog.repaint();
			
			if (torrentUrl == null)
			{
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), 
						Lang.getString("TedParser.ErrorNotSupportedFeed1") + " "+ serie.getName() + " " + Lang.getString("TedParser.ErrorNotSupportedFeed2") + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Lang.getString("TedParser.ErrorNotSupportedFeed3"), Lang.getString("TedParser.ErrorNotSupportedFeed4")+ " " + source); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else
			{	
				// save found torrent to file
				//String fileName = serie.getName()+"s"+season+"e"+episode;
			
				try
				{
					this.checkIfBest(torrentUrl, serie);
				}
				catch (Exception e)
				{
					TedLog.error(e, "Error checking torrent"); //$NON-NLS-1$
					//e.printStackTrace();
				}
			}
		}
		else if(season == serie.getCurrentSeason()+1 && episode == 1)
		{
			TedLog.debug(Lang.getString("TedParser.FoundNextSeason"));
			torrentUrl = tIO.translateUrl(torrentUrl, sTitle, TedConfig.getTimeOutInSecs());
			
			// make connection with torrent
			URL url;
			try
			{
				url = new URL(torrentUrl);
			}
			catch (Exception e)
			{
				String message = Lang.getString("TedParser.ErrorWhileChecking1") + " " + torrentUrl + " " + Lang.getString("TedParser.ErrorWhileChecking2") + serie.getName(); //$NON-NLS-1$ //$NON-NLS-2$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, "Exception"); //$NON-NLS-1$ //$NON-NLS-2$
				e.printStackTrace();
				return;
			}
			
			TedLog.debug("Loading torrent"); //$NON-NLS-1$
			TorrentImpl torrent = new TorrentImpl(url, TedConfig.getTimeOutInSecs());
			
			// check size and amount of seeders to filter out fakes
			boolean correctSize = true;
			try
			{
				hasCorrectSize(torrent,serie);
			}
			catch(FileSizeException e)
			{
				correctSize = false;
			}
			
			if(hasEnoughSeeders(torrent,serie) && correctSize)
			{
				
				// we found a new season, does the user wants to download it?
				
				if (TedConfig.isDownloadNewSeason())
				{
					int answer = JOptionPane.NO_OPTION;
					
					// ask user if he wants to download new season
					answer = JOptionPane.showOptionDialog(tMainDialog, Lang.getString("TedParser.DialogNewSeason1")+ " " + season+ " " + Lang.getString("TedParser.DialogNewSeason2") + " " +  serie.getName()+"." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								+ "\n" + Lang.getString("TedParser.DialiogNewSeason3")+ " " + season+"?" //$NON-NLS-1$ //$NON-NLS-2$
								+ "\n" + Lang.getString("TedParser.DialogNewSeason4")+ " " + (season-1)
								+ "\n" + Lang.getString("TedParser.DialogNewSeason5"), //$NON-NLS-1$ //$NON-NLS-2$
								Lang.getString("TedParser.DialogNewSeasonHeader") + serie.getName(), //$NON-NLS-1$
				                JOptionPane.YES_NO_OPTION,
				                JOptionPane.QUESTION_MESSAGE, null, Lang.getYesNoLocale(), Lang.getYesNoLocale()[0]);
						
					if (answer == JOptionPane.YES_OPTION)
					{
						// download new season
						serie.setCurrentEpisode(1);
						serie.setCurrentSeason(season);
						tMainDialog.saveShows();
						
						// only the season and episode are changed, no torrent has actually
						// been downloaded
						foundTorrent = false;
					}
					else
					{
						// remember the preference of the user
						TedConfig.setDownloadNewSeason(false);
					}	
				}
			}
		}
		else
		{
			tPDateChecker.setLastParseDate(tPDateChecker.getThisParseDate());
		}
		
	}
	
	private void addDailyItem(Item item, TedSerie serie)
	{
		TedIO tIO = new TedIO();
		String torrentUrl = item.getLink().toString();
		
		this.bestTorrent = null;
		this.bestTorrentInfo = null;
		this.bestTorrentState = null;
		this.bestTorrentUrl = null;
		
		checkIfBest(torrentUrl, serie);
		
		if(this.bestTorrentUrl!=null)
		{
			DailyDate dd = getDailyDateFromItem(item);
			dd.setSeeders(bestTorrentState.getComplete());
			dd.setUrl(bestTorrentUrl);
			
			checkIfBestDaily(dd);
		}
	}
	
	
	private void checkIfBestDaily(DailyDate dd) 
	{
		for(int i=0; i<dailyItems.size(); i++)
		{
			DailyDate item = (DailyDate)dailyItems.get(i);
			
			if(item.getDate().getTimeInMillis()==dd.getDate().getTimeInMillis())
			{
				if(item.getSeeders() < dd.getSeeders())
				{
					dailyItems.remove(i);
					dailyItems.add(dd);
	
					return;
				}
				else
				{
					return;
				}
			}
		}
		
		dailyItems.add(dd);		
	}



	/**
	 * Checks if torrent satisfies user size and seeder settings and
	 * if its better than the current selected best torrent
	 * @param torrentUrl URL of torrent to check
	 * @param serie TedSerie that torrent belongs to
	 */
	private void checkIfBest(String torrentUrl, TedSerie serie)
	{
		// make url
		URL url;
		TorrentImpl torrent;
		TorrentState torrentState;
		TorrentInfo torrentInfo;
		
		try
		{
			url = new URL(torrentUrl);
		}
		catch (Exception e)
		{
			String message = Lang.getString("TedParser.ErrorWhileChecking1") + " " + torrentUrl + " " + Lang.getString("TedParser.ErrorWhileChecking2") + serie.getName(); //$NON-NLS-1$ //$NON-NLS-2$
			tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, "Exception"); //$NON-NLS-1$ //$NON-NLS-2$
			e.printStackTrace();
			return;
		}
		
		// download torrent info
		try
		{
			TedLog.debug("Loading torrent"); //$NON-NLS-1$
			torrent = new TorrentImpl(url, TedConfig.getTimeOutInSecs());
			// get torrent info (for size)
			torrentInfo = torrent.getInfo();
						
			try
			{
				hasCorrectSize(torrent, serie);
			}
			catch (FileSizeException e)
			{
				throw e;
			}
			
			// if the user does not want to download compressed files
			if(TedConfig.getDoNotDownloadCompressed())
			{
				// and the torrent contains compressed files
				if(this.containsCompressedFiles(torrent))
				{
					// reject it
					return;
				}
			}
			
			// get torrent state (for seeders)	
			try
			{
				// get torrent state (containing seeders/leechers
				torrentState = torrent.getState(TedConfig.getTimeOutInSecs());
				
				//	compare with best	
				// if more seeders than best and more seeders than min seeders
				if ((this.bestTorrentUrl == null || torrentState.getComplete() > this.bestTorrentState.getComplete()) 
						&& torrentState.getComplete() > serie.getMinNumOfSeeders())
				{
					// print seeders
					TedLog.debug("Found new best torrent! (" + torrentState.getComplete()+ " seeders)"); //$NON-NLS-1$ //$NON-NLS-2$
					// current is best
					this.bestTorrentUrl = url;
					this.bestTorrentInfo = torrentInfo;
					this.bestTorrentState = torrentState;
					this.bestTorrent = torrent;		
				}
				else
				{
					TedLog.debug("Torrent has not enough seeders (" + torrentState.getComplete()+")"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				
			}
			catch (Exception e)
			{
				TedLog.error(e, "Error getting trackerstate for torrent " + torrentInfo.getName()); //$NON-NLS-1$
			}			
		}
		// TODO: catch all exceptions here and determine logging
		catch (BencodingException e)
		{
			// error reading torrentinfo or info from tracker
			if(e.getMessage().startsWith("Unknown object"))
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), Lang.getString("TedParser.ErrorWhileChecking1") + torrentUrl + "." 
						+ "\n" + Lang.getString("TedParser.ErrorBencoding"), "Exception"); //$NON-NLS-1$ //$NON-NLS-2$
			else
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), e.getMessage(), "Exception"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch (TorrentException e)
		{
			// error reading torrentinfo or info from tracker
			//tMainDialog.displayError("ted Error!", e.getMessage(), "Exception");
			TedLog.error(e, e.getLocalizedMessage());
			return;
		
		}
		catch (FileSizeException e)
		{
			// torrent contents too small
			return;
		}		
		catch (RuntimeException e)
		{
			// happens when scraping tracker for torrent seeder information
            TedLog.error(e, e.getLocalizedMessage());
			e.printStackTrace();
			return;
		}
		catch (Exception e)
		{
			String message = 	Lang.getString("TedParser.ErrorDownloadingContent1") + " " + torrentUrl + 
								" " + Lang.getString("TedParser.ErrorDownloadingContent2") + " " + serie.getName(); //$NON-NLS-1$ //$NON-NLS-2$
			tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, "Exception"); //$NON-NLS-1$ //$NON-NLS-2$
            TedLog.error(e, e.getLocalizedMessage());
			e.printStackTrace();
		}			
	}
	
	/**
	 * Checks if torrent satisfies user size 
	 * @param torrent torrentImpl of torrent to check
	 * @param serie TedSerie that torrent belongs to
	 * @return Returns if the torrent has the correct size
	 */
	private boolean hasCorrectSize(TorrentImpl torrent, TedSerie serie) throws FileSizeException
	{
		int minSize = serie.getMinSize();
		int maxSize = serie.getMaxSize();
		
		// get torrent info (for size)
		TorrentInfo torrentInfo = torrent.getInfo();
		// first check if size is between min and max
		// convert bytes to MB
		double byteSize = 9.5367431 * Math.pow(10, -7);
		int sizeMB = (int)Math.round(torrentInfo.getLength() * byteSize);
		
		//the size of the file(s) is zero. Not useful and probably not even a torrent
		if(sizeMB == 0)
		{
			TedLog.debug("File size is too small (" + sizeMB + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			FileSizeException e = new FileSizeException();
			e.size = 0;
			throw e;
		}
		
		//the size is smaller than the minimum size or larger than the maximum size
		if((minSize != 0 && minSize >= sizeMB) || (maxSize != 0 && maxSize <= sizeMB) )
		{
			// print error
			if (sizeMB > maxSize)
			{
				TedLog.debug("File size is too large (" + sizeMB + " mb)"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			else if (sizeMB < minSize)
			{
				TedLog.debug("File size is too small (" + sizeMB + " mb)"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			// throw exception
			FileSizeException e = new FileSizeException();
			e.size = sizeMB;
			throw e;
		}
		else
		{
			TedLog.debug("File size is ok! (" + sizeMB + " mb)"); //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}	
	}
	
	/**
	 * Checks if torrent satisfies seeder settings
	 * @param torrent torrentImpl of torrent to check
	 * @param serie TedSerie that torrent belongs to
	 * @return Returns if the torrent has enough seeders
	 */
	private boolean hasEnoughSeeders(TorrentImpl torrent, TedSerie serie)
	{
		// get torrent state (containing seeders/leechers)
		TorrentState torrentState = torrent.getState(TedConfig.getTimeOutInSecs());
		
		return(torrentState.getComplete() > serie.getMinNumOfSeeders());
	}
	
	/**
	 * Checks if torrent contains compressed files
	 * @param torrent torrentImpl of torrent to check
	 * @return Returns if the torrents contains a compressed file
	 */
	private boolean containsCompressedFiles(TorrentImpl torrent)
	{
		TorrentInfo torrentInfo = torrent.getInfo();
		TorrentFile[] files;
		
		// check if the torrent contains multiple files
		if(!torrentInfo.isSingleFile())
		{
			files = torrentInfo.getMultiFile();
			String name, type;
			
			// check all files if any of them is a compressed file
			for(int i=0; i<files.length; i++)
			{
				name = files[i].getPath().toString();
				type = name.substring(name.length()-3);
				
				if(isCompressedFile(type))
				{
					// found a compressed file
					TedLog.debug(Lang.getString("TedParser.CompressedFiles"));
					return true;
				}
			}
			
			// no compressed files found
			return false;
		}
		else
		{
			// check to see if the single file is compressed
			if(isCompressedFile(torrentInfo.getName().substring(torrentInfo.getName().length()-3)))
			{
				TedLog.debug(Lang.getString("TedParser.CompressedFiles"));
				return true;
			}
			else 
			{
				return false;
			}
		}
	}
	
	/**
	 * Checks if the given extension is that of a compressed file
	 * @param extension The extension of the file to check
	 * @return Returns if the extension is that of a compressed file
	 */
	private boolean isCompressedFile(String extension)
	{
		String extensions = TedConfig.getFilterExtensions();
		if(extensions.contains(extension))
			return true;
		else
			return false;
	}
	
	private void downloadBestDaily(TedSerie serie)
	{
		Collections.sort(dailyItems);
		
		int maxDownloads = Math.min(dailyItems.size(), ((TedDailySerie)serie).getMaxDownloads());
				
		DailyDate dd;
		long oldDate = ((TedDailySerie)serie).getLatestDownloadDateInMillis();
		long newDate = 0;
		for(int i=0; i<maxDownloads; i++)
		{
			dd = (DailyDate)dailyItems.get(i);
			this.bestTorrentUrl = dd.getUrl(); 
			
			newDate = dd.getDate().getTimeInMillis();
			
			if(newDate>oldDate)
			{
				oldDate=newDate;
				((TedDailySerie)serie).setLatestDownloadDateInMillis(oldDate);
			}
			
			try 
			{
				downloadBest(serie);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Downloads the currently best torrent to the userset location
	 * Announces download via balloon if succesful
	 * @param serie Current serie the best torrent belongs to
	 * @throws Exception
	 */
	private void downloadBest(TedSerie serie) throws Exception
	{
		foundTorrent = false;
		if (this.bestTorrentUrl != null)
		{
			boolean isDaily = serie.isDaily(); //TODO: check still needed if watcher not is used?
			int season = serie.getCurrentSeason();
			int episode = serie.getCurrentEpisode();
			
			// download torrent
			String fileName; 
			
			if(!isDaily)
				fileName = serie.getName()+"s"+season+"e"+episode; //$NON-NLS-1$ //$NON-NLS-2$
			else
				fileName = serie.getName();
				
			TedIO tio = new TedIO();
			try
			{
				tio.downloadTorrent(this.bestTorrentUrl, fileName);
			}
			catch (Exception e)
			{
				throw e;
			}
			
			// announce to user and update serie
			// everything went okay, notify user and save the changes
			String message;
			
			
			if(!isDaily)
				message = 	Lang.getString("TedParser.BalloonFoundTorrent1") + " " + season + " " + 
							Lang.getString("TedParser.BalloonFoundTorrent2") + " " + episode + " " +
							Lang.getString("TedParser.BalloonFoundTorrent3") + " " + serie.getName(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			else
				message = Lang.getString("TedParser.BallonFoundEntry") + " " + serie.getName(); //$NON-NLS-1$
			
			tMainDialog.displayHurray(Lang.getString("TedParser.BallonFoundTorrentHeader"), message, "Download succesful"); //$NON-NLS-1$ //$NON-NLS-2$
			
			serie.setStatusString(Lang.getString("TedParser.SerieStatusDownloaded1") + " " + season + " " + Lang.getString("TedParser.SerieStatusDownloaded1") + " " + episode + ".", tMainDialog); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			
			//ADD TO MONITOR W.I.P.
			//TedFolderMonitor t = TedFolderMonitor.getInstance();
			//t.addFileToMonitor(bestTorrentInfo.getName().toString());
			
			
			// check if this episode is the break episode
			if (serie.checkBreakEpisode(episode))
			{
				serie.setStatus(TedSerie.STATUS_HOLD);
			}
			else
			{
				foundTorrent = true;
			}
			
			if(!isDaily)
			{
				// update serie to look for next episode
				serie.setCurrentEpisode(episode+1);
				serie.setCurrentSeason(season);
			}
			
			tPDateChecker.setLastParseDate(tPDateChecker.getThisParseDate());
			
			// save the shows
			tMainDialog.saveShows();
			
//			 if no episode is found set the date of this serie
	        // otherwise ted checks again the whole feed
	        if(foundTorrent)
	        {
		        	// we found something so we can pause the serie again
		        	if (serie.isUseEpisodeSchedule())
		        	{
		        		serie.setStatus(TedSerie.STATUS_PAUSE);
		        	}
		        	
		        	if(!serie.isDaily)
		        	{
		        		// but we have to parse the serie until we have all the availble torrents
		        		ParseSerie(serie, tMainDialog);
		        	}
	        }			
		}
		else
		{
			serie.setStatusString(Lang.getString("TedSerie.Done"), tMainDialog); //$NON-NLS-1$
		}
		
		this.bestTorrentUrl = null;
	}

	public void setToLatestDate(TedSerie serie, TedFeedsTableModel table, TedSerieFeed[] feeds, TedMainDialog main)
	{
		this.tMainDialog = main;
		Rss rss;
		// parse the rss feed of the serie
		RssParser parser;
		
		TedSerieFeed currentFeed;
		Vector newFeeds =  new Vector(); 
		
		// walk through all the feeds for this show
		for (int i = 0; i < feeds.length; i++)
		{
			currentFeed = feeds[i];
			TedSerieFeed tempFeed;
			
			try 
			{
				// create an RSS parser
				parser = RssParserFactory.createDefault();
				rss = parser.parse(new URL(currentFeed.getUrl()));
				
				Channel channel = rss.getChannel();
		        Object[] items = channel.getItems().toArray();

		        Item item = (Item)items[0];
		        tempFeed = new TedSerieFeed(currentFeed.getUrl(), 0);
		        tempFeed.setDate(tPDateChecker.newestEntryInFeed(item));
		        newFeeds.addElement(tempFeed);
			}
			catch (RssParserException e) 
			{
				String message = Lang.getString("TedParser.ErrorCreatingParser1") + " " + serie.getName() + //$NON-NLS-1$
				"\n" + Lang.getString("TedParser.ErrorCreatingParser2") + 
				"\n" + Lang.getString("TedParser.ErrorCreatingParser3") + " " + currentFeed.getUrl(); //$NON-NLS-1$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (MalformedURLException e) 
	        {
	        	String message = Lang.getString("TedParser.ErrorNotValidURL1") + " " +  serie.getName() + ". " + Lang.getString("TedParser.ErrorNotValidURL2") + //$NON-NLS-1$ //$NON-NLS-2$
				"\n" + currentFeed.getUrl();; //$NON-NLS-1$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (IOException e) 
			{
	        	String message = Lang.getString("TedParser.Error404Feed1") + " " + serie.getName() + 
	        	".\n" + Lang.getString("TedParser.Error404Feed2") +currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (NullPointerException e)
	        {
	        	// no items in the feed
	        	String message = "ted could not find any items in the feed of " + serie.getName() + "\nFeed: " + currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				tMainDialog.displayError("ted Error!", message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
	        }
	        catch (Exception e)
	        {
	        	TedLog.error(e, "Unkown exception while parsing RSS feed ("+currentFeed.getUrl()+")"); //$NON-NLS-1$ //$NON-NLS-2$
	        	//return;
	        }
		}
		
		if(newFeeds.size()!=0)
		{
			table.clear();
			
			for(int i=0; i<newFeeds.size(); i++)
				table.addSerie((TedSerieFeed)newFeeds.get(i));
		}
	}

	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	
	/**
	 * Returns vector with all available seasons and episodes from the feeds
	 * of the serie
	 * @param serie
	 */
	public Vector getItems(TedSerie serie)
	{
		Vector items = new Vector();
		
		Rss rss;
		// load xml
		// parse the rss feed of the serie
		RssParser parser;
		Vector feeds = serie.getFeeds();
		TedSerieFeed currentFeed;
		// walk through all the feeds for this show
		//toDo += feeds.size();
		
		
		for (int i = 0; i < feeds.size(); i++)
		{
			//progress++;
			currentFeed = (TedSerieFeed) feeds.get(i);
			//tPDateChecker.setLastParseDate(currentFeed.getDate());
			try 
			{
				URLConnection urlc;
				URL feedURL = new URL(currentFeed.getUrl());
				
				TedLog.debug("Loading feed from " + serie.getName() + " URL: " + feedURL); //$NON-NLS-1$ //$NON-NLS-2$
				//serie.setStatusString(Lang.getString("TedParser.StatusLoading") + " " + feedURL, tMainDialog);		 //$NON-NLS-1$
				
				urlc = feedURL.openConnection();
				// timeout for connection
				urlc.setConnectTimeout(5000);
				
				// create an RSS parser
				parser = RssParserFactory.createDefault();
				//parser.
				
				rss = parser.parse(urlc.getInputStream());
				Channel channel = rss.getChannel();
		        Object[] items2 = channel.getItems().toArray();
		        

		        for (int j = items2.length - 1; j >= 0; j--)
		        {
		        	
		        	Item item = (Item)items2[j];
		        	//serie.setProgress((int) Math.abs(progress), tMainDialog);
		        	//serie.setStatusString(Lang.getString("TedParser.StatusCheckingItem") + " " + itemProgress + "/" + itemLength , tMainDialog); //$NON-NLS-1$ //$NON-NLS-2$
		        	
					//if(tPKeyChecker.checkKeywords(item.getTitle().toString().toLowerCase(), serie.getKeywords().toLowerCase()))
		        	//{
		        	SeasonEpisode se = null;
		        	DailyDate dd = null;
		        	if(!serie.isDaily)
		        		se = this.getSeasonEpisodeFromItem(item, serie, channel.getTitle().getText(), true);
		        	else
		        		dd = this.getDailyDateFromItem(item);
	        		
		        	if (se != null)
	        		{
		        		items.add(se);
	        		}
		        	
		        	if (dd != null)
	        		{
		        		items.add(dd);
	        		}
			       // }
		        }
			}
			catch (RssParserException e) 
			{
				String message = Lang.getString("TedParser.ErrorCreatingParser1") + " " + serie.getName() + //$NON-NLS-1$
				"\n" + Lang.getString("TedParser.ErrorCreatingParser2") + 
				"\n" + Lang.getString("TedParser.ErrorCreatingParser3") + " " + currentFeed.getUrl(); //$NON-NLS-1$
				//tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (MalformedURLException e) 
	        {
	        	String message = Lang.getString("TedParser.ErrorNotValidURL1") + " " +  serie.getName() + ". " + Lang.getString("TedParser.ErrorNotValidURL2") + //$NON-NLS-1$ //$NON-NLS-2$
				"\n" + currentFeed.getUrl();; //$NON-NLS-1$
				//tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (IOException e) 
			{
	        	String message = Lang.getString("TedParser.Error404Feed1") + " " + serie.getName() + 
	        	"\n" + Lang.getString("TedParser.Error404Feed2") + " "+currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				//tMainDialog.displayError(Lang.getString("TedParser.ErrorHeader"), message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
			}
	        catch (NullPointerException e)
	        {
	        	// no items in the feed
	        	String message = "ted could not find any items in the feed of " + serie.getName() + "\nFeed: " + currentFeed.getUrl(); //$NON-NLS-1$ //$NON-NLS-2$
				//tMainDialog.displayError("ted Error!", message, ""); //$NON-NLS-1$ //$NON-NLS-2$
				//return;
	        }
	        catch (Exception e)
	        {
	        	TedLog.error(e, "Unkown exception while parsing RSS feed ("+currentFeed.getUrl()+")"); //$NON-NLS-1$ //$NON-NLS-2$
	        	//return;
	        }
	   
	       
		}
		
		if(serie.isDaily)
			return removeDoublesDD(items);
		else
			return removeDoublesSE(items);
	}

	private DailyDate getDailyDateFromItem(Item item)
	{
		DailyDate dd = new DailyDate();

		dd.setPublishDate(this.tPDateChecker.getItemDate(item));
		
		String title = item.getTitle().toString().toLowerCase();
		
		String sMatch = "((\\d)+)-((\\d)+)-((\\d)+)";
		Pattern pDate = Pattern.compile(sMatch);
		Matcher mDate = pDate.matcher(title);
		
		if(mDate.find())
		{
			String match = mDate.group();
			String split[] = match.split("-");
			dd.setYear(Integer.parseInt(split[0]));
			dd.setMonth(Integer.parseInt(split[1]));
			dd.setDay(Integer.parseInt(split[2]));
		}
		
		if(dd.getYear()!=0)
			return dd;
		else 
			return null;
	}

	private SeasonEpisode getSeasonEpisodeFromItem(Item item, TedSerie serie, String text, boolean checkLatest)
	{
		SeasonEpisode se = new SeasonEpisode();

		se.setPublishDate(this.tPDateChecker.getItemDate(item));
		
		String sTitle = item.getTitle().toString();
		String sTitle_lower = sTitle.toLowerCase();
		String eTitle = sTitle_lower;
		String xTitle = sTitle_lower;
		
		String sMatch = "(season|s)(\\W)*"; //$NON-NLS-1$
		//TODO: ep with a '.' don't work?
		String eMatch = "(episode|ep|e)(\\W)*"; //$NON-NLS-1$
		String xMatch = "x"; //$NON-NLS-1$
		
		// make 2 patterns, one to match season and one to match episode
		Pattern pSeason = Pattern.compile(sMatch+"(\\d)+"); //$NON-NLS-1$
		Pattern pEpisode = Pattern.compile(eMatch+"(\\d)+"); //$NON-NLS-1$
		Pattern pX = Pattern.compile("(\\d)+" + xMatch + "(\\d)+"); //$NON-NLS-1$ //$NON-NLS-2$
		Pattern pNum = Pattern.compile("(\\d){3,4}"); //$NON-NLS-1$
		
		// match the patterns to the title
		Matcher mSeason = pSeason.matcher(sTitle_lower);
		Matcher mEpisode = pEpisode.matcher(eTitle);
		Matcher mX	= pX.matcher(xTitle);
		Matcher mNum = pNum.matcher(xTitle);
		
		// if they both are found in the title
		if (mSeason.find() && mEpisode.find())
		{			
			// get the substrings that matched
			String sSeason = mSeason.group();
			String sEpisode = mEpisode.group();
			
			//replace all found episode strings or seasonstrings with one letter so we dont confuse them
			//sSeason.replaceAll(sMatch, "s");
			//sEpisode.replaceAll(eMatch, "e");
			
			// split the title to get the integers
			String[] splitSeason = sSeason.split(sMatch);
			String[] splitEpisode = sEpisode.split(eMatch);
			
			// parse the integers
			try
			{
				se.setSeason(Integer.parseInt(splitSeason[1]));
				se.setEpisode(Integer.parseInt(splitEpisode[1]));
			}
			catch (Exception e)
			{
                TedLog.error(e, "Error parsing season (" + splitSeason[1] +") or episode (" + splitEpisode[1]+ ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				return null;
			}
		}
		else if (mX.find())
		{
			String x = mX.group();
			
			String [] splitX = x.split(xMatch);
			
			try
			{
				se.setSeason(Integer.parseInt(splitX[0]));
				se.setEpisode(Integer.parseInt(splitX[1]));
			}
			catch (Exception e)
			{
                TedLog.error(e, "Error parsing season (" + splitX[0] +") or episode (" + splitX[2]+ ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				return null;
			}
		}
		else if (mNum.find() && !checkLatest)
		{
			String x = mNum.group();
			if (x.length() == 3)
			{
				// first number is season, following two are episode
				se.setSeason(Integer.parseInt(x.substring(0, 1)));
				se.setEpisode(Integer.parseInt(x.substring(1,3)));
				
			}
			else if (x.length() == 4)
			{
				// first two numbers are season, second two numbers episode
				se.setSeason(Integer.parseInt(x.substring(0, 2)));
				se.setEpisode(Integer.parseInt(x.substring(2,4)));
			}
			
		}
		else
		{
			// nothing found
			return null;
		}
		
		if (se.getSeason() < 50 && se.getEpisode() < 50)
		{
			return se;
		}
		else
		{
			return null;
		}
	}



	private Vector removeDoublesSE(Vector seasonEpisodes)
	{
		Collections.sort(seasonEpisodes);
		
		Vector singleVector = new Vector();
		
		if (seasonEpisodes.size() > 0)
		{
			SeasonEpisode currentSE = (SeasonEpisode)seasonEpisodes.get(0);
			
			currentSE.setQuality(1);
		
			for (int i = 1; i < seasonEpisodes.size(); i++)
			{
				SeasonEpisode se = (SeasonEpisode)seasonEpisodes.get(i);
				
				if (se.compareTo(currentSE) == 0)
				{
					currentSE.setQuality(currentSE.getQuality()+1);
					
					if (se.getPublishDate().compareTo(currentSE.getPublishDate()) < 0)
					{
						currentSE.setPublishDate(se.getPublishDate());
					}
				}
				else
				{
					singleVector.add(currentSE);
					currentSE = se;
					currentSE.setQuality(1);
				}
				
			}
		}
		
		return singleVector;
	}
	
	private Vector removeDoublesDD(Vector dailyDates)
	{
		Collections.sort(dailyDates);
		
		Vector singleVector = new Vector();
		
		if (dailyDates.size() > 0)
		{
			DailyDate currentSE = (DailyDate)dailyDates.get(0);
			
			currentSE.setQuality(1);
		
			for (int i = 1; i < dailyDates.size(); i++)
			{
				DailyDate se = (DailyDate)dailyDates.get(i);
				
				if (se.compareTo(currentSE) == 0)
				{
					currentSE.setQuality(currentSE.getQuality()+1);
					
					if (se.getPublishDate().compareTo(currentSE.getPublishDate()) < 0)
					{
						currentSE.setPublishDate(se.getPublishDate());
					}
				}
				else
				{
					singleVector.add(currentSE);
					currentSE = se;
					currentSE.setQuality(1);
				}
				
			}
		}
		
		return singleVector;
	}
}
