package ted.ui.editshowdialog;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
public class SchedulePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8342065043462454211L;
	private int width = 400;
	private JSeparator jSeparator1;
	private int height = 300;	
	WeekSchedulePanel wPanel;
	BreakSchedulePanel bPanel;
	
	public SchedulePanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
				"5dlu, 255dlu, 5dlu",
				"max(p;66dlu), 5dlu, 115dlu");
			this.setLayout(thisLayout);
			wPanel = new WeekSchedulePanel();
			bPanel = new BreakSchedulePanel(); 
			this.setPreferredSize(new Dimension(width, height));
			{
				this.add(wPanel, new CellConstraints("2, 1, 1, 1, default, default"));
			}
			{
				jSeparator1 = new JSeparator();
				this.add(jSeparator1, new CellConstraints("2, 2, 1, 1, default, default"));
			}
			{
				this.add(bPanel, new CellConstraints("2, 3, 1, 1, default, default"));
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
	}
	
	public void setValues(TedSerie serie)
	{
		wPanel.setValues(serie);
		bPanel.setValues(serie);
	}
	
	public boolean checkValues()
	{
		return bPanel.checkValues();
	}
}
