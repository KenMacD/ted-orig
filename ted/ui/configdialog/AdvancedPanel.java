package ted.ui.configdialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;

import ted.Lang;
import ted.TedConfig;

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
public class AdvancedPanel extends JPanel implements ActionListener
{

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel advancedPanel;  //  @jve:decl-index=0:visual-constraint="10,37"
	private JRadioButton radioTorrentSettings1;
	private JSeparator jSeparator2;
	private JRadioButton radioTorrentSettings2;
	private JLabel labelTorrentSettings;
	private JCheckBox useAutoScheduleCheckBox;	
	private JCheckBox filterTrackersCheckBox;
	private JSeparator jSeparator3;
	private JSeparator jSeparatorProxy;
	private ButtonGroup seederSettingGroup;  //  @jve:decl-index=0:
	private JCheckBox checkNotDownloadCompressed = null;
	private JTextField filterExtensions;
		
	
	public AdvancedPanel()
	{
		this.initGUI();
	}
	
	private void initGUI() 
	{
		try 
		{
			advancedPanel = new JPanel();
			this.add(advancedPanel);	
			FormLayout advancedPanelLayout = new FormLayout(
					"max(p;6dlu), 10dlu, 22dlu, max(p;6dlu), 88dlu, 35dlu:grow, max(p;16dlu)", 
					"max(p;5dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), 15dlu, max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu)");
			advancedPanel.setPreferredSize(new java.awt.Dimension(500, 243));
			advancedPanel.setSize(new java.awt.Dimension(500,500));
			advancedPanel.setLayout(advancedPanelLayout);

			labelTorrentSettings = new JLabel();
			advancedPanel.add(labelTorrentSettings, new CellConstraints("2, 2, 5, 1, default, default"));
			labelTorrentSettings.setText(Lang.getString("TedConfigDialog.LabelSeeders"));
			labelTorrentSettings.setBounds(14, 439, 350, 28);

			radioTorrentSettings1 = new JRadioButton();
			advancedPanel.add(radioTorrentSettings1, new CellConstraints("2, 3, 5, 1, default, default"));
			radioTorrentSettings1.setText(Lang
					.getString("TedConfigDialog.RadioMinimumSeeders"));
			radioTorrentSettings1.setBounds(7, 459, 364, 28);

			radioTorrentSettings2 = new JRadioButton();
			advancedPanel.add(radioTorrentSettings2, new CellConstraints("2, 4, 5, 1, default, default"));
			radioTorrentSettings2.setText(Lang
					.getString("TedConfigDialog.RadioMostSeeders"));
			radioTorrentSettings2.setBounds(7, 483, 364, 28);

			seederSettingGroup = new ButtonGroup();
			seederSettingGroup.add(radioTorrentSettings1);
			seederSettingGroup.add(radioTorrentSettings2);

			jSeparator2 = new JSeparator();
			advancedPanel.add(jSeparator2, new CellConstraints("2, 5, 5, 1, default, default"));

			checkNotDownloadCompressed = new JCheckBox();
			checkNotDownloadCompressed.setBounds(14, 257, 371, 21);
			checkNotDownloadCompressed.setText(Lang.getString("TedConfigDialog.FilterFiles"));
			advancedPanel.add(checkNotDownloadCompressed, new CellConstraints("2, 6, 5, 1, default, default"));
			checkNotDownloadCompressed.addActionListener(this);
			checkNotDownloadCompressed.setActionCommand("compressed");

			filterExtensions = new JTextField();
			filterExtensions.setBounds(15, 257, 371, 21);
			filterExtensions.setText("zip, rar, r01");
			advancedPanel.add(filterExtensions, new CellConstraints("2, 7, 5, 1, default, default"));
			{
				jSeparator3 = new JSeparator();
				advancedPanel.add(jSeparator3, new CellConstraints("2, 8, 5, 1, default, default"));
			}
			{
				useAutoScheduleCheckBox = new JCheckBox();
				advancedPanel.add(useAutoScheduleCheckBox, new CellConstraints("2, 9, 5, 1, default, default"));
				useAutoScheduleCheckBox.setText(Lang.getString("TedConfigDialog.AutomaticSchedule"));
			}
			
			{
				jSeparatorProxy = new JSeparator();
				advancedPanel.add(jSeparatorProxy, new CellConstraints("2, 10, 5, 1, default, default"));
			}						
			{
				filterTrackersCheckBox = new JCheckBox();
				advancedPanel.add(filterTrackersCheckBox, new CellConstraints("2, 11, 5, 1, default, default"));
				filterTrackersCheckBox.setText(Lang.getString("TedConfigDialog.PrivateTrackers"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the values of the current config in the fields
	 */
	public void setValues()
	{		
		this.setSelectedButton(seederSettingGroup, TedConfig.getInstance().getSeederSetting());
		this.setPreferredSize(new java.awt.Dimension(500, 245));
		this.setSize(new java.awt.Dimension(500,500));
		checkNotDownloadCompressed.setSelected(TedConfig.getInstance().getDoNotDownloadCompressed());
		filterExtensions.setText(TedConfig.getInstance().getFilterExtensions());
		filterExtensions.setEnabled(checkNotDownloadCompressed.isSelected());
		useAutoScheduleCheckBox.setSelected(TedConfig.getInstance().isUseAutoSchedule());
		filterTrackersCheckBox.setSelected(TedConfig.getInstance().isFilterPrivateTrackers());		
	}
	
	/**
	 * Check filled info
	 */
	public boolean checkValues()
	{
		return true;
	}
	
	/**
	 * Save values from the dialog to the config file
	 * @param config
	 */
	public void saveValues()
	{
		// get values
		int seederSetting = this.getSelectedButton(seederSettingGroup);
		
		TedConfig.getInstance().setSeederSetting(seederSetting);
		TedConfig.getInstance().setDoNotDownloadCompressed(checkNotDownloadCompressed.isSelected());
		TedConfig.getInstance().setFilterExtensions(filterExtensions.getText());
		TedConfig.getInstance().setUseAutoSchedule(useAutoScheduleCheckBox.isSelected());
		TedConfig.getInstance().setFilterPrivateTrackers(filterTrackersCheckBox.isSelected());
	}
	
	/**
	 * Get selected state from buttongroup
	 * @param group
	 * @return
	 */
	private int getSelectedButton(ButtonGroup group)
	{
		int i = 0;
		Enumeration buttons = group.getElements();
		JRadioButton temp;
		while (buttons.hasMoreElements())
		{
			temp = (JRadioButton)buttons.nextElement();
			if (temp.isSelected())
			{
				// check which state is selected and return it
				String text = temp.getText();
				if  (text.equals((Lang.getString("TedConfigDialog.RadioMinimumSeeders")))) //$NON-NLS-1$
					return TedConfig.DOWNLOADMINIMUMSEEDERS;
				else if (text.equals((Lang.getString("TedConfigDialog.RadioMostSeeders")))) //$NON-NLS-1$
					return TedConfig.DOWNLOADMOSTSEEDERS;
			}
			else
				i++;
		}
		
		// default return value
		return 1;
	}
	/**
	 * Select button from the group
	 * @param group
	 * @param toSelect
	 */
	private void setSelectedButton(ButtonGroup group, int toSelect)
	{
		int i = 0;
		Enumeration buttons = group.getElements();
		JRadioButton temp;
		while (buttons.hasMoreElements())
		{
			temp = (JRadioButton)buttons.nextElement();
			String text = temp.getText();
			if (((text.equals(Lang.getString("TedConfigDialog.RadioMinimumSeeders")) && toSelect == TedConfig.DOWNLOADMINIMUMSEEDERS) //$NON-NLS-1$
					|| text.equals(Lang.getString("TedConfigDialog.RadioMostSeeders")) && toSelect == TedConfig.DOWNLOADMOSTSEEDERS)) //$NON-NLS-1$
			{
				temp.setSelected(true);
			}
			else
				temp.setSelected(false);
			i++;
		}
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		
		if(action.equals("compressed"))
		{
			filterExtensions.setEnabled(checkNotDownloadCompressed.isSelected());
		}		
	}


	public JCheckBox getUseAutoScheduleCheckBox() 
	{
		return useAutoScheduleCheckBox;
	}
}
