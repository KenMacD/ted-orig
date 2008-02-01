package ted.ui.editshowdialog;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.w3c.dom.Element;

import ted.BrowserLauncher;
import ted.Lang;
import ted.TedDailySerie;
import ted.TedIO;
import ted.TedMainDialog;
import ted.TedPopupItem;
import ted.TedPopupMenu;
import ted.TedSerie;
import ted.TedSystemInfo;
import ted.TedXMLParser;
import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;


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
public class EditShowDialog extends javax.swing.JDialog implements ActionListener
{
	
	private int width = 500;
	private int height = 500;
	private int tabsHeight = 350;
	//private int bottomButtonLocationY = height - 60;

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
	private JButton button_Save;
	private JButton jButton1;
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
		this.setModal(true);
		this.setResizable(false);
		this.tedDialog = frame;
		this.currentSerie = serie;
		this.newSerie = newSerie;
		this.initGUI();
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
		// posistion of ok and cancel button for mac and linux
		int bottomButtonLocationY 	= this.height - 60;
		int bottomButtonOkX 		= this.width - 110;
		int bottomButtonCancelX 	= this.width - 220;
		int bottomButtonHelpX		= 11;
		
		// compute x and y location of buttons on windows
		if (TedSystemInfo.osIsWindows())
		{
			bottomButtonLocationY 	= this.height - 75;
			bottomButtonOkX 		= this.width - 225;
			bottomButtonCancelX 	= this.width - 120;		
		}
		
		this.getContentPane().setLayout(null);
		
		String titlestring = Lang.getString("TedEpisodeDialog.WindowTitleNew"); //$NON-NLS-1$
		if (!newSerie)
		{
			titlestring = Lang.getString("TedEpisodeDialog.WindowTitleEdit") + " " + currentSerie.getName(); //$NON-NLS-1$
		}
		this.setTitle(titlestring);
		
		jShowTabs = new JPanel(new CardLayout());
		getContentPane().add(jShowTabs);
		jShowTabs.setBounds(0, 75, width, this.tabsHeight);
		
		EditShowToolBar toolBarPanel = new EditShowToolBar(this);
		getContentPane().add(toolBarPanel);
		toolBarPanel.setBounds(0, 0, width, 70);
		
		toolBarPanel.setBackground(Color.WHITE);

		jHelpButton = new JButton();
		getContentPane().add(jHelpButton);
		jHelpButton.setIcon(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/help.png")));
		jHelpButton.setActionCommand("Help");
		jHelpButton.setBounds(bottomButtonHelpX, bottomButtonLocationY, 28, 28);
		jHelpButton.addActionListener(this);
		jHelpButton.setToolTipText(Lang.getString("TedGeneral.ButtonHelpToolTip"));
		{
			jButton1 = new JButton();
			getContentPane().add(jButton1);
			jButton1.setActionCommand("cancel");
			jButton1.setText(Lang.getString("TedGeneral.ButtonCancel"));
			jButton1.setBounds(bottomButtonCancelX, bottomButtonLocationY, 98, 28);
			jButton1.addActionListener(this);
		}
		{
			button_Save = new JButton();
			getContentPane().add(button_Save);
			button_Save.setActionCommand("save");
			if (this.newSerie)
			{
				button_Save.setText(Lang.getString("TedGeneral.ButtonAdd"));
			}
			else
			{
				button_Save.setText(Lang.getString("TedGeneral.ButtonSave"));
			}
			button_Save.setBounds(bottomButtonOkX, bottomButtonLocationY, 98, 28);
			button_Save.addActionListener(this);
			this.getRootPane().setDefaultButton(button_Save);
		}
		
		generalPanel = new GeneralPanel(this);
		jShowTabs.add("general", generalPanel);
		generalPanel.setValues(this.currentSerie, newSerie);
		generalPanel.setSize(this.width, this.tabsHeight);
		
		feedsPanel = new FeedsPanel(this.initPopupMenu());
		jShowTabs.add("feeds", feedsPanel);
		feedsPanel.setValues(this.currentSerie);
		feedsPanel.setSize(this.width, this.tabsHeight);
		
		filterPanel = new FilterPanel();
		jShowTabs.add("filter", filterPanel);
		filterPanel.setValues(this.currentSerie);
		filterPanel.setSize(this.width, this.tabsHeight);
		
		schedulePanel = new SchedulePanel();
		jShowTabs.add("schedule", schedulePanel);
		schedulePanel.setValues(this.currentSerie);
		schedulePanel.setSize(this.width, this.tabsHeight);
		
		this.setVisible(true);
		
	}
	
	private TedPopupMenu initPopupMenu()
	{	
		Vector items = new Vector();
		
		TedXMLParser p = new TedXMLParser();
		Element e = p.readXMLFromFile(TedIO.XML_SHOWS_FILE);
		items = p.getPopupItems(e);
		
		return new ted.TedPopupMenu(this, items);
	}

	/**
	 * Show a specific panel
	 * @param command
	 */
	public void showPanel(String command)
	{
		CardLayout cl = (CardLayout)(jShowTabs.getLayout());
	    cl.show(jShowTabs, command);		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		if (action.equals("save"))
		{
			// save the values from the different tabs in the serie
			if (this.saveShow(currentSerie))
			{
				// close the dialog		
				this.setVisible(false);
				this.dispose();
				
				if (newSerie)
				{
					// add the show to teds main window
					tedDialog.addSerie(currentSerie);
				}
				
				// save the changed shows
				tedDialog.saveShows();
			}
		}
		else if (action.equals("cancel"))
		{
			// close the dialog
			this.setVisible(false);
			this.dispose();
		}
		else if (action.equals("Help"))
		{
			// launch documentation website
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/wiki/index.php/Edit_show"); //$NON-NLS-1$
			} 
			catch (Exception err)
			{
				
			}
		}
		else if (action.equals("popupepisodedialog"))
		{
			// create a temp show with filled in values
			TedSerie temp;
			if (this.currentSerie.isDaily())
			{
				temp = new TedDailySerie();
			}
			else
			{
				temp = new TedSerie();
			}
			
			temp.setName(currentSerie.getName());
			
			if (this.saveShow(temp))
			{		
				// popup a select episode dialog
				EpisodeChooserDialog ecd = new EpisodeChooserDialog(this);
				ecd.loadEpisodes(temp);
				ecd.setVisible(true);
			}
		}
		else if (action.equals("switch"))
		{
			// switch show type
			if (this.currentSerie.isDaily())
			{
				TedSerie copy = new TedSerie();
				// copy items that are listed on the general panel
				copy.setName(generalPanel.getShowName());
				copy.setUsePresets(generalPanel.isUsePresets());
				this.currentSerie = copy;
				
				
			}
			else
			{
				TedDailySerie copy = new TedDailySerie();
				// copy items that are listed on the general panel
				copy.setName(generalPanel.getShowName());
				copy.setUsePresets(generalPanel.isUsePresets());
				this.currentSerie = copy;

			}
			this.generalPanel.setValues(this.currentSerie, this.newSerie);
		}
		
	}

	/**
	 * Save the values of the panels in a show
	 * @param show
	 * @return if the filled in values were valid
	 */
	private boolean saveShow(TedSerie show) 
	{
		String currentName = show.getName();
		
		// first check for showname
		if (this.generalPanel.checkValues())
		{
			this.generalPanel.saveValues(show);
			
			if (!currentName.equals(show.getName()) && !currentName.equals(""))
			{
				int action = JOptionPane.showConfirmDialog(null, Lang.getString("TedEpisodeDialog.NameAdjustedQuestion") 
						 + " " + Lang.getString("TedEpisodeDialog.GenerateFeedsQuestion"));
				
				// 0 means generate feeds, is done below
				// 1 means do nothing
				// 2 = Cancel the save operation, indicated by user.
				if (action == 2)
				{
					return false;
				}
				else if (action == 0)
				{
					show.setSearchName("");
					show.generateFeedLocations();
					feedsPanel.setValues(show);
					this.feedsPanel.saveValues(show);
				}
			}
		
			if (!this.feedsPanel.checkValues())
			{
				//JOptionPane.showMessageDialog(null, Lang.getString("TedEpisodeDialog.DialogFeedCount")); //$NON-NLS-1$
				int answer = JOptionPane.showOptionDialog(null, 
						Lang.getString("TedEpisodeDialog.NoFeedsQuestion") + " " + Lang.getString("TedEpisodeDialog.GenerateFeedsQuestion"),
						Lang.getString("TedEpisodeDialog.NoFeedsHeader"),
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE, null, Lang.getYesNoLocale(), Lang.getYesNoLocale()[0]);
				
				if (answer == JOptionPane.YES_OPTION)
				{
					// auto generate feed locations	
					show.removeAllFeeds();
					show.generateFeedLocations();
					
					this.feedsPanel.setValues(show);
					this.feedsPanel.saveValues(show);
				}
			}
			
			if (this.feedsPanel.checkValues())
			{
				this.feedsPanel.saveValues(show);		
				if (this.filterPanel.checkValues() && this.schedulePanel.checkValues())
				{		
					this.filterPanel.saveValues(show);
					this.schedulePanel.saveValues(show);
						
					show.checkDate();
					show.resetStatus(true);
					// backup name
					//this.currentSerie.setName(show.getName());
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * @return The name of the show
	 */
	public String getShowName() 
	{
		return this.generalPanel.getShowName();
	}

	/**
	 * Add keywords to the show
	 * @param words
	 */
	public void addKeywords(String words) 
	{
		this.filterPanel.addKeywords(words);
		
	}

	/**
	 * Add a feed to the show
	 * @param url
	 */
	public void addFeed(String url) 
	{
		this.feedsPanel.addFeed(url);
		
	}
	
	/**
	 * Add a feed to the show 
	 */
	public void addFeed() 
	{
		this.feedsPanel.addFeed();
		
	}

	/**
	 * Set a dailydate or a seasonepisode as current episode for the displayed show
	 * @param selectedStructure
	 */
	public void setEpisode(StandardStructure selectedStructure) 
	{
		if (this.currentSerie.isDaily())
		{
			this.generalPanel.setEpisode((DailyDate)selectedStructure);
		}
		else
		{
			this.generalPanel.setEpisode((SeasonEpisode)selectedStructure);
		}
		
	}

}
