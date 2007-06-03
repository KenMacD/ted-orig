package ted.ui.editshowdialog;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.w3c.dom.Element;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import ted.BrowserLauncher;
import ted.Lang;
import ted.TedFeedsTableModel;
import ted.TedIO;
import ted.TedPopupMenu;
import ted.TedSerie;
import ted.TedSerieFeed;
import ted.TedXMLParser;
import ted.ui.TableRenderer;


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
public class FeedsPanel extends JPanel implements ActionListener
{
	private int width = 400;
	private int height = 300;
	private JButton jButtonAdd;
	private JButton jButtonMoveFeedUp;
	private JScrollPane jScrollPane1;
	private JTable feedsTable;
	private JButton jButtonMoveFeedDown;
	private JButton jFindButton;
	private JButton jOpenButton;
	private JButton jButtonDelete;
	private JToolBar feedsToolBar;
	private TedFeedsTableModel feedsTableModel = new TedFeedsTableModel();
	MouseListener popupListener = new PopupListener();
	private TedPopupMenu findRSSPopupMenu;

	public FeedsPanel()
	{
		this.initUI();
	}

	private void initUI()
	{
		try 
		{
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new Dimension(width, height));

			feedsToolBar = new JToolBar();
			this.add(feedsToolBar, BorderLayout.CENTER);
			feedsToolBar.setFloatable(false);
			feedsToolBar.setBounds(14, 217, 441, 28);
			feedsToolBar.setPreferredSize(new java.awt.Dimension(231, 32));

			jButtonAdd = new JButton();
			feedsToolBar.add(jButtonAdd);
			jButtonAdd.setActionCommand("addfeed");
			jButtonAdd.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/Aid.png")));
			jButtonAdd.setPreferredSize(new java.awt.Dimension(119, 21));
			jButtonAdd.setBounds(15, 248, 77, 21);

			jButtonDelete = new JButton();
			feedsToolBar.add(jButtonDelete);
			jButtonDelete.setActionCommand("deletefeed");
			jButtonDelete.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/Cancel.png")));
			jButtonDelete.setBounds(96, 248, 105, 21);

			jOpenButton = new JButton();
			feedsToolBar.add(jOpenButton);
			jOpenButton.setActionCommand("openfeed");
			jOpenButton.setBounds(205, 248, 70, 21);

			jFindButton = new JButton();
			feedsToolBar.add(jFindButton);
			jFindButton.setBounds(280, 248, 70, 21);

			jButtonMoveFeedDown = new JButton();
			feedsToolBar.add(jButtonMoveFeedDown);
			jButtonMoveFeedDown.setActionCommand("movefeeddown");
			jButtonMoveFeedDown.setIcon(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/down.png")));
			jButtonMoveFeedDown.setBounds(384, 248, 35, 21);

			jButtonMoveFeedUp = new JButton();
			feedsToolBar.add(jButtonMoveFeedUp);
			jButtonMoveFeedUp.setActionCommand("movefeedup");
			jButtonMoveFeedUp.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/up.png")));
			jButtonMoveFeedUp.setBounds(420, 248, 35, 21);
			jButtonMoveFeedUp.addActionListener(this);

			jButtonMoveFeedDown.addActionListener(this);

			jFindButton.addMouseListener(popupListener);

			jOpenButton.addActionListener(this);

			jButtonDelete.addActionListener(this);

			jButtonAdd.addActionListener(this);

			jScrollPane1 = new JScrollPane();
			this.add(jScrollPane1, BorderLayout.NORTH);
			jScrollPane1.setPreferredSize(new java.awt.Dimension(453, 243));
			jScrollPane1.setBounds(14, 133, 441, 84);

			feedsTable = new JTable();
			jScrollPane1.setViewportView(feedsTable);
			feedsTable.setModel(feedsTableModel);
			feedsTable.setAutoCreateColumnsFromModel(true);
			feedsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			feedsTable.setEditingRow(1);
			
			TableRenderer tr = new TableRenderer();
			feedsTable.setDefaultRenderer(Object.class, tr);
			
//			 make sure the first column is always 16 width
			TableColumn	column = feedsTable.getColumnModel().getColumn(0);
			column.setMaxWidth(25);
			column.setMinWidth(16);
			
			// and the column with the type is also smaller
			column = feedsTable.getColumnModel().getColumn(2);
			column.setMaxWidth(100);
			column.setMinWidth(100);

			jButtonAdd
				.setText(Lang.getString("TedEpisodeDialog.ButtonAddFeed"));
			jButtonAdd.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipAddFeed"));

			jButtonDelete.setText(Lang
				.getString("TedEpisodeDialog.ButtonDeleteFeed"));
			jButtonDelete.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipDeleteFeed"));

			jOpenButton.setText(Lang.getString("TedEpisodeDialog.ButtonOpen"));
			jOpenButton.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipOpen"));

			jFindButton.setText(Lang.getString("TedEpisodeDialog.ButtonFind"));
			jFindButton.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipFind"));

			jButtonMoveFeedDown.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipMoveFeedDown"));

			jButtonMoveFeedUp.setToolTipText(Lang
				.getString("TedEpisodeDialog.ButtonToolTipMoveFeedUp"));

		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Add a new feed to the table
	 */
	private void addFeed()
	{
		TedSerieFeed newFeed = new TedSerieFeed("", 0, true); //$NON-NLS-1$
		int row = feedsTableModel.addSerie(newFeed);
		feedsTable.setRowSelectionInterval(row,row);
		
		JViewport viewport = (JViewport)feedsTable.getParent();
        Rectangle rect = feedsTable.getCellRect(row, 0, true);
        
        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
    
        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
		feedsTable.requestFocus();
		
		feedsTable.editCellAt(row, 1);
	}
	
	private void addFeed(String s)
	{
		TedSerieFeed newFeed = new TedSerieFeed(s, 0);
		feedsTableModel.addSerie(newFeed);
	}
	
	/**
	 * Delete the selected feed from the table
	 */
	private void deleteSelectedFeed()
	{
		// ASK for confirmation?
		// TODO: if nothing selected -> error
		int selectedRow = feedsTable.getSelectedRow();
		if (selectedRow != -1)
		{
			feedsTableModel.removeSerieAt(selectedRow);
		}
	}
	
	/**
	 * Open the url of the selected feed in the browser of the user
	 */
	private void openRSSFeed() 
	{
		// open rss url
		try 
		{
			int selectedRow = feedsTable.getSelectedRow();
			if (selectedRow != -1)
			{
				// get selected feed
				TedSerieFeed selectedFeed = feedsTableModel.getSerieAt(selectedRow);
				// convert to url to filter out weird spacings
				URL url = new URL(selectedFeed.getUrl());
				BrowserLauncher.openURL(url.toString());
			}
		} 
		catch (IOException e) 
		{
			//TODO: add error message
		}
	}
	
	private void moveSelectedFeedUp()
	{
		int selectedRow = feedsTable.getSelectedRow();
		if (selectedRow > 0)
		{
			feedsTableModel.moveUp(selectedRow);
			feedsTable.setRowSelectionInterval(selectedRow-1, selectedRow-1);
		}
		
	}

	private void moveSelectedFeedDown()
	{
		int selectedRow = feedsTable.getSelectedRow();
		if (selectedRow != -1 && selectedRow < feedsTableModel.getRowCount()-1)		
		{
			feedsTableModel.moveDown(selectedRow);
			feedsTable.setRowSelectionInterval(selectedRow+1, selectedRow+1);
		}
	}

	/**
	 * Stop the editing of cells in the table
	 */
	public void stopEditing()
	{
		if (feedsTable.isEditing())
		{
			feedsTable.getCellEditor().stopCellEditing();
		}
	}

	public void setValues(TedSerie serie)
	{
		this.feedsTableModel.setSeriesFeeds(serie.getFeeds());
		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String action = arg0.getActionCommand();
		this.stopEditing();
		
		if (action.equals("openfeed"))
		{
			this.openRSSFeed();
		}
		else if (action.equals("addfeed"))
		{
			this.addFeed();
		}
		else if (action.equals("deletefeed"))
		{
			this.deleteSelectedFeed();
		}
		else if (action.equals("movefeedup"))
		{
			this.moveSelectedFeedUp();
		}
		else if (action.equals("movefeeddown"))
		{
			this.moveSelectedFeedDown();
		}
		
	}	
	
	// POPUP MENU
	
	class PopupListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) {
	        if (true) { //e.isPopupTrigger() -> left clicking now also brings up menu
	            findRSSPopupMenu.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
	}
	
	private void initPopupMenu()
	{
		//rssNames = new Vector();
		//rssLocations = new Vector();
		
		Vector items = new Vector();
		
		TedXMLParser p = new TedXMLParser();
		Element e = p.readXMLFile(TedIO.XML_SHOWS_FILE);
		items = p.getPopupItems(e);
		
		//findRSSPopupMenu = new ted.TedPopupMenu(this,items);
	}

}
