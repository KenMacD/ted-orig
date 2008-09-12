package ted.ui.editshowdialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ted.BrowserLauncher;
import ted.Lang;
import ted.TedSerie;
import ted.TedConfig;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

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
public class SchedulePanel extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8342065043462454211L;
	private JSeparator jSeparator1;
	WeekSchedulePanel wPanel;
	BreakSchedulePanel bPanel;
	private JButton jOpenButton;
	private JTextField textEpguidesID;
	private JLabel labelEpGuides;
	private JCheckBox checkAutoSchedule;

	public SchedulePanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), max(p;15dlu):grow, max(p;15dlu), max(p;15dlu)", 
					"max(p;15dlu), max(p;15dlu), max(p;66dlu), 5dlu, 115dlu");
			this.setLayout(thisLayout);
			wPanel = new WeekSchedulePanel();
			bPanel = new BreakSchedulePanel(); 
			{
				this.add(wPanel, new CellConstraints("3, 3, 5, 1, default, default"));
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("3, 4, 5, 1, default, default"));
			}
			{
				checkAutoSchedule = new JCheckBox();
				this.add(checkAutoSchedule, new CellConstraints("2, 1, 5, 1, default, default"));
				checkAutoSchedule.setText(Lang.getString("TedEpisodeDialog.CheckAutoSchedule"));
				checkAutoSchedule.setActionCommand("autoupdate");
				checkAutoSchedule.addActionListener(this);
				checkAutoSchedule.setEnabled(TedConfig.isUseAutoSchedule());
			}
			{
				this.add(bPanel, new CellConstraints("3, 5, 5, 1, default, default"));
				{
					labelEpGuides = new JLabel();
					this.add(labelEpGuides, new CellConstraints("3, 2, 1, 1, default, default"));
					labelEpGuides.setText("Epguides ID");
				}
				{
					textEpguidesID = new JTextField();
					this.add(textEpguidesID, new CellConstraints("5, 2, 2, 1, default, default"));
				}
				{
					jOpenButton = new JButton();
					this.add(jOpenButton, new CellConstraints("7, 2, 1, 1, default, default"));
					jOpenButton.setText(Lang.getString("TedEpisodeDialog.ButtonOpen"));
					jOpenButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/EditShowDialog-feeds-open.png")));
					jOpenButton.setActionCommand("openepguides");
					jOpenButton.setToolTipText(Lang.getString("TedEpisodeDialog.ButtonToolTipOpen"));
					jOpenButton.setBounds(205, 248, 70, 21);
					jOpenButton.addActionListener(this);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void saveValues(TedSerie serie)
	{
		wPanel.saveValues(serie);
		bPanel.saveValues(serie);
		
		serie.setUseAutoSchedule(this.checkAutoSchedule.isSelected());
		serie.setEpguidesName(this.textEpguidesID.getText());
	}
	
	public void setValues(TedSerie serie)
	{
		wPanel.setValues(serie);
		bPanel.setValues(serie);
		
		this.checkAutoSchedule.setSelected(serie.isUseAutoSchedule());
		this.textEpguidesID.setText(serie.getEpguidesName());
		
		this.updatePanels();
	}
	
	public boolean checkValues()
	{
		return bPanel.checkValues();
	}

	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if (command.equals("autoupdate"))
		{
			updatePanels();
		}
		else if (command.equals("openepguides"))
		{
			String epguidesid = this.textEpguidesID.getText();
			try
			{
				BrowserLauncher.openURL("http://www.epguides.com/" + epguidesid + "/");
				
			} 
			catch (MalformedURLException e1)
			{
				// show popup?
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

	private void updatePanels()
	{
		boolean isAutoSchedule = checkAutoSchedule.isSelected();
		boolean autoScheduleGloballyEnabled = TedConfig.isUseAutoSchedule();
		
		this.labelEpGuides.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.textEpguidesID.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.jOpenButton.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		
		wPanel.setContentsEnabled(!isAutoSchedule || !autoScheduleGloballyEnabled);
		bPanel.setContentsEnabled(!isAutoSchedule || !autoScheduleGloballyEnabled);
	}
}
