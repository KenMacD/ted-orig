package ted;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ted.ui.addshowdialog.*;
import ted.ui.editshowdialog.EditShowDialog;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * TED: Torrent Episode Downloader (2005 - 2007)
 * 
 * This is the mainwindow of ted
 * It shows all the shows with their urls, status and more and includes menus
 * and buttons for the user to interact with ted.
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
public class TedMainDialog extends javax.swing.JFrame implements ActionListener 
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 3722636937353936684L;

	private static final double tedVersion = 0.81;
	
//	 menu images
	private ImageIcon tedProgramIcon = new ImageIcon(getClass().getClassLoader().getResource("icon-ted2.png")); //$NON-NLS-1$
	private ImageIcon tedIdleIcon = new ImageIcon(getClass().getClassLoader().getResource("icon-ted.gif")); //$NON-NLS-1$
	private ImageIcon tedActiveIcon = new ImageIcon(getClass().getClassLoader().getResource("icon-active-ted.gif")); //$NON-NLS-1$
	
	private JLabel label_count;
	private JScrollPane jScrollPane1;
	
	private TedTable serieTable;
	
	// for the series:
	//private TedConfig tConfig;
	
	private TedCounter tCounter;
	
	private TedLogDialog tLog;
	
	
	
	private TedMainToolBar TedToolBar;
	private JPanel jStatusPanel;
	
	
	private JPanel jPanel1;

	//private boolean isParsing;
	private boolean osHasTray = TedSystemInfo.osSupportsTray();
	private boolean stopParsing = false;
	private boolean isParsing = false;
	
	private boolean uiInitialized = false;
	
	private TedTablePopupMenu ttPopupMenu;
	private TedMainMenuBar tMenuBar;
	
	private TedTrayIcon tedTray;
	//


  	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Constructs a new TedMainDialog
	 * @param userWantsTray Flag if the user wants ted to add a trayicon
	 */
	public TedMainDialog(boolean userWantsTray, boolean saveInLocalDir) 
	{
		super();
		this.osHasTray = this.osHasTray && userWantsTray;
		
		// set if user wants to save / read files from local dir instead of users dir
		TedSystemInfo.setSaveInLocalDir(saveInLocalDir);
		
		// check if the java version is correct
		if (TedSystemInfo.isSupportedJavaVersion())
		{
			initGUI();
		}
		else
		{
			// show dialog that asks user to download latest javaversion
			JOptionPane.showMessageDialog(this, Lang.getString("TedMainDialog.DialogJavaVersion1") + " (" + TedSystemInfo.getJavaVersion() + ") "+ Lang.getString("TedMainDialog.DialogJavaVersion2") + " \n" + //$NON-NLS-1$
					Lang.getString("TedMainDialog.DialogJavaVersion3") + " " + TedSystemInfo.MINIMUM_JAVA + ".\n" +
					Lang.getString("TedMainDialog.DialogJavaVersion4"));
		}
	}
	
	
	
	
	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	private void initGUI() 
	{
		uiInitialized = false;
		// set look and feel to system default
		try
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e1)
		{
			TedLog.error(e1, Lang.getString("TedMainDialog.ErrorLookAndFeel")); //$NON-NLS-1$
			SwingUtilities.updateComponentTreeUI( this );
		}
		
//		 load config file
		TedIO tcio = new TedIO();
		
		try 
		{
			tcio.GetConfig();
		} 
		catch (FileNotFoundException e) 
		{
			// if no config: generate one and show config dialog
			Lang.setLanguage(TedConfig.getLocale());
			// config file is not found, ask user to input preferences
			JOptionPane.showMessageDialog(this, Lang.getString("TedMainDialog.DialogStartup1") + "\n" + //$NON-NLS-1$
					Lang.getString("TedMainDialog.DialogStartup2") + "\n" +  //$NON-NLS-1$
					Lang.getString("TedMainDialog.DialogStartup3")); //$NON-NLS-1$
			
			// set initial size of maindialog
			this.setSize(550, 400);
			this.setLocation(20, 20);
			// show configuration screen without cancel button
			
			TedConfigDialog tcd = new TedConfigDialog(this, false);
			tcd.setVisible(true);
		}
		
		Lang.setLanguage(TedConfig.getLocale());
		
		
		// only if the os is supported by the trayicon program
		// currently supports windows, linux and solaris
		
		this.osHasTray = this.osHasTray && TedConfig.isAddSysTray();
		
		if (osHasTray)
		{
			try
			{
				tedTray = new TedTrayIcon(this, tedIdleIcon);
			}
			catch (Exception e)
			{
				TedLog.error(e, "Error while adding tray icon. Disabling tray in config");
				this.osHasTray = false;
				TedConfig.setAddSysTray(false);
				TedConfig.setStartMinimized(false);
			}
		}
		
		try 
		{
			// main layout
			BoxLayout thisLayout = new BoxLayout(
				this.getContentPane(),
				javax.swing.BoxLayout.Y_AXIS);
			this.getContentPane().setLayout(thisLayout);

			// init menubar	
			tMenuBar = new TedMainMenuBar(this);
			setJMenuBar(tMenuBar);
			
			// fill panel on window
			jPanel1 = new JPanel();
			this.getContentPane().add(jPanel1);
			jPanel1.setMaximumSize(new java.awt.Dimension(32767, 20));

			// add toolbar to panel
			TedToolBar = new TedMainToolBar(this);
			jPanel1.add(TedToolBar);
			
			// add scrollpane to panel
			jScrollPane1 = new JScrollPane();
			this.getContentPane().add(jScrollPane1);
			
			//	add context menu for table
			this.ttPopupMenu = new TedTablePopupMenu(this);
			this.getContentPane().add(ttPopupMenu);
			
			// add table to scrollpanel
			serieTable = new TedTable(this, ttPopupMenu);	
			jScrollPane1.setViewportView(serieTable);
			
			
			
			// status bar
			jStatusPanel = new JPanel();
			GridLayout jStatusPanelLayout = new GridLayout(1, 1);
			jStatusPanelLayout.setColumns(3);
			jStatusPanelLayout.setHgap(5);
			jStatusPanelLayout.setVgap(5);
			jStatusPanel.setLayout(jStatusPanelLayout);
			getContentPane().add(jStatusPanel);
			jStatusPanel.setPreferredSize(new java.awt.Dimension(455, 18));
			jStatusPanel.setMaximumSize(new java.awt.Dimension(32767, 18));

			label_count = new JLabel();
			jStatusPanel.add(label_count);
			FlowLayout label_countLayout = new FlowLayout();
			label_count.setLayout(label_countLayout);
			label_count.setBounds(0, 0, 189, 14);
			label_count.setPreferredSize(new java.awt.Dimension(424, 17));		
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		this.setStatusString(Lang.getString("TedMain.LoadingConfig"));
		
		// register for mac os quit, preferences and about dialog items in menu
		if (TedSystemInfo.osIsMac())
		{
			// add mac specific action listeners to the mac menu bar
			TedMainMacListener tmml = new TedMainMacListener(this);				
		}
		
		
		this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent evt) 
				{
					tLog.setVisible(false);
					rootWindowClosing(evt);
				}
				public void windowIconified(WindowEvent evt)
				{
					tLog.setVisible(false);
					rootWindowIconified(evt);
				}
				public void windowDeiconified(WindowEvent evt)
				{
					if(!tLog.getIsClosed())
					{
						tLog.setVisible(true);
					}
					
					rootWindowDeiconified(evt);
				}
			}
		);
		
		
		
		tLog = TedLogDialog.getInstance();
		TedLog.debug(Lang.getString("TedMainDialog.LogTedStarted")); //$NON-NLS-1$
		
		
		
		//isParsing = false;
		
		// add title and icon to window
		this.setTitle(Lang.getString("TedMainDialog.WindowTitle")); //$NON-NLS-1$
		this.setIconImage(tedProgramIcon.getImage());
		
		// load the config files
		serieTable.setSeries(tcio.GetShows());

		
		tCounter = new TedCounter(this);
		
		TedFolderMonitor t = TedFolderMonitor.getInstance();
		
		// set size and position of ted
		this.setSize(TedConfig.getWidth(), TedConfig.getHeight());
		this.setLocation(TedConfig.getX(), TedConfig.getY());
		
//		 reset all previous saved statusinformation of all shows
		this.resetStatusOfAllShows();
		
		// set buttons according to selected row	
		this.updateButtonsAndMenu();
		
		if (TedConfig.isStartMinimized())
		{
			if (this.osHasTray)
			{
				this.setVisible(false);
			}
			else
			{
				this.setVisible(true);
				this.toBack();
			}
		}
		else
		{
			this.setVisible(true);
		}
		if (TedConfig.isCheckVersion())
		{
			this.setStatusString(Lang.getString("TedMain.CheckingNewTed"));
			this.isNewTed(false);
		}
		
		// if the shows.xml file does not exist download it
		File f = new File(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
		if(!f.exists())
		{
			this.setStatusString(Lang.getString("TedMain.CheckingNewShows"));
			TedIO tio = new TedIO();
			tio.downloadXML(this, TedConfig.getTimeOutInSecs(), -1);
		}
		// check to see if there is a new shows.xml file available
		else if (TedConfig.isAutoUpdateFeedList() || TedConfig.askAutoUpdateFeedList())
		{
			this.setStatusString(Lang.getString("TedMain.CheckingNewShows"));
			TedIO tio = new TedIO();
			tio.checkNewXMLFile(this, false, serieTable);
		}
		
		// start the counter
		tCounter.start();
		
		uiInitialized = true;
	}
	
	/**
	 * Update all GUI elements
	 * Mostly called when the locale of ted is changed
	 */
	private void updateGUI()
	{
		// only if UI is initialized
		if (uiInitialized)
		{
			Lang.setLanguage(TedConfig.getLocale());
			
			this.TedToolBar.updateText();
			this.serieTable.updateText();
			this.ttPopupMenu.updateText();
			tMenuBar.updateText();
			
			if (!this.isParsing)
			{
				this.resetStatusOfAllShows();
			}
			
			if (tedTray != null)
			{
				this.tedTray.updateText();
			}
			
			this.repaint();
		}
	}
	
	
	
	
	/**
	 * Updates the buttons and menu of ted according to if something is selected
	 * in the serie table
	 */
	public void updateButtonsAndMenu()
	{
		int row;
		boolean statusDelete = false;
		boolean statusEdit = false;
		row = this.serieTable.getSelectedRow();
		if (row < 0)
		{
			statusDelete = false;
			statusEdit = false;
			this.tMenuBar.setNothingSelected();
		}
		else if (!this.isParsing)
		{
			statusEdit = true;
			statusDelete = true;
			this.tMenuBar.setSomethingSelected();
		}
		else
		{
			statusEdit = true;
			statusDelete = false;
		}
		
		//this.te
		
		this.TedToolBar.setEditButtonStatus(statusEdit);
		this.TedToolBar.setDeleteButtonStatus(statusDelete);
	}




	private void rootWindowClosing(WindowEvent evt) 
	{		
		// if user has tray to minimize to
		if (this.osHasTray)
		{
			this.setVisible(false);
		}
		// else we shut down ted
		else
		{
			this.quit();
		}
	}
	
	private void rootWindowIconified(WindowEvent evt)
	{
		
	}
	
	private void rootWindowDeiconified(WindowEvent evt)
	{
		
	}
	
	
	
	/**
	 * Set the current trayicon of ted
	 * @param icon Icon to be set
	 */
	private void setIcon(ImageIcon icon) 
	{
		if (osHasTray)
		{
			tedTray.setIcon(icon);
		}	
	}
	
	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	
	/**
	 * Adds a show to the table
	 * @param currentSerie Show to add
	 */
	public void addSerie(TedSerie newSerie) 
	{
		serieTable.addSerie(newSerie);
	}
		
	/**
	 * Parses all shows listed in the table of ted
	 */
	public void parseShows() 
	{
		// first check if ted is up to date
		if(TedConfig.getTimesParsedSinceLastCheck()==5)
		{
			isNewTed(false);
			isNewPredefinedShowsXML(false);
			TedConfig.setTimesParsedSinceLastCheck(0);
		}
		else
		{
			TedConfig.setTimesParsedSinceLastCheck(TedConfig.getTimesParsedSinceLastCheck()+1);
		}
		
		// set parsing flag and trayicon
		//isParsing = true;
		//this.setIcon(tedActiveIcon);
		this.setStatusToParsing();
		
		TedParser tp = new TedParser();
		int rows = serieTable.getRowCount();
		for (int i = 0; i < rows ; i++)
		{
			if (this.stopParsing)
			{
				this.setStatusToIdle();
				return;
			}
			TedSerie serie = serieTable.getSerieAt(i);
			
			// if it is the day to start checking the serie again
			serie.checkDate();
			
			// if the serie is not paused
			if(serie.isCheck())
			{
				serie.setActivity(TedSerie.IS_PARSING);
				tp.ParseSerie(serie, this);
			}
			serie.setActivity(TedSerie.IS_IDLE);
			serieTable.tableUpdate();
		}

		// set flags and icon back
		//isParsing = false;
		this.setStatusToIdle();
	}
	
	
	/**
	 * Update the countertext in the mainwindow
	 * @param count Number of minutes left to next parserrround
	 */
	public void updateCounter(int count) 
	{
		if (count == 0)
		{
			this.label_count.setText(Lang.getString("TedMainDialog.StatusBarChecking")); //$NON-NLS-1$
		}
		else if (count == 1)
		{
			this.label_count.setText(Lang.getString("TedMainDialog.StatusBarLessThan1Minute")); //$NON-NLS-1$
		}
		else
		{
			this.label_count.setText(Lang.getString("TedMainDialog.StatusBarNextCheckInStart") + " " + count + " " + Lang.getString("TedMainDialog.StatusBarNextCheckInEnd")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		this.repaint();
		
	}
	
	/**
	 * Display a balloon/alert to inform the user
	 * @param header Header of the message
	 * @param message Message to the user
	 */
	public void displayBalloon(String header, String message)
	{
		if (osHasTray && TedSystemInfo.osSupportsBalloon())
		{
			// display a message above the system tray
			// TODO: als er meerdere achter elkaar worden aangeroepen zie je alleen de 1e
			if (header.equals("")) //$NON-NLS-1$
			{
				header = Lang.getString("TedMainDialog.BalloonDefautHeader"); //$NON-NLS-1$
			}
			tedTray.displayMessage(header, message, 500);
		}
		else
		{
			// instead of showing a balloon, we show an alert
			// TODO: this messagedialog stops ted until you close it again :(
			JOptionPane.showMessageDialog(this, message, header,  JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	
	/**
	 * Alert the user of an error that happened while running ted
	 * @param header Header of the errormessage
	 * @param message Body of the errormessage
	 * @param details Details that are posted in the logwindow only
	 */
	public void displayError(String header, String message, String details)
	{
		if (TedConfig.isShowErrors())
		{
			this.displayBalloon(header, message);
		}
		TedLog.error(message+"\n"+details); //$NON-NLS-1$
	}
	
	/**
	 * Show the user that ted found an episode of a specific show
	 * @param header Header of the message
	 * @param message Body of the message
	 * @param details Details that are posted to the logwindow only
	 */
	public void displayHurray(String header, String message, String details)
	{
		if (TedConfig.isShowHurray())
		{
			this.displayBalloon(header, message);
		}
		TedLog.debug(message+"\n"+details); //$NON-NLS-1$
	}
		
	public void actionPerformed(ActionEvent e)
	{
		// handles all the events on the ted mainwindow
		String action = e.getActionCommand();
		
		if(action.equals("Preferences...")) //$NON-NLS-1$
		{
			// show ted config
			TedConfigDialog tcd = new TedConfigDialog(this, true);
			tcd.setVisible(true);
		}
		else if(action.equals("Log")) //$NON-NLS-1$
		{
			// show log
			tLog.setIsClosed(false);
			tLog.setLines(tLog.getMaxLines()); //Log is created before reading the config
			tLog.resetAction();
			tLog.setVisible(true);
		}
		else if(action.equals("Delete")) //$NON-NLS-1$
		{
			serieTable.DeleteSelectedShow();
		}
		else if(action.equals("New")) //$NON-NLS-1$
		{
			// create a new serie and show it in the episode dialog
			/*TedSerie newSerie = new TedSerie(1, 1, "", "", 0, 0, "", 0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			TedEpisodeDialog henk = new TedEpisodeDialog(this, newSerie, true);
			henk.setVisible(true);*/
			AddShowDialog henk = new AddShowDialog(this);
			henk.setVisible(true);
		}
		else if(action.equals("Watcher")) //$NON-NLS-1$
		{
			// create a new watcher and show it
			TedWatcher watcher = new TedWatcher("", "", 0, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			TedWatcherDialog dialog = new TedWatcherDialog(this, watcher, true);
			dialog.setVisible(true);
		}
		else if(action.equals("Exit")) //$NON-NLS-1$
		{
			this.quit();
		}
		else if(action.equals("Edit")) //$NON-NLS-1$
		{
			// get the selected show and open a episode dialog for it
			int pos = serieTable.getSelectedRow();
			if (pos >= 0)
			{
				TedSerie selectedserie = serieTable.getSerieAt(pos);
				EditShowDialog henk = new EditShowDialog(this, selectedserie, false);
				henk.setVisible(true);
			}
		}
		else if (action.equals("parse selected")) //$NON-NLS-1$
		{
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			this.setCursor(hourglassCursor);
			
			// parse only the selected show, regardles of the status it has
			int pos = serieTable.getSelectedRow();
			
			if (pos >= 0)
			{
				TedSerie selectedserie = serieTable.getSerieAt(pos);
				this.setStatusToParsing();
				
				
				TedParser tp = new TedParser();
				tp.ParseSerie(selectedserie, this);
				
				serieTable.tableUpdate();	
				
				this.setStatusToIdle();
			}
			
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setCursor(normalCursor);
			
		}
		else if (action.equals("setstatuscheck")) //$NON-NLS-1$
		{
			serieTable.setSelectedStatus(TedSerie.STATUS_CHECK);
			this.saveShows();
		}
		else if (action.equals("setstatuspause")) //$NON-NLS-1$
		{
			serieTable.setSelectedStatus(TedSerie.STATUS_PAUSE);
			this.saveShows();
		}
		else if (action.equals("setstatushold")) //$NON-NLS-1$
		{
			serieTable.setSelectedStatus(TedSerie.STATUS_HOLD);
			this.saveShows();
		}
		else if (action.equals("checkupdates")) //$NON-NLS-1$
		{
			this.isNewTed(true);
		}
		else if (action.equals("checkRSS")) //$NON-NLS-1$
		{
			this.isNewPredefinedShowsXML(true);
		}
		else if (action.equals("help")) //$NON-NLS-1$
		{
			// try to open the ted documentation website
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/documentation/"); //$NON-NLS-1$
			} 
			catch (IOException ep) 
			{
				// error launching ted website
				// TODO: add error message
				System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
				ep.printStackTrace();
			}	
		}
		else if (action.equals("opensite")) //$NON-NLS-1$
		{
			// try to open the ted website
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/"); //$NON-NLS-1$
			} 
			catch (IOException ep) 
			{
				// error launching ted website
				// TODO: add error message
				System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
				ep.printStackTrace();
			}			
		}
		else if (action.equals("Donate")) //$NON-NLS-1$
		{
			// try to open the ted website
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/donate.php"); //$NON-NLS-1$
			} 
			catch (IOException ep) 
			{
				// error launching ted website
				// TODO: add error message
				System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
				ep.printStackTrace();
			}			
		}
		else if(action.equals("PressAction")) //$NON-NLS-1$
		{
			this.setVisible(true);
			this.toFront();
			if(!tLog.getIsClosed())
			{
				tLog.setVisible(true);
			}
		}
		else if(action.equals("Help")) //$NON-NLS-1$
		{
			// show logwindow
			tLog.setVisible(true);
		}
		else if(action.equals("Parse")) //$NON-NLS-1$
		{
			// parse all shows
			//this.parseShows();
			this.tCounter.setCount(0);
		}
		else if(action.equals("stop parsing")) //$NON-NLS-1$
		{
			this.stopParsing = true;
			this.TedToolBar.setParseButtonStatus(false);
			this.TedToolBar.setParseButtonText(Lang.getString("TedMainDialog.ButtonCheckShowsStopping"));
			// TODO: breakin in current loop
		}
		else if(action.equals("About ted")) //$NON-NLS-1$
		{
			this.showAboutDialog();
		}
		else if(action.equals("Export")) //$NON-NLS-1$
		{
			Vector series = serieTable.getSeries();
			TedXMLWriter writer = new TedXMLWriter(series);
		}
		else if(action.equals("synchronize")) //$NON-NLS-1$
		{
			TedIO tio = new TedIO();
			tio.UpdateShow(this, false, serieTable);
			serieTable.fireTableDataChanged();
		}
	}
	
	public void showAboutDialog() 
	{
		// show about window
		TedAboutDialog tAbout = new TedAboutDialog(tedVersion);
		tAbout.setVisible(true);	
	}
	
	public void showPreferencesDialog()
	{
		// show ted config
		TedConfigDialog tcd = new TedConfigDialog(this, true);
		tcd.setVisible(true);	
	}
	
	public void quit()
	{
		// close ted
		this.saveShows();
		this.saveConfig(false);
		System.exit(0);
	}	

	/**
	 * Saves the shows that are in displayed in the ted window
	 */
	public void saveShows() 
	{
		TedIO tcio = new TedIO();
		tcio.SaveShows(serieTable.getSeries());	
		this.repaint();
	}
	
	/**
	 * Save the configuration of ted
	 * @param resetTime Parse interval
	 */
	public void saveConfig(boolean resetTime) 
	{
		TedIO tcio = new TedIO();
		
		// set the current width, heigth and position of the window
		TedConfig.setWidth(this.getWidth());
		TedConfig.setHeight(this.getHeight());
		TedConfig.setX(this.getX());
		TedConfig.setY(this.getY());
		
		// save
		tcio.SaveConfig();
		
		this.updateGUI();
		
		// notify counter of refreshtime change
		if (resetTime && tCounter.isAlive())
		{
			tCounter.updateRefreshTime();
		}
		
	}	
	
	/**
	 * Check if there is a new version of ted available
	 * @param show Show the user if he has the current version
	 */
	public void isNewTed(boolean show)
	{
		// check the website if there is a new version availble
		TedIO tio = new TedIO();
		double currentVersion = tio.checkNewTed(TedMainDialog.tedVersion, TedConfig.getTimeOutInSecs());
		
		if (currentVersion > TedMainDialog.tedVersion)
		{
			int answer = JOptionPane.showOptionDialog(this,
	                Lang.getString("TedMainDialog.DialogNewVersion1Begin")+ " (" + currentVersion + ") "+ Lang.getString("TedMainDialog.DialogNewVersion1End") + //$NON-NLS-1$ //$NON-NLS-2$
	                "\n" + Lang.getString("TedMainDialog.DialogNewVersion2")+ " " +  TedMainDialog.tedVersion + "\n" +  //$NON-NLS-1$
									Lang.getString("TedMainDialog.DialogNewVersion3"), //$NON-NLS-1$
	                Lang.getString("TedMainDialog.DialogNewVersionHeader"), //$NON-NLS-1$
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE, null, Lang.getYesNoLocale(), Lang.getYesNoLocale()[0]);
			
			if (answer == JOptionPane.YES_OPTION)
			{	
				// launch ted website in browser
				try 
				{
					BrowserLauncher.openURL("http://www.ted.nu/download.php"); //$NON-NLS-1$
				} 
				catch (IOException e) 
				{
					// error launching ted website
				}
				return;
			}
		}
		else if (show)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedMainDialog.DialogLatestVersionBegin")
					 + " ("+ TedMainDialog.tedVersion + ") " + 
					 Lang.getString("TedMainDialog.DialogLatestVersionEnd")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
	}
	
	public void isNewPredefinedShowsXML(boolean show)
	{
		TedIO tio = new TedIO();
		tio.checkNewXMLFile(this, show, serieTable);
	}
	
	/**
	 *  Reset all statusmessages for all series in the window to the default
	 */
	private void resetStatusOfAllShows()
	{
		int rows = serieTable.getRowCount();
		for (int i = 0; i < rows ; i++)
		{
			TedSerie serie = serieTable.getSerieAt(i);
			serie.resetStatus();
		}
		this.repaint();		
	}
	
		
	/****************************************************
	 * GETTERS & SETTERS
	 ****************************************************/
	/**
	 * DEPRECATED
	 * @return Current TedConfig
	 */
	
	/*
	public TedConfig getConfig()
	{
		return tConfig;
	}
	*/
	
	/*public TedTableModel getSerieTableModel()
	{
		return serieTableModel;
	}*/
	
	/**
	 * Set the GUI elements to parsing, disable delete button and menuitem
	 * and change parsing button to "stop parsing"
	 */
	public void setStatusToParsing()
	{
		this.isParsing = true;
		
		// set icon
		this.setIcon(tedActiveIcon);
		
		// disable delete buttons and menu items
		updateButtonsAndMenu();

		
		this.tMenuBar.setParsing();		
		this.ttPopupMenu.setParsing();
		this.TedToolBar.setParsing();
	}
	
	/**
	 * Set the status of the GUI to idle. So ted is not parsing
	 */
	public void setStatusToIdle()
	{
		this.isParsing = false;
		
		// set icon
		this.setIcon(tedIdleIcon);
		this.resetStatusOfAllShows();
		this.stopParsing = false;
		
		// enable buttons and menu items
		this.tMenuBar.setIdle();
		this.ttPopupMenu.setIdle();
		this.TedToolBar.setIdle();
		updateButtonsAndMenu();	
	}
	
	/**
	 * @return Wheter the user has clicked the stop-parsing button
	 */
	public boolean getStopParsing()
	{
		return this.stopParsing;
	}
	
	/**
	 * Update the status bar with a new text
	 * @param status
	 */
	public void setStatusString(String status)
	{
		this.label_count.setText(status);
	}
}
