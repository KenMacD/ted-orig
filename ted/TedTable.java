package ted;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import ted.ui.editshowdialog.EditShowDialog;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the table that holds all the shows that are in ted.
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
public class TedTable extends JTable
{

	private static final long serialVersionUID = 3101958907833506800L;
	private TedTableModel serieTableModel;
	private TedMainDialog tedMain;
	private TedTablePopupMenu ttPopupMenu;

	/**
	 * Create a table that can hold the series of ted
	 * @param main current tedmaindialog
	 * @param ttPopupMenu ted table popup menu
	 */
	public TedTable(TedMainDialog main, TedTablePopupMenu ttPopupMenu)
	{
		this.ttPopupMenu = ttPopupMenu;
		this.tedMain = main;
		
		serieTableModel = new TedTableModel();
		this.setBackground(Color.WHITE);
		this.setModel(serieTableModel);
		
		//TableRowSorter sorter = new TableRowSorter(this.getModel());
		//this.setRowSorter(sorter);
		
		this.setAutoCreateColumnsFromModel(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		this.setEditingRow(0);
		
		//	disable horizontal lines in table
		this.setShowHorizontalLines(false);
		this.setRowHeight(this.getRowHeight()+5);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TedTableRenderer mtr = new TedTableRenderer();
		TedTableProgressbarRenderer ttpr = new TedTableProgressbarRenderer(0, 100);
		
		this.setDefaultRenderer(JProgressBar.class, ttpr);
		this.setDefaultRenderer(Object.class, mtr);
		this.setDefaultRenderer(Integer.class, mtr);
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				serieTableKeyReleased(evt);
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				serieTableMouseClicked(evt);
			}
		});
		
		this.setColumnWidths();
				
	}
	
	/**
	 * Set columns in the table to default widths
	 */
	private void setColumnWidths()
	{
		// set all preferred sizes of the columns
		TableColumn	column;				
		for (int i = 0; i < serieTableModel.getColumnCount(); i++) 
		{
		    column = this.getColumnModel().getColumn(i);
		    if (i == 0) // icon column
		    {
		    	column.setMaxWidth(20);
				column.setMinWidth(20);
		    }
		    if (i == 2) // season/episode column
		    {
		    	column.setPreferredWidth(300);
		    }
		    else if (i == 3) // progress column
		    {
		    	column.setPreferredWidth(20);
		    	column.setMinWidth(75);
		    	column.setMaxWidth(75);
		    }
		    else if (i == 1) // name column
		    {
		    	column.setPreferredWidth(200);
		    	
		    }
		    else
		    {
		    	column.setPreferredWidth(300);
		    }
		}
	}


	
	/**
	 * Handles clicks on the table in the mainwindow ted
	 * @param evt MouseEvent
	 */
	private void serieTableMouseClicked(MouseEvent evt) 
	{
		// the user clicked on a serie in the table
		int viewRow = this.getSelectedRow();
		int row = viewRow;
		
		// else show the EpisodeDialog of the selected show
		if (evt.getClickCount() == 2)
		{	
			TedSerie selectedserie = serieTableModel.getSerieAt(row);
			new EditShowDialog(tedMain, selectedserie, false);		
		}
		// or did the user click right?
		else if (SwingUtilities.isRightMouseButton(evt))
		{
			// show context menu
			int selectedrow = this.rowAtPoint(evt.getPoint());
			ListSelectionModel selectionModel = this.getSelectionModel();
			selectionModel.setSelectionInterval(selectedrow, selectedrow);
			ttPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
		}
		
		// if the user selected something, update status of buttons
		tedMain.updateButtonsAndMenu();
	}
	
	/**
	 * Handles key release on table
	 * @param evt
	 */
	private void serieTableKeyReleased(KeyEvent evt) 
	{
		int keyCode = evt.getKeyCode();
		if (keyCode == KeyEvent.VK_DELETE)
		{
			this.DeleteSelectedShow();
		}
	}
	
	
	/**
	 * Set shows in the table
	 * @param vector containing the shows
	 */
	public void setSeries(Vector vector)
	{
		serieTableModel.setSeries(vector);		
	}
	
	/**
	 * Set status of the selected row in the table
	 * @param status New status
	 */
	public void setSelectedStatus(int status) 
	{
			TedSerie selectedserie = this.getSelectedShow();
			if (selectedserie != null)
			{
				selectedserie.setStatus(status);
				selectedserie.setLastDatesToToday();	
			}
	}

	/**
	 * @param i
	 * @return show at row i
	 */
	public TedSerie getSerieAt(int i)
	{
		return serieTableModel.getSerieAt(i);
	}
	
	/**
	 * @return the selected show or null when no show is selected
	 */
	public TedSerie getSelectedShow()
	{
		int pos = this.getSelectedRow();
		if (pos >= 0)
		{
			return this.getSerieAt(pos);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Update the table
	 */
	public void tableUpdate()
	{
		serieTableModel.tableUpdate();
		
	}

	/**
	 * Add a show
	 * @param newSerie
	 */
	public void addSerie(TedSerie newSerie)
	{
		serieTableModel.addSerie(newSerie);
		
	}

	/**
	 * @return All the shows in the table
	 */
	public Vector getSeries()
	{
		return serieTableModel.getSeries();
	}

	/**
	 * Update the data in the table
	 */
	public void fireTableDataChanged()
	{
		serieTableModel.fireTableDataChanged();
		
	}
	
	/**
	 * Delete the selected show from ted
	 */
	public void DeleteSelectedShow()
	{
		if (this.getSelectedRow() >= 0)
		{
			// ask the user if he really wants to delete the show
			int answer = JOptionPane.showOptionDialog(this,
	                Lang.getString("TedMainDialog.DialogConfirmDeleteBegin") + " " + serieTableModel.getSerieAt(this.getSelectedRow()).getName() + Lang.getString("TedMainDialog.DialogConfirmDeleteEnd"), //$NON-NLS-1$ //$NON-NLS-2$
	                "ted", //$NON-NLS-1$
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE, null, Lang.getYesNoLocale(), Lang.getYesNoLocale()[0]);
			
			if (answer == JOptionPane.YES_OPTION)
			{
				serieTableModel.removeSerieAt(this.getSelectedRow());
				tedMain.saveShows();
			}
		}
	}

	/**
	 * Update all texts in the tedtable
	 */
	public void updateText()
	{
		serieTableModel.updateText();
		this.tableHeader.updateUI();
		TableColumnModel tcm = this.getTableHeader().getColumnModel();
		
		for (int i = 0; i < serieTableModel.getColumnCount(); i++)
		{
			tcm.getColumn(i).setHeaderValue(serieTableModel.getColumnName(i));
		}
		
	}
}
