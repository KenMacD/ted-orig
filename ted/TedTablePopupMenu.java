package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is popup menu that we show when a user clicks the right button on the table
 * in the mainwindow of ted
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

public class TedTablePopupMenu extends JPopupMenu
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 6924907434718003055L;
	private TedMainDialog mainDialog;
	private ImageIcon showPaused   = new ImageIcon(getClass().getClassLoader().getResource("icons/pause.png")); //$NON-NLS-1$
	private ImageIcon showPlay	   = new ImageIcon(getClass().getClassLoader().getResource("icons/play.png")); //$NON-NLS-1$
	private ImageIcon showStopped  = new ImageIcon(getClass().getClassLoader().getResource("icons/stop.png")); //$NON-NLS-1$
	private ImageIcon showDisabled = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-ted.gif")); //$NON-NLS-1$
	private JMenuItem menuDelete;
	private JMenuItem menuEdit;
	private JMenuItem buyDVD;
	private JMenuItem menuParse;
	private JMenu menuStatus;
	private JMenuItem statusCheck;
	private JMenuItem statusPause;
	private JMenuItem statusHold;
	private JMenuItem statusDisabled;
	private JCheckBoxMenuItem checkAutoSchedule;

	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Create a new table popup menu
	 * @param main Mainwindow where we want to add the popup menu
	 */
	public TedTablePopupMenu(TedMainDialog main)
	{
		this.mainDialog = main;
		// Create some menu items for the popup
		menuEdit = new JMenuItem(); //$NON-NLS-1$
		menuEdit.addActionListener(mainDialog);
		menuEdit.setActionCommand("Edit"); //$NON-NLS-1$
		
		menuDelete = new JMenuItem(); //$NON-NLS-1$
		menuDelete.addActionListener(mainDialog);
		menuDelete.setActionCommand("Delete"); //$NON-NLS-1$
		
		menuParse = new JMenuItem(); //$NON-NLS-1$
		menuParse.addActionListener(mainDialog);
		menuParse.setActionCommand("parse selected"); //$NON-NLS-1$
		
		
		menuStatus = new JMenu(); //$NON-NLS-1$
		
		statusCheck = new JMenuItem (); //$NON-NLS-1$
		statusCheck.addActionListener(mainDialog);
		statusCheck.setActionCommand("setstatuscheck"); //$NON-NLS-1$
		statusCheck.setIcon(showPlay);
		
		statusPause = new JMenuItem (); //$NON-NLS-1$
		statusPause.addActionListener(mainDialog);
		statusPause.setActionCommand("setstatuspause"); //$NON-NLS-1$
		statusPause.setIcon(showPaused);
		
		statusHold = new JMenuItem (); //$NON-NLS-1$
		statusHold.addActionListener(mainDialog);
		statusHold.setActionCommand("setstatushold"); //$NON-NLS-1$
		statusHold.setIcon(showStopped);
		
		statusDisabled = new JMenuItem (); //$NON-NLS-1$
		statusDisabled.addActionListener(mainDialog);
		statusDisabled.setActionCommand("setstatusdisabled"); //$NON-NLS-1$
		statusDisabled.setIcon(showDisabled);
		
		buyDVD = new JMenuItem ();
		buyDVD.addActionListener(mainDialog);
		buyDVD.setActionCommand("buyDVDselectedshow");
		
		checkAutoSchedule = new JCheckBoxMenuItem();
		checkAutoSchedule.addActionListener(mainDialog);
		checkAutoSchedule.setActionCommand("toggleautoschedule");
		
		JSeparator separator = new JSeparator();
		JSeparator separator2 = new JSeparator();
		
		this.add( menuEdit );
		this.add( menuDelete );
		this.add( menuParse );
		this.add(separator);
		this.add(checkAutoSchedule);
		this.add( menuStatus );
		this.add(separator2);
		this.add( buyDVD);
		
		menuStatus.add(statusCheck);
		menuStatus.add(statusPause);
		menuStatus.add(statusHold);
		menuStatus.add(statusDisabled);
		
		this.updateText();
	}
	
	/**
	 * Update text of the status menu
	 */
	public void updateText()
	{
		menuEdit.setText(Lang.getString("TedMainMenuBar.Edit.Edit")); //$NON-NLS-1$
		menuDelete.setText( Lang.getString("TedMainMenuBar.Edit.Delete") ); //$NON-NLS-1$
		menuParse.setText( Lang.getString("TedTablePopupMenu.CheckShow") ); //$NON-NLS-1$
		menuStatus.setText( Lang.getString("TedMainMenuBar.Edit.SetStatus") ); //$NON-NLS-1$
		statusCheck.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Check")); //$NON-NLS-1$
		statusPause.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Pause")); //$NON-NLS-1$
		statusHold.setText(Lang.getString("TedMainMenuBar.Edit.SetSTatus.Hold")); //$NON-NLS-1$
		statusDisabled.setText(Lang.getString("TedMainMenuBar.Edit.SetStatus.Disabled"));
		buyDVD.setText(Lang.getString("TedTablePopupMenu.BuyDVD"));	
		checkAutoSchedule.setText(Lang.getString("TedEpisodeDialog.CheckAutoSchedule"));
	}
	
	/**
	 * Disable all menuitems that can't be used while parsing
	 */
	public void setParsing()
	{
		this.menuDelete.setEnabled(false);
	}
	/**
	 * Enable all menuitems that can be used while idleing.
	 */
	public void setIdle()
	{
		this.menuDelete.setEnabled(true);
	}

	public void checkAutoSchedule(boolean b)
	{		
		this.checkAutoSchedule.setState(b);
		this.checkAutoSchedule.setEnabled(TedConfig.isUseAutoSchedule());
		this.menuStatus.setEnabled(!b);
	}
}
