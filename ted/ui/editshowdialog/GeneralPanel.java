package ted.ui.editshowdialog;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ted.Lang;
import ted.TedDailySerie;
import ted.TedSerie;
import ted.datastructures.DailyDate;
import ted.datastructures.SeasonEpisode;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


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
/**
 * Panel that displays general settings for a tedshow
 * @author roel
 *
 */
public class GeneralPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7836753687519373743L;
	private int width = 400;
	private JSeparator jSeparator1;
	private JSeparator jSeparator4;
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
				"max(p;5dlu), max(p;15dlu), max(p;5dlu), max(p;15dlu), max(p;5dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), 5dlu, max(p;15dlu), 17dlu");
			this.setLayout(lookFeelPanelLayout);

			labelName = new JLabel();
			this.add(labelName, new CellConstraints("2, 2, 1, 1, default, default"));
			labelName.setText(Lang.getString("TedEpisodeDialog.LabelName"));
			labelName.setBounds(18, 101, 98, 28);

			textName = new JTextField();
			this.add(textName, new CellConstraints("2, 4, 1, 1, default, default"));
			textName.setBounds(133, 105, 322, 21);

			checkUpdatePresets = new JCheckBox();
			this.add(checkUpdatePresets, new CellConstraints("2, 10, 1, 1, default, default"));
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
					.setText(Lang.getString("TedEpisodeDialog.ButtonSelectFromList"));
				popupEpisodeDialogButton.setToolTipText(Lang.getString("TedEpisodeDialog.ButtonSelectFromListToolTip"));
				popupEpisodeDialogButton.setActionCommand("popupepisodedialog");
				popupEpisodeDialogButton.addActionListener(editShowDialog);
			}
			{
				jSeparator4 = new JSeparator();
				this.add(jSeparator4, new CellConstraints("2, 5, 1, 1, default, default"));
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("2, 9, 1, 1, default, default"));
			}

		}
		catch (Exception e)
		{
			
		}
		
	}

	/**
	 * Add values of a show to the general panel
	 * @param serie
	 */
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

	/**
	 * @return Wheter all values in the panel are valid
	 */
	public boolean checkValues() 
	{
		// check show name
		if (textName.getText().equals("")) //$NON-NLS-1$
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogShowName")); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	/**
	 * Save filled in values into the show
	 * @param currentSerie
	 */
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

	/**
	 * @return The name of the show
	 */
	public String getShowName() 
	{
		return this.textName.getText();
	}

	/**
	 * Set a date in the general panel
	 * @param selectedStructure
	 */
	public void setEpisode(DailyDate selectedStructure) 
	{
		this.dailyPanel.setDate(selectedStructure.getDate().getTimeInMillis());
		
	}
	/**
	 * Set a season/episode in the general panel
	 * @param selectedStructure
	 */
	public void setEpisode(SeasonEpisode selectedStructure) 
	{		
		this.seasonEpisodePanel.setSeasonEpisode(selectedStructure.getSeason(), selectedStructure.getEpisode());
	}
	

}
