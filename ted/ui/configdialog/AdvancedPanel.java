package ted.ui.configdialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel advancedPanel;
	private JLabel labelTimeOutSeconds;
	private JLabel labelTimeOut;
	private JTextField textTimeOutSecs;
	private JRadioButton radioTorrentSettings1;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private JRadioButton radioTorrentSettings2;
	private JLabel labelTorrentSettings;
	private JCheckBox useAutoScheduleCheckBox;
	private JSeparator jSeparator3;
	private JLabel labelTimeOutInSecs;
	private ButtonGroup seederSettingGroup;
	private JCheckBox checkNotDownloadCompressed = null;
	private JTextField filterExtensions;
	private JSlider timeoutInSecondsSlider;
	private final int MINTIMEOUT = 1;
	private final int MAXTIMEOUT = 20;
	
	public AdvancedPanel()
	{
		this.initGUI();
	}
	
	private void initGUI() 
	{
		try 
		{
			this.setSize(400, 400);
			advancedPanel = new JPanel();
			this.add(advancedPanel);
			FormLayout advancedPanelLayout = new FormLayout(
					"max(p;6dlu), 10dlu, 22dlu, max(p;6dlu), 88dlu, 35dlu:grow, max(p;16dlu)", 
			"max(p;5dlu), max(p;5dlu), 30dlu, 15dlu, max(p;15dlu), max(p;15dlu), max(p;15dlu), 15dlu, max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu)");
			advancedPanel.setMaximumSize(new java.awt.Dimension(400, 400));
			advancedPanel.setPreferredSize(new java.awt.Dimension(400, 400));
			advancedPanel.setLayout(advancedPanelLayout);

			labelTimeOut = new JLabel();
			advancedPanel.add(labelTimeOut, new CellConstraints("2, 2, 5, 1, default, default"));
			labelTimeOut.setText(Lang.getString("TedConfigDialog.LabelTimeout"));
			labelTimeOut.setBounds(14, 381, 371, 28);

			timeoutInSecondsSlider = new JSlider(JSlider.HORIZONTAL,
					MINTIMEOUT, MAXTIMEOUT, 10);
			timeoutInSecondsSlider.setMajorTickSpacing(19);
			timeoutInSecondsSlider.setMinorTickSpacing(1);
			//Create the label table
			Hashtable labelTable = new Hashtable();
			labelTable.put( new Integer( MINTIMEOUT ), new JLabel(MINTIMEOUT+"") );
			labelTable.put( new Integer( MAXTIMEOUT/2 ), new JLabel(Lang.getString("TedConfigDialog.Seconds")) );
			labelTable.put( new Integer( MAXTIMEOUT ), new JLabel(MAXTIMEOUT+"") );
			timeoutInSecondsSlider.setLabelTable( labelTable );

			timeoutInSecondsSlider.setPaintTicks(true);
			timeoutInSecondsSlider.setPaintLabels(true);
			timeoutInSecondsSlider.setPaintTrack(true);
			timeoutInSecondsSlider.setSnapToTicks(true);

			advancedPanel.add(timeoutInSecondsSlider, new CellConstraints("2, 3, 5, 1, fill, fill"));

			labelTorrentSettings = new JLabel();
			advancedPanel.add(labelTorrentSettings, new CellConstraints("2, 5, 5, 1, default, default"));
			labelTorrentSettings.setText(Lang.getString("TedConfigDialog.LabelSeeders"));
			labelTorrentSettings.setBounds(14, 439, 350, 28);

			radioTorrentSettings1 = new JRadioButton();
			advancedPanel.add(radioTorrentSettings1, new CellConstraints("2, 6, 5, 1, default, default"));
			radioTorrentSettings1.setText(Lang
					.getString("TedConfigDialog.RadioMinimumSeeders"));
			radioTorrentSettings1.setBounds(7, 459, 364, 28);

			radioTorrentSettings2 = new JRadioButton();
			advancedPanel.add(radioTorrentSettings2, new CellConstraints("2, 7, 5, 1, default, default"));
			radioTorrentSettings2.setText(Lang
					.getString("TedConfigDialog.RadioMostSeeders"));
			radioTorrentSettings2.setBounds(7, 483, 364, 28);

			seederSettingGroup = new ButtonGroup();
			seederSettingGroup.add(radioTorrentSettings1);
			seederSettingGroup.add(radioTorrentSettings2);

			jSeparator1 = new JSeparator();
			advancedPanel.add(jSeparator1, new CellConstraints("2, 4, 5, 1, default, default"));

			jSeparator2 = new JSeparator();
			advancedPanel.add(jSeparator2, new CellConstraints("2, 8, 5, 1, default, default"));

			checkNotDownloadCompressed = new JCheckBox();
			checkNotDownloadCompressed.setBounds(14, 257, 371, 21);
			checkNotDownloadCompressed.setText(Lang.getString("TedConfigDialog.GetCompressed"));
			advancedPanel.add(checkNotDownloadCompressed, new CellConstraints("2, 9, 5, 1, default, default"));
			checkNotDownloadCompressed.addActionListener(this);
			checkNotDownloadCompressed.setActionCommand("compressed");

			filterExtensions = new JTextField();
			filterExtensions.setBounds(15, 257, 371, 21);
			filterExtensions.setText("zip, rar, r01");
			advancedPanel.add(filterExtensions, new CellConstraints("3, 10, 3, 1, default, default"));
			{
				jSeparator3 = new JSeparator();
				advancedPanel.add(jSeparator3, new CellConstraints("2, 11, 5, 1, default, default"));
			}
			{
				useAutoScheduleCheckBox = new JCheckBox();
				advancedPanel.add(getUseAutoScheduleCheckBox(), new CellConstraints("2, 12, 5, 1, default, default"));
				useAutoScheduleCheckBox.setText(Lang.getString("TedConfigDialog.AutomaticSchedule"));
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
		// get values
		int timeOut = TedConfig.getTimeOutInSecs();
		if (timeOut < MINTIMEOUT)
		{
			timeOut = MINTIMEOUT;
		}
		else if (timeOut > MAXTIMEOUT)
		{
			timeOut = MAXTIMEOUT;
		}
		timeoutInSecondsSlider.setValue(timeOut);
		
		
		this.setSelectedButton(seederSettingGroup, TedConfig.getSeederSetting());
		this.setPreferredSize(new java.awt.Dimension(400, 400));
		checkNotDownloadCompressed.setSelected(TedConfig.getDoNotDownloadCompressed());
		filterExtensions.setText(TedConfig.getFilterExtensions());
		filterExtensions.setEnabled(checkNotDownloadCompressed.isSelected());
		useAutoScheduleCheckBox.setSelected(TedConfig.isUseAutoSchedule());
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

		int newTime = timeoutInSecondsSlider.getValue();
		int seederSetting = this.getSelectedButton(seederSettingGroup);
		
		TedConfig.setTimeOutInSecs(newTime);
		TedConfig.setSeederSetting(seederSetting);
		TedConfig.setDoNotDownloadCompressed(checkNotDownloadCompressed.isSelected());
		TedConfig.setFilterExtensions(filterExtensions.getText());
		TedConfig.setUseAutoSchedule(useAutoScheduleCheckBox.isSelected());
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
	
	public JCheckBox getUseAutoScheduleCheckBox() {
		return useAutoScheduleCheckBox;
	}
}
