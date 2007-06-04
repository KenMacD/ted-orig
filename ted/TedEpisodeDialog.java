package ted;

/****************************************************
 * IMPORTS
 ****************************************************/
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import org.w3c.dom.Element;

import ted.datastructures.SeasonEpisode;

/**
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the dialog where a user can edit a show
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
/**
 * @author Roel
 *
 */
public class TedEpisodeDialog extends javax.swing.JDialog
{
	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = -1273578400944157973L;
	private JButton button_Save;
	private JLabel labelCe;
	private JLabel labelCS;
	private JTextField text_CurrentEpisode;
	private JTextField text_CurrentSeason;
	private JToolBar feedsToolBar;
	private JButton jFindButton;
	private JCheckBox jCheckBoxBreakFrom;
	private JCheckBox jCheckBoxBreakEpisode;
	private JCheckBox checkbox_Preset;
	private JLabel labelMinSeedersText;
	private JLabel jLabel19;
	private JLabel jLabel18;
	private JLabel jLabel17;
	private JTextField text_minSeeders;
	private JButton jButtonMoveFeedDown;
	private JButton jButtonMoveFeedUp;
	private JButton jButtonDelete;
	private JButton jButtonAdd;
	private JTable feedsTable;
	private JPanel panelFeeds;
	private JScrollPane jScrollPane1;
	private JLabel jKeywordLabel3;
	private JComboBox jBreakYear;
	private JComboBox jBreakMonth;
	private JComboBox jBreakDay;
	private JComboBox jFromBreakYear;
	private JComboBox jFromBreakMonth;
	private JComboBox jFromBreakDay;
	private JSeparator jSeparator4;
	private JSeparator jSeparator3;
	private JSeparator jSeparator2;
	private JLabel jBreakLabel2;
	private JLabel jBreakLabel3;
	private JTextField jBreakEpisode;
	private JLabel jBreakLabel;
	private JSeparator jSeparator1;
	private JCheckBox jCheckBreakSchedule;
	private JCheckBox jCheckSunday;
	private JCheckBox jCheckSaturday;
	private JCheckBox jCheckFriday;
	private JCheckBox jCheckThursday;
	private JCheckBox jCheckWednesday;
	private JCheckBox jCheckTuesday;
	private JCheckBox jCheckMonday;
	private JLabel jLabel16;
	private JLabel jScheduleText;
	private JCheckBox jCheckSchedule;
	private JLabel jLabel8;
	private JLabel jLabel14;
	private JLabel jLabel13;
	private JButton jOpenButton;
	private JLabel labelName;
	private JTextField text_Name;
	private JTabbedPane jTabbedPane;
	private TextField keyword_text;
	private JLabel jLabel12;
	private JLabel jLabel6;
	private JButton latest_Button;
	private JLabel jLabel11;
	private JLabel jLabel10;
	private JLabel labelSeeders;
	private JLabel jLabel7;
	private JLabel jLabel5;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JTextField text_maxSize;
	private JTextField text_minSize;
	private JLabel labelMaxSize;
	private JLabel labelMinSeeders;
	private JButton jButton1;
	private JLabel label_explain;
	private JLabel label_advanced;
	private JLabel label_title;
	private TedFeedsTableModel feedsTableModel;
	private JPopupMenu findRSSPopupMenu;

	private boolean wasUsedBreakSchedule;
	private int yearDiff;
	
	private TedSerie currentSerie;
	private TedMainDialog tedDialog;
	private Vector names;
	//private Vector rssNames;
	//private Vector rssLocations;
	private boolean newSerie;
	
	private TedEpisodeListener TedEL = new TedEpisodeListener(this);
	MouseListener popupListener = new PopupListener();
	
	//private String[] intervals = {"week", "2 weeks", "3 weeks", "month"};
	private String[] months = {Lang.getString("TedEpisodeDialog.MonthJan"), Lang.getString("TedEpisodeDialog.MonthFeb"), Lang.getString("TedEpisodeDialog.MonthMar"), Lang.getString("TedEpisodeDialog.MonthApr"), Lang.getString("TedEpisodeDialog.MonthMay"), Lang.getString("TedEpisodeDialog.MonthJun"), Lang.getString("TedEpisodeDialog.MonthJul"), Lang.getString("TedEpisodeDialog.MonthAug"), Lang.getString("TedEpisodeDialog.MonthSep"), Lang.getString("TedEpisodeDialog.MonthOct"), Lang.getString("TedEpisodeDialog.MonthNov"), Lang.getString("TedEpisodeDialog.MonthDec")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
	private String[] days  = initString(1, 31);
	private String[] years;
	private JLabel jLabel15;
	private JCheckBox downloadAll_check;
	
	private Font normalFont = new Font(null,0,11);
	private Font boldFont = new Font(null,1,11);
	private Font smallFont = new Font(null,0,11);

	/****************************************************
	 * CONSTRUCTORS
	 ****************************************************/
	/**
	 * Creates a new EpisodeDialog
	 * @param frame TedMainDialog
	 * @param serie Serie where we have to make this dialog for
	 * @param newSerie Is it a new serie?
	 */
	public TedEpisodeDialog(TedMainDialog frame, TedSerie serie, boolean newSerie) 
	{
		super(frame);
		this.setModal(true);
		this.setResizable(false);
		this.tedDialog = frame;
		this.currentSerie = serie;
		this.newSerie = newSerie;
		this.initGUI();
		this.setValues(currentSerie);
	}
	
	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	
	/**
	 * Initialize a array with strings
	 * @param low first value of the array
	 * @param high last value of the array
	 * @return A array filled with entries between low and high
	 */
	private String[] initString(int low, int high) 
	{
		String [] strings = new String [high-low+1];
		
		for (int i = 0; i+low <= high; i++)
		{
			strings[i] = ""+(low+i); //$NON-NLS-1$
		}
		
		return strings;
	}

	private void initGUI() 
	{
		try {
				// set the years which has to be put in the drop down menu
				Calendar c = new GregorianCalendar();
				int year1 = c.get(Calendar.YEAR);
				long x = currentSerie.getBreakUntil();
				long y = currentSerie.getBreakFrom();
				long z;
				
				if(x<y)
					z = x;
				else
					z = y;
				
				// take the from year as most early year except when it's a new show
				if(z!=0)
					c.setTimeInMillis(z);
				
				int year2 = c.get(Calendar.YEAR);
				years = initString(year2, 2010);
				
				if(year2 < year1)
					yearDiff = year1 - year2;
				
				initPopupMenu();
				
				//for updating the show status by (un)checking the break scheduler
				this.wasUsedBreakSchedule = currentSerie.isUseBreakSchedule();
			{
				this.getContentPane().setLayout(null);
			}
			{
				
				button_Save = new JButton();
				this.getContentPane().add(button_Save);
				button_Save.setText(Lang.getString("TedEpisodeDialog.ButtonSave")); //$NON-NLS-1$
				button_Save.setBounds(385, 455, 98, 28);
				button_Save.setToolTipText(Lang.getString("TedEpisodeDialog.ButtonToolTipSave")); //$NON-NLS-1$
				button_Save.addActionListener(TedEL);
				button_Save.setActionCommand("save"); //$NON-NLS-1$
			}
			{
				jButton1 = new JButton();
				this.getContentPane().add(jButton1);
				jButton1.setText(Lang.getString("TedEpisodeDialog.ButtonCancel")); //$NON-NLS-1$
				jButton1.setBounds(280, 455, 98, 28);
				jButton1.setToolTipText(Lang.getString("TedEpisodeDialog.ButtonToolTipCancel")); //$NON-NLS-1$
				jButton1.addActionListener(TedEL);
				jButton1.setActionCommand("cancel"); //$NON-NLS-1$
			}
			{
				jTabbedPane = new JTabbedPane();
				this.getContentPane().add(jTabbedPane);
				jTabbedPane.setBounds(7, 35, 476, 415);
				{
					panelFeeds = new JPanel();
					jTabbedPane.addTab("Feeds", null, panelFeeds, null);
					label_explain = new JLabel();
					jTabbedPane.addTab(Lang.getString("TedEpisodeDialog.TabGeneralSettings"), null, label_explain, null); //$NON-NLS-1$
					label_explain.setBounds(105, 63, 195, 224);
					label_explain.setPreferredSize(new java.awt.Dimension(392, 287));
					{
						text_Name = new JTextField();
						label_explain.add(text_Name);
						text_Name.setText(currentSerie.getName());
						text_Name.setBounds(133, 105, 322, 21);
						text_Name.setFont(smallFont);
					}
					{
						labelName = new JLabel();
						label_explain.add(labelName);
						labelName.setText(Lang.getString("TedEpisodeDialog.LabelName")); //$NON-NLS-1$
						labelName.setFont(boldFont);
						labelName.setBounds(18, 101, 98, 28);
					}
					{
						latest_Button = new JButton();
						label_explain.add(latest_Button);
						latest_Button.setText(Lang.getString("TedEpisodeDialog.ButtonGetLatest")); //$NON-NLS-1$
						latest_Button.setBounds(14, 311, 196, 28);
						latest_Button.setToolTipText(Lang.getString("TedEpisodeDialog.ButtonToolTipGetLatest")); //$NON-NLS-1$
						latest_Button.addActionListener(TedEL);
						latest_Button.setActionCommand("getlatest"); //$NON-NLS-1$
						latest_Button.setIcon(new ImageIcon(
								getClass().getClassLoader().getResource(
									"icons/bril.png"))); //$NON-NLS-1$
					}
					{
						jLabel2 = new JLabel();
						jLabel2.setFont(normalFont);
						label_explain.add(jLabel2);
						jLabel2.setText(Lang.getString("TedEpisodeDialog.LabelSeasonEpisode")); //$NON-NLS-1$
						jLabel2.setBounds(7, 250, 434, 28);
					}
					{
						labelCe = new JLabel();
						labelCe.setFont(boldFont);
						label_explain.add(labelCe);
						labelCe.setText(Lang.getString("TedEpisodeDialog.LabelCurrentEpisode")); //$NON-NLS-1$
						labelCe.setBounds(231, 278, 147, 28);
					}
					{
						labelCS = new JLabel();
						labelCS.setFont(boldFont);
						label_explain.add(labelCS);
						labelCS.setText(Lang.getString("TedEpisodeDialog.LabelCurrentSeason")); //$NON-NLS-1$
						labelCS.setBounds(14, 277, 147, 28);
					}
					{
						text_CurrentEpisode = new JTextField();
						text_CurrentEpisode.setFont(smallFont);
						label_explain.add(text_CurrentEpisode);
						text_CurrentEpisode.setText("" //$NON-NLS-1$
							+ currentSerie.getCurrentEpisode());
						text_CurrentEpisode.setBounds(378, 278, 42, 28);
					}
					{
						text_CurrentSeason = new JTextField();
						text_CurrentSeason.setFont(smallFont);
						label_explain.add(text_CurrentSeason);
						text_CurrentSeason.setText("" //$NON-NLS-1$
							+ currentSerie.getCurrentSeason());
						text_CurrentSeason.setBounds(168, 278, 42, 28);
					}
					{
						downloadAll_check = new JCheckBox();
						downloadAll_check.setFont(boldFont);
						label_explain.add(downloadAll_check);
						downloadAll_check.setText(Lang.getString("TedEpisodeDialog.CheckBoxDownloadAll")); //$NON-NLS-1$
						downloadAll_check.setBounds(9, 336, 455, 35);
						
						// set selected must be put in front of the actionListener
						// because otherwise it will give problems while initializing
						// the episode dialog (pop-up window)
						downloadAll_check.setSelected(currentSerie.isDownloadAll());
						downloadAll_check.addActionListener(TedEL);
						downloadAll_check.setActionCommand("downloadall"); //$NON-NLS-1$
						downloadAll_check.setOpaque(false);
						
					}
					{
						jSeparator2 = new JSeparator();
						label_explain.add(jSeparator2);
						jSeparator2.setBounds(14, 252, 441, 7);
					}

					jScrollPane1 = new JScrollPane();
					panelFeeds.add(jScrollPane1);
					jScrollPane1.setBounds(14, 133, 441, 84);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(453, 243));

					//jButtonAdd.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					//jButtonDelete.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					//jButtonResetDate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

					{
						checkbox_Preset = new JCheckBox();
						label_explain.add(checkbox_Preset);
						checkbox_Preset.setText(Lang.getString("TedEpisodeDialog.CheckAutoUpdate")); //$NON-NLS-1$
						checkbox_Preset.setBounds(10, 49, 448, 28);
						checkbox_Preset.setOpaque(false);
						checkbox_Preset.setFont(boldFont);
						checkbox_Preset.setSelected(currentSerie.isUsePresets());
					}

					feedsToolBar = new JToolBar();
					feedsToolBar.setFloatable(false);
					panelFeeds.add(feedsToolBar);
					feedsToolBar.setBounds(14, 217, 441, 28);

					jButtonAdd = new JButton();
					feedsToolBar.add(jButtonAdd);
					jButtonAdd.setFont(normalFont);
					jButtonAdd.setIcon(new ImageIcon(getClass()
						.getClassLoader().getResource("icons/Aid.png"))); //$NON-NLS-1$
					jButtonAdd.setActionCommand("addfeed"); //$NON-NLS-1$
					jButtonAdd.setToolTipText(Lang
						.getString("TedEpisodeDialog.ButtonToolTipAddFeed")); //$NON-NLS-1$
					jButtonAdd.setBounds(15, 248, 77, 21);
					jButtonAdd.setText(Lang
						.getString("TedEpisodeDialog.ButtonAddFeed")); //$NON-NLS-1$
						jButtonAdd.setPreferredSize(new java.awt.Dimension(119, 21));

						jButtonDelete = new JButton();
						feedsToolBar.add(jButtonDelete);
						jButtonDelete.setActionCommand("deletefeed"); //$NON-NLS-1$
						jButtonDelete
							.setToolTipText(Lang
								.getString("TedEpisodeDialog.ButtonToolTipDeleteFeed")); //$NON-NLS-1$
						jButtonDelete.setBounds(96, 248, 105, 21);
						jButtonDelete.setFont(normalFont);
						jButtonDelete.setIcon(new ImageIcon(getClass()
							.getClassLoader().getResource("icons/Cancel.png"))); //$NON-NLS-1$
						jButtonDelete.setText(Lang
							.getString("TedEpisodeDialog.ButtonDeleteFeed")); //$NON-NLS-1$
							{
								jOpenButton = new JButton();
								feedsToolBar.add(jOpenButton);
								jOpenButton.setText(Lang
									.getString("TedEpisodeDialog.ButtonOpen")); //$NON-NLS-1$
								jOpenButton.setBounds(205, 248, 70, 21);
								jOpenButton
									.setToolTipText(Lang
										.getString("TedEpisodeDialog.ButtonToolTipOpen")); //$NON-NLS-1$
								jOpenButton.addActionListener(TedEL);
								jOpenButton.setActionCommand("openfeed"); //$NON-NLS-1$
								//jOpenButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
								jOpenButton.setFont(normalFont);
							}
							{
								jFindButton = new JButton();
								feedsToolBar.add(jFindButton);
								jFindButton.setText(Lang
									.getString("TedEpisodeDialog.ButtonFind")); //$NON-NLS-1$
								jFindButton.setBounds(280, 248, 70, 21);
								jFindButton
									.setToolTipText(Lang
										.getString("TedEpisodeDialog.ButtonToolTipFind")); //$NON-NLS-1$
								jFindButton.setFont(normalFont);
								jFindButton.addMouseListener(popupListener);
							}

							jButtonMoveFeedDown = new JButton();
							feedsToolBar.add(jButtonMoveFeedDown);
							jButtonMoveFeedDown
								.setActionCommand("movefeeddown"); //$NON-NLS-1$
							jButtonMoveFeedDown.setFont(boldFont);
							jButtonMoveFeedDown
								.setToolTipText(Lang
									.getString("TedEpisodeDialog.ButtonToolTipMoveFeedDown")); //$NON-NLS-1$
							jButtonMoveFeedDown.setBounds(384, 248, 35, 21);
							jButtonMoveFeedDown.addActionListener(TedEL);
							jButtonMoveFeedDown.setIcon(new ImageIcon(
								getClass().getClassLoader().getResource(
									"icons/down.png"))); //$NON-NLS-1$

									jButtonMoveFeedUp = new JButton();
									feedsToolBar.add(jButtonMoveFeedUp);
									jButtonMoveFeedUp
										.setActionCommand("movefeedup"); //$NON-NLS-1$
									jButtonMoveFeedUp.setFont(boldFont);
									jButtonMoveFeedUp
										.setToolTipText(Lang
											.getString("TedEpisodeDialog.ButtonToolTipMoveFeedUp")); //$NON-NLS-1$
									jButtonMoveFeedUp.setBounds(
										420,
										248,
										35,
										21);
									jButtonMoveFeedUp.setIcon(new ImageIcon(
										getClass().getClassLoader()
											.getResource("icons/up.png"))); //$NON-NLS-1$
									jButtonMoveFeedUp.addActionListener(TedEL);

						jButtonDelete.addActionListener(TedEL);

					jButtonAdd.addActionListener(TedEL);

					feedsTableModel = new TedFeedsTableModel();
					feedsTable = new JTable();
					feedsTable.setFont(smallFont);
					jScrollPane1.setViewportView(feedsTable);
					feedsTable.setModel(feedsTableModel);
					feedsTable.setAutoCreateColumnsFromModel(true);
					feedsTable
						.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
					feedsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					feedsTable.setEditingRow(1);
					
					

				}
					label_advanced = new JLabel();
					jTabbedPane.addTab(Lang.getString("TedEpisodeDialog.TabFilters"), null, label_advanced, null); //$NON-NLS-1$
					{
						jLabel15 = new JLabel();
						jTabbedPane.addTab(Lang.getString("TedEpisodeDialog.TabSchedule"), null, jLabel15, null); //$NON-NLS-1$
						jLabel15.setPreferredSize(new java.awt.Dimension(
							373,
							300));
						jLabel15.setBounds(105, 63, 195, 224);
						{
							jCheckSchedule = new JCheckBox();
							jCheckSchedule.setOpaque(false);
							jCheckSchedule.setFont(boldFont);
							jCheckSchedule.addActionListener(TedEL);
							jCheckSchedule.setActionCommand("schedule"); //$NON-NLS-1$
							jLabel15.add(jCheckSchedule);
							jCheckSchedule.setText(Lang.getString("TedEpisodeDialog.CheckEpisodeSchedule")); //$NON-NLS-1$
							jCheckSchedule.setBounds(8, 4, 229, 30);
							jCheckSchedule.setSelected(currentSerie
								.isUseEpisodeSchedule());
						}
						{
							jCheckMonday = new JCheckBox();
							jCheckMonday.setFont(normalFont);
							jLabel15.add(jCheckMonday);
							jCheckMonday.setText(Lang.getString("TedEpisodeDialog.Monday")); //$NON-NLS-1$
							jCheckMonday.setBounds(23, 67, 133, 28);
							jCheckMonday.setOpaque(false);
						}
						{
							jCheckTuesday = new JCheckBox();
							jCheckTuesday.setFont(normalFont);
							jLabel15.add(jCheckTuesday);
							jCheckTuesday.setText(Lang.getString("TedEpisodeDialog.Tuesday")); //$NON-NLS-1$
							jCheckTuesday.setBounds(159, 68, 119, 28);
							jCheckTuesday.setOpaque(false);
						}
						{
							jCheckWednesday = new JCheckBox();
							jLabel15.add(jCheckWednesday);
							jCheckWednesday.setFont(normalFont);
							jCheckWednesday.setText(Lang.getString("TedEpisodeDialog.Wednesday")); //$NON-NLS-1$
							jCheckWednesday.setBounds(284, 67, 161, 28);
							jCheckWednesday.setOpaque(false);
						}
						{
							jCheckThursday = new JCheckBox();
							jCheckThursday.setFont(normalFont);
							jLabel15.add(jCheckThursday);
							jCheckThursday.setText(Lang.getString("TedEpisodeDialog.Thursday")); //$NON-NLS-1$
							jCheckThursday.setBounds(23, 95, 133, 28);
							jCheckThursday.setOpaque(false);
						}
						{
							jCheckFriday = new JCheckBox();
							jCheckFriday.setFont(normalFont);
							jLabel15.add(jCheckFriday);
							jCheckFriday.setText(Lang.getString("TedEpisodeDialog.Friday")); //$NON-NLS-1$
							jCheckFriday.setBounds(159, 96, 119, 28);
							jCheckFriday.setOpaque(false);
						}
						{
							jCheckSaturday = new JCheckBox();
							jLabel15.add(jCheckSaturday);
							jCheckSaturday.setText(Lang.getString("TedEpisodeDialog.Saturday")); //$NON-NLS-1$
							jCheckSaturday.setBounds(284, 95, 161, 28);
							jCheckSaturday.setFont(normalFont);
							jCheckSaturday.setOpaque(false);
						}
						{
							jCheckSunday = new JCheckBox();
							jCheckSunday.setFont(normalFont);
							jLabel15.add(jCheckSunday);
							jCheckSunday.setText(Lang.getString("TedEpisodeDialog.Sunday")); //$NON-NLS-1$
							jCheckSunday.setBounds(23, 123, 133, 28);
							jCheckSunday.setOpaque(false);
						}
						{
							jCheckBreakSchedule = new JCheckBox();
							jCheckBreakSchedule.setOpaque(false);
							jCheckBreakSchedule.setFont(boldFont);
							jLabel15.add(jCheckBreakSchedule);
							jCheckBreakSchedule.setText(Lang.getString("TedEpisodeDialog.CheckBreakSchedule")); //$NON-NLS-1$
							jCheckBreakSchedule.setBounds(7, 182, 434, 28);
							jCheckBreakSchedule.setSelected(currentSerie
								.isUseBreakSchedule());
							jCheckBreakSchedule.addActionListener(TedEL);
							jCheckBreakSchedule
								.setActionCommand("breakschedule"); //$NON-NLS-1$
						}
						{
							jSeparator1 = new JSeparator();
							jLabel15.add(jSeparator1);
							jSeparator1.setBounds(14, 168, 413, 7);
						}
						{
							jBreakLabel = new JLabel();
							jLabel15.add(jBreakLabel);
							jBreakLabel.setText(Lang.getString("TedEpisodeDialog.LabelBreakEpisode")); //$NON-NLS-1$
							jBreakLabel.setFont(normalFont);
							jBreakLabel.setBounds(42, 210, 182, 28);
						}
						{
							jBreakEpisode = new JTextField();
							jBreakEpisode.setFont(smallFont);
							jLabel15.add(jBreakEpisode);
							jBreakEpisode.setBounds(223, 213, 60, 30);
							jBreakEpisode.setText(currentSerie
								.getBreakEpisode()
								+ ""); //$NON-NLS-1$
						}
						{
							jScheduleText = new JLabel();
							jLabel15.add(jScheduleText);
							jScheduleText.setText(Lang.getString("TedEpisodeDialog.LabelEpisodeSchedule")); //$NON-NLS-1$
							jScheduleText.setFont(normalFont);
							jScheduleText.setBounds(28, 35, 420, 28);
							{
								jLabel16 = new JLabel();
								jScheduleText.add(jLabel16);
								jLabel16
									.setText(Lang.getString("TedEpisodeDialog.LabelEpisodeScheduleCheck")); //$NON-NLS-1$
								jLabel16.setBounds(24, 61, 192, 30);
							}
						}
						{
							jBreakLabel2 = new JLabel();
							jBreakLabel2.setFont(normalFont);
							jLabel15.add(jBreakLabel2);
							jBreakLabel2.setText(Lang.getString("TedEpisodeDialog.LabelBreakHold")); //$NON-NLS-1$
							jBreakLabel2.setBounds(21, 294, 203, 28);
						}
						{
							jBreakLabel3 = new JLabel();
							jBreakLabel3.setFont(normalFont);
							jLabel15.add(jBreakLabel3);
							jBreakLabel3.setText(Lang.getString("TedEpisodeDialog.LabelBreakFrom")); //$NON-NLS-1$
							jBreakLabel3.setBounds(42, 252, 182, 28);
						}
						{
							ComboBoxModel jBreakDayModel = new DefaultComboBoxModel(
								days);
							jBreakDay = new JComboBox();
							jBreakDay.setFont(smallFont);
							jLabel15.add(jBreakDay);
							jBreakDay.setModel(jBreakDayModel);
							jBreakDay.setBounds(223, 294, 63, 28);
						}
						{
							ComboBoxModel jBreakMonthModel = new DefaultComboBoxModel(
								months);
							jBreakMonth = new JComboBox();
							jLabel15.add(jBreakMonth);
							jBreakMonth.setModel(jBreakMonthModel);
							jBreakMonth.setBounds(294, 294, 63, 28);
							jBreakMonth.setFont(smallFont);
						}
						{
							ComboBoxModel jBreakYearModel = new DefaultComboBoxModel(
								years);
							jBreakYear = new JComboBox();
							jBreakYear.setFont(smallFont);
							jLabel15.add(jBreakYear);
							jBreakYear.setModel(jBreakYearModel);
							jBreakYear.setBounds(365, 294, 63, 28);
						}
						{
							ComboBoxModel jFromBreakDayModel = new DefaultComboBoxModel(
								days);
							jFromBreakDay = new JComboBox();
							jFromBreakDay.setFont(smallFont);
							jLabel15.add(jFromBreakDay);
							jFromBreakDay.setModel(jFromBreakDayModel);
							jFromBreakDay.setBounds(223, 254, 63, 28);
						}
						{
							ComboBoxModel jFromBreakMonthModel = new DefaultComboBoxModel(
								months);
							jFromBreakMonth = new JComboBox();
							jLabel15.add(jFromBreakMonth);
							jFromBreakMonth.setModel(jFromBreakMonthModel);
							jFromBreakMonth.setBounds(294, 254, 63, 28);
							jFromBreakMonth.setFont(smallFont);
						}
						{
							ComboBoxModel jFromBreakYearModel = new DefaultComboBoxModel(
								years);
							jFromBreakYear = new JComboBox();
							jFromBreakYear.setFont(smallFont);
							jLabel15.add(jFromBreakYear);
							jFromBreakYear.setModel(jFromBreakYearModel);
							jFromBreakYear.setBounds(365, 254, 63, 28);
						}
						{
							jCheckBoxBreakEpisode = new JCheckBox();
							jLabel15.add(jCheckBoxBreakEpisode);
							jCheckBoxBreakEpisode.setBounds(20, 210, 60, 30);
							jCheckBoxBreakEpisode.setOpaque(false);
							jCheckBoxBreakEpisode.setSelected(currentSerie.isUseBreakScheduleEpisode());
							jCheckBoxBreakEpisode.addActionListener(TedEL);
							jCheckBoxBreakEpisode.setActionCommand("breakschedule"); //$NON-NLS-1$
						}
						{
							jCheckBoxBreakFrom = new JCheckBox();
							jLabel15.add(jCheckBoxBreakFrom);
							jCheckBoxBreakFrom.setBounds(21, 253, 21, 28);
							jCheckBoxBreakFrom.setOpaque(false);
							jCheckBoxBreakFrom.setSelected(currentSerie.isUseBreakScheduleFrom());
							jCheckBoxBreakFrom.addActionListener(TedEL);
							jCheckBoxBreakFrom.setActionCommand("breakschedule"); //$NON-NLS-1$
						}
					}
					if(currentSerie.isDaily)
					{
						labelCe.setVisible(false);
						labelCS.setVisible(false);
						//text_CurrentEpisode.setVisible(false);
						text_CurrentEpisode.setText("" + ((TedDailySerie)currentSerie).getMaxDownloads());
						//text_CurrentSeason.setVisible(false);
						text_CurrentSeason.setText("" + ((TedDailySerie)currentSerie).getLatestDownloadDateInMillis());
						latest_Button.setVisible(false);
						jLabel2.setVisible(false);
						
					}
			}
			{
				label_title = new JLabel();
				this.getContentPane().add(label_title);
				label_title.setBounds(9, 5, 301, 30);
			}
			/*{
				text_maxSize = new JTextField();
				text_maxSize.setText("" + currentSerie.getMaxSize());
				text_maxSize.setBounds(107, 265, 44, 30);
			}*/
			this.setSize(498, 520);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		String titlestring = Lang.getString("TedEpisodeDialog.WindowTitleNew"); //$NON-NLS-1$
		if (!newSerie)
		{
			titlestring = Lang.getString("TedEpisodeDialog.WindowTitleEdit") + " " + currentSerie.getName(); //$NON-NLS-1$
		}
		this.setTitle(titlestring);
		label_title.setText(titlestring);
		{
			keyword_text = new TextField();
			keyword_text.setFont(smallFont);
			label_advanced.add(keyword_text);
			keyword_text.setText(currentSerie.getKeywords());
			keyword_text.setBounds(168, 280, 266, 77);
		}
		{
			jLabel12 = new JLabel();
			jLabel12.setFont(normalFont);
			label_advanced.add(jLabel12);
			jLabel12.setText(Lang.getString("TedEpisodeDialog.LabelKeywordsHelp2")); //$NON-NLS-1$
			jLabel12.setBounds(7, 231, 427, 28);
		}
		{
			jLabel6 = new JLabel();
			label_advanced.add(jLabel6);
			jLabel6.setFont(boldFont);
			jLabel6.setText(Lang.getString("TedEpisodeDialog.LabelKeywords1")); //$NON-NLS-1$
			jLabel6.setBounds(14, 276, 147, 28);
		}
		{
			text_maxSize = new JTextField();
			text_maxSize.setFont(smallFont);
			label_advanced.add(text_maxSize);
			text_maxSize.setText("" + currentSerie.getMaxSize()); //$NON-NLS-1$
			text_maxSize.setBounds(166, 77, 98, 28);
		}
		{
			text_minSize = new JTextField();
			text_minSize.setFont(smallFont);
			label_advanced.add(text_minSize);
			text_minSize.setText("" + currentSerie.getMinSize()); //$NON-NLS-1$
			text_minSize.setBounds(166, 44, 98, 28);
		}
		{
			labelMaxSize = new JLabel();
			labelMaxSize.setFont(boldFont);
			label_advanced.add(labelMaxSize);
			labelMaxSize.setText(Lang.getString("TedEpisodeDialog.LabelMaxSize")); //$NON-NLS-1$
			labelMaxSize.setBounds(14, 77, 154, 28);
		}
		{
			labelMinSeeders = new JLabel();
			labelMinSeeders.setFont(boldFont);
			label_advanced.add(labelMinSeeders);
			labelMinSeeders.setText(Lang.getString("TedEpisodeDialog.LabelMinSeeders")); //$NON-NLS-1$
			labelMinSeeders.setBounds(14, 161, 154, 28);
		}
		{
			labelSeeders = new JLabel();
			labelSeeders.setFont(normalFont);
			label_advanced.add(labelSeeders);
			labelSeeders.setText(Lang.getString("TedEpisodeDialog.LabelSeeders")); //$NON-NLS-1$
			labelSeeders.setFont(new java.awt.Font("Dialog",0,12)); //$NON-NLS-1$
			labelSeeders.setBounds(280, 161, 154, 28);
			labelSeeders.setFont(normalFont);
			{
				jLabel10 = new JLabel();
				labelSeeders.add(jLabel10);
				jLabel10
					.setText(Lang.getString("TedEpisodeDialog.LabelSize")); //$NON-NLS-1$
				jLabel10.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
				jLabel10.setBounds(8, 289, 253, 57);
			}
		}
		{
			jLabel3 = new JLabel();
			jLabel3.setFont(normalFont);
			label_advanced.add(jLabel3);
			jLabel3.setText(Lang.getString("TedEpisodeDialog.LabelMegaByte")); //$NON-NLS-1$
			jLabel3.setFont(new java.awt.Font("Dialog",0,12)); //$NON-NLS-1$
			jLabel3.setBounds(280, 77, 175, 28);
			jLabel3.setFont(normalFont);
			{
				jLabel5 = new JLabel();
				jLabel3.add(jLabel5);
				jLabel5
					.setText(Lang.getString("TedEpisodeDialog.LabelSize")); //$NON-NLS-1$
				jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
				jLabel5.setBounds(8, 289, 253, 57);
			}
			{
				jLabel7 = new JLabel();
				jLabel3.add(jLabel7);
				jLabel7.setText(Lang.getString("TedEpisodeDialog.LabelMegaByte")); //$NON-NLS-1$
				jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
				jLabel7.setBounds(159, 334, 28, 29);
				{
					jLabel8 = new JLabel();
					jLabel7.add(jLabel8);
					jLabel8
						.setText(Lang.getString("TedEpisodeDialog.LabelSize")); //$NON-NLS-1$
					jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
					jLabel8.setBounds(8, 289, 253, 57);
				}
			}
		}
		{
			jLabel14 = new JLabel();
			jLabel14.setFont(normalFont);
			label_advanced.add(jLabel14);
			jLabel14.setText(Lang.getString("TedEpisodeDialog.LabelKeywords")); //$NON-NLS-1$
			jLabel14.setBounds(7, 210, 427, 28);
		}
		{
			jSeparator3 = new JSeparator();
			label_advanced.add(jSeparator3);
			jSeparator3.setBounds(14, 203, 448, 7);
		}
		{
			jSeparator4 = new JSeparator();
			label_advanced.add(jSeparator4);
			jSeparator4.setBounds(14, 112, 448, 14);
		}

		jKeywordLabel3 = new JLabel();
		label_advanced.add(jKeywordLabel3);
		jKeywordLabel3.setFont(normalFont);
		jKeywordLabel3.setText(Lang.getString("TedEpisodeDialog.LabelKeywordsHelp3")); //$NON-NLS-1$
		jKeywordLabel3.setBounds(7, 252, 427, 28);

		text_minSeeders = new JTextField();
		label_advanced.add(text_minSeeders);
		text_minSeeders.setFont(smallFont);
		text_minSeeders.setText("" + currentSerie.getMinNumOfSeeders()); //$NON-NLS-1$
		text_minSeeders.setBounds(166, 161, 98, 28);

		jLabel17 = new JLabel();
		label_advanced.add(jLabel17);
		jLabel17.setText(Lang.getString("TedEpisodeDialog.LabelMegaByte")); //$NON-NLS-1$
		jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
		jLabel17.setBounds(280, 42, 161, 28);
		jLabel17.setFont(normalFont);

		jLabel19 = new JLabel();
		label_advanced.add(jLabel19);
		jLabel19.setText(Lang.getString("TedEpisodeDialog.LabelMinSize2")); //$NON-NLS-1$
		jLabel19.setFont(boldFont);
		jLabel19.setBounds(14, 43, 154, 28);

		{
			jLabel11 = new JLabel();
			jLabel11.setFont(normalFont);
			label_advanced.add(jLabel11);
			jLabel11.setText(Lang.getString("TedEpisodeDialog.LabelSize2")); //$NON-NLS-1$
			jLabel11.setBounds(7, 0, 420, 56);

			labelMinSeedersText = new JLabel();
			label_advanced.add(labelMinSeedersText);
			labelMinSeedersText.setText(Lang.getString("TedEpisodeDialog.LabelSeeders2")); //$NON-NLS-1$
			labelMinSeedersText.setFont(normalFont);
			labelMinSeedersText.setBounds(7, 112, 420, 56);

		}

		jLabel18 = new JLabel();
		jLabel17.add(jLabel18);
		jLabel18.setText(Lang.getString("TedEpisodeDialog.LabelSize")); //$NON-NLS-1$
		jLabel18.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
		jLabel18.setBounds(8, 289, 253, 57);

		// make sure the first column is always 16 width
		TableColumn	column = feedsTable.getColumnModel().getColumn(0);
		column.setMaxWidth(25);
		column.setMinWidth(16);
		
		// and the column with the type is also smaller
		column = feedsTable.getColumnModel().getColumn(2);
		column.setMaxWidth(100);
		column.setMinWidth(100);
		
		 //Get the screen size
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screenSize = toolkit.getScreenSize();

	    //Calculate the frame location
	    int x = (screenSize.width - this.getWidth()) / 2;
	    int y = (screenSize.height - this.getHeight()) / 2;

	    //Set the new frame location
	    this.setLocation(x, y);
	}
	
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
		
		//findRSSPopupMenu = new TedPopupMenu(this,items);
	}
	
	private void setValues(TedSerie serie)
	{
		this.text_Name.setText(serie.getName());
		this.text_maxSize.setText("" + serie.getMaxSize()); //$NON-NLS-1$
		this.text_minSize.setText("" + serie.getMinSize()); //$NON-NLS-1$
		this.text_minSeeders.setText("" + serie.getMinNumOfSeeders()); //$NON-NLS-1$
		this.keyword_text.setText(serie.getKeywords());		
		this.jBreakEpisode.setText("" + serie.getBreakEpisode()); //$NON-NLS-1$
		this.jCheckBreakSchedule.setSelected(serie.isUseBreakSchedule());
		this.jCheckBoxBreakEpisode.setSelected(serie.isUseBreakScheduleEpisode());
		this.jCheckBoxBreakFrom.setSelected(serie.isUseBreakScheduleFrom());
		this.jCheckSchedule.setSelected(serie.isUseEpisodeSchedule());
		this.initDays(serie);
		this.scheduleUpdate();
		this.initBreakDate(serie.getBreakFrom(), serie.getBreakUntil());
		this.breakUpdate();
		this.downloadAllUpdate(true);
		this.feedsTableModel.setSeriesFeeds(serie.getFeeds());
	}
	
	private void readXMLFile()
	{
		names = new Vector();		
		names.addElement(Lang.getString("TedEpisodeDialog.FeedsListSelectOne")); //$NON-NLS-1$
		
		TedXMLParser parser = new TedXMLParser();
		Element shows = parser.readXMLFile(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
		
		/*if(shows!=null)
			//parser.getNames(shows, names);
		else
			TedLog.error(Lang.getString("TedEpisodeDialog.LogXmlNotFound")); //$NON-NLS-1$*/
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
		
		if(from != 0 || until != 0)
		{
			/* If break dates are specified fill them in */
			if(until!=0)
				c.setTimeInMillis(until);
			
			if(from!=0)
				c2.setTimeInMillis(from);			
		}
		/*else if(!jCheckBreakSchedule.isSelected())
		{
			/* If break schedule not checked at all fill in for both date the 
			 * date of today
			 
			
			c  = c3;
			c2 = c3;
		}
		else if(!jCheckBoxBreakFrom.isSelected())
		{
			/* If not break from is checked set from date to today 
			c2 = c3;
		}*/
		

		try
		{
			jBreakDay.setSelectedIndex(c.get(Calendar.DAY_OF_MONTH)-1);
			jBreakMonth.setSelectedIndex(c.get(Calendar.MONTH));
			jBreakYear.setSelectedIndex(c.get(Calendar.YEAR)-c3.get(Calendar.YEAR) + yearDiff);
								
			jFromBreakDay.setSelectedIndex(c2.get(Calendar.DAY_OF_MONTH)-1);
			jFromBreakMonth.setSelectedIndex(c2.get(Calendar.MONTH));
			jFromBreakYear.setSelectedIndex(c2.get(Calendar.YEAR)-c3.get(Calendar.YEAR) + yearDiff);
		}
		catch(Exception e)
		{
			TedLog.debug(Lang.getString("TedEpisodeDialog.LogWrongDates") + //$NON-NLS-1$
					 c.get(Calendar.DAY_OF_MONTH)  + "-" + (c.get(Calendar.MONTH)+1)  + "-" + c.get(Calendar.YEAR)  + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					 c2.get(Calendar.DAY_OF_MONTH) + "-" + (c2.get(Calendar.MONTH)+1) + "-" + c2.get(Calendar.YEAR) + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					 c3.get(Calendar.DAY_OF_MONTH) + "-" + (c3.get(Calendar.MONTH)+1) + "-" + c3.get(Calendar.YEAR) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}
		

	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	/**
	 * Close the dialog
	 */
	public void closeDialog() 
	{
		//currentSerie.setDate(dateBeforeReset);
		this.setVisible(false);	
	}

	/**
	 * Save values of the dialog into the show
	 */
	public void saveShow()
	{
		int cs = 0;
		int ce = 0;
		int min = 0;
		int max = 0;
		int breakep;
		int minSeeders = 0;
		try
		{
			cs = Integer.parseInt(text_CurrentSeason.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogNumericSeason")); //$NON-NLS-1$
			return;
		}
		try
		{
			breakep = Integer.parseInt(jBreakEpisode.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogNumericBreakEpisode") + //$NON-NLS-1$
					Lang.getString("TedEpisodeDialog.DialogNumericBreakEpisode2")); //$NON-NLS-1$
			return;
		}
		try
		{
			ce = Integer.parseInt(text_CurrentEpisode.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogNumericEpisode")); //$NON-NLS-1$
			return;
		}
		try
		{
			min = Integer.parseInt(text_minSize.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogMinimumSize")); //$NON-NLS-1$
			return;
		}
		try
		{
			max = Integer.parseInt(text_maxSize.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogMaximumSize")); //$NON-NLS-1$
			return;
		}
		try
		{
			minSeeders = Integer.parseInt(text_minSeeders.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogMinimumSeeders")); //$NON-NLS-1$
			return;
		}
		if (text_Name.getText().equals("")) //$NON-NLS-1$
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogShowName")); //$NON-NLS-1$
			return;
		}
		/*if (text_Url.getText().equals(""))
		{
			JOptionPane.showMessageDialog(this, "Please enter a RSS feed for this show");
			return;
		}*/
		if(min>max)
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogMinLargerThanMax")); //$NON-NLS-1$
			return;
		}
		if(!checkBrackets(keyword_text.getText()))
		{
			JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogBrackets")); //$NON-NLS-1$
			return;
		}	
		
		Calendar c = new GregorianCalendar();
		int day2  = jBreakDay.getSelectedIndex();
		int month = jBreakMonth.getSelectedIndex();
		int year  = jBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
		
		int fday   = jFromBreakDay.getSelectedIndex();
		int fmonth = jFromBreakMonth.getSelectedIndex();
		int fyear  = jFromBreakYear.getSelectedIndex()+c.get(Calendar.YEAR) - yearDiff;
		
		if(jCheckBreakSchedule.isSelected() && jCheckBoxBreakFrom.isSelected())
		{
			if((year<fyear) || (year==fyear && month<fmonth) || (year==fyear && month==fmonth && day2<fday))
			{
				JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogFromDateLargerThanBreak")); //$NON-NLS-1$
				return;
			}
			
			if(year==fyear && month==fmonth && day2==fday)
			{
				JOptionPane.showMessageDialog(this, Lang.getString("TedEpisodeDialog.DialogFromIsBreak")); //$NON-NLS-1$
				return;
			}
		}
				
		currentSerie.setName(text_Name.getText());
		//currentSerie.setFeeds()
		//currentSerie.setUrl(text_Url.getText());
		currentSerie.setCurrentSeason(cs);
		currentSerie.setCurrentEpisode(ce);
		currentSerie.setUsePresets(checkbox_Preset.isSelected());
		currentSerie.setMinSize(min);
		currentSerie.setMaxSize(max);
		currentSerie.setKeywords(keyword_text.getText().toLowerCase());
		currentSerie.setDownloadAll(downloadAll_check.isSelected());
		currentSerie.setEpisodeSchedule(jCheckSchedule.isSelected(), this.getDays());
		currentSerie.setWeeklyInterval(1);
		currentSerie.setUseBreakSchedule(jCheckBreakSchedule.isSelected());
		currentSerie.setUseBreakScheduleEpisode(jCheckBoxBreakEpisode.isSelected());
		currentSerie.setUseBreakScheduleFrom(jCheckBoxBreakFrom.isSelected());
		
		currentSerie.setBreakUntil(day2, month, year);
		currentSerie.setBreakFrom(fday, fmonth, fyear);
		currentSerie.setBreakEpisode(breakep);
		
		currentSerie.setFeeds(feedsTableModel.getSerieFeeds());
		currentSerie.setMinNumOfSeeders(minSeeders);
		
		// when the user wants to put the show immediatly on hold
		if(!(currentSerie.isUseBreakScheduleEpisode() || currentSerie.isUseBreakScheduleFrom()))
		{
			if(currentSerie.isUseBreakSchedule() && (currentSerie.getStatus() == TedSerie.STATUS_CHECK))
				currentSerie.setStatus(TedSerie.STATUS_HOLD);
			else if(this.wasUsedBreakSchedule && !currentSerie.isUseBreakSchedule())
				currentSerie.setStatus(TedSerie.STATUS_CHECK);
		}
		
		this.setVisible(false);
		
		if (newSerie)
		{
			tedDialog.addSerie(currentSerie);
		}
		
		tedDialog.saveShows();
		currentSerie.checkDate();
		currentSerie.resetStatus();
	}
	
	private boolean checkBrackets(String s)
	{
		boolean result = false;
		int count = 0;
		char c;
		
		for(int i=0; i<s.length(); i++)
		{
			c = s.charAt(i);
			if(c=='(')
				count++;
			else if(c==')')
				count--;
		}
		
		if(count==0)
			result = true;
		
		return result;
	}
	
	/**
	 * Try to read the latest season and episode from the rss feed
	 */
	public void getLatestSandE() 
	{		
		// check only if there are feeds in the table
		if (feedsTableModel.getRowCount() > 0)
		{
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			this.setCursor(hourglassCursor);
			
			// make temp serie to parse on
			TedParser parse = new TedParser();
			TedSerie tempSerie = new TedSerie();
			tempSerie.setName(text_Name.getText());
			tempSerie.setKeywords(keyword_text.getText().toLowerCase());
			tempSerie.setFeeds(feedsTableModel.getSerieFeeds());

			// set latest season and episode
			Vector items = parse.getItems(tempSerie);
			SeasonEpisode se = (SeasonEpisode)items.get(0);
			text_CurrentSeason.setText(""+ se.getSeason()); //$NON-NLS-1$
			text_CurrentEpisode.setText(""+ se.getEpisode()); //$NON-NLS-1$
			
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setCursor(normalCursor);
		}
		
	}
	
	/**
	 * Read values from the chosen serie into the textboxes of the dialog
	 * @param e
	 */
	public void choose_Serie(ActionEvent e)
	{
		JComboBox cb = (JComboBox)e.getSource();
		// if the user doesnt select the "Select One" string
		if (cb.getSelectedIndex() > 0)
		{
			// put the name and the url of the selected show in the textfields
			String name = (String)names.get(cb.getSelectedIndex());
			//String url = (String)locations.get(cb.getSelectedIndex());
			
			feedsTableModel.deleteAllRows();
			
			TedXMLParser parser = new TedXMLParser();
			Element series = parser.readXMLFile(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$
			TedSerie serie  = parser.getSerie(series, name);

			if(serie!=null)
			{
				currentSerie.setUsePresets(checkbox_Preset.isSelected());
				//currentSerie.AutoFillInPresets(serie);
				this.setValues(serie);
								
				/*text_minSize.setText("" + serie.getMinSize());
				text_maxSize.setText("" + serie.getMaxSize());
				text_minSeeders.setText("" + serie.getMinNumOfSeeders());
				text_Name.setText(serie.getName());
				
				for(int j=0; j<currentSerie.getFeeds().size(); j++)
				{
					addFeed(((TedSerieFeed)currentSerie.getFeeds().elementAt(j)).getUrl());
				}*/
			}
			else
			{
				addFeed(Lang.getString("TedEpisodeDialog.ListXMLNotFound")); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * If the user checks download all, set all the other fields to not enabled
	 */
	public void downloadAllUpdate(boolean dialogStartup) 
	{
		boolean b = downloadAll_check.isSelected();
		
		// set the labels and fields for season and episode on !b
		text_CurrentSeason.setEnabled(!b);
		text_CurrentEpisode.setEnabled(!b);
		labelCS.setEnabled(!b);
		labelCe.setEnabled(!b);
		latest_Button.setEnabled(!b);
		
		if(b && !dialogStartup)
		{
			// ask if the user wants to download all items from the feed
			int answer = JOptionPane.showOptionDialog(tedDialog, Lang.getString("TedEpisodeDialog.DialogDownloadAll1") + //$NON-NLS-1$
					" " + Lang.getString("TedEpisodeDialog.DialogDownloadAll2") + //$NON-NLS-1$
					"\n" + Lang.getString("TedEpisodeDialog.DialogDownloadAll3"), //$NON-NLS-1$
		                Lang.getString("TedEpisodeDialog.DialogDownloadAllHeader"),  //$NON-NLS-1$
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE, null, Lang.getYesNoLocale(), Lang.getYesNoLocale()[0]);
				
			if (answer == JOptionPane.YES_OPTION)
			{
				TedSerieFeed [] feeds = this.getFeeds();
				TedParser tp = new TedParser();
				tp.setToLatestDate(currentSerie, feedsTableModel, feeds, tedDialog);
			}
		}
	}
	
	/**
	 * @return Array of checked days in the schedule
	 */
	public boolean[] getDays()
	{
		boolean [] days = new boolean [7];
		days[Calendar.SUNDAY-1] = jCheckSunday.isSelected();
		days[Calendar.MONDAY-1] = jCheckMonday.isSelected();
		days[Calendar.TUESDAY-1] = jCheckTuesday.isSelected();
		days[Calendar.WEDNESDAY-1] = jCheckWednesday.isSelected();
		days[Calendar.THURSDAY-1] = jCheckThursday.isSelected();
		days[Calendar.FRIDAY-1] = jCheckFriday.isSelected();
		days[Calendar.SATURDAY-1] = jCheckSaturday.isSelected();
		
		return days;
	}
	
	/**
	 * @return Selected days in the episode dialog, from sunday = 0 to saturday (= 7)
	 */
	public TedSerieFeed[] getFeeds()
	{
		TedSerieFeed [] feeds = new TedSerieFeed[feedsTable.getRowCount()];
		
		for (int i=0; i<feedsTable.getRowCount(); i++)
		{
			feeds[i] = feedsTableModel.getSerieAt(i);
		}
		
		return feeds;
	}
	
	/**
	 * Init the checked days in the schedule
	 */
	public void initDays(TedSerie serie)
	{
		boolean [] days = serie.getDays();
		
		jCheckSunday.setSelected(days[Calendar.SUNDAY-1]);
		jCheckMonday.setSelected(days[Calendar.MONDAY-1]);
		jCheckTuesday.setSelected(days[Calendar.TUESDAY-1]);
		jCheckWednesday.setSelected(days[Calendar.WEDNESDAY-1]);
		jCheckThursday.setSelected(days[Calendar.THURSDAY-1]);
		jCheckFriday.setSelected(days[Calendar.FRIDAY-1]);
		jCheckSaturday.setSelected(days[Calendar.SATURDAY-1]);
	}

	/**
	 * If the user wants to use the schedule, set the fields enabled.
	 */
	public void scheduleUpdate()
	{
		boolean b = jCheckSchedule.isSelected();
		
		// set all the days and labels for the schedule 
		jCheckSunday.setEnabled(b);
		jCheckMonday.setEnabled(b);
		jCheckTuesday.setEnabled(b);
		jCheckWednesday.setEnabled(b);
		jCheckThursday.setEnabled(b);
		jCheckFriday.setEnabled(b);
		jCheckSaturday.setEnabled(b);
		jScheduleText.setEnabled(b);
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
		
		jBreakYear.setEnabled(b);
		jBreakMonth.setEnabled(b);
		jBreakDay.setEnabled(b);
		jFromBreakYear.setEnabled(b&&f);
		jFromBreakMonth.setEnabled(b&&f);
		jFromBreakDay.setEnabled(b&&f);
		jBreakEpisode.setEnabled(b&&e);
		jBreakLabel.setEnabled(b);
		jBreakLabel2.setEnabled(b);
		jBreakLabel3.setEnabled(b);
	}

	/**
	 * Add a new feed to the table
	 */
	public void addFeed()
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
	
	public void addFeed(String s)
	{
		TedSerieFeed newFeed = new TedSerieFeed(s, 0);
		feedsTableModel.addSerie(newFeed);
	}
	
	/**
	 * Delete the selected feed from the table
	 */
	public void deleteSelectedFeed()
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
	public void openRSSFeed() 
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

	/**
	 * Reset the last parse date of the selected feed
	 */
	public void resetSelectedDate()
	{
		//TODO: save the previous date?
//		 get selected feed
		int selectedRow = feedsTable.getSelectedRow();
		if (selectedRow != -1)
		{
			TedSerieFeed selectedFeed = feedsTableModel.getSerieAt(selectedRow);
			selectedFeed.setDate(0);
			feedsTableModel.fireTableDataChanged();
		}
		
	}

	public void moveSelectedFeedUp()
	{
		int selectedRow = feedsTable.getSelectedRow();
		if (selectedRow > 0)
		{
			feedsTableModel.moveUp(selectedRow);
			feedsTable.setRowSelectionInterval(selectedRow-1, selectedRow-1);
		}
		
	}

	public void moveSelectedFeedDown()
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
	
	public String getEpisodeName()
	{
		return text_Name.getText();
	}
	
	public void addKeywords(String key)
	{
		String oldKeywords = this.keyword_text.getText();
		String newKeywords;
		if(!oldKeywords.contains(key))
		{
			if(!oldKeywords.equals(""))
				newKeywords = "((" + oldKeywords + ")&" + key + ')';
			else 
				newKeywords = key;
			
			this.keyword_text.setText(newKeywords);
		}
	}
}
