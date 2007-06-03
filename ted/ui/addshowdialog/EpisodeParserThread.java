package ted.ui.addshowdialog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.JEditorPane;

import org.w3c.dom.Element;

import ted.TedDailySerie;
import ted.TedIO;
import ted.TedParser;
import ted.TedSerie;
import ted.TedXMLParser;
import ted.datastructures.SimpleTedSerie;

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
	private SimpleTedSerie selectedRow;
	private AddShowDialog addShowDialog;
	private JEditorPane showInfoPane;
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new counter
	 * @param m the TedMainDialog
	 * @param tc the current config
	 */
	public EpisodeParserThread(AddShowDialog asd, EpisodeChooserPanel ecp, SimpleTedSerie serie, JEditorPane ip)
	{
		this.episodeChooserPanel = ecp;
		this.selectedRow = serie;
		this.addShowDialog = asd;
		this.episodeChooserPanel.setActivityStatus(true);
		this.episodeChooserPanel.clear();
		this.showInfoPane = ip;
		
		

	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	public void run()
	{
		
		showInfoPane.setText("");
		TedXMLParser parser = new TedXMLParser();
		Element series = parser.readXMLFile(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
		
		TedSerie selectedSerie = parser.getSerie(series, selectedRow.getName());

		if(selectedSerie!=null)
		{	
			if (selectedSerie.getTVcom() != "" && selectedSerie.getTVcom() != null)
			{
				URL url;
				try
				{
					url = new URL("http://www.rulecam.net/ted/showinfo.php?tvcom="+selectedSerie.getTVcom());
					showInfoPane.setPage(url);
					//this.addShowDialog.repaint();
				} catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					showInfoPane.setText("Error retrieving show information. Malformed URL.");
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					showInfoPane.setText("Error retrieving show information. Cannot read information page.");
					e.printStackTrace();
				}
			}
			else
			{
				showInfoPane.setText("No show information available");
			}
			TedParser showParser = new TedParser();
			Vector seasonEpisodes = showParser.getItems(selectedSerie);
			this.episodeChooserPanel.setSeasonEpisodes(seasonEpisodes);
			this.addShowDialog.setSelectedSerie(selectedSerie);
		}
		
		this.episodeChooserPanel.setActivityStatus(false);
	
	}
}
