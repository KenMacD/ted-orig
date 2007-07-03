package ted.ui.editshowdialog;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;

import ted.Lang;
import ted.TedLog;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = -8342065043462454211L;
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
	private JComboBox jBreakYear;
	private JComboBox jBreakMonth;
	private JComboBox jBreakDay;
	private JComboBox jFromBreakYear;
	private JComboBox jFromBreakMonth;
	private String[] years;
	private int yearDiff;
	private boolean wasUseBreakSchedule;
	
	public SchedulePanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
				"max(p;5dlu), 10dlu, max(p;40dlu), 5dlu, 37dlu, max(p;15dlu), max(p;5dlu), max(p;10px)",
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
				this.add(jScheduleText, new CellConstraints("3, 3, 5, 1, default, default"));
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
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("2, 7, 6, 1, default, default"));
			}
			{
				jCheckBreakSchedule = new JCheckBox();
				this.add(jCheckBreakSchedule, new CellConstraints("2, 8, 5, 1, default, default"));
				jCheckBreakSchedule.setActionCommand("breakschedule");
				jCheckBreakSchedule.setText(Lang
					.getString("TedEpisodeDialog.CheckBreakSchedule"));
				jCheckBreakSchedule.setOpaque(false);
				jCheckBreakSchedule.addActionListener(this);

			}
			{
				jCheckBoxBreakEpisode = new JCheckBox();
				this.add(jCheckBoxBreakEpisode, new CellConstraints("3, 9, 1, 1, default, default"));
				jCheckBoxBreakEpisode.setActionCommand("breakschedule");
				jCheckBoxBreakEpisode.setOpaque(false);
				jCheckBoxBreakEpisode.addActionListener(this);
				jCheckBoxBreakEpisode.setText(Lang.getString("TedEpisodeDialog.LabelBreakEpisode"));
			}
			{
				jBreakEpisode = new JTextField();
				this.add(jBreakEpisode, new CellConstraints("5, 9, 1, 1, default, default"));
			}
			{
				jCheckBoxBreakFrom = new JCheckBox();
				this.add(jCheckBoxBreakFrom, new CellConstraints("3, 10, 1, 1, default, default"));
				jCheckBoxBreakFrom.setActionCommand("breakschedule");
				jCheckBoxBreakFrom.setOpaque(false);
				jCheckBoxBreakFrom.addActionListener(this);
				jCheckBoxBreakFrom.setText(Lang.getString("TedEpisodeDialog.LabelBreakFrom"));
			}
			{
				jBreakLabel2 = new JLabel();
				this.add(jBreakLabel2, new CellConstraints("3, 11, 1, 1, default, default"));
				jBreakLabel2.setText(Lang
					.getString("TedEpisodeDialog.LabelBreakHold"));
			}
			{
				ComboBoxModel jFromBreakDayModel = new DefaultComboBoxModel(
						days);
				jFromBreakDay = new JComboBox();
				this.add(jFromBreakDay, new CellConstraints("5, 10, 1, 1, default, default"));
				jFromBreakDay.setModel(jFromBreakDayModel);
				jFromBreakDay.setBounds(223, 254, 63, 28);
			}
			{
				ComboBoxModel jFromBreakMonthModel = new DefaultComboBoxModel(months);
				jFromBreakMonth = new JComboBox();
				this.add(jFromBreakMonth, new CellConstraints("6, 10, 1, 1, default, default"));
				jFromBreakMonth.setModel(jFromBreakMonthModel);
				jFromBreakMonth.setBounds(294, 254, 63, 28);
			}
			{
				
				jFromBreakYear = new JComboBox();
				this.add(jFromBreakYear, new CellConstraints("7, 10, 1, 1, default, default"));
				
				jFromBreakYear.setBounds(365, 254, 63, 28);
			}
			{
				ComboBoxModel jBreakDayModel = new DefaultComboBoxModel(days);
				jBreakDay = new JComboBox();
				this.add(jBreakDay, new CellConstraints("5, 11, 1, 1, default, default"));
				jBreakDay.setModel(jBreakDayModel);
				jBreakDay.setBounds(223, 294, 63, 28);
			}
			{
				ComboBoxModel jBreakMonthModel = new DefaultComboBoxModel(months);
				jBreakMonth = new JComboBox();
				this.add(jBreakMonth, new CellConstraints("6, 11, 1, 1, default, default"));
				jBreakMonth.setModel(jBreakMonthModel);
				jBreakMonth.setBounds(294, 294, 63, 28);
			}
			{
				
				jBreakYear = new JComboBox();
				this.add(jBreakYear, new CellConstraints("7, 11, 1, 1, default, default"));

				jBreakYear.setBounds(365, 294, 63, 28);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setValues(TedSerie serie)
	{
		this.initYears(serie);
		
		
		this.jBreakEpisode.setText("" + serie.getBreakEpisode()); //$NON-NLS-1$
		this.jCheckBreakSchedule.setSelected(serie.isUseBreakSchedule());
		this.jCheckBoxBreakEpisode.setSelected(serie.isUseBreakScheduleEpisode());
		this.jCheckBoxBreakFrom.setSelected(serie.isUseBreakScheduleFrom());
		this.jCheckSchedule.setSelected(serie.isUseEpisodeSchedule());
		
		this.initDays(serie);
		
		this.initBreakDate(serie.getBreakFrom(), serie.getBreakUntil());
		this.scheduleUpdate();
		this.breakUpdate();
		
		//	for updating the show status by (un)checking the break scheduler
		this.wasUseBreakSchedule = serie.isUseBreakSchedule();
	}
	
	/**
	 * Save values from the panel into the serie
	 * @param currentSerie
	 */
	public void saveValues(TedSerie currentSerie)
	{
		if (this.checkValues())
		{
			int breakep = Integer.parseInt(jBreakEpisode.getText());
			
			currentSerie.setEpisodeSchedule(jCheckSchedule.isSelected(), this.getDays());
			currentSerie.setWeeklyInterval(1);
			currentSerie.setUseBreakSchedule(jCheckBreakSchedule.isSelected());
			currentSerie.setUseBreakScheduleEpisode(jCheckBoxBreakEpisode.isSelected());
			currentSerie.setUseBreakScheduleFrom(jCheckBoxBreakFrom.isSelected());
			
			Calendar c = new GregorianCalendar();
			int day2  = jBreakDay.getSelectedIndex();
			int month = jBreakMonth.getSelectedIndex();
			int year  = jBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
			
			int fday   = jFromBreakDay.getSelectedIndex();
			int fmonth = jFromBreakMonth.getSelectedIndex();
			int fyear  = jFromBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
			
			currentSerie.setBreakUntil(day2, month, year);
			currentSerie.setBreakFrom(fday, fmonth, fyear);
			currentSerie.setBreakEpisode(breakep);
			
			//	when the user wants to put the show immediatly on hold
			if(!(currentSerie.isUseBreakScheduleEpisode() || currentSerie.isUseBreakScheduleFrom()))
			{
				if(currentSerie.isUseBreakSchedule() && (currentSerie.getStatus() == TedSerie.STATUS_CHECK))
					currentSerie.setStatus(TedSerie.STATUS_HOLD);
				else if(this.wasUseBreakSchedule && !currentSerie.isUseBreakSchedule())
					currentSerie.setStatus(TedSerie.STATUS_CHECK);
			}
		}

	}
	
	/**
	 * @return Whether all values are filled in correctly
	 */
	boolean checkValues()
	{
		if (jCheckBreakSchedule.isSelected() && jCheckBoxBreakEpisode.isSelected())
		{
			int breakep;
			
			try
			{
				breakep = Integer.parseInt(jBreakEpisode.getText());
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogNumericBreakEpisode") + //$NON-NLS-1$
						Lang.getString("TedEpisodeDialog.DialogNumericBreakEpisode2")); //$NON-NLS-1$
				return false;
			}
		}
		else
		{
			jBreakEpisode.setText(""+0);
		}
		Calendar c = new GregorianCalendar();
		int day2  = jBreakDay.getSelectedIndex();
		int month = jBreakMonth.getSelectedIndex();
		int year  = jBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
		
		int fday   = jFromBreakDay.getSelectedIndex();
		int fmonth = jFromBreakMonth.getSelectedIndex();
		int fyear  = jFromBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
		
		if(jCheckBreakSchedule.isSelected() && jCheckBoxBreakFrom.isSelected())
		{
			if((year<fyear) || (year==fyear && month<fmonth) || (year==fyear && month==fmonth && day2<fday))
			{
				JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogFromDateLargerThanBreak")); //$NON-NLS-1$
				return false;
			}
			
			if(year==fyear && month==fmonth && day2==fday)
			{
				JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogFromIsBreak")); //$NON-NLS-1$
				return false;
			}
		}
		return true;
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
		boolean b = jCheckBreakSchedule.isSelected();
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
		jBreakLabel2.setEnabled(b);
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
	
	private void initYears(TedSerie currentSerie)
	{
		//	set the years which has to be put in the drop down menu
		Calendar c = new GregorianCalendar();
		int year1 = c.get(Calendar.YEAR);
		long x = currentSerie.getBreakUntil();
		long y = currentSerie.getBreakFrom();
		long z;
		
		if(x<y)
			z = x;
		else
			z = y;
		
		// take the from year as most early year except when it's a new show
		if(z!=0)
			c.setTimeInMillis(z);
		
		int year2 = c.get(Calendar.YEAR);
		years = initString(year2, year2+5);
		
		if(year2 < year1)
			yearDiff = year1 - year2;
		
		ComboBoxModel jBreakYearModel = new DefaultComboBoxModel(years);
		jBreakYear.setModel(jBreakYearModel);
		
		ComboBoxModel jFromBreakYearModel = new DefaultComboBoxModel(years);
		jFromBreakYear.setModel(jFromBreakYearModel);
		
	}
	
	/**
	 * Select the corresponding day, month and year in the dialog
	 * @param time
	 */
	private void initBreakDate (long from, long until)
	{
		Calendar c  = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		Calendar c3 = new GregorianCalendar();
		
		if(from != 0 || until != 0)
		{
			/* If break dates are specified fill them in */
			if(until!=0)
				c.setTimeInMillis(until);
			
			if(from!=0)
				c2.setTimeInMillis(from);			
		}
		/*else if(!jCheckBreakSchedule.isSelected())
		{
			/* If break schedule not checked at all fill in for both date the 
			 * date of today
			 
			
			c  = c3;
			c2 = c3;
		}
		else if(!jCheckBoxBreakFrom.isSelected())
		{
			/* If not break from is checked set from date to today 
			c2 = c3;
		}*/
		

		try
		{
			jBreakDay.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
			jBreakMonth.setSelectedIndex(c.get(Calendar.MONTH));
			jBreakYear.setSelectedIndex(c.get(Calendar.YEAR)-c3.get(Calendar.YEAR) + yearDiff);
								
			jFromBreakDay.setSelectedIndex(c2.get(Calendar.DAY_OF_MONTH)-1);
			jFromBreakMonth.setSelectedIndex(c2.get(Calendar.MONTH));
			jFromBreakYear.setSelectedIndex(c2.get(Calendar.YEAR)-c3.get(Calendar.YEAR) + yearDiff);
		}
		catch(Exception e)
		{
			TedLog.debug(Lang.getString("TedEpisodeDialog.LogWrongDates") + //$NON-NLS-1$
					 c.get(Calendar.DAY_OF_MONTH)  + "-" + (c.get(Calendar.MONTH)+1)  + "-" + c.get(Calendar.YEAR)  + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					 c2.get(Calendar.DAY_OF_MONTH) + "-" + (c2.get(Calendar.MONTH)+1) + "-" + c2.get(Calendar.YEAR) + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					 c3.get(Calendar.DAY_OF_MONTH) + "-" + (c3.get(Calendar.MONTH)+1) + "-" + c3.get(Calendar.YEAR) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
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

}
