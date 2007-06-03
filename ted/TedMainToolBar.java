package ted;

import javax.swing.JToolBar;

/**
 * TED: Torrent Episode Downloader (2005 - 2007)
 * 
 * This is the toolbar holding the buttons for the mainwindow of ted
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
public class TedMainToolBar extends JToolBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6781755801501055730L;
	private TedMainToolBarButton btn_AddShow;
	private TedMainToolBarButton btn_Edit;
	private TedMainToolBarButton btn_Delete;
	private TedMainToolBarButton btn_Parse;
	
	private String stopCheckingIcon = "StopChecking.png"; //$NON-NLS-1$
	private String startCheckingIcon = "Refresh.png"; //$NON-NLS-1$
	
	public TedMainToolBar()
	{
		this.initButtons(null);
	}
	
	public TedMainToolBar(TedMainDialog tMain)
	{
		// not floatable
		this.setFloatable(false);
		
		this.initButtons(tMain);	
	}
	
	private void initButtons(TedMainDialog tMain)
	{
		btn_AddShow = new TedMainToolBarButton("Aid.png", 
				"TedMainDialog.ButtonAddShow",
				"TedMainDialog.ButtonToolTipAddShow",
				"New", tMain);
		
		this.add(btn_AddShow);
		
		btn_Delete = new TedMainToolBarButton("Cancel.png",
				"TedMainDialog.ButtonDeleteShow",
				"TedMainDialog.ButtonToolTipDeleteShow",
				"Delete", tMain);
		this.add(btn_Delete);
		
		btn_Edit = new TedMainToolBarButton("Edit.png",
				"TedMainDialog.ButtonEditShow",
				"TedMainDialog.ButtonToolTipEditShow",
				"Edit", tMain);
		this.add(btn_Edit);
		
		btn_Parse = new TedMainToolBarButton(this.startCheckingIcon,
				"TedMainDialog.ButtonCheckShows",
				"TedMainDialog.ButtonToolTipCheckShows",
				"Parse", tMain);
		this.add(btn_Parse);
	}

	public void setEditButtonStatus(boolean statusEdit)
	{
		btn_Edit.setEnabled(statusEdit);
		
	}

	public void setDeleteButtonStatus(boolean statusDelete)
	{
		btn_Delete.setEnabled(statusDelete);
		
	}
	
	public void setParseButtonStatus(boolean b)
	{
		btn_Parse.setEnabled(b);		
	}

	public void setParsing()
	{
		this.btn_Parse.setText(Lang.getString("TedMainDialog.ButtonStopChecking")); //$NON-NLS-1$
		this.btn_Parse.setIcon(this.stopCheckingIcon);
		this.btn_Parse.setActionCommand("stop parsing"); //$NON-NLS-1$	
		
		this.setDeleteButtonStatus(false);
	}

	public void setIdle()
	{
		this.setDeleteButtonStatus(true);	
		this.btn_Parse.setText(Lang.getString("TedMainDialog.ButtonCheckShows")); //$NON-NLS-1$
		this.btn_Parse.setIcon(this.startCheckingIcon);
		this.btn_Parse.setEnabled(true);
		
		this.btn_Parse.setActionCommand("Parse"); //$NON-NLS-1$
		
	}

	public void updateText()
	{
		btn_Parse.updateText();
		btn_Edit.updateText();
		btn_Delete.updateText();
		this.btn_AddShow.updateText();
		
	}

	public void setParseButtonText(String string)
	{
		btn_Parse.setText(string);
		
	}


}
