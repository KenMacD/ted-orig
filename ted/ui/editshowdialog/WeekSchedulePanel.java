package ted.ui.editshowdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ted.Lang;
import ted.TedSerie;

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
public class WeekSchedulePanel extends JPanel implements ActionListener
{
	JCheckBox jCheckSchedule;
	private JCheckBox jCheckThursday;
	private JCheckBox jCheckSunday;
	private JCheckBox jCheckSaturday;
	private JCheckBox jCheckFriday;
	private JCheckBox jCheckWednesday;
	private JCheckBox jCheckTuesday;
	private JCheckBox jCheckMonday;
	private JLabel jLabelEpisode;
	private JLabel jScheduleText;
	
	public WeekSchedulePanel() 
	{
		initGUI();
	}
	
	private void initGUI()
	{
		FormLayout thisLayout = new FormLayout(
			"5dlu, 10dlu, 30dlu:grow, 5dlu, 30dlu:grow, 5dlu, 30dlu:grow, 10px",
			"max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;5dlu), max(p;15dlu), max(p;10px)");
		this.setLayout(thisLayout);
		{
			jCheckSchedule = new JCheckBox();
			this.add(jCheckSchedule, new CellConstraints("2, 2, 6, 1, default, default"));
			jCheckSchedule.setActionCommand("schedule");
			jCheckSchedule.setText(Lang
				.getString("TedEpisodeDialog.CheckEpisodeSchedule"));
			jCheckSchedule.setOpaque(false);
			jCheckSchedule.setBounds(8, 4, 229, 30);
			jCheckSchedule.addActionListener(this);
		}
		{
			jScheduleText = new JLabel();
			this.add(jScheduleText, new CellConstraints("3, 3, 5, 1, default, default"));
			jScheduleText.setText(Lang
				.getString("TedEpisodeDialog.LabelEpisodeSchedule"));
			{
				jLabelEpisode = new JLabel();
				jScheduleText.add(jLabelEpisode);
				jLabelEpisode
					.setText(Lang
						.getString("TedEpisodeDialog.LabelEpisodeScheduleCheck"));
				jLabelEpisode.setBounds(24, 61, 192, 30);
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
			this.add(jCheckTuesday, new CellConstraints("5, 4, 2, 1, default, default"));
			jCheckTuesday.setText(Lang
				.getString("TedEpisodeDialog.Tuesday"));
			jCheckTuesday.setOpaque(false);
		}
		{
			jCheckWednesday = new JCheckBox();
			this.add(jCheckWednesday, new CellConstraints("7, 4, 1, 1, default, default"));
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
			this.add(jCheckFriday, new CellConstraints("5, 5, 2, 1, default, default"));
			jCheckFriday.setText(Lang.getString("TedEpisodeDialog.Friday"));
			jCheckFriday.setOpaque(false);
			jCheckFriday.setBounds(159, 96, 119, 28);
		}
		{
			jCheckSaturday = new JCheckBox();
			this.add(jCheckSaturday, new CellConstraints("7, 5, 1, 1, default, default"));
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
	}
	
	public void setValues(TedSerie serie)
	{
		jCheckSchedule.setSelected(serie.isUseEpisodeSchedule());
		this.initDays(serie);
		this.scheduleUpdate();
	}

	/**
	 * Init the checked days in the schedule
	 */
	private void initDays(TedSerie serie)
	{
		boolean [] days = serie.getDays();
		
		jCheckSunday.setSelected(days[Calendar.SUNDAY-1]);
		jCheckMonday.setSelected(days[Calendar.MONDAY-1]);
		jCheckTuesday.setSelected(days[Calendar.TUESDAY-1]);
		jCheckWednesday.setSelected(days[Calendar.WEDNESDAY-1]);
		jCheckThursday.setSelected(days[Calendar.THURSDAY-1]);
		jCheckFriday.setSelected(days[Calendar.FRIDAY-1]);
		jCheckSaturday.setSelected(days[Calendar.SATURDAY-1]);
	}
	
	/**
	 * If the user wants to use the schedule, set the fields enabled.
	 */
	public void scheduleUpdate()
	{
		boolean b = jCheckSchedule.isSelected();
		
		setScheduleEnabled(b);
	}
	
	/**
	 * Save values from the panel into the serie
	 * @param currentSerie
	 */
	public void saveValues(TedSerie currentSerie)
	{
		currentSerie.setEpisodeSchedule(jCheckSchedule.isSelected(), this.getDays());
		currentSerie.setWeeklyInterval(1);
	}

	/**
	 * @return Array of checked days in the schedule
	 */
	private boolean[] getDays()
	{
		boolean [] days = new boolean [7];
		days[Calendar.SUNDAY-1] = jCheckSunday.isSelected();
		days[Calendar.MONDAY-1] = jCheckMonday.isSelected();
		days[Calendar.TUESDAY-1] = jCheckTuesday.isSelected();
		days[Calendar.WEDNESDAY-1] = jCheckWednesday.isSelected();
		days[Calendar.THURSDAY-1] = jCheckThursday.isSelected();
		days[Calendar.FRIDAY-1] = jCheckFriday.isSelected();
		days[Calendar.SATURDAY-1] = jCheckSaturday.isSelected();
		
		return days;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		scheduleUpdate();
	}

	public void setContentsEnabled(boolean enabled)
	{	
		jCheckSchedule.setEnabled(enabled);
		jScheduleText.setEnabled(enabled);
		jLabelEpisode.setEnabled(enabled);
		
		if (enabled)
		{
			// check for disabled items according to checkboxes
			this.scheduleUpdate();
		}
		else
		{
			// set all items to enabled bool
			setScheduleEnabled(enabled);
		}
	}
	
	public void setScheduleEnabled(boolean enabled)
	{
		// set all the days and labels for the schedule 
		jCheckSunday.setEnabled(enabled);
		jCheckMonday.setEnabled(enabled);
		jCheckTuesday.setEnabled(enabled);
		jCheckWednesday.setEnabled(enabled);
		jCheckThursday.setEnabled(enabled);
		jCheckFriday.setEnabled(enabled);
		jCheckSaturday.setEnabled(enabled);
		jScheduleText.setEnabled(enabled);
	}
}
