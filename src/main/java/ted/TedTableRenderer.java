package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is renderer for the table in the mainwindow of ted
 * 
 * The only thing it does is changing the background colors of odd/even rows in the table
 * 
 * @author Roel
 * @author Joost
 *
 * ted License:
 * This file is part of ted. ted and all of it's parts are licensed
 * under GNU General Public License (GPL) version 2.0
 * 
 * for more details see: http://en.wikipedia.org/wiki/GNU_General_Public_License
 *
 */
public class TedTableRenderer extends DefaultTableCellRenderer  
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 146021159938321504L;

	public Component getTableCellRendererComponent(JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	 {
	   TedTableModel ttmodel = (TedTableModel) table.getModel();
	   TedSerie serie = ttmodel.getSerieAt(row);
	   
	   // let the default renderer prepare the component for us
       Component comp = super.getTableCellRendererComponent(table, value, 
                                           isSelected, hasFocus, row, column);
       
       int width = comp.getWidth();
       int height = comp.getWidth();
       
       comp.setBackground(colorForRow(row, isSelected));
       
       JLabel name = new JLabel(serie.getName());
       //comp.(name);
	   return comp;
	 }
	
	/**
     * Returns the appropriate background color for the given row.
     */
    protected Color colorForRow(int row, boolean isSelected) {
    	Color c = getBackground();
    	if (isSelected)
    	{
    		return TedConfig.getInstance().getSelectedRowColor();
    	}
    	if( (row%2)==0)
    	{     return TedConfig.getInstance().getEvenRowColor();
    	}
    	else 
    	{
    		return TedConfig.getInstance().getOddRowColor();
    	}
    }
}
