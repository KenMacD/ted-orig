package ted.ui.configdialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import ted.Lang;
import ted.TedConfig;
import ted.TedSystemInfo;


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
public class LooknFeelPanel extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6042247710601857318L;
	private JPanel lookFeelPanel;
	private JCheckBox checkStartMinimized;
	private JCheckBox checkAlertNewTorrent;
	private JSeparator jSeparator4;
	private JCheckBox checkAddSystemTray;
	private JComboBox comboLanguages;
	private JSeparator jSeparator5;
	private JCheckBox checkAlertErrors;
	private JLabel labelLanguage;
	private int width = 400;
	private int height = 300;
	private Locale[] locales = Lang.getAvailableLocales();
	
	public LooknFeelPanel()
	{
		this.initGUI();
	}
	
	private void initGUI() 
	{
		try 
		{
			this.setPreferredSize(new Dimension(width, height));

		lookFeelPanel = new JPanel();
		this.add(lookFeelPanel);
		lookFeelPanel.setPreferredSize(new Dimension(width, height));
		FormLayout lookFeelPanelLayout = new FormLayout(
			"max(p;6dlu), 6dlu, 15dlu:grow, max(p;16dlu)",
			"max(p;5dlu), max(p;15dlu), max(p;5dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), 17dlu");
		lookFeelPanel.setLayout(lookFeelPanelLayout);

		labelLanguage = new JLabel();
		lookFeelPanel.add(labelLanguage, new CellConstraints("2, 8, 2, 1, default, default"));
		labelLanguage.setText(Lang.getString("TedConfigDialog.LabelLanguage"));
		labelLanguage.setBounds(231, 483, 371, 28);

		checkStartMinimized = new JCheckBox();
		lookFeelPanel.add(checkStartMinimized, new CellConstraints("3, 3, 1, 1, default, default"));
		checkStartMinimized.setText(Lang
			.getString("TedConfigDialog.CheckStartMinimized"));
		checkStartMinimized.setSelected(false);
		checkStartMinimized.setBounds(14, 164, 371, 21);

		checkAlertNewTorrent = new JCheckBox();
		lookFeelPanel.add(checkAlertNewTorrent, new CellConstraints("2, 5, 2, 1, default, default"));
		checkAlertNewTorrent.setText(Lang
			.getString("TedConfigDialog.CheckAlertNewTorrent"));
		checkAlertNewTorrent.setBounds(14, 236, 371, 21);

		checkAlertErrors = new JCheckBox();
		lookFeelPanel.add(checkAlertErrors, new CellConstraints("2, 6, 2, 1, default, default"));
		checkAlertErrors
			.setText(Lang.getString("TedConfigDialog.CheckAlertError"));
		checkAlertErrors.setBounds(14, 257, 371, 21);

		jSeparator5 = new JSeparator();
		lookFeelPanel.add(jSeparator5, new CellConstraints("2, 7, 2, 1, default, default"));
		jSeparator5.setBounds(-28, 266, 350, 7);

		comboLanguages = new JComboBox();
		lookFeelPanel.add(comboLanguages, new CellConstraints("2, 9, 2, 1, default, default"));
		//comboLanguages.setSelectedIndex(toSelect);
		comboLanguages.setBounds(21, 530, 343, 28);

		checkAddSystemTray = new JCheckBox();
		lookFeelPanel.add(checkAddSystemTray, new CellConstraints("2, 2, 2, 1, default, default"));
		checkAddSystemTray.setText(Lang.getString("TedConfigDialog.CheckAddTray"));
		checkAddSystemTray.addActionListener(this);
		checkAddSystemTray
			.setActionCommand("systray"); //$NON-NLS-1$

		jSeparator4 = new JSeparator();
		lookFeelPanel.add(jSeparator4, new CellConstraints("2, 4, 2, 1, default, default"));
		jSeparator4.setBounds(14, 45, 350, 7);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the values of the current config in the fields
	 */
	public void setValues()
	{
		checkAlertNewTorrent.setSelected(TedConfig.isShowHurray());
		checkAlertErrors.setSelected(TedConfig.isShowErrors());
		
		// when the os ted runs on does not support a tray icon
		if (!TedSystemInfo.osSupportsTray())
		{
			checkAddSystemTray.setEnabled(false);
			checkAddSystemTray.setSelected(false);
			
			checkStartMinimized.setEnabled(false);
			checkStartMinimized.setSelected(false);	
		}
		else
		{
			checkAddSystemTray.setSelected(TedConfig.isAddSysTray());
			checkStartMinimized.setSelected(TedConfig.isStartMinimized());
			
			if (!TedConfig.isAddSysTray())
			{
				this.checkStartMinimized.setSelected(false);
				this.checkStartMinimized.setEnabled(false);
			}
			
			
		}
		
		
		// fill combobox with available languages, and select current language
		String currentLanguage = TedConfig.getLocale().getDisplayLanguage();
		int toSelect = 0;
		for (int i = 0; i < locales.length; i++)
		{
			comboLanguages.addItem(locales[i].getDisplayLanguage());
			if (locales[i].getDisplayLanguage().equals(currentLanguage))
			{
				toSelect = i;
			}
		}
		comboLanguages.setSelectedIndex(toSelect);
		
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
		boolean alertHurray = checkAlertNewTorrent.isSelected();
		boolean alertError = checkAlertErrors.isSelected();
		boolean startMinimized = checkStartMinimized.isSelected();
		boolean addSysTray = checkAddSystemTray.isSelected();
		Locale languageSetting = locales[this.comboLanguages.getSelectedIndex()];
	
		TedConfig.setShowHurray(alertHurray);
		TedConfig.setShowErrors(alertError);
		TedConfig.setStartMinimized(startMinimized);
		TedConfig.setAddSysTray(addSysTray);
		TedConfig.setLocale(languageSetting);	
		
		
		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String command = arg0.getActionCommand();
		
		if (command.equals("systray"))
		{
			boolean isChecked = this.checkAddSystemTray.isSelected();
			
			if (!isChecked)
			{
				this.checkStartMinimized.setSelected(false);
			}
			
			this.checkStartMinimized.setEnabled(isChecked);
		}
		
	}

}
