package ted.ui.editshowdialog;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JSeparator;
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
public class SchedulePanel extends JPanel implements ActionListener
{
	private int width = 400;
	private JCheckBox jCheckThursday;
	private JComboBox jFromBreakDay;
	private JLabel jBreakLabel2;
	private JCheckBox jCheckBoxBreakFrom;
	private JTextField jBreakEpisode;
	private JCheckBox jCheckBoxBreakEpisode;
	private JCheckBox jCheckBreakSchedule;
	private JSeparator jSeparator1;
	private JCheckBox jCheckSunday;
	private JCheckBox jCheckSaturday;
	private JCheckBox jCheckFriday;
	private JCheckBox jCheckWednesday;
	private JCheckBox jCheckTuesday;
	private JCheckBox jCheckMonday;
	private JLabel jLabel16;
	private JLabel jScheduleText;
	private JCheckBox jCheckSchedule;
	private int height = 300;
	
	private String[] months = {Lang.getString("TedEpisodeDialog.MonthJan"), Lang.getString("TedEpisodeDialog.MonthFeb"), Lang.getString("TedEpisodeDialog.MonthMar"), Lang.getString("TedEpisodeDialog.MonthApr"), Lang.getString("TedEpisodeDialog.MonthMay"), Lang.getString("TedEpisodeDialog.MonthJun"), Lang.getString("TedEpisodeDialog.MonthJul"), Lang.getString("TedEpisodeDialog.MonthAug"), Lang.getString("TedEpisodeDialog.MonthSep"), Lang.getString("TedEpisodeDialog.MonthOct"), Lang.getString("TedEpisodeDialog.MonthNov"), Lang.getString("TedEpisodeDialog.MonthDec")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
	private String[] days  = initString(1, 31);
	private String[] years;
	
	public SchedulePanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
				"max(p;5dlu), 10dlu, max(p;40dlu), max(p;45dlu):grow, max(p;5dlu), max(p;10px)",
				"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu):grow, max(p;10px)");
			this.setLayout(thisLayout);
			this.setPreferredSize(new Dimension(width, height));
			{
				jCheckSchedule = new JCheckBox();
				this.add(jCheckSchedule, new CellConstraints("2, 2, 4, 1, default, default"));
				jCheckSchedule.setActionCommand("schedule");
				jCheckSchedule.setText(Lang
					.getString("TedEpisodeDialog.CheckEpisodeSchedule"));
				jCheckSchedule.setOpaque(false);
				jCheckSchedule.setBounds(8, 4, 229, 30);
				jCheckSchedule.addActionListener(this);
			}
			{
				jScheduleText = new JLabel();
				this.add(jScheduleText, new CellConstraints("3, 3, 3, 1, default, default"));
				jScheduleText.setText(Lang
					.getString("TedEpisodeDialog.LabelEpisodeSchedule"));
				{
					jLabel16 = new JLabel();
					jScheduleText.add(jLabel16);
					jLabel16
						.setText(Lang
							.getString("TedEpisodeDialog.LabelEpisodeScheduleCheck"));
					jLabel16.setBounds(24, 61, 192, 30);
				}
			}
			{
				jCheckMonday = new JCheckBox();
				this.add(jCheckMonday, new CellConstraints("3, 4, 1, 1, default, default"));
				jCheckMonday.setText(Lang.getString("TedEpisodeDialog.Monday"));
				jCheckMonday.setOpaque(false);
			}
			{
				jCheckTuesday = new JCheckBox();
				this.add(jCheckTuesday, new CellConstraints("4, 4, 1, 1, default, default"));
				jCheckTuesday.setText(Lang
					.getString("TedEpisodeDialog.Tuesday"));
				jCheckTuesday.setOpaque(false);
			}
			{
				jCheckWednesday = new JCheckBox();
				this.add(jCheckWednesday, new CellConstraints("5, 4, 1, 1, default, default"));
				jCheckWednesday.setText(Lang
					.getString("TedEpisodeDialog.Wednesday"));
				jCheckWednesday.setOpaque(false);
			}
			{
				jCheckThursday = new JCheckBox();
				this.add(jCheckThursday, new CellConstraints("3, 5, 1, 1, default, default"));
				jCheckThursday.setText(Lang
					.getString("TedEpisodeDialog.Thursday"));
				jCheckThursday.setOpaque(false);
				jCheckThursday.setBounds(23, 95, 133, 28);
			}
			{
				jCheckFriday = new JCheckBox();
				this.add(jCheckFriday, new CellConstraints("4, 5, 1, 1, default, default"));
				jCheckFriday.setText(Lang.getString("TedEpisodeDialog.Friday"));
				jCheckFriday.setOpaque(false);
				jCheckFriday.setBounds(159, 96, 119, 28);
			}
			{
				jCheckSaturday = new JCheckBox();
				this.add(jCheckSaturday, new CellConstraints("5, 5, 1, 1, default, default"));
				jCheckSaturday.setText(Lang
					.getString("TedEpisodeDialog.Saturday"));
				jCheckSaturday.setOpaque(false);
				jCheckSaturday.setBounds(284, 95, 161, 28);
			}
			{
				jCheckSunday = new JCheckBox();
				this.add(jCheckSunday, new CellConstraints("3, 6, 1, 1, default, default"));
				jCheckSunday.setText(Lang.getString("TedEpisodeDialog.Sunday"));
				jCheckSunday.setOpaque(false);
				jCheckSunday.setBounds(23, 123, 133, 28);
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("2, 7, 4, 1, default, default"));
			}
			{
				jCheckBreakSchedule = new JCheckBox();
				this.add(jCheckBreakSchedule, new CellConstraints("2, 8, 4, 1, default, default"));
				jCheckBreakSchedule.setActionCommand("breakschedule");
				jCheckBreakSchedule.setText(Lang
					.getString("TedEpisodeDialog.CheckBreakSchedule"));
				jCheckBreakSchedule.setOpaque(false);
				jCheckBreakSchedule.setBounds(7, 182, 434, 28);

			}
			{
				jCheckBoxBreakEpisode = new JCheckBox();
				this.add(jCheckBoxBreakEpisode, new CellConstraints("3, 9, 1, 1, default, default"));
				jCheckBoxBreakEpisode.setActionCommand("breakschedule");
				jCheckBoxBreakEpisode.setOpaque(false);
				jCheckBoxBreakEpisode.setBounds(20, 210, 60, 30);
				jCheckBoxBreakEpisode.addActionListener(this);
				jCheckBoxBreakEpisode.setText(Lang.getString("TedEpisodeDialog.LabelBreakEpisode"));
			}
			{
				jBreakEpisode = new JTextField();
				this.add(jBreakEpisode, new CellConstraints("4, 9, 1, 1, default, default"));
				jBreakEpisode.setBounds(223, 213, 60, 30);
			}
			{
				jCheckBoxBreakFrom = new JCheckBox();
				this.add(jCheckBoxBreakFrom, new CellConstraints("3, 10, 1, 1, default, default"));
				jCheckBoxBreakFrom.setActionCommand("breakschedule");
				jCheckBoxBreakFrom.setOpaque(false);
				jCheckBoxBreakFrom.setBounds(21, 253, 21, 28);
				jCheckBoxBreakFrom.addActionListener(this);
				jCheckBoxBreakFrom.setText(Lang.getString("TedEpisodeDialog.LabelBreakFrom"));
			}
			{
				jBreakLabel2 = new JLabel();
				this.add(jBreakLabel2, new CellConstraints("3, 11, 1, 1, default, default"));
				jBreakLabel2.setText(Lang
					.getString("TedEpisodeDialog.LabelBreakHold"));
				jBreakLabel2.setBounds(21, 294, 203, 28);
			}
			{
				ComboBoxModel jFromBreakDayModel = new DefaultComboBoxModel(
						days);
				jFromBreakDay = new JComboBox();
				this.add(jFromBreakDay, new CellConstraints("4, 10, 1, 1, default, default"));
				jFromBreakDay.setModel(jFromBreakDayModel);
				jFromBreakDay.setBounds(223, 254, 63, 28);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setValues(TedSerie serie)
	{
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		//	handle events on the episode dialog
		String action = arg0.getActionCommand();
		
		
		if (action.equals("schedule"))
		{
			this.scheduleUpdate();
		}
		else if (action.equals("breakschedule"))
		{
			this.breakUpdate();
		}
		
	}
	
	/**
	 * If the user wants to use the schedule, set the fields enabled.
	 */
	private void scheduleUpdate()
	{
		boolean b = jCheckSchedule.isSelected();
		
		// set all the days and labels for the schedule 
		jCheckSunday.setEnabled(b);
		jCheckMonday.setEnabled(b);
		jCheckTuesday.setEnabled(b);
		jCheckWednesday.setEnabled(b);
		jCheckThursday.setEnabled(b);
		jCheckFriday.setEnabled(b);
		jCheckSaturday.setEnabled(b);
		jScheduleText.setEnabled(b);
	}
	

	/**
	 * If the user wants to use the break, set the fields to enabled
	 */
	private void breakUpdate()
	{
		/*boolean b = jCheckBreakSchedule.isSelected();
		boolean e = jCheckBoxBreakEpisode.isSelected();
		boolean f = jCheckBoxBreakFrom.isSelected();
		
		// set all the days and labels for the schedule 
		jCheckBoxBreakEpisode.setEnabled(b);
		jCheckBoxBreakFrom.setEnabled(b);
		
		jBreakYear.setEnabled(b);
		jBreakMonth.setEnabled(b);
		jBreakDay.setEnabled(b);
		jFromBreakYear.setEnabled(b&&f);
		jFromBreakMonth.setEnabled(b&&f);
		jFromBreakDay.setEnabled(b&&f);
		jBreakEpisode.setEnabled(b&&e);
		jBreakLabel.setEnabled(b);
		jBreakLabel2.setEnabled(b);
		jBreakLabel3.setEnabled(b);*/
	}
	
	/**
	 * Initialize a array with strings
	 * @param low first value of the array
	 * @param high last value of the array
	 * @return A array filled with entries between low and high
	 */
	private String[] initString(int low, int high) 
	{
		String [] strings = new String [high-low+1];
		
		for (int i = 0; i+low <= high; i++)
		{
			strings[i] = ""+(low+i); //$NON-NLS-1$
		}
		
		return strings;
	}

}
