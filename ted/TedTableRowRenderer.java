package ted;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TedTableRowRenderer extends JPanel implements TableCellRenderer
{
	private final Font SMALL_FONT = new Font("Dialog",0,10);
	private final Font LARGE_FONT = new Font("Dialog",0,15);
	private final ImageIcon showPaused = new ImageIcon(getClass().getClassLoader().getResource("icons/pause.png")); //$NON-NLS-1$
	private final ImageIcon showPlay	 = new ImageIcon(getClass().getClassLoader().getResource("icons/play.png")); //$NON-NLS-1$
	private final ImageIcon showStopped	 = new ImageIcon(getClass().getClassLoader().getResource("icons/stop.png")); //$NON-NLS-1$
	private final ImageIcon showActive = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-active-ted.gif")); //$NON-NLS-1$
	private final ImageIcon activityIm = new ImageIcon(getClass().getClassLoader().getResource("icons/activity.gif"));


	/**
	 * 
	 */
	private static final long serialVersionUID = 3002065666816958045L;

	public Component getTableCellRendererComponent(JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		JPanel currentPanel = new JPanel();
		currentPanel.setLayout(null);
		
		// we can assume value is a tedserie
		TedSerie serie = (TedSerie)value;
		  
		currentPanel.setBackground(colorForRow(row, isSelected));
		  
		// name
		JLabel nameLabel = new JLabel(serie.getName());
		nameLabel.setFont(LARGE_FONT);
		nameLabel.setForeground(this.getFontColor(Color.BLACK, isSelected));
		nameLabel.setBounds(40, 3, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
		currentPanel.add(nameLabel);
		// search for
		JLabel searchforLabel = new JLabel (serie.getSearchForString());
		searchforLabel.setFont(SMALL_FONT);
		searchforLabel.setBounds(40, 22, searchforLabel.getPreferredSize().width, searchforLabel.getPreferredSize().height);
		searchforLabel.setForeground(this.getFontColor(Color.DARK_GRAY, isSelected));
		currentPanel.add(searchforLabel);
        // progress
		JLabel progressLabel = new JLabel (serie.getStatusString());
		progressLabel.setForeground(this.getFontColor(Color.GRAY, isSelected));
		progressLabel.setFont(SMALL_FONT);
		progressLabel.setBounds(40, 35, progressLabel.getPreferredSize().width, progressLabel.getPreferredSize().height);
		currentPanel.add(progressLabel);
		// icon
		JLabel iconLabel = new JLabel (this.getIconForShow(serie));
		iconLabel.setBounds(10, 10, iconLabel.getPreferredSize().width, iconLabel.getPreferredSize().height);
		currentPanel.add(iconLabel);
		// progress bar
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(serie.getProgress());
		progressBar.setStringPainted(false);
		progressBar.setBounds (5, 38, 30, 5);
		currentPanel.add(progressBar);
        
		return currentPanel;
	 }
	
	/**
    * Returns the appropriate background color for the given row.
    */
	protected Color colorForRow(int row, boolean isSelected) {
		Color c = Color.WHITE;
	   	if (isSelected)
	   	{
	   		return(new Color( 61, 128, 223));
	   	}
	   	if( (row%2)==0)
	   	{     return(new Color( c.getRed()-20,
		                                  c.getGreen()-10,
		                                  c.getBlue()));
	   	}
	   	else 
	   	{
	   		return c;
	   	}
   }
   private Color getFontColor(Color color, boolean isSelected) 
   {
	   	if (isSelected) {
	   		return Color.WHITE;
	   	} else {
	   		return color;
	   	}
	}
   
   private ImageIcon getIconForShow(TedSerie show)
   {
   	if (show.getActivity() == TedSerie.IS_PARSING)
		{
			return showActive;
		}
		if (show.getStatus() == TedSerie.STATUS_PAUSE)
		{
			return showPaused;
		}
		else if (show.getStatus() == TedSerie.STATUS_CHECK)
		{
			return showPlay;
		}
		else// if (show.getStatus() == TedSerie.STATUS_HOLD)
		{
			return showStopped;
		}
   }
	

}
