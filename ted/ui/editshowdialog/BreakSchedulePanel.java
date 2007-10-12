package ted.ui.editshowdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ted.Lang;
import ted.TedLog;
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
public class BreakSchedulePanel extends JPanel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7141843947943884130L;
	private JLabel jBreakLabel2;
	private JCheckBox jCheckBoxBreakFrom;
	private JTextField jBreakEpisode;
	private JCheckBox jCheckBoxBreakEpisode;
	private JCheckBox jCheckBreakSchedule;
	private boolean wasUseBreakSchedule;
	
	private DatePanel fromBreakDatePanel;
	private DatePanel untilBreakDatePanel;

	public BreakSchedulePanel()
	{
		initGUI();
	}
	
	private void initGUI()
	{
		FormLayout thisLayout = new FormLayout(
			"5dlu, 10dlu, max(m;100dlu), 5dlu, 40dlu, 10px:grow",
			"max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;15dlu):grow, max(p;10px)");
		this.setLayout(thisLayout);
		{
			jCheckBreakSchedule = new JCheckBox();
			this.add(jCheckBreakSchedule, new CellConstraints("2, 1, 4, 1, default, default"));
			jCheckBreakSchedule.setActionCommand("breakschedule");
			jCheckBreakSchedule.setText(Lang
				.getString("TedEpisodeDialog.CheckBreakSchedule"));
			jCheckBreakSchedule.setOpaque(false);
			jCheckBreakSchedule.addActionListener(this);

		}
		{
			jCheckBoxBreakEpisode = new JCheckBox();
			this.add(jCheckBoxBreakEpisode, new CellConstraints("3, 2, 1, 1, default, default"));
			jCheckBoxBreakEpisode.setActionCommand("breakschedule");
			jCheckBoxBreakEpisode.setOpaque(false);
			jCheckBoxBreakEpisode.addActionListener(this);
			jCheckBoxBreakEpisode.setText(Lang.getString("TedEpisodeDialog.LabelBreakEpisode"));
		}
		{
			jBreakEpisode = new JTextField();
			this.add(jBreakEpisode, new CellConstraints("5, 2, 1, 1, default, default"));
		}
		{
			jCheckBoxBreakFrom = new JCheckBox();
			this.add(jCheckBoxBreakFrom, new CellConstraints("3, 3, 3, 1, default, default"));
			jCheckBoxBreakFrom.setActionCommand("breakschedule");
			jCheckBoxBreakFrom.setOpaque(false);
			jCheckBoxBreakFrom.addActionListener(this);
			jCheckBoxBreakFrom.setText(Lang.getString("TedEpisodeDialog.LabelBreakFrom"));
		}
		{
			fromBreakDatePanel = new DatePanel();
			this.add(fromBreakDatePanel, new CellConstraints("3, 4, 3, 1, left, default"));
			untilBreakDatePanel = new DatePanel();
			this.add(untilBreakDatePanel, new CellConstraints("3, 6, 3, 1, left, default"));
			
		}
		{
			jBreakLabel2 = new JLabel();
			this.add(jBreakLabel2, new CellConstraints("3, 5, 3, 1, default, default"));
			jBreakLabel2.setText(Lang
				.getString("TedEpisodeDialog.LabelBreakHold"));
		}
	}
	
	public void setValues(TedSerie serie)
	{ 		
		jBreakEpisode.setText("" + serie.getBreakEpisode()); //$NON-NLS-1$
		jCheckBreakSchedule.setSelected(serie.isUseBreakSchedule());
		jCheckBoxBreakEpisode.setSelected(serie.isUseBreakScheduleEpisode());
		jCheckBoxBreakFrom.setSelected(serie.isUseBreakScheduleFrom());
		
		
		this.initBreakDate(serie.getBreakFrom(), serie.getBreakUntil());
		
		this.breakUpdate();
		
		//	for updating the show status by (un)checking the break scheduler
		this.wasUseBreakSchedule = serie.isUseBreakSchedule();
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
		
		if(jCheckBreakSchedule.isSelected() && (from != 0 || until != 0))
		{
			/* If break dates are specified fill them in */
			if(until!=0)
				c.setTimeInMillis(until);
			
			if(from!=0)
				c2.setTimeInMillis(from);			
		}
		else if(!jCheckBreakSchedule.isSelected())
		{
			// If break schedule not checked at all fill in for both date the 
			// date of today
			c  = c3;
			c2 = c3;
		}
		else if(!jCheckBoxBreakFrom.isSelected())
		{
			// If not break from is checked set from date to today 
			c2 = c3;
		}

		try
		{		
			untilBreakDatePanel.setDate(c);
			fromBreakDatePanel.setDate(c2);

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
	 * If the user wants to use the break, set the fields to enabled
	 */
	public void breakUpdate()
	{
		boolean b = jCheckBreakSchedule.isSelected();
		boolean e = jCheckBoxBreakEpisode.isSelected();
		boolean f = jCheckBoxBreakFrom.isSelected();
		
		// set all the days and labels for the schedule 
		jCheckBoxBreakEpisode.setEnabled(b);
		jCheckBoxBreakFrom.setEnabled(b);
		

		untilBreakDatePanel.setEnabledContents(b);
		fromBreakDatePanel.setEnabledContents(b&&f);
		jBreakEpisode.setEnabled(b&&e);
		jBreakLabel2.setEnabled(b);
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
			
			currentSerie.setUseBreakSchedule(jCheckBreakSchedule.isSelected());
			currentSerie.setUseBreakScheduleEpisode(jCheckBoxBreakEpisode.isSelected());
			currentSerie.setUseBreakScheduleFrom(jCheckBoxBreakFrom.isSelected());
					
			currentSerie.setBreakUntil(untilBreakDatePanel.getDateInMillis());
			currentSerie.setBreakFrom(fromBreakDatePanel.getDateInMillis());
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
	public boolean checkValues()
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

		int day2  = untilBreakDatePanel.getDay();
		int month = untilBreakDatePanel.getMonth();
		int year  = untilBreakDatePanel.getYear();

		int fday   = fromBreakDatePanel.getDay();
		int fmonth = fromBreakDatePanel.getMonth();
		int fyear  = fromBreakDatePanel.getYear();

		
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
		
	public void actionPerformed(ActionEvent e) {
		breakUpdate();
	}
}
