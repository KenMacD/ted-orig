package ted.ui.editshowdialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

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
	//WeekSchedulePanel wPanel;
	//BreakSchedulePanel bPanel;
	private JButton buttonOpenEpguides;
	private JButton buttonOpenTVRage;
	private JTextField textTVRage;
	private JLabel labelTVRage;
	private JTextField textEpguidesID;
	private JLabel labelEpGuides;
	private JCheckBox checkAutoSchedule;
	private JLabel labelRefresh;
	private JLabel labelRefreshNext;
	private JButton buttonRefreshNow;
	private TedSerie serie;
	private final Font SMALL_FONT = new Font("Dialog",0,10);

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
					"max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), 115dlu");
			this.setLayout(thisLayout);
			/*wPanel = new WeekSchedulePanel();
			bPanel = new BreakSchedulePanel(); 
			{
				this.add(wPanel, new CellConstraints("3, 3, 5, 1, default, default"));
			}*/
			{
				checkAutoSchedule = new JCheckBox();
				this.add(checkAutoSchedule, new CellConstraints("2, 1, 5, 1, default, default"));
				checkAutoSchedule.setText(Lang.getString("TedEpisodeDialog.CheckAutoSchedule"));
				checkAutoSchedule.setActionCommand("autoupdate");
				checkAutoSchedule.addActionListener(this);
				checkAutoSchedule.setEnabled(TedConfig.isUseAutoSchedule());
			}
			{
				//this.add(bPanel, new CellConstraints("3, 5, 5, 1, default, default"));
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
					buttonOpenEpguides = new JButton();
					this.add(buttonOpenEpguides, new CellConstraints("7, 2, 1, 1, default, default"));
					buttonOpenEpguides.setText(Lang.getString("TedEpisodeDialog.ButtonOpen"));
					buttonOpenEpguides.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/EditShowDialog-feeds-open.png")));
					buttonOpenEpguides.setActionCommand("openepguides");
					buttonOpenEpguides.setToolTipText("Open Epguides in je browser");
					buttonOpenEpguides.setBounds(205, 248, 70, 21);
					buttonOpenEpguides.addActionListener(this);
				}
				{
					labelTVRage = new JLabel();
					this.add(labelTVRage, new CellConstraints("3, 3, 1, 1, default, default"));
					labelTVRage.setText("TVRage ID");
				}
				{
					textTVRage = new JTextField();
					this.add(textTVRage, new CellConstraints("5, 3, 2, 1, default, default"));
					textTVRage.setText("jTextField1");
				}
				{
					buttonOpenTVRage = new JButton();
					this.add(buttonOpenTVRage, new CellConstraints("7, 3, 1, 1, default, default"));
					buttonOpenTVRage.setText(Lang.getString("TedEpisodeDialog.ButtonOpen"));
					buttonOpenTVRage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/EditShowDialog-feeds-open.png")));
					buttonOpenTVRage.setActionCommand("opentvrage");
					buttonOpenTVRage.setToolTipText("Open TVRage in je browser");
					buttonOpenTVRage.setBounds(205, 248, 70, 21);
					buttonOpenTVRage.addActionListener(this);
				}
				{
					labelRefresh = new JLabel();
					this.add(labelRefresh, new CellConstraints("3, 4, 5, 1, default, default"));
					labelRefresh.setFont(SMALL_FONT);
				}
				{
					labelRefreshNext = new JLabel();
					labelRefreshNext.setFont(SMALL_FONT);
					this.add(labelRefreshNext, new CellConstraints("3, 5, 5, 1, default, default"));
				}
				{
					buttonRefreshNow = new JButton();
					this.add(buttonRefreshNow, new CellConstraints("6, 6, 2, 1, default, default"));
					buttonRefreshNow.setText("Refresh now!");
					buttonRefreshNow.setActionCommand("refreshschedule");
					buttonRefreshNow.addActionListener(this);
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
		//wPanel.saveValues(serie);
		//bPanel.saveValues(serie);
		
		serie.setUseAutoSchedule(this.checkAutoSchedule.isSelected());
		serie.setEpguidesName(this.textEpguidesID.getText());
		serie.setTVRageID(this.textTVRage.getText());
	}
	
	public void setValues(TedSerie serie)
	{
		this.serie = serie;
		//wPanel.setValues(serie);
		//bPanel.setValues(serie);
		
		this.checkAutoSchedule.setSelected(serie.isUseAutoSchedule());
		this.textEpguidesID.setText(serie.getEpguidesName());
		this.textTVRage.setText(serie.getTVRageID());
		
		// Format the date
		String lastRefresh = this.formatDate(serie.getScheduleLastUpdateDate());
		String nextRefresh = this.formatDate(serie.getScheduleNextUpdateDate());
		
		this.labelRefresh.setText("The schedule was last refreshed on "
									+ lastRefresh
									+ ".");
		
		this.labelRefreshNext.setText("The next refresh is scheduled for "
									+ nextRefresh
									+ ".");
		
		this.updatePanels();
	}
	
	private String formatDate(Date date) 
	{
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
		return df.format(date);
	}

	public boolean checkValues()
	{
		return true;
		//return bPanel.checkValues();
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
		else if (command.equals("opentvrage"))
		{
			String epguidesid = this.textTVRage.getText();
			try
			{
				BrowserLauncher.openURL("http://www.tvrage.com/shows/id-" + epguidesid + "/");
				
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
		else if (command.equals("refreshschedule"))
		{
			this.saveValues(serie);
			serie.refreshSchedule();
			this.setValues(serie);
		}
		
	}

	private void updatePanels()
	{
		boolean isAutoSchedule = checkAutoSchedule.isSelected();
		boolean autoScheduleGloballyEnabled = TedConfig.isUseAutoSchedule();
		
		this.labelEpGuides.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.textEpguidesID.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.buttonOpenEpguides.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.labelTVRage.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.textTVRage.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.buttonOpenTVRage.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		
		this.labelRefresh.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.labelRefreshNext.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		this.buttonRefreshNow.setEnabled(isAutoSchedule && autoScheduleGloballyEnabled);
		//wPanel.setContentsEnabled(!isAutoSchedule || !autoScheduleGloballyEnabled);
		//bPanel.setContentsEnabled(!isAutoSchedule || !autoScheduleGloballyEnabled);
	}
}
