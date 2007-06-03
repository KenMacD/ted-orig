package ted;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;




/**
 * @author roel
 * Main menu bar for Ted
 */
public class TedMainMenuBar extends JMenuBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8573600372750247532L;
	// menu items
	private JMenuItem helpMenuItem;
	private JMenu jMenu5;
	private JMenuItem deleteMenuItem;
	private JMenuItem editMenuItem;
	private JSeparator jSeparator1;
	private JMenuItem cutMenuItem;
	private JMenuItem logMenuItem;
	private JMenu jMenu4;
	private JMenuItem exitMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JMenuItem exportMenuItem;
	private JMenu menuStatus;
	private JMenuItem statusCheck;
	private JMenuItem statusHold;
	private JMenuItem statusPause;
	private JMenuItem versionItem;
	private JMenuItem donateItem;
	private JMenuItem webItem;
	private JMenuItem synchronizeMenuItem;
	private JMenuItem RSSItem;
	private JMenuItem aboutItem;
	
	private TedMainDialog tMain;
	
	/**
	 * Main menubar for ted
	 * @param main MainDialog that this bar is added to
	 */
	public TedMainMenuBar(TedMainDialog main)
	{
		this.tMain = main;
		
		this.initMenu();
	}
	
	public void initMenu()
	{
//		 File Menu
		jMenu3 = new JMenu();
		this.add(jMenu3);
		
		{
			newFileMenuItem = new JMenuItem();
			exportMenuItem	= new JMenuItem();
			// add show
			jMenu3.add(newFileMenuItem);
			
			newFileMenuItem.addActionListener(tMain);
			newFileMenuItem.setActionCommand("New"); //$NON-NLS-1$
			jMenu3.add(exportMenuItem);
			
			exportMenuItem.setActionCommand("Export"); //$NON-NLS-1$
			exportMenuItem.addActionListener(tMain);
			if (!TedSystemInfo.osIsMac())
			{
				// seperate
				jSeparator1 = new JSeparator();
				jMenu3.add(jSeparator1);
				// exit
				exitMenuItem = new JMenuItem();
				jMenu3.add(exitMenuItem);
				
				exitMenuItem.setActionCommand("Exit");
				exitMenuItem.addActionListener(tMain);
			}
		}
		// Edit Menu
		jMenu4 = new JMenu();
		this.add(jMenu4);
		
		{
			// edit..
			editMenuItem = new JMenuItem();
			jMenu4.add(editMenuItem);
			
			editMenuItem.setActionCommand("Edit"); //$NON-NLS-1$
			editMenuItem.addActionListener(tMain);
			// delete
			deleteMenuItem = new JMenuItem();
			jMenu4.add(deleteMenuItem);
			
			deleteMenuItem.setActionCommand("Delete");
			deleteMenuItem.addActionListener(tMain);
			// set status
			/*TODO: Duplicated code: same as TedTablePopupMenu*/
			{
				ImageIcon showPaused 	= new ImageIcon(getClass().getClassLoader().getResource("pause.png")); //$NON-NLS-1$
				ImageIcon showPlay		 = new ImageIcon(getClass().getClassLoader().getResource("play.png")); //$NON-NLS-1$
				ImageIcon showStopped	 = new ImageIcon(getClass().getClassLoader().getResource("stop.png")); //$NON-NLS-1$
				
				menuStatus = new JMenu(  ); //$NON-NLS-1$
				
				
				statusCheck = new JMenuItem (); //$NON-NLS-1$
				statusCheck.addActionListener(tMain);
				statusCheck.setActionCommand("setstatuscheck"); //$NON-NLS-1$
				statusCheck.setIcon(showPlay);
				
				statusPause = new JMenuItem (); //$NON-NLS-1$
				statusPause.addActionListener(tMain);
				statusPause.setActionCommand("setstatuspause"); //$NON-NLS-1$
				statusPause.setIcon(showPaused);
				
				statusHold = new JMenuItem (); //$NON-NLS-1$
				statusHold.addActionListener(tMain);
				statusHold.setActionCommand("setstatushold"); //$NON-NLS-1$
				statusHold.setIcon(showStopped);
				
				menuStatus.add(statusCheck);
				menuStatus.add(statusPause);
				menuStatus.add(statusHold);
				jMenu4.add(menuStatus);
			}
			
			// seperator
			jSeparator1 = new JSeparator();
			jMenu4.add(jSeparator1);
			// show log	
			logMenuItem = new JMenuItem();
			jMenu4.add(logMenuItem);
			logMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.ShowLog")); //$NON-NLS-1$
			logMenuItem.setActionCommand("Log"); //$NON-NLS-1$
			logMenuItem.addActionListener(tMain);
			if (!TedSystemInfo.osIsMac())
			{
				jSeparator1 = new JSeparator();
				jMenu4.add(jSeparator1);
				// preferences
				cutMenuItem = new JMenuItem();
				jMenu4.add(cutMenuItem);
				cutMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Preferences")); //$NON-NLS-1$
				cutMenuItem.setActionCommand("Preferences...");
				cutMenuItem.addActionListener(tMain);
			}
		}
		
		// Help Menu
		jMenu5 = new JMenu();
		this.add(jMenu5);
		{
			helpMenuItem = new JMenuItem();
			// help
			jMenu5.add(helpMenuItem);
			helpMenuItem.addActionListener(tMain);
			helpMenuItem.setActionCommand("help"); //$NON-NLS-1$
			jMenu5.add(helpMenuItem);
			// Seperator
			jSeparator1 = new JSeparator();
			jMenu5.add(jSeparator1);
			// Check for updated
			versionItem = new JMenuItem();
			jMenu5.add(versionItem);
			versionItem.addActionListener(tMain);
			versionItem.setActionCommand("checkupdates"); //$NON-NLS-1$
			// Check RSS updates
			RSSItem = new JMenuItem();
			jMenu5.add(RSSItem);
			RSSItem.addActionListener(tMain);
			RSSItem.setActionCommand("checkRSS"); //$NON-NLS-1$
			// Synchronize show.xml with current shows
			synchronizeMenuItem = new JMenuItem();
			jMenu5.add(synchronizeMenuItem);
			synchronizeMenuItem.addActionListener(tMain);
			synchronizeMenuItem.setActionCommand("synchronize"); //$NON-NLS-1$
			// seperate
			jSeparator1 = new JSeparator();
			jMenu5.add(jSeparator1);
			// open ted website
			webItem = new JMenuItem();
			webItem.setActionCommand("opensite"); //$NON-NLS-1$
			webItem.addActionListener(tMain);
			jMenu5.add(webItem);
			// donate!!
			donateItem = new JMenuItem();
			jMenu5.add(donateItem);
			donateItem.setActionCommand("Donate"); //$NON-NLS-1$
			donateItem.addActionListener(tMain);
			// sperate
			jSeparator1 = new JSeparator();
			jMenu5.add(jSeparator1);
			// about ted
			aboutItem = new JMenuItem();
			jMenu5.add(aboutItem);
			aboutItem.setText(Lang.getString("TedMainMenuBar.Help.AboutTed")); //$NON-NLS-1$
			aboutItem.setActionCommand("About ted"); //$NON-NLS-1$
			aboutItem.addActionListener(tMain);
		}
		this.updateText();
	}
	
	public void updateText()
	{
		jMenu3.setText(Lang.getString("TedMainMenuBar.File")); //$NON-NLS-1$
		newFileMenuItem.setText(Lang.getString("TedMainMenuBar.File.NewShow")); //$NON-NLS-1$
		exportMenuItem.setText(Lang.getString("TedMainMenuBar.File.ExportToXML")); //$NON-NLS-1$
		if (!TedSystemInfo.osIsMac())
		{
			exitMenuItem.setText(Lang.getString("TedMainMenuBar.File.Exit")); //$NON-NLS-1$
			cutMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Preferences")); //$NON-NLS-1$
		}
		
		// edit menu
		jMenu4.setText(Lang.getString("TedMainMenuBar.Edit")); //$NON-NLS-1$
		editMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Edit")); //$NON-NLS-1$
		deleteMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Delete")); //$NON-NLS-1$
		
		menuStatus.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus"));
		
		statusCheck.setText (Lang.getString("TedMainMenuBar.Edit.SetStatus.Check")); //$NON-NLS-1$
		
		statusPause.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Pause")); //$NON-NLS-1$

		
		statusHold.setText(Lang.getString("TedMainMenuBar.Edit.SetSTatus.Hold")); //$NON-NLS-1$
		
		logMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.ShowLog")); //$NON-NLS-1$
		
		
		// help menu
		jMenu5.setText(Lang.getString("TedMainMenuBar.Help")); //$NON-NLS-1$
		helpMenuItem.setText(Lang.getString("TedMainMenuBar.Help.Help")); //$NON-NLS-1$
	
		versionItem.setText(Lang.getString("TedMainMenuBar.Help.CheckForUpdates")); //$NON-NLS-1$
	
		RSSItem.setText(Lang.getString("TedMainMenuBar.Help.CheckShowDefinitions")); //$NON-NLS-1$
	
		synchronizeMenuItem.setText(Lang.getString("TedMainMenuBar.Help.SynchronizeShows")); //$NON-NLS-1$
	
		webItem.setText(Lang.getString("TedMainMenuBar.Help.TedOnline")); //$NON-NLS-1$
	
		donateItem.setText(Lang.getString("TedMainMenuBar.Help.Donate")); //$NON-NLS-1$
		aboutItem.setText(Lang.getString("TedMainMenuBar.Help.AboutTed")); //$NON-NLS-1$
	
		
	}
	
	/**
	 * Disable all menuitems that can't be used while parsing
	 */
	public void setParsing()
	{
		this.deleteMenuItem.setEnabled(false);
	}
	/**
	 * Enable all menuitems that can be used while idleing.
	 */
	public void setIdle()
	{
		this.deleteMenuItem.setEnabled(true);
	}

	/**
	 * Disable all menuitems that can't be used while nothing is selected in the table
	 */
	public void setNothingSelected()
	{
		this.deleteMenuItem.setEnabled(false);
		this.editMenuItem.setEnabled(false);
		this.menuStatus.setEnabled(false);
	}
	/**
	 * Enable all menuitems that can be used while something is selected.
	 */
	public void setSomethingSelected()
	{
		this.deleteMenuItem.setEnabled(true);
		this.editMenuItem.setEnabled(true);
		this.menuStatus.setEnabled(true);
	}
}
