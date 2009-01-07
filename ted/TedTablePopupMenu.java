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
	private JMenuItem menuDelete;
	private JMenuItem menuEdit;
	private JMenuItem buyDVD;
	private JMenuItem menuParse;
	private JMenuItem menuEnable;
	private JMenuItem menuDisable;
	private JCheckBoxMenuItem checkAutoSchedule;
	private boolean disabledShow = false;

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
		
		
		menuEnable = new JMenuItem (); //$NON-NLS-1$
		menuEnable.addActionListener(mainDialog);
		menuEnable.setActionCommand("setstatusenabled"); //$NON-NLS-1$
		
		menuDisable = new JMenuItem (); //$NON-NLS-1$
		menuDisable.addActionListener(mainDialog);
		menuDisable.setActionCommand("setstatusdisabled"); //$NON-NLS-1$
		
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
		this.add(menuEnable);
		this.add(menuDisable);
		this.add(separator2);
		this.add( buyDVD);
		
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
		buyDVD.setText(Lang.getString("TedTablePopupMenu.BuyDVD"));	
		checkAutoSchedule.setText(Lang.getString("TedEpisodeDialog.CheckAutoSchedule"));
		menuEnable.setText(Lang.getString("TedMainMenuBar.Edit.EnableShow"));
		menuDisable.setText(Lang.getString("TedMainMenuBar.Edit.DisableShow"));
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
	}

	public void checkDisabled(boolean disabled, boolean showBoth) 
	{
		this.menuDisable.setVisible(!disabled || showBoth);
		this.menuEnable.setVisible(disabled || showBoth);
	}
}
