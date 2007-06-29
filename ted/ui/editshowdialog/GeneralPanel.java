package ted.ui.editshowdialog;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;

import ted.Lang;
import ted.TedDailySerie;
import ted.TedSerie;
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
public class GeneralPanel extends JPanel
{
	private int width = 400;
	private JButton popupEpisodeDialogButton;
	private int height = 300;
	//private JPanel generalPanel;
	private JLabel labelLookingFor;
	private JTextField textName;
	private JCheckBox checkUpdatePresets;
	private JLabel labelName;
	private DailyPanel dailyPanel;
	private SeasonEpisodePanel seasonEpisodePanel;
	private EditShowDialog editShowDialog;

	public GeneralPanel(EditShowDialog parent)
	{
		editShowDialog = parent;
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
			
			dailyPanel = new DailyPanel();
			this.add(dailyPanel, new CellConstraints("2, 7, 1, 1, default, default"));
			
			seasonEpisodePanel = new SeasonEpisodePanel();
			this.add(seasonEpisodePanel, new CellConstraints("2, 7, 1, 1, default, default"));
			{
				labelLookingFor = new JLabel();
				this.add(labelLookingFor, new CellConstraints("2, 6, 1, 1, default, default"));
				labelLookingFor.setText(Lang.getString("TedEpisodeDialog.LabelSeasonEpisode"));
			}
			{
				popupEpisodeDialogButton = new JButton();
				this.add(popupEpisodeDialogButton, new CellConstraints("2, 8, 1, 1, default, default"));
				popupEpisodeDialogButton
					.setText("Select from available episodes");
				popupEpisodeDialogButton.setActionCommand("popupepisodedialog");
				popupEpisodeDialogButton.addActionListener(editShowDialog);
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
			this.dailyPanel.setVisible(true);
			this.dailyPanel.setValues((TedDailySerie) serie);
			this.seasonEpisodePanel.setVisible(false);
		}
		else
		{
			// display season episode panel
			this.dailyPanel.setVisible(false);
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
				// set date and max downloads
				this.dailyPanel.saveValues((TedDailySerie)currentSerie);
				
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

	public void setEpisode(DailyDate selectedStructure) 
	{
		this.dailyPanel.setDate(selectedStructure.getDate().getTimeInMillis());
		
	}
	public void setEpisode(SeasonEpisode selectedStructure) 
	{		
		this.seasonEpisodePanel.setSeasonEpisode(selectedStructure.getSeason(), selectedStructure.getEpisode());
	}
	

}
