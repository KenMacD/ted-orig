package ted.ui.editshowdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JToolBar;

import ted.ui.ToolBarButton;

public class EditShowToolBar extends JToolBar implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 293340525572515270L;
	private ToolBarButton generalButton;
	private ToolBarButton feedsButton;
	private ToolBarButton filterButton;
	private ToolBarButton scheduleButton;
	private EditShowDialog editDialog;
	
	/**
	 * ConfigDialog toolbar
	 * @param tcd
	 */
	public EditShowToolBar(EditShowDialog ed)
	{
		this.setFloatable(false);
		this.editDialog = ed;
		this.makeToolBar();
	}
	
	public EditShowToolBar()
	{
		this.makeToolBar();
	}
	
	/**
	 * Create the toolbar for the config dialog
	 */
	private void makeToolBar()
	{
		ButtonGroup toolBarButtons = new ButtonGroup();

	    generalButton = new ToolBarButton(editDialog.GENERALCOMMAND, this, "EditShowDialog");
	    toolBarButtons.add(generalButton);
	    this.add(generalButton);
	    generalButton.setSelected(true);
		

	    feedsButton = new ToolBarButton(editDialog.FEEDSCOMMAND, this, "EditShowDialog");
	    toolBarButtons.add(feedsButton);
	    this.add(feedsButton);
	    
	    filterButton = new ToolBarButton(editDialog.FILTERCOMMAND, this, "EditShowDialog");
	    toolBarButtons.add(filterButton);
	    this.add(filterButton);
	    
	    scheduleButton = new ToolBarButton(editDialog.SCHEDULECOMMAND, this, "EditShowDialog");
	    toolBarButtons.add(scheduleButton);
	    this.add(scheduleButton);	
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String command = arg0.getActionCommand();	
		editDialog.showPanel(command);			
	}

}
