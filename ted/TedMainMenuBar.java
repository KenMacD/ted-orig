package ted;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;





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
	private JMenuItem statusDisabled;
	private JMenuItem versionItem;
	private JMenuItem donateItem;
	private JMenuItem webItem;
	private JMenuItem synchronizeMenuItem;
	private JMenuItem RSSItem;
	private JMenuItem aboutItem;
	private JMenu supportMenu;
	private JMenuItem buyDVDItem;
	private JMenuItem translateItem;
	private JMenuItem languageItem;
	
	private JMenu subUpdateMenu;
	private JCheckBoxMenuItem sortDescendingRadioItem;
	private JCheckBoxMenuItem sortAscendingRadioItem;
	private JSeparator jSeparator3;
	private JCheckBoxMenuItem sortOnStatusItem;
	private JCheckBoxMenuItem sortOnNameItem;
	private JMenu sortMenuItem;
	private JSeparator jSeparator2;
	private JMenu subLangMenu;
	
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
			
			// Disabled this menu item because it's rather useless at
			// the moment. Left it in the code because maybe we want
			// to use it again some day.
			//jMenu3.add(exportMenuItem);
			
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
				ImageIcon showPaused 	= new ImageIcon(getClass().getClassLoader().getResource("icons/pause.png")); //$NON-NLS-1$
				ImageIcon showPlay		 = new ImageIcon(getClass().getClassLoader().getResource("icons/play.png")); //$NON-NLS-1$
				ImageIcon showStopped	 = new ImageIcon(getClass().getClassLoader().getResource("icons/stop.png")); //$NON-NLS-1$
				ImageIcon showDisabled    = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-ted.gif")); //$NON-NLS-1$
				
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
				
				statusDisabled = new JMenuItem (); //$NON-NLS-1$
				statusDisabled.addActionListener(tMain);
				statusDisabled.setActionCommand("setstatusdisabled"); //$NON-NLS-1$
				statusDisabled.setIcon(showDisabled);
				
				menuStatus.add(statusCheck);
				menuStatus.add(statusPause);
				menuStatus.add(statusHold);
				menuStatus.add(statusDisabled);
				jMenu4.add(menuStatus);
			}
			{
				jSeparator2 = new JSeparator();
				jMenu4.add(jSeparator2);
				sortMenuItem = new JMenu();
				jMenu4.add(sortMenuItem);
				sortMenuItem.setText("Sort shows");
				{
					sortOnStatusItem = new JCheckBoxMenuItem();
					sortMenuItem.add(sortOnStatusItem);
					sortOnStatusItem.setText("Sort on status and airdate");
					sortOnStatusItem.addActionListener(tMain);
					sortOnStatusItem.setActionCommand("sort_status");
				}
				{
					sortOnNameItem = new JCheckBoxMenuItem();
					sortMenuItem.add(sortOnNameItem);
					sortOnNameItem.setText("Sort on name");
					sortOnNameItem.addActionListener(tMain);
					sortOnNameItem.setActionCommand("sort_name");
				}
				{
					jSeparator3 = new JSeparator();
					sortMenuItem.add(jSeparator3);
				}
				{
					sortAscendingRadioItem = new JCheckBoxMenuItem();
					sortMenuItem.add(sortAscendingRadioItem);
					sortAscendingRadioItem.setText("Sort ascending");
					sortAscendingRadioItem.addActionListener(tMain);
					sortAscendingRadioItem.setActionCommand("sort_ascending");
				}
				{
					sortDescendingRadioItem = new JCheckBoxMenuItem();
					sortMenuItem.add(sortDescendingRadioItem);
					sortDescendingRadioItem.setText("Sort descending");
					sortDescendingRadioItem.addActionListener(tMain);
					sortDescendingRadioItem.setActionCommand("sort_descending");
				}
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
		{
		}

		// support menu
		supportMenu = new JMenu();
		this.add(supportMenu);
		
		// buy dvd item
		buyDVDItem = new JMenuItem();
		buyDVDItem.addActionListener(tMain);
		buyDVDItem.setActionCommand("buydvd");
		supportMenu.add(buyDVDItem);
		
		//donate!!
		donateItem = new JMenuItem();
		supportMenu.add(donateItem);
		donateItem.setActionCommand("Donate"); //$NON-NLS-1$
		donateItem.addActionListener(tMain);
		
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
			// update submenus
			subUpdateMenu = new JMenu();
			subLangMenu = new JMenu();
			jMenu5.add(subUpdateMenu);
			jMenu5.add(subLangMenu);
			// Check for updated
			versionItem = new JMenuItem();
			subUpdateMenu.add(versionItem);
			versionItem.addActionListener(tMain);
			versionItem.setActionCommand("checkupdates"); //$NON-NLS-1$
			// Check RSS updates
			RSSItem = new JMenuItem();
			subUpdateMenu.add(RSSItem);
			RSSItem.addActionListener(tMain);
			RSSItem.setActionCommand("checkRSS"); //$NON-NLS-1$
			// Synchronize show.xml with current shows
			synchronizeMenuItem = new JMenuItem();
			subUpdateMenu.add(synchronizeMenuItem);
			synchronizeMenuItem.addActionListener(tMain);
			synchronizeMenuItem.setActionCommand("synchronize"); //$NON-NLS-1$
			// Translate ted
			translateItem = new JMenuItem();
			subLangMenu.add(translateItem);
			translateItem.addActionListener(tMain);
			translateItem.setActionCommand("translate");
			// Get latest language version
			languageItem = new JMenuItem();
			subLangMenu.add(languageItem);
			languageItem.addActionListener(tMain);
			languageItem.setActionCommand("language");
			// seperate
			jSeparator1 = new JSeparator();
			jMenu5.add(jSeparator1);
			// open ted website
			webItem = new JMenuItem();
			webItem.setActionCommand("opensite"); //$NON-NLS-1$
			webItem.addActionListener(tMain);
			jMenu5.add(webItem);
			if (!TedSystemInfo.osIsMac())
			{
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
		}
		this.updateText();
		this.updateSortMenu();
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
			aboutItem.setText(Lang.getString("TedMainMenuBar.Help.AboutTed")); //$NON-NLS-1$
		}
		
		// edit menu
		jMenu4.setText(Lang.getString("TedMainMenuBar.Edit")); //$NON-NLS-1$
		editMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Edit")); //$NON-NLS-1$
		deleteMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Delete")); //$NON-NLS-1$
		
		menuStatus.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus"));
		
		statusCheck.setText (Lang.getString("TedMainMenuBar.Edit.SetStatus.Check")); //$NON-NLS-1$		
		statusPause.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Pause")); //$NON-NLS-1$
		statusHold.setText(Lang.getString("TedMainMenuBar.Edit.SetSTatus.Hold")); //$NON-NLS-1$
		statusDisabled.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Disabled"));
		
		sortMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.Sort"));	
		sortOnStatusItem.setText(Lang.getString("TedMainMenuBar.Edit.Sort.OnStatus"));
		sortOnNameItem.setText(Lang.getString("TedMainMenuBar.Edit.Sort.OnName"));
		sortAscendingRadioItem.setText(Lang.getString("TedMainMenuBar.Edit.Sort.Ascending"));
		sortDescendingRadioItem.setText(Lang.getString("TedMainMenuBar.Edit.Sort.Descending"));
		
		logMenuItem.setText(Lang.getString("TedMainMenuBar.Edit.ShowLog")); //$NON-NLS-1$
		
		// support menu
		supportMenu.setText(Lang.getString("TedMainMenuBar.Support"));
		buyDVDItem.setText(Lang.getString("TedMainMenuBar.Support.BuyDVDs"));
		
		// help menu
		jMenu5.setText(Lang.getString("TedMainMenuBar.Help")); //$NON-NLS-1$
		helpMenuItem.setText(Lang.getString("TedMainMenuBar.Help.Help")); //$NON-NLS-1$
	
		versionItem.setText(Lang.getString("TedMainMenuBar.Help.CheckForUpdates")); //$NON-NLS-1$
	
		RSSItem.setText(Lang.getString("TedMainMenuBar.Help.CheckShowDefinitions")); //$NON-NLS-1$
	
		synchronizeMenuItem.setText(Lang.getString("TedMainMenuBar.Help.SynchronizeShows")); //$NON-NLS-1$
	
		translateItem.setText(Lang.getString("TedMainMenuBar.Help.Translate"));
		
		webItem.setText(Lang.getString("TedMainMenuBar.Help.TedOnline")); //$NON-NLS-1$
	
		this.donateItem.setText(Lang.getString("TedMainMenuBar.Help.Donate")); //$NON-NLS-1$
		
	
		subUpdateMenu.setText(Lang.getString("TedMainMenuBar.Help.SubSoftware"));
		
		subLangMenu.setText(Lang.getString("TedMainMenuBar.Help.SubTranslate"));
		
		languageItem.setText(Lang.getString("TedMainMenuBar.Help.LanguageUpdate"));
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
	
	public void updateSortMenu()
	{
		// update sort type menu
		boolean isSortOnName = (TedConfig.getSortType() == TedConfig.SORT_NAME);
		boolean isSortOnStatus = (TedConfig.getSortType() == TedConfig.SORT_STATUS);
		
		this.sortOnNameItem.setSelected(isSortOnName);
		this.sortOnStatusItem.setSelected(isSortOnStatus);
		
		// update sort direction menu
		boolean isSortAscending = (TedConfig.getSortDirection() == TedConfig.SORT_ASCENDING);
		boolean isSortDescending = (TedConfig.getSortDirection() == TedConfig.SORT_DESCENDING);
		
		this.sortAscendingRadioItem.setSelected(isSortAscending);
		this.sortDescendingRadioItem.setSelected(isSortDescending);
		
	}
}
