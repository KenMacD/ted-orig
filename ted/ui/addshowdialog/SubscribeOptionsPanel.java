package ted.ui.addshowdialog;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ted.Lang;
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
	
	private JRadioButton lastAiredRadio;
	private JRadioButton nextEpisodeRadio;
	private JRadioButton customSelectRadio;
	
	private StandardStructure selectedStructure = null;
	private StandardStructure nextEpisode;
	private StandardStructure lastAiredEpisode;
	
	private AddShowDialog addShowDialog;

	private StandardStructure customStructure;
	
	public SubscribeOptionsPanel(AddShowDialog addDialog)
	{
		init();
		this.addShowDialog = addDialog;
	}
	
	private void init()
	{
		String buttonString = Lang.getString("AddShowDialog.SubscribeOptions.LastAired");
		lastAiredRadio = new JRadioButton(buttonString);
		lastAiredRadio.setActionCommand("latest");
		lastAiredRadio.addActionListener(this);
		lastAiredRadio.setFont(this.LARGE_FONT);
				
		buttonString = Lang.getString("AddShowDialog.SubscribeOptions.NextEpisode");
		nextEpisodeRadio = new JRadioButton(buttonString);
		nextEpisodeRadio.setActionCommand("only");
		nextEpisodeRadio.addActionListener(this);
		nextEpisodeRadio.setFont(this.LARGE_FONT);
		
		buttonString = Lang.getString("AddShowDialog.SubscribeOptions.CustomEpisode");
		customSelectRadio = new JRadioButton(buttonString);
		customSelectRadio.setActionCommand("select");
		customSelectRadio.addActionListener(this);
		customSelectRadio.setFont(this.LARGE_FONT);
		
		subscribeOptions = new ButtonGroup();
		subscribeOptions.add(lastAiredRadio);
		subscribeOptions.add(nextEpisodeRadio);
		subscribeOptions.add(customSelectRadio);
		
		nextEpisodeRadio.setSelected(true);
		
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
		
		this.add(nextEpisodeRadio, new CellConstraints("1, 1, 2, 1, default, default"));
		this.add(futureEpisodeLabel, new CellConstraints("2, 2, 1, 1, default, default"));
		this.add(lastAiredRadio, new CellConstraints("1, 4, 2, 1, default, default"));
		this.add(airedEpisodeLabel, new CellConstraints("2, 5, 1, 1, default, default"));
		this.add(customSelectRadio, new CellConstraints("1, 7, 2, 1, default, default"));
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
			if (this.customStructure == null)
			{
				this.customEpisodeLabel.setText("");
			}
		}
		else if (action.equals("only"))
		{
			selectedOption = ONLY_SUBSCRIBE;
			this.selectedStructure = this.nextEpisode;
			this.addShowDialog.setEpisodeChooserVisible(false);
			if (this.customStructure == null)
			{
				this.customEpisodeLabel.setText("");
			}
		}
		else if  (action.equals("select"))
		{
			// show epchooser dialog
			selectedOption = SELECT_EPISODE;
			this.addShowDialog.setEpisodeChooserVisible(true);
			
			if (this.customStructure == null)
			{
				this.customEpisodeLabel.setText(Lang.getString("AddShowDialog.SubscribeOptions.CustomEpisodeSelect"));
			}
		}
		
		if (this.customStructure != null)
		{
			this.customEpisodeLabel.setText(this.customStructure.getSearchString());
		}
		
		this.addShowDialog.subscribeOptionChanged();
		
	}

	public void clear()
	{
		// remove text for season/epsiodes and disable options
		lastAiredRadio.setEnabled(false);
		this.airedEpisodeLabel.setText("");
		nextEpisodeRadio.setEnabled(false);
		this.futureEpisodeLabel.setText("");
		customSelectRadio.setEnabled(false);
		this.customEpisodeLabel.setText("");
		
		lastAiredRadio.setVisible(false);
		this.airedEpisodeLabel.setVisible(false);
		nextEpisodeRadio.setVisible(false);
		this.futureEpisodeLabel.setVisible(false);
		customSelectRadio.setVisible(false);
		this.customEpisodeLabel.setVisible(false);
		
		this.nextEpisode = null;
		this.lastAiredEpisode = null;
		this.customStructure = null;
		this.selectedStructure = null;
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
			this.customStructure = customStructure;
			// show in label
			
			// also set as selected in addshow dialog
			this.addShowDialog.subscribeOptionChanged();
			
			this.customEpisodeLabel.setText(customStructure.getSearchString());
		}
	}

	public void setAiredSeasonEpisodes(Vector<StandardStructure> episodes)
	{
		// first item in list is last aired episode
		if (episodes.size() > 0)
		{
			this.lastAiredEpisode = episodes.elementAt(0);
			this.airedEpisodeLabel.setText(	this.lastAiredEpisode.getSearchString());
			
			// enable custom selection
			customSelectRadio.setEnabled(true);
			this.updateEnabledOptions();
		}	
	}

	public void setNextEpisode(StandardStructure nextEpisode2) 
	{
		if (nextEpisode2 != null)
		{
			this.nextEpisode = nextEpisode2;
			this.selectedStructure = nextEpisode;
			this.futureEpisodeLabel.setText( nextEpisode.getSearchString());
			this.updateEnabledOptions();
		}		
	}
	
	private void updateEnabledOptions()
	{
		// set visible
		lastAiredRadio.setVisible(true);
		this.airedEpisodeLabel.setVisible(true);
		nextEpisodeRadio.setVisible(true);
		this.futureEpisodeLabel.setVisible(true);
		customSelectRadio.setVisible(true);
		this.customEpisodeLabel.setVisible(true);
		
		// enable/disable
		if (this.nextEpisode != null)
		{
			this.nextEpisodeRadio.setEnabled(true);
		}
		if (this.lastAiredEpisode != null)
		{
			this.lastAiredRadio.setEnabled(true);
		}
		
		// default value
		if (this.nextEpisode != null)
		{
			this.nextEpisodeRadio.setSelected(true);
		}
		else if (this.lastAiredEpisode != null)
		{
			this.lastAiredRadio.setSelected(true);
		}
		
		this.addShowDialog.subscribeOptionChanged();
	}
}

