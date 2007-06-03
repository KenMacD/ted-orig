package ted.ui;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.Color;
import java.awt.Component;

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
public class TableRenderer extends DefaultTableCellRenderer  
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 146021159938321504L;

	public Component getTableCellRendererComponent(JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	 {
	   if( !isSelected ) 
	   {
		   		   // get odd/even rows a different color
	      Color c = table.getBackground();
	     if( (row%2)==0 && c.getRed()>10 && c.getGreen()>10 && c.getBlue()>10 )
	         setBackground(new Color( c.getRed()-20,
	                                  c.getGreen()-10,
	                                  c.getBlue()));
	      else
	         setBackground(c);
	   }
	   
	   return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	 }
}