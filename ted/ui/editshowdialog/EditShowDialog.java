package ted.ui.editshowdialog;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ted.Lang;
import ted.TedConfigDialogToolBar;
import ted.TedMainDialog;
import ted.TedSerie;


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
public class EditShowDialog extends javax.swing.JDialog
{
	
	private int width = 400;
	private int height = 450;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8118091583517036628L;
	private JPanel jShowTabs;
	private TedMainDialog tedDialog;
	private TedSerie currentSerie;
	private boolean newSerie;
	private JButton jHelpButton;
	private GeneralPanel generalPanel;
	private FeedsPanel feedsPanel;
	private FilterPanel filterPanel;
	private SchedulePanel schedulePanel;
	
	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a new Edit Show Dialog
	 * @param frame TedMainDialog
	 * @param serie Serie where we have to make this dialog for
	 * @param newSerie Is it a new serie?
	 */
	public EditShowDialog(TedMainDialog frame, TedSerie serie, boolean newSerie) 
	{
		super(frame);
		this.setModal(true);
		this.setResizable(false);
		this.tedDialog = frame;
		this.currentSerie = serie;
		this.newSerie = newSerie;
		this.initGUI();
		//this.setValues(currentSerie);
	}

	private void initGUI()
	{
		try 
		{
			this.setSize(width, height);

		    //Get the screen size
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Dimension screenSize = toolkit.getScreenSize();

		    //Calculate the frame location
		    int x = (screenSize.width - this.getWidth()) / 2;
		    int y = (screenSize.height - this.getHeight()) / 2;

		    //Set the new frame location
		    this.setLocation(x, y);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		loadShow();
		
	}

	private void loadShow()
	{
		this.getContentPane().setLayout(null);
		
		String titlestring = Lang.getString("TedEpisodeDialog.WindowTitleNew"); //$NON-NLS-1$
		if (!newSerie)
		{
			titlestring = Lang.getString("TedEpisodeDialog.WindowTitleEdit") + " " + currentSerie.getName(); //$NON-NLS-1$
		}
		this.setTitle(titlestring);
		
		jShowTabs = new JPanel(new CardLayout());
		getContentPane().add(jShowTabs);
		jShowTabs.setBounds(0, 75, 400, 300);
		
		EditShowToolBar toolBarPanel = new EditShowToolBar(this);
		getContentPane().add(toolBarPanel);
		toolBarPanel.setBounds(0, 0, 400, 70);
		
		//jConfigTabs.setModel(toolBarPanel);
		
		toolBarPanel.setBackground(Color.WHITE);

		jHelpButton = new JButton();
		getContentPane().add(jHelpButton);
		jHelpButton.setIcon(new ImageIcon(getClass()
				.getClassLoader().getResource("help.png")));
		jHelpButton.setActionCommand("Help");
		jHelpButton.setBounds(11, 380, 28, 28);
		//jHelpButton.addActionListener(TCListener);
		
		generalPanel = new GeneralPanel();
		jShowTabs.add("general", generalPanel);
		generalPanel.setValues(this.currentSerie);
		
		feedsPanel = new FeedsPanel();
		jShowTabs.add("feeds", feedsPanel);
		feedsPanel.setValues(this.currentSerie);
		
		filterPanel = new FilterPanel();
		jShowTabs.add("filter", filterPanel);
		filterPanel.setValues(this.currentSerie);
		
		schedulePanel = new SchedulePanel();
		jShowTabs.add("schedule", schedulePanel);
		schedulePanel.setValues(this.currentSerie);
		
	}

	public void showPanel(String command)
	{
		CardLayout cl = (CardLayout)(jShowTabs.getLayout());
	    cl.show(jShowTabs, command);		
	}

}
