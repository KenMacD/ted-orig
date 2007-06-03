package ted;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JToolBar;

import ted.ui.ToolBarButton;

public class TedConfigDialogToolBar extends JToolBar implements ActionListener  
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ToolBarButton generalButton;
	private ToolBarButton looknfeelButton;
	private ToolBarButton advancedButton;
	private ToolBarButton updatesButton;
	private TedConfigDialog tedConfigDialog;

	/**
	 * ConfigDialog toolbar
	 * @param tcd
	 */
	public TedConfigDialogToolBar(TedConfigDialog tcd)
	{
		this.setFloatable(false);
		this.tedConfigDialog = tcd;
		this.makeToolBar();
	}
	
	public TedConfigDialogToolBar()
	{
		this.makeToolBar();
	}
	
	/**
	 * Create the toolbar for the config dialog
	 */
	private void makeToolBar()
	{
		ButtonGroup toolBarButtons = new ButtonGroup();

	    generalButton = new ToolBarButton("general", this, "TedConfigDialog");
	    toolBarButtons.add(generalButton);
	    this.add(generalButton);
	    generalButton.setSelected(true);
		

	    looknfeelButton = new ToolBarButton("looknfeel", this, "TedConfigDialog");
	    toolBarButtons.add(looknfeelButton);
	    this.add(looknfeelButton);
	    
	    advancedButton = new ToolBarButton("advanced", this, "TedConfigDialog");
	    toolBarButtons.add(advancedButton);
	    this.add(advancedButton);
	    
	    updatesButton = new ToolBarButton("updates", this, "TedConfigDialog");
	    toolBarButtons.add(updatesButton);
	    this.add(updatesButton);	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		String command = arg0.getActionCommand();	
		tedConfigDialog.showPanel(command);	
	}

}
