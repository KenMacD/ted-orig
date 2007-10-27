package ted;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JViewport;
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
	private ImageIcon showPaused = new ImageIcon(getClass().getClassLoader().getResource("icons/pause.png")); //$NON-NLS-1$
	private ImageIcon showPlay	 = new ImageIcon(getClass().getClassLoader().getResource("icons/play.png")); //$NON-NLS-1$
	private ImageIcon showStopped	 = new ImageIcon(getClass().getClassLoader().getResource("icons/stop.png")); //$NON-NLS-1$
	private ImageIcon showActive = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-active-ted.gif")); //$NON-NLS-1$
	private ImageIcon activityIm = new ImageIcon(getClass().getClassLoader().getResource("icons/activity.gif"));

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
		
		//this.setAutoCreateColumnsFromModel(true);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		this.setEditingRow(0);
		
		//	disable horizontal lines in table
		setShowHorizontalLines(false);
        setShowVerticalLines(false);
		this.setRowHeight(50);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*TedTableRenderer mtr = new TedTableRenderer();
		TedTableProgressbarRenderer ttpr = new TedTableProgressbarRenderer(0, 100);
		
		this.setDefaultRenderer(JProgressBar.class, ttpr);
		this.setDefaultRenderer(Object.class, mtr);
		this.setDefaultRenderer(Integer.class, mtr);*/
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
		
		//this.setColumnWidths();
				
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
		int row = this.getSelectedRow();
		
		if (row >= 0)
		{
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
		/*this.tableHeader.updateUI();
		TableColumnModel tcm = this.getTableHeader().getColumnModel();
		
		for (int i = 0; i < serieTableModel.getColumnCount(); i++)
		{
			tcm.getColumn(i).setHeaderValue(serieTableModel.getColumnName(i));
		}*/
		
	}
	
	 /**
     * Returns the appropriate background color for the given row.
     */
    protected Color colorForRow(int row) {
    	Color c = getBackground();
    	if (this.getSelectedRow() == row)
    	{
    		return(new Color( 61, 128, 223));
    	}
    	if( (row%2)==0 && c.getRed()>10 && c.getGreen()>10 && c.getBlue()>10 )
    	{     return(new Color( c.getRed()-20,
	                                  c.getGreen()-10,
	                                  c.getBlue()));
    	}
    	else 
    	{
    		return c;
    	}
    }
	
	/**
     * Paints empty rows too, after letting the UI delegate do
     * its painting.
     */
    public void paint(Graphics g) {
        //super.paint(g);
        paintRows(g);
    }

    /**
     * Paints the backgrounds of the implied empty rows when the
     * table model is insufficient to fill all the visible area
     * available to us. We don't involve cell renderers, because
     * we have no data.
     */
    protected void paintRows(Graphics g) {
        final int rowCount = getRowCount();
        final Rectangle clip = g.getClipBounds();
        final int height = clip.y + clip.height;
        TedSerie currentRow;
        for (int i = 0; i < rowCount; i++)
        {
        	currentRow = this.getSerieAt(i);
        	// background
        	g.setColor(colorForRow(i));
            g.fillRect(clip.x, i * rowHeight, clip.width, rowHeight);
            // icon
            g.drawImage(
            		this.getIconForShow(currentRow).getImage(), 
            		clip.x+10, 
            		i * rowHeight + 20, 
            		16, 
            		16, 
            		editorComp);
            // name
            g.setColor(this.getFontColor(Color.BLACK, i));
            g.setFont(new java.awt.Font("Dialog",0,15));
            g.drawString(currentRow.getName(), clip.x+40, i * rowHeight + 18);
            // search for
            g.setFont(new java.awt.Font("Dialog",0,10));
            g.setColor(this.getFontColor(Color.DARK_GRAY, i));
            g.drawString(currentRow.getSearchForString(), clip.x+40, i * rowHeight + 32);
            // progress
            g.setColor(this.getFontColor(Color.GRAY, i));
            g.drawString(currentRow.getStatusString(), clip.x+40, i * rowHeight + 45);
            
            // activity icon / button
            /*g.drawImage(
            		this.activityIm.getImage(), 
            		clip.width-20, 
            		i * rowHeight + 20, 
            		16, 
            		16, 
            		editorComp);*/
        }
        if (rowCount * rowHeight < height) {
            for (int i = rowCount; i <= height/rowHeight; ++i) {
                g.setColor(colorForRow(i));
                g.fillRect(clip.x, i * rowHeight, clip.width, rowHeight);
            }
        }
        
        if (rowCount == 0)
        {
        	// display message that user has to add shows
        	// yellow box
        	g.setColor( new Color( 255,	255, 225));
            g.fillRect(clip.x, 0, clip.width, 20);
            // text
            g.setColor(Color.BLACK);
            g.setFont(new java.awt.Font("Dialog",0,10));
            g.drawString("Please add a show to ted", clip.x+40, 14);
        }
    }
    
    private Color getFontColor(Color color, int row) 
    {
    	if (row == this.getSelectedRow()) {
    		return Color.WHITE;
    	} else {
    		return color;
    	}
	}

	/**
     * Changes the behavior of a table in a JScrollPane to be more like
     * the behavior of JList, which expands to fill the available space.
     * JTable normally restricts its size to just what's needed by its
     * model.
     */
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            JViewport parent = (JViewport) getParent();
            return (parent.getHeight() > getPreferredSize().height);
        }
        return false;
    }
    
    public ImageIcon getIconForShow(TedSerie show)
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
