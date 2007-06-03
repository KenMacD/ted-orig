package ted.ui.addshowdialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import org.w3c.dom.Element;

import ted.BrowserLauncher;
import ted.Lang;
import ted.TedIO;
import ted.TedLog;
import ted.TedMainDialog;
import ted.TedSerie;
import ted.TedXMLParser;
import ted.datastructures.SimpleTedSerie;
import ted.ui.TableRenderer;

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
public class AddShowDialog extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1006862655927988046L;
	private JTable showsTable;
	private JButton cancelButton;
	private JButton okButton;
	//private JTable episodesTable;
	//private JScrollPane episodesScrollPane;
	private JScrollPane showsScrollPane;
	private ShowsTableModel showsTableModel;
	private TedSerie selectedSerie;
	private TedMainDialog tedMain;
	private JTextPane showInfoPane;
	private JLabel showNameLabel;
	private JLabel selectShowLabel;
	private JLabel selectEpisodeLabel;
	private JButton jHelpButton;
	private JScrollPane showInfoScrollPane;

	private EpisodeChooserPanel episodeChooserPanel = new EpisodeChooserPanel();
	
	public AddShowDialog()
	{
		this.initGUI();
	}
	
	public AddShowDialog(TedMainDialog main)
	{
		super(main);
		this.setModal(true);
		this.tedMain = main;
		this.initGUI();
	}

	private void initGUI() {
		try {
			

		    
			this.episodeChooserPanel.setActivityStatus(false);
			FormLayout thisLayout = new FormLayout(
				"max(p;5dlu), max(m;28px), 10dlu:grow, 9dlu, 5dlu:grow, max(p;15dlu), 5dlu, 85dlu, max(p;5dlu)",
				"max(p;5dlu), max(p;15dlu), 5dlu, 10dlu:grow, max(p;5dlu), max(p;15dlu), 5dlu, 28dlu, 10dlu:grow, 5dlu, max(p;15dlu), 5dlu, max(p;15dlu), max(p;5dlu)");
			getContentPane().setLayout(thisLayout);

			showsTableModel = new ShowsTableModel();
			showsTable = new JTable();
			//getContentPane().add(showsTable, new CellConstraints("4, 3, 1, 1, default, default"));
			getShowsScrollPane().setViewportView(showsTable);
			getContentPane().add(getShowsScrollPane(), new CellConstraints("2, 4, 2, 6, fill, fill"));
			getContentPane().add(episodeChooserPanel, new CellConstraints("5, 8, 4, 4, fill, fill"));
			getContentPane().add(getOkButton(), new CellConstraints("8, 13, 1, 1, default, default"));
			getContentPane().add(getCancelButton(), new CellConstraints("6, 13, 1, 1, default, default"));
			getContentPane().add(getShowInfoScrollPane(), new CellConstraints("5, 4, 4, 1, fill, fill"));
			getContentPane().add(getJHelpButton(), new CellConstraints("2, 13, 1, 1, left, default"));
			getContentPane().add(getSelectShowLabel(), new CellConstraints("2, 2, 2, 1, left, bottom"));
			getContentPane().add(getSelectEpisodeLabel(), new CellConstraints("5, 6, 4, 1, left, bottom"));
			getContentPane().add(getShowNameLabel(), new CellConstraints("5, 2, 4, 1, left, bottom"));
			showsTable.setModel(showsTableModel);
			showsTableModel.setSeries(this.readShowNames());
			
			showsTable.setAutoCreateColumnsFromModel(true);
			showsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			showsTable.setEditingRow(0);
			showsTable.setFont(new java.awt.Font("Dialog",0,15));
		    showsTable.setRowHeight(showsTable.getRowHeight()+10);
		    TableRenderer tr = new TableRenderer();
		    showsTable.setDefaultRenderer(Object.class, tr);
			
			//	disable horizontal lines in table
			showsTable.setShowHorizontalLines(false);
			showsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			showsTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					showsTableMouseClicked(evt);
				}});

			this.setSize(700, 500);
			
//			Get the screen size
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Dimension screenSize = toolkit.getScreenSize();

		    //Calculate the frame location
		    int x = (screenSize.width - this.getWidth()) / 2;
		    int y = (screenSize.height - this.getHeight()) / 2;

		    //Set the new frame location
		    this.setLocation(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Vector readShowNames()
	{
		Vector names = new Vector();		
		
		TedXMLParser parser = new TedXMLParser();
		Element shows = parser.readXMLFile(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
		
		if(shows!=null)
			names = parser.getNames(shows);
		else
			TedLog.error(Lang.getString("TedEpisodeDialog.LogXmlNotFound")); //$NON-NLS-1$
		
		return names;
	}
	
	private JScrollPane getShowsScrollPane() {
		if (showsScrollPane == null) {
			showsScrollPane = new JScrollPane();
			
		}
		return showsScrollPane;
	}
	
	
	
	
	private void showsTableMouseClicked(MouseEvent evt)
	{
		int selectedRow = showsTable.getSelectedRow();
		if (selectedRow >= 0)
		{
			SimpleTedSerie selectedShow = this.showsTableModel.getSerieAt(selectedRow);
			this.showNameLabel.setText(selectedShow.getName());
			EpisodeParserThread ept = new EpisodeParserThread(this, this.episodeChooserPanel, selectedShow, this.getShowInfoPane());
			ept.start();
			
			
		}
		
	}
	
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("Ok");
			okButton.setActionCommand("OK");
			okButton.addActionListener(this);
		}
		return okButton;
	}
	
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		String command = arg0.getActionCommand();
		
		if (command.equals("OK"))
		{
			// add show
			if (selectedSerie != null)
			{
				// set season and episode settings
				selectedSerie.setCurrentEpisode(this.episodeChooserPanel.getSelectedEpisode());
				selectedSerie.setCurrentSeason(this.episodeChooserPanel.getSelectedSeason());
				
				tedMain.addSerie(selectedSerie);
				this.setVisible(false);
			}
		}
		else if (command.equals("Cancel"))
		{
			// close dialog
			this.setVisible(false);
		}
		else if (command.equals("Help"))
		{
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/wiki/index.php/Add_show"); //$NON-NLS-1$
			} 
			catch (Exception err)
			{
				
			}
		}
		
	}

	public void setSelectedSerie(TedSerie selectedSerie2)
	{
		this.selectedSerie = selectedSerie2;
		
	}
	
	private JScrollPane getShowInfoScrollPane() {
		if (showInfoScrollPane == null) {
			showInfoScrollPane = new JScrollPane();
			showInfoScrollPane.setViewportView(getShowInfoPane());
		}
		return showInfoScrollPane;
	}
	
	private JTextPane getShowInfoPane() {
		if (showInfoPane == null) {
			showInfoPane = new JTextPane();
			showInfoPane.setContentType( "text/html" );
			showInfoPane.setEditable( false );
			showInfoPane.setPreferredSize(new java.awt.Dimension(325, 128));
			//showInfoPane.setText("jTextPane1");
			
		}
		return showInfoPane;
	}
	
	private JButton getJHelpButton() {
		if (jHelpButton == null) {
			jHelpButton = new JButton();
			jHelpButton.setActionCommand("Help");
			jHelpButton.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/help.png")));
			jHelpButton.setBounds(11, 380, 28, 28);
			jHelpButton.addActionListener(this);
		}
		return jHelpButton;
	}
	
	private JLabel getSelectShowLabel() {
		if (selectShowLabel == null) {
			selectShowLabel = new JLabel();
			selectShowLabel.setText("Select a show");
		}
		return selectShowLabel;
	}
	
	private JLabel getSelectEpisodeLabel() {
		if (selectEpisodeLabel == null) {
			selectEpisodeLabel = new JLabel();
			selectEpisodeLabel
				.setText("Select the episode you want to download");
		}
		return selectEpisodeLabel;
	}
	
	private JLabel getShowNameLabel() {
		if (showNameLabel == null) {
			showNameLabel = new JLabel();
			showNameLabel.setFont(new java.awt.Font("Tahoma",1,14));

		}
		return showNameLabel;
	}

}
