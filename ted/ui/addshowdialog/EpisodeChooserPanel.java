package ted.ui.addshowdialog;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
//import javax.swing.table.TableRowSorter;

import ted.TedSerie;
import ted.TedTableProgressbarRenderer;
import ted.datastructures.SeasonEpisode;
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
public class EpisodeChooserPanel extends JPanel
{
	
	private JTable episodesTable;
	private JSpinner seasonSpinner;
	private JLabel EpisodeLabel;
	private JLabel seasonLabel;
	private JSpinner episodeSpinner;
	private JScrollPane episodesScrollPane;
	private EpisodesTableModel episodesTableModel = new EpisodesTableModel();
	
	private SpinnerNumberModel episodeSpinnerModel = new SpinnerNumberModel();
	private SpinnerNumberModel seasonSpinnerModel = new SpinnerNumberModel();
	private Canvas activityCanvas;
	TedTableProgressbarRenderer ttpr;

	public EpisodeChooserPanel()
	{
		//this.getContentPane().add(getEpisodesScrollPane());
		this.initGUI();
	}
	
	public void episodesTableMouseClicked(MouseEvent evt)
	{
		int viewRow = episodesTable.getSelectedRow();
		int selectedRow = viewRow;
		//int selectedRow = episodesTable.convertRowIndexToModel(viewRow);
		if (selectedRow >= 0)
		{
			//SeasonEpisode selected = (SeasonEpisode)episodesTableModel.getStandardStructureAt(selectedRow);
			//Integer episode = new Integer (selected.getEpisode());
			//Integer season = new Integer (selected.getSeason());
			//this.episodeSpinner.setValue(episode);
			//this.seasonSpinner.setValue(season);
		}
	}

	private JTable getEpisodesTable() 
	{
		if (episodesTable == null) {
			
			episodesTable = new JTable();
			episodesTable.setModel(episodesTableModel);
			//TableRowSorter sorter = new TableRowSorter(episodesTable.getModel());
			//episodesTable.setRowSorter(sorter);

			
			episodesTable.setAutoCreateColumnsFromModel(true);
			episodesTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			episodesTable.setEditingRow(0);
			
			//	disable horizontal lines in table
			episodesTable.setShowHorizontalLines(false);
			episodesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			episodesTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					episodesTableMouseClicked(evt);
				}});
			ttpr = new TedTableProgressbarRenderer(0, 100);
			ttpr.setStringPainted(false);
			
			TableRenderer tr = new TableRenderer();
			episodesTable.setDefaultRenderer(Object.class, tr);
			episodesTable.setRowHeight(episodesTable.getRowHeight()+5);
			
			episodesTable.setDefaultRenderer(JProgressBar.class, ttpr);
		}
		return episodesTable;
	}
	
	private JScrollPane getEpisodesScrollPane() 
	{
		if (episodesScrollPane == null) {
			episodesScrollPane = new JScrollPane();
			episodesScrollPane.setViewportView(getEpisodesTable());
		}
		return episodesScrollPane;
	}
	
	private void initGUI() {
		try {
			
			FormLayout thisLayout = new FormLayout(
				"25dlu, max(p;15dlu):grow, 30dlu, 5dlu, 31dlu, 5dlu, 25dlu, 5dlu, 29dlu",
				"max(p;15dlu), 30dlu:grow, 16dlu");
			this.setLayout(thisLayout);
			this.add(getEpisodesScrollPane(), new CellConstraints("1, 1, 9, 2, default, default"));
			this.add(getSeasonSpinner(), new CellConstraints("5, 3, 1, 1, default, default"));
			this.add(getEpisodeSpinner(), new CellConstraints("9, 3, 1, 1, default, default"));
			this.add(getSeasonLabel(), new CellConstraints("3, 3, 1, 1, default, default"));
			this.add(getEpisodeLabel(), new CellConstraints("7, 3, 1, 1, default, default"));
			this.add(getActivityCanvas(), new CellConstraints("1, 3, 1, 1, default, default"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSeasonEpisodes(Vector seasonEpisodes)
	{
		this.episodesTableModel.setSeasonEpisodes(seasonEpisodes);		
		ttpr.setMaximum(this.episodesTableModel.getMaxQuality());
	}

	public void clear()
	{
		this.episodesTableModel.clear();
		
	}
	
	private JSpinner getSeasonSpinner() {
		if (seasonSpinner == null) {
			seasonSpinner = new JSpinner();
			seasonSpinner.setModel(this.seasonSpinnerModel);
		}
		return seasonSpinner;
	}
	
	private JSpinner getEpisodeSpinner() {
		if (episodeSpinner == null) {
			episodeSpinner = new JSpinner();
			episodeSpinner.setModel(this.episodeSpinnerModel);
		}
		return episodeSpinner;
	}
	
	private JLabel getSeasonLabel() {
		if (seasonLabel == null) {
			seasonLabel = new JLabel();
			seasonLabel.setText("Season");
		}
		return seasonLabel;
	}
	
	private JLabel getEpisodeLabel() {
		if (EpisodeLabel == null) {
			EpisodeLabel = new JLabel();
			EpisodeLabel.setText("Episode");
		}
		return EpisodeLabel;
	}

	public int getSelectedEpisode()
	{		
		return this.episodeSpinnerModel.getNumber().intValue();
	}

	public int getSelectedSeason()
	{
		return this.seasonSpinnerModel.getNumber().intValue();
	}
	
	private Canvas getActivityCanvas() {
		if (activityCanvas == null) {
			activityCanvas = new ImageCanvas("activity.gif");
			activityCanvas.setPreferredSize(new java.awt.Dimension(16, 16));
		}
		return activityCanvas;
	}
	
	public void setActivityStatus(boolean active)
	{
		this.seasonLabel.setVisible(!active);
		this.seasonSpinner.setVisible(!active);
		this.EpisodeLabel.setVisible(!active);
		this.episodeSpinner.setVisible(!active);
		this.activityCanvas.setVisible(active);
	}
}
