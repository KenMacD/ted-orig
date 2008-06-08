package ted.ui.addshowdialog;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.BoxLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ted.datastructures.SeasonEpisode;
import ted.datastructures.StandardStructure;
import ted.interfaces.EpisodeChooserListener;


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
public class SubscribeOptionsPanel extends JPanel
								   implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static final int DOWNLOAD_LATEST = 0;
	public static final int ONLY_SUBSCRIBE  = 1;
	public static final int SELECT_EPISODE  = 2;
	
	private final Font SMALL_FONT = new Font("Dialog",0,10);
	private final Font LARGE_FONT = new Font("Dialog",0,15);
	
	private ButtonGroup subscribeOptions;
	private int selectedOption = 0;
	private JLabel futureEpisodeLabel;
	private JLabel airedEpisodeLabel;
	private JLabel customEpisodeLabel;
	
	private JRadioButton downloadLatestAndSubscribe;
	private JRadioButton OnlySubscribe;
	private JRadioButton SubscribeAndSelectEpisode;
	
	private StandardStructure selectedStructure = null;
	private StandardStructure nextEpisode;
	private StandardStructure lastAiredEpisode;
	
	private AddShowDialog addShowDialog;
	
	public SubscribeOptionsPanel(AddShowDialog addDialog)
	{
		init();
		this.addShowDialog = addDialog;
	}
	
	private void init()
	{
		String buttonString = "Last aired episode";
		downloadLatestAndSubscribe = new JRadioButton(buttonString);
		downloadLatestAndSubscribe.setActionCommand("latest");
		downloadLatestAndSubscribe.addActionListener(this);
		downloadLatestAndSubscribe.setFont(this.LARGE_FONT);
				
		buttonString = "Next episode";
		OnlySubscribe = new JRadioButton(buttonString);
		OnlySubscribe.setActionCommand("only");
		OnlySubscribe.addActionListener(this);
		OnlySubscribe.setFont(this.LARGE_FONT);
		
		buttonString = "Custom episode";
		SubscribeAndSelectEpisode = new JRadioButton(buttonString);
		SubscribeAndSelectEpisode.setActionCommand("select");
		SubscribeAndSelectEpisode.addActionListener(this);
		SubscribeAndSelectEpisode.setFont(this.LARGE_FONT);
		
		subscribeOptions = new ButtonGroup();
		subscribeOptions.add(downloadLatestAndSubscribe);
		subscribeOptions.add(OnlySubscribe);
		subscribeOptions.add(SubscribeAndSelectEpisode);
		
		OnlySubscribe.setSelected(true);
		
		FormLayout thisLayout = new FormLayout(
				"max(p;10dlu), max(p;5dlu):grow, max(p;5dlu)", 
				"max(p;5dlu), max(p;0dlu), 5dlu, max(p;5dlu), max(p;0dlu), 5dlu, max(p;5dlu), max(p;0dlu)");
		this.setLayout(thisLayout);
		
		futureEpisodeLabel = new JLabel();
		futureEpisodeLabel.setFont(this.SMALL_FONT);
		futureEpisodeLabel.setForeground(Color.DARK_GRAY);
		airedEpisodeLabel	= new JLabel();
		airedEpisodeLabel.setFont(this.SMALL_FONT);
		airedEpisodeLabel.setForeground(Color.DARK_GRAY);
		customEpisodeLabel	= new JLabel();
		customEpisodeLabel.setFont(this.SMALL_FONT);
		customEpisodeLabel.setForeground(Color.DARK_GRAY);
		
		this.add(OnlySubscribe, new CellConstraints("1, 1, 2, 1, default, default"));
		this.add(futureEpisodeLabel, new CellConstraints("2, 2, 1, 1, default, default"));
		this.add(downloadLatestAndSubscribe, new CellConstraints("1, 4, 2, 1, default, default"));
		this.add(airedEpisodeLabel, new CellConstraints("2, 5, 1, 1, default, default"));
		this.add(SubscribeAndSelectEpisode, new CellConstraints("1, 7, 2, 1, default, default"));
		this.add(customEpisodeLabel, new CellConstraints("2, 8, 2, 1, default, default"));
	}
	
	public int getSelectedOption()
	{
		return selectedOption;
	}
	
	public StandardStructure getSelectedEpisode()
	{
		return this.selectedStructure;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		this.selectedStructure = null;
		if (action.equals("latest"))
		{
			selectedOption = DOWNLOAD_LATEST;
			this.selectedStructure = this.lastAiredEpisode;
			this.addShowDialog.setEpisodeChooserVisible(false);
		}
		else if (action.equals("only"))
		{
			selectedOption = ONLY_SUBSCRIBE;
			this.selectedStructure = this.nextEpisode;
			this.addShowDialog.setEpisodeChooserVisible(false);
		}
		else if  (action.equals("select"))
		{
			// show epchooser dialog
			selectedOption = SELECT_EPISODE;
			this.addShowDialog.setEpisodeChooserVisible(true);
			this.customEpisodeLabel.setText("Select a custom episode above...");
		}
		
		this.addShowDialog.subscribeOptionChanged();
		
	}

	public void clear()
	{
		// remove text for season/epsiodes and disable options
		downloadLatestAndSubscribe.setEnabled(false);
		this.airedEpisodeLabel.setText("");
		OnlySubscribe.setEnabled(false);
		this.futureEpisodeLabel.setText("");
		SubscribeAndSelectEpisode.setEnabled(false);
		this.customEpisodeLabel.setText("");
	}
	
	/**
	 * This method is used as callback when the user selects a custom structure from
	 * the episode selection panel.
	 * @param customStructure
	 */
	public void setCustomEpisode(StandardStructure customStructure)
	{
		if (customStructure != null)
		{
			this.selectedStructure = customStructure;
			// show in label
			
			// also set as selected in addshow dialog
			this.addShowDialog.subscribeOptionChanged();
			
			this.customEpisodeLabel.setText(customStructure.getSearchString());
		}
	}

	public void setSeasonEpisodes(Vector episodes)
	{
		// TODO: add handling for episodes without airdate
		Date current = new Date();
		
		int i = 0;
		// find last aired episode
		for (i = 0; i < episodes.size(); i++)
		{
			StandardStructure currentEpisode = (StandardStructure)episodes.get(i);
			Date airdate = currentEpisode.getAirDate();
			
			if (airdate == null)
			{
				continue;
			}
			
			if (airdate.before(current))
			{
				downloadLatestAndSubscribe.setEnabled(true);
				this.lastAiredEpisode = currentEpisode;
				this.airedEpisodeLabel.setText(	currentEpisode.getSearchString());
				break;
			}
		}
		// check if next episode is also available
		if (i > 0)
		{
			StandardStructure nextEpisode = (StandardStructure)episodes.get(i-1);	
			this.nextEpisode = nextEpisode;
			this.selectedStructure = nextEpisode;
			this.futureEpisodeLabel.setText( nextEpisode.getSearchString());
		}
		else
		{
			// what to set in nextEpisode?!
			this.futureEpisodeLabel.setText("Unknown episode, Unknown airdate");
		}
		OnlySubscribe.setEnabled(true);
		OnlySubscribe.setSelected(true);
		
		
		if (episodes.size() > 0)
		{		
			SubscribeAndSelectEpisode.setEnabled(true);
		}
		
		//this.addShowDialog.episodeSelectionChanged();
		this.addShowDialog.subscribeOptionChanged();
		//this.validate();	
	}
}

