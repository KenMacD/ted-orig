package ted.ui.editshowdialog;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;

import ted.Lang;
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
public class GeneralPanel extends JPanel
{
	private int width = 400;
	private int height = 300;
	//private JPanel generalPanel;
	private JLabel labelLookingFor;
	private JTextField textName;
	private JCheckBox checkUpdatePresets;
	private JLabel labelName;
	private DatePanel datePanel;
	private SeasonEpisodePanel seasonEpisodePanel;

	public GeneralPanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			this.setPreferredSize(new Dimension(width, height));

			//this.add(generalPanel);
			this.setPreferredSize(new Dimension(width, height));
			FormLayout lookFeelPanelLayout = new FormLayout(
				"max(p;6dlu), 15dlu:grow, max(p;16dlu)",
				"max(p;5dlu), max(p;15dlu), max(p;5dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), 17dlu");
			this.setLayout(lookFeelPanelLayout);

			labelName = new JLabel();
			this.add(labelName, new CellConstraints("2, 2, 1, 1, default, default"));
			labelName.setText(Lang.getString("TedEpisodeDialog.LabelName"));
			labelName.setBounds(18, 101, 98, 28);

			textName = new JTextField();
			this.add(textName, new CellConstraints("2, 4, 1, 1, default, default"));
			textName.setBounds(133, 105, 322, 21);

			checkUpdatePresets = new JCheckBox();
			this.add(checkUpdatePresets, new CellConstraints("2, 9, 1, 1, default, default"));
			checkUpdatePresets.setText(Lang
				.getString("TedEpisodeDialog.CheckAutoUpdate"));
			checkUpdatePresets.setOpaque(false);
			checkUpdatePresets.setBounds(10, 49, 448, 28);
			
			datePanel = new DatePanel();
			this.add(datePanel, new CellConstraints("2, 7, 1, 1, default, default"));
			
			seasonEpisodePanel = new SeasonEpisodePanel();
			this.add(seasonEpisodePanel, new CellConstraints("2, 7, 1, 1, default, default"));
			{
				labelLookingFor = new JLabel();
				this.add(labelLookingFor, new CellConstraints("2, 6, 1, 1, default, default"));
				labelLookingFor.setText(Lang.getString("TedEpisodeDialog.LabelSeasonEpisode"));
			}

		}
		catch (Exception e)
		{
			
		}
		
	}

	public void setValues(TedSerie serie)
	{
		this.textName.setText(serie.getName());
		this.checkUpdatePresets.setSelected(serie.isUsePresets());
		
		if (serie.isDaily())
		{
			// display date panel
			this.datePanel.setVisible(true);
			this.seasonEpisodePanel.setVisible(false);
			
			// set date
		}
		else
		{
			// display season episode panel
			this.datePanel.setVisible(false);
			this.seasonEpisodePanel.setVisible(true);
			
			// set season/episode
			this.seasonEpisodePanel.setSeasonEpisode(serie.getCurrentSeason(), serie.getCurrentEpisode());
		}
	}

	public boolean checkValues() 
	{
		if (textName.getText().equals("")) //$NON-NLS-1$
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogShowName")); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	public void saveValues(TedSerie currentSerie) 
	{
		if (this.checkValues())
		{
			currentSerie.setName(textName.getText());
			currentSerie.setUsePresets(checkUpdatePresets.isSelected());
			
			if (currentSerie.isDaily())
			{
				// set date
				
			}
			else
			{
				// set season and episode
				currentSerie.setCurrentEpisode(this.seasonEpisodePanel.getEpisode());
				currentSerie.setCurrentSeason(this.seasonEpisodePanel.getSeason());
			}
		}
	}

	public String getShowName() 
	{
		return this.textName.getText();
	}

}
