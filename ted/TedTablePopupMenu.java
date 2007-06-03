package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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
	private ImageIcon showPaused = new ImageIcon(getClass().getClassLoader().getResource("pause.png")); //$NON-NLS-1$
	private ImageIcon showPlay	 = new ImageIcon(getClass().getClassLoader().getResource("play.png")); //$NON-NLS-1$
	private ImageIcon showStopped	 = new ImageIcon(getClass().getClassLoader().getResource("stop.png")); //$NON-NLS-1$
	private JMenuItem menuDelete;
	private JMenuItem menuEdit;
	JMenuItem menuParse;
	JMenu menuStatus;
	JMenuItem statusCheck;
	JMenuItem statusPause;
	JMenuItem statusHold;

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
		
		this.add( menuEdit );
		this.add( menuDelete );
		this.add( menuParse );
		this.add( menuStatus );
		
		menuStatus.add(statusCheck);
		menuStatus.add(statusPause);
		menuStatus.add(statusHold);
		
		this.updateText();
	}
	
	/**
	 * Update text of the status menu
	 */
	public void updateText()
	{
		menuEdit.setText(Lang.getString("TedTablePopupMenu.Edit")); //$NON-NLS-1$
		menuDelete.setText( Lang.getString("TedTablePopupMenu.Delete") ); //$NON-NLS-1$
		menuParse.setText( Lang.getString("TedTablePopupMenu.CheckShow") ); //$NON-NLS-1$
		menuStatus.setText( Lang.getString("TedTablePopupMenu.SetStatus") ); //$NON-NLS-1$
		statusCheck.setText(Lang.getString("TedTablePopupMenu.SetStatus.Check")); //$NON-NLS-1$
		statusPause.setText(Lang.getString("TedTablePopupMenu.SetStatus.Pause")); //$NON-NLS-1$
		statusHold.setText(Lang.getString("TedTablePopupMenu.SetStatus.Hold")); //$NON-NLS-1$
		
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

}
