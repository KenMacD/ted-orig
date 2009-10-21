package ted;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.w3c.dom.Element;

import ted.infrastructure.ITedMain;
import ted.infrastructure.TedMainCore;
import ted.ui.addshowdialog.AddShowDialog;
import ted.ui.configdialog.ConfigDialog;
import ted.ui.editshowdialog.EditMultipleShowsDialog;
import ted.ui.editshowdialog.EditShowDialog;
import ted.ui.messaging.MessengerCenter;

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
 * TED: Torrent Episode Downloader (2005 - 2009)
 *
 * This is the mainwindow of ted
 * It shows all the shows with their urls, status and more and includes menus
 * and buttons for the user to interact with ted.
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
public class TedMainDialog extends javax.swing.JFrame implements ActionListener, ITedMain
{
    /****************************************************
     * GLOBAL VARIABLES
     ****************************************************/
    private static final long serialVersionUID = 3722636937353936684L;

    private TedMainCore tedCore = new TedMainCore();

    private static final double tedVersion = 0.971;

    // menu images
    private ImageIcon tedProgramIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-ted2.png")); //$NON-NLS-1$
    private ImageIcon tedIdleIcon    = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-ted.gif")); //$NON-NLS-1$
    private ImageIcon tedActiveIcon  = new ImageIcon(getClass().getClassLoader().getResource("icons/icon-active-ted.gif")); //$NON-NLS-1$
    private final Font SMALL_FONT    = new Font("Dialog",0,10);

    private JLabel label_count;
    private JScrollPane jScrollPane1;

    private TedTable          serieTable;
    private TedCounter        tCounter;
    private TedLogDialog      tLog;
    private TedParseHandler   tParseHandler;
    private TedMainToolBar    TedToolBar;
    private TedTablePopupMenu ttPopupMenu;
    private TedMainMenuBar    tMenuBar;
    private TedTrayIcon       tedTray;

    private JPanel jStatusPanel;
    private JPanel jPanel1;

    private boolean osHasTray = TedSystemInfo.osSupportsTray();
    private boolean stopParsing = false;
    private boolean isParsing = false;

    private boolean uiInitialized = false;

    private MessengerCenter messengerCenter;

    private Preferences preferences;

      /****************************************************
     * CONSTRUCTORS
     ****************************************************/
    /**
     * Constructs a new TedMainDialog
     * @param userWantsTray Flag if the user wants ted to add a trayicon
     */
    public TedMainDialog(boolean userWantsTray, boolean saveInLocalDir)
    {
        this.preferences = Preferences.userNodeForPackage(TedMainDialog.class);

        this.osHasTray = this.osHasTray && userWantsTray;

        // set if user wants to save / read files from local dir instead of users dir
        TedSystemInfo.setSaveInLocalDir(saveInLocalDir);

        // check if the java version is correct
        if (TedSystemInfo.isSupportedJava())
        {
            initGUI();
        }
        else
        {
            // show dialog that asks user to download latest javaversion
            JOptionPane.showMessageDialog(null, Lang.getString("TedMainDialog.DialogJavaVersion1") + " (" + TedSystemInfo.getJavaVersion() + ") "+ Lang.getString("TedMainDialog.DialogJavaVersion2") + " \n" + //$NON-NLS-1$
                    Lang.getString("TedMainDialog.DialogJavaVersion3") + " " + TedSystemInfo.MINIMUM_JAVA + ".\n" +
                    Lang.getString("TedMainDialog.DialogJavaVersion4"));
        }
    }


    /****************************************************
     * LOCAL METHODS
     ****************************************************/
    private void initGUI()
    {
        uiInitialized = false;
        // set look and feel to system default
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (Exception e1)
        {
            TedLog.error(e1, Lang.getString("TedMainDialog.ErrorLookAndFeel")); //$NON-NLS-1$
            SwingUtilities.updateComponentTreeUI( this );
        }


        try
        {
            TedIO.getInstance().GetConfig();
        }
        catch (FileNotFoundException e)
        {
            // Doesn't matter, and is going away.
        }

        Lang.setLanguage(TedConfig.getInstance().getLocale());

        try
        {
            // main layout
            BoxLayout thisLayout = new BoxLayout(
                this.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS);
            this.getContentPane().setLayout(thisLayout);

            // init menubar
            tMenuBar = new TedMainMenuBar(this);
            setJMenuBar(tMenuBar);

            // fill panel on window
            jPanel1 = new JPanel();
            this.getContentPane().add(jPanel1);
            jPanel1.setMaximumSize(new java.awt.Dimension(32767, 20));


            // add toolbar to panel
            TedToolBar = new TedMainToolBar(this);
            jPanel1.add(TedToolBar);
            //jPanel1.setAlignmentX(JPanel.RIGHT_ALIGNMENT);

            // add scrollpane to panel
            jScrollPane1 = new JScrollPane();
            this.getContentPane().add(jScrollPane1);

            //	add context menu for table
            this.ttPopupMenu = new TedTablePopupMenu(this);
            this.getContentPane().add(ttPopupMenu);

            // add table to scrollpanel
            serieTable = new TedTable(this, ttPopupMenu);
            jScrollPane1.setViewportView(serieTable);

            // status bar
            jStatusPanel = new JPanel();
            GridLayout jStatusPanelLayout = new GridLayout(1, 1);
            jStatusPanelLayout.setColumns(3);
            jStatusPanelLayout.setHgap(5);
            jStatusPanelLayout.setVgap(5);
            jStatusPanel.setFont(this.SMALL_FONT);
            jStatusPanel.setLayout(jStatusPanelLayout);
            getContentPane().add(jStatusPanel);
            jStatusPanel.setPreferredSize(new java.awt.Dimension(455, 18));
            jStatusPanel.setMaximumSize(new java.awt.Dimension(32767, 18));

            label_count = new JLabel();
            jStatusPanel.add(label_count);
            FlowLayout label_countLayout = new FlowLayout();
            label_count.setLayout(label_countLayout);
            label_count.setFont(this.SMALL_FONT);
            label_count.setBounds(10, 0, 189, 14);
            label_count.setPreferredSize(new java.awt.Dimension(424, 17));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // if the shows.xml file does not exist download it
        File f = new File(TedIO.XML_SHOWS_FILE); //$NON-NLS-1$

        boolean showsXMLExists = f.exists();
        if(!showsXMLExists)
        {
            // disable add show button when show list is not yet available
            this.TedToolBar.setAddButtonEnabled(false);
        }

        this.setStatusString(Lang.getString("TedMain.LoadingConfig"));

        // register for mac os quit, preferences and about dialog items in menu
        if (TedSystemInfo.osIsMac())
        {
            new TedMainMacListener(this);
        }

        this.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent evt)
                {
                    tLog.setVisible(false);
                    rootWindowClosing(evt);
                }
                public void windowIconified(WindowEvent evt)
                {
                    tLog.setVisible(false);
                    rootWindowIconified(evt);
                }
                public void windowDeiconified(WindowEvent evt)
                {
                    if(!tLog.getIsClosed())
                    {
                        tLog.setVisible(true);
                    }

                    rootWindowDeiconified(evt);
                }
            }
        );

        tLog = TedLogDialog.getInstance();
        TedLog.debug(Lang.getString("TedMainDialog.LogTedStarted")); //$NON-NLS-1$

        tParseHandler = new TedParseHandler(this);

        // add title and icon to window
        this.setTitle(Lang.getString("TedMainDialog.WindowTitle")); //$NON-NLS-1$
        this.setIconImage(tedProgramIcon.getImage());

        // load the config files

        this.tedCore.setSeries(TedIO.getInstance().GetShows());
        serieTable.setSeries(this.tedCore.getSeries());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int defaultHeight = (int) Math.round(screenSize.height * 0.8);
        int defaultX = (screenSize.width - this.getWidth()) / 2;
        int defaultY = (screenSize.height - this.getHeight()) / 2;

        // set size and position of ted
        this.setSize(preferences.getInt("width", 400), preferences.getInt("height", defaultHeight));
        this.setLocation(preferences.getInt("x", defaultX), preferences.getInt("y", defaultY));
        this.setMinimumSize(new java.awt.Dimension(320, 320));

        // Save the window settings after resize or move.
        this.addComponentListener(new WindowComponentListener());

        // only if the os is supported by the trayicon program
        // currently supports windows, linux and solaris
        this.osHasTray = this.osHasTray && TedConfig.getInstance().isAddSysTray();

        if (osHasTray)
        {
            try
            {
                tedTray = new TedTrayIcon(this, tedIdleIcon);

            }
            catch (Exception e)
            {
                TedLog.error(e, "Error while adding tray icon. Disabling tray in config");
                this.osHasTray = false;
                TedConfig.getInstance().setAddSysTray(false);
                TedConfig.getInstance().setStartMinimized(false);
            }
        }
        if (TedConfig.getInstance().isStartMinimized())
        {
            if (this.osHasTray)
            {
                this.setVisible(false);
            }
            else
            {
                this.setVisible(true);
                this.toBack();
            }
        }
        else
        {
            this.setVisible(true);
        }

        tCounter = new TedCounter(this);

        // reset all previous saved statusinformation of all shows
        this.resetStatusOfAllShows(true);

        // set buttons according to selected row
        this.updateButtonsAndMenu();

        this.messengerCenter = new MessengerCenter(this);

        // if the shows.xml file does not exist download it
        if(!showsXMLExists)
        {
            this.setStatusString(Lang.getString("TedMain.CheckingNewShows"));
            TedIO.getInstance().downloadXML();
            this.TedToolBar.setAddButtonEnabled(true);
        }
        // check to see if there is a new shows.xml file available
        else if (TedConfig.getInstance().isAutoUpdateFeedList() || TedConfig.getInstance().askAutoUpdateFeedList())
        {
            this.setStatusString(Lang.getString("TedMain.CheckingNewShows"));
            TedIO.getInstance().checkNewXMLFile(this, false, serieTable);
        }

        // Check if the file is now actually present on the user's system.
        TedIO.getInstance().checkForShowsXML();

        // start the counter
        tCounter.start();

        if (TedConfig.getInstance().isCheckVersion())
        {
            this.isNewTed(false);
        }

        uiInitialized = true;
    }

    /**
     * Update all GUI elements
     * Mostly called when the locale of ted is changed
     */
    public void updateGUI()
    {
        // only if UI is initialized
        if (uiInitialized)
        {
            Lang.setLanguage(TedConfig.getInstance().getLocale());

            this.TedToolBar.updateText();
            this.serieTable.updateText();
            this.ttPopupMenu.updateText();
            tMenuBar.updateText();

            if (!this.isParsing)
            {
                this.resetStatusOfAllShows(true);
            }

            if (tedTray != null)
            {
                this.tedTray.updateText();
            }

            this.repaint();
        }
    }

    /**
     * Updates the buttons and menu of ted according to if something is selected
     * in the serie table
     */
    public void updateButtonsAndMenu()
    {
        int row;
        boolean statusDelete = false;
        boolean statusEdit = false;
        row = this.serieTable.getSelectedRow();
        if (row < 0)
        {
            statusDelete = false;
            statusEdit = false;
            this.tMenuBar.setSomethingSelected(false);
        }
        else if (!this.isParsing)
        {
            statusEdit = true;
            statusDelete = true;
            this.tMenuBar.setSomethingSelected(true);
        }
        else
        {
            statusEdit = true;
            statusDelete = false;
        }

        this.TedToolBar.setEditButtonStatus(statusEdit);
        this.TedToolBar.setDeleteButtonStatus(statusDelete);
    }

    private void rootWindowClosing(WindowEvent evt)
    {
        // if user has tray to minimize to
        if (this.osHasTray)
        {
            this.setVisible(false);
        }
        // else we shut down ted
        else
        {
            this.quit();
        }
    }

    private void rootWindowIconified(WindowEvent evt)
    {

    }

    private void rootWindowDeiconified(WindowEvent evt)
    {
    }

    /**
     * Set the current trayicon of ted
     * @param icon Icon to be set
     */
    private void setIcon(ImageIcon icon)
    {
        if (osHasTray)
        {
            tedTray.setIcon(icon);
        }
    }

    /****************************************************
     * PUBLIC METHODS
     ****************************************************/

    /**
     * Adds a show to the table
     * @param currentSerie Show to add
     */
    public void addSerie(TedSerie newSerie)
    {
        serieTable.addSerie(newSerie);
        this.tedCore.setSeries(this.serieTable.getSeries());

        // if it is the day to start checking the serie again
        newSerie.updateShowStatus();

        // Save the shows. If ted is not closed by using the File->Close
        // option, but by for example the OS when shutting down or by a
        // user using the task manager it could be that shows get lost.
        this.saveShows();

        // if the serie is not paused
        if(newSerie.isCheck())
        {
            // parse new show
            TedParseHandler handler = new TedParseHandler(newSerie, this);
            handler.start();
        }
    }

    /**
     * Parses all shows listed in the table of ted
     */
    public void parseShows()
    {
        if(!isParsing)
        {
            // first check if ted is up to date
            if(TedConfig.getInstance().getTimesParsedSinceLastCheck() == 5)
            {
                if(TedConfig.getInstance().isCheckVersion())
                {
                    isNewTed(false);
                }

                isNewPredefinedShowsXML(false);
                TedConfig.getInstance().setTimesParsedSinceLastCheck(0);
            }
            else
            {
                TedConfig.getInstance().setTimesParsedSinceLastCheck(TedConfig.getInstance().getTimesParsedSinceLastCheck()+1);
            }

            tParseHandler = new TedParseHandler(this);

            int rows = serieTable.getRowCount();
            for (int i = 0; i < rows ; i++)
            {
                TedSerie serie = serieTable.getSerieAt(i);

                tParseHandler.addParseThread(serie, false);
            }

            tParseHandler.start();
        }
    }

    /**
     * Update the countertext in the mainwindow
     * @param count Number of minutes left to next parserrround
     */
    public void updateCounter(int count)
    {
        if (count == 0)
        {
            this.label_count.setText(Lang.getString("TedMainDialog.StatusBarChecking")); //$NON-NLS-1$
        }
        else if (count == 1)
        {
            this.label_count.setText(Lang.getString("TedMainDialog.StatusBarLessThan1Minute")); //$NON-NLS-1$
        }
        else
        {
            this.label_count.setText(Lang.getString("TedMainDialog.StatusBarNextCheckInStart") + " " + count + " " + Lang.getString("TedMainDialog.StatusBarNextCheckInEnd")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        this.repaint();

    }

    /**
     * Alert the user of an error that happened while running ted
     * @param header Header of the errormessage
     * @param message Body of the errormessage
     * @param details Details that are posted in the logwindow only
     */
    public void displayError(String header, String message, String details)
    {
        this.messengerCenter.displayError(header, message);

        TedLog.error(message+"\n"+details); //$NON-NLS-1$
    }

    /**
     * Show the user that ted found an episode of a specific show
     * @param header Header of the message
     * @param message Body of the message
     * @param details Details that are posted to the logwindow only
     */
    public void displayHurray(String header, String message, String details)
    {
        this.messengerCenter.displayHurray(header, message);
        TedLog.debug(message+"\n"+details); //$NON-NLS-1$
    }

    public void actionPerformed(ActionEvent e)
    {
        // handles all the events on the ted mainwindow
        String action = e.getActionCommand();

        if(action.equals("Preferences...")) //$NON-NLS-1$
        {
            new ConfigDialog(this);
        }
        else if(action.equals("Log")) //$NON-NLS-1$
        {
            // show log
            tLog.setIsClosed(false);
            tLog.setLines(tLog.getMaxLines()); //Log is created before reading the config
            tLog.resetAction();
            tLog.setVisible(true);
        }
        else if(action.equals("Delete")) //$NON-NLS-1$
        {
            serieTable.DeleteSelectedShows();
        }
        else if(action.equals("New")) //$NON-NLS-1$
        {
            AddShowDialog asd = new AddShowDialog(this);
        }
        else if(action.equals("Exit")) //$NON-NLS-1$
        {
            this.quit();
        }
        else if(action.equals("Edit")) //$NON-NLS-1$
        {
            this.showEditShowDialog();
        }
        else if (action.equals("parse selected")) //$NON-NLS-1$
        {
            // parse only the selected show, regardles of the status it has
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            for (int i = 0; i < selectedShows.length; i++)
            {
                TedParseHandler handler = new TedParseHandler(selectedShows[i], this, true);
                handler.start();
            }

        }
        else if (action.equals("setstatusenabled"))
        {
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            for (int i = 0; i < selectedShows.length; i++)
            {
                selectedShows[i].setStatus(TedSerie.STATUS_CHECK);
                selectedShows[i].updateShowStatus();
            }

            this.saveShows();
        }
        else if (action.equals("setstatusdisabled"))
        {
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            for (int i = 0; i < selectedShows.length; i++)
            {
                selectedShows[i].setStatus(TedSerie.STATUS_DISABLED);
            }

            this.saveShows();
        }
        else if (action.equals("setautoscheduleenabled"))
        {
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            for (int i = 0; i < selectedShows.length; i++)
            {
                selectedShows[i].setUseAutoSchedule(true);
            }

            this.saveShows();
        }
        else if (action.equals("setautoscheduledisabled"))
        {
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            for (int i = 0; i < selectedShows.length; i++)
            {
                selectedShows[i].setUseAutoSchedule(false);
            }

            this.saveShows();
        }
        else if (action.equals("checkupdates")) //$NON-NLS-1$
        {
            this.isNewTed(true);
        }
        else if (action.equals("checkRSS")) //$NON-NLS-1$
        {
            this.isNewPredefinedShowsXML(true);
        }
        else if (action.equals("help")) //$NON-NLS-1$
        {
            // try to open the ted documentation website
            try
            {
                BrowserLauncher.openURL("http://www.ted.nu/documentation/"); //$NON-NLS-1$
            }
            catch (IOException ep)
            {
                // error launching ted website
                // TODO: add error message
                System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
                ep.printStackTrace();
            }
        }
        else if (action.equals("opensite")) //$NON-NLS-1$
        {
            // try to open the ted website
            try
            {
                BrowserLauncher.openURL("http://www.ted.nu/"); //$NON-NLS-1$
            }
            catch (IOException ep)
            {
                // error launching ted website
                // TODO: add error message
                System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
                ep.printStackTrace();
            }
        }
        else if (action.equals("Donate")) //$NON-NLS-1$
        {
            // try to open the ted website
            try
            {
                BrowserLauncher.openURL("http://www.ted.nu/donate.php"); //$NON-NLS-1$
            }
            catch (IOException ep)
            {
                // error launching ted website
                // TODO: add error message
                System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
                ep.printStackTrace();
            }
        }
        else if (action.equals("DownloadTed"))
        {
            // try to open the ted website
            try
            {
                BrowserLauncher.openURL("http://www.ted.nu/download.php"); //$NON-NLS-1$
            }
            catch (IOException ep)
            {
                // error launching ted website
                // TODO: add error message
                System.out.println(Lang.getString("TedMainDialog.LogErrorWebsite")); //$NON-NLS-1$
                ep.printStackTrace();
            }
        }
        else if (action.equals("DownloadXml"))
        {
            TedIO.getInstance().downloadXML();
            TedIO.getInstance().updateShows(serieTable);
        }
        else if (action.equals("buydvd")) //$NON-NLS-1$
        {
            int rows = serieTable.getRowCount();

            // loop through all the shows and put names in address

            String names = "";
            for (int i = 0; i < rows ; i++)
            {
                String spacer = "|";
                if (i == rows-1)
                {
                    spacer = "";
                }
                TedSerie serie = serieTable.getSerieAt(i);
                String name;
                try
                {
                    name = URLEncoder.encode("\""+serie.getName()+"\""+spacer, "UTF-8");
                    names += name;
                }
                catch (UnsupportedEncodingException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            this.openBuyLink(names);
        }
        else if (action.equals("buyDVDselectedshow"))
        {
            // try to open the amazon.com website

            // get selected showname
            TedSerie[] selectedShows = serieTable.getSelectedShows();

            String names = "";
            String spacer = "|";
            String name;

            for (int i = 0; i < selectedShows.length; i++)
            {
                if (i == selectedShows.length-1)
                {
                    spacer = "";
                }
                try
                {
                    name = URLEncoder.encode("\""+selectedShows[i].getName()+"\""+spacer, "UTF-8");
                    names += name;
                }
                catch (UnsupportedEncodingException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            this.openBuyLink(names);
        }


        else if(action.equals("PressAction")) //$NON-NLS-1$
        {
            this.setVisible(true);
            this.toFront();
            if(!tLog.getIsClosed())
            {
                tLog.setVisible(true);
            }
        }
        else if(action.equals("Help")) //$NON-NLS-1$
        {
            // show logwindow
            tLog.setVisible(true);
        }
        else if(action.equals("Parse")) //$NON-NLS-1$
        {
            // parse all shows
            this.parseShows();
        }
        else if(action.equals("stop parsing")) //$NON-NLS-1$
        {
            tParseHandler.stopParsing();
            this.TedToolBar.setParseButtonStatus(false);
            this.TedToolBar.setParseButtonText(Lang.getString("TedMainDialog.ButtonCheckShowsStopping"));
            this.resetStatusOfAllShows(true);
        }
        else if(action.equals("About ted")) //$NON-NLS-1$
        {
            this.showAboutDialog();
        }
        else if(action.equals("Export")) //$NON-NLS-1$
        {
            this.saveShows();
            exportShows();
        }
        else if (action.equals("Import"))
        {
            importShows();
            serieTable.setSeries(this.tedCore.getSeries());
        }
        else if(action.equals("synchronize")) //$NON-NLS-1$
        {
            TedIO.getInstance().UpdateShow(false, serieTable);
            serieTable.fireTableDataChanged();
        }
        else if(action.equals("translate"))
        {
            TedTranslateDialog trans = new TedTranslateDialog();
            trans.setVisible(true);
        }
        else if(action.equals("language"))
        {
            this.openTranslationLink();
        }
        else if (action.equals("sort_name"))
        {
            TedConfig.getInstance().setSortType(TedConfig.SORT_NAME);
            this.tMenuBar.updateSortMenu();
            this.serieTable.sort();
        }
        else if (action.equals("sort_status"))
        {
            TedConfig.getInstance().setSortType(TedConfig.SORT_STATUS);
            this.tMenuBar.updateSortMenu();
            this.serieTable.sort();
        }
        else if (action.equals("sort_ascending"))
        {
            TedConfig.getInstance().setSortDirection(TedConfig.SORT_ASCENDING);
            this.tMenuBar.updateSortMenu();
            this.serieTable.sort();
        }
        else if (action.equals("sort_descending"))
        {
            TedConfig.getInstance().setSortDirection(TedConfig.SORT_DESCENDING);
            this.tMenuBar.updateSortMenu();
            this.serieTable.sort();
        }
        else if (action.equals("editAllShows"))
        {
            // select all shows in table
            this.serieTable.selectAll();

            // open edit dialog
            this.showEditShowDialog();
        }
    }

    public void showEditShowDialog()
    {
        TedSerie[] selectedShows = serieTable.getSelectedShows();
        if (selectedShows.length == 1)
        {
            new EditShowDialog(this, selectedShows[0], false);
        }
        else if (selectedShows.length > 1)
        {
            new EditMultipleShowsDialog(this, selectedShows);
        }
    }

    public void showAboutDialog()
    {
        new TedAboutDialog(tedVersion);
    }

    public void showPreferencesDialog()
    {
        new ConfigDialog(this);
    }

    public void quit()
    {
        // close ted
        this.saveShows();
        this.saveConfig(false);
        System.exit(0);
    }

    /**
     * Saves the shows that are in displayed in the ted window
     */
    public void saveShows()
    {
        serieTable.sort();
        tedCore.setSeries(this.serieTable.getSeries());
        tedCore.saveShows();
        this.repaint();
    }

    /**
     * Save the configuration of ted
     * @param resetTime Parse interval
     */
    public void saveConfig(boolean resetTime)
    {
        // set the current width, heigth and position of the window
        this.preferences.putInt("width", this.getWidth());
        this.preferences.putInt("height", this.getHeight());
        this.preferences.putInt("x", this.getX());
        this.preferences.putInt("y", this.getY());
        try {
            this.preferences.flush();
        } catch (BackingStoreException e) {
            // TODO-KMD: Unable to save preferences. Log and ignore
        }

        tedCore.saveConfig(resetTime);
        this.updateGUI();

        // notify counter of refreshtime change
        if (resetTime && tCounter.isAlive())
        {
            tCounter.updateRefreshTime();
        }

    }

    /**
     * Check if there is a new version of ted available
     * @param show Show the user if he has the current version
     */
    public void isNewTed(boolean show)
    {
        // check the website if there is a new version availble
        double currentVersion = TedIO.getInstance().checkNewTed(TedMainDialog.tedVersion);

        if (currentVersion > TedMainDialog.tedVersion)
        {
            String title = Lang.getString("TedMainDialog.DialogNewVersionHeader");
            String message = "<html><body><b>" + title + "</b><br>"+Lang.getString("TedMainDialog.DialogNewVersion1Begin")+ " (" + currentVersion + ") "+ Lang.getString("TedMainDialog.DialogNewVersion1End") + //$NON-NLS-1$ //$NON-NLS-2$
                                            "<br>" + Lang.getString("TedMainDialog.DialogNewVersion2")+ " " +  TedMainDialog.tedVersion + ". <br>" +  //$NON-NLS-1$
                                            Lang.getString("TedMainDialog.DialogNewVersion3") + "</body></html>";

            new TedUpdateWindow(title,
                                message,
                                "http://ted.sourceforge.net/newtedinfo.php",
                                "DownloadTed",
                                Lang.getString("TedGeneral.ButtonDownload"),
                                Lang.getString("TedGeneral.ButtonLater"),
                                this);

            return;
        }
        else if (show)
        {
            JOptionPane.showMessageDialog(null, Lang.getString("TedMainDialog.DialogLatestVersionBegin")
                     + " ("+ TedMainDialog.tedVersion + ") " +
                     Lang.getString("TedMainDialog.DialogLatestVersionEnd")); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }
    }

    public void isNewPredefinedShowsXML(boolean show)
    {
        TedIO.getInstance().checkNewXMLFile(this, show, serieTable);
    }

    /**
     *  Reset all statusmessages for all series in the window to the default
     * @param resetCheck Reset status messages of shows with status CHECK?
     */
    private void resetStatusOfAllShows(Boolean resetCheck)
    {
        int rows = serieTable.getRowCount();
        for (int i = 0; i < rows ; i++)
        {
            TedSerie serie = serieTable.getSerieAt(i);
            // only reset the statusstring if the status of the show is not check
            if (serie.isCheck())
            {
                serie.resetStatus(resetCheck);
            }
            else
            {
                serie.resetStatus(true);
            }
        }
        serieTable.sort();
        this.repaint();
    }


    /****************************************************
     * GETTERS & SETTERS
     ****************************************************/

    /**
     * Set the GUI elements to parsing, disable delete button and menuitem
     * and change parsing button to "stop parsing"
     */
    public void setStatusToParsing()
    {
        this.isParsing = true;
        this.setStopParsing(false);

        // set icon
        this.setIcon(tedActiveIcon);

        // disable delete buttons and menu items
        updateButtonsAndMenu();

        this.tMenuBar.setParsing();
        this.ttPopupMenu.setParsing();
        this.TedToolBar.setParsing();
    }

    /**
     * Set the status of the GUI to idle. So ted is not parsing
     */
    public void setStatusToIdle()
    {
        this.isParsing = false;

        // set icon
        this.setIcon(tedIdleIcon);
        this.resetStatusOfAllShows(false);

        // enable buttons and menu items
        this.tMenuBar.setIdle();
        this.ttPopupMenu.setIdle();
        this.TedToolBar.setIdle();
        updateButtonsAndMenu();
    }

    /**
     * @return Wheter the user has clicked the stop-parsing button
     */
    public boolean getStopParsing()
    {
        return this.stopParsing;
    }

    public void setStopParsing(boolean b)
    {
        this.stopParsing = b;
    }

    /**
     * Update the status bar with a new text
     * @param status
     */
    public void setStatusString(String status)
    {
        this.label_count.setText(status);
    }




    /**
     * Open buy dvd website of amazon.com for specific show
     * @param name name of the show
     */
    public void openBuyLink(String name)
    {
        TedXMLParser parser = new TedXMLParser();
        Element nl = parser.readXMLFromFile(TedIO.XML_SHOWS_FILE);
        Vector<String> locations = parser.getAmazonURLs(nl);

        if(locations.size()==3)
        {
            try
            {
                // open search for dvds
                BrowserLauncher.openURL(locations.get(0)+name+locations.get(1)); //$NON-NLS-1$
            }
            catch (Exception ep)
            {
                // error launching ted website
                // TODO: add error message
                System.out.println(Lang.getString("TedLog.AmazonError")); //$NON-NLS-1$
                ep.printStackTrace();
            }
        }
        else
        {
            TedLog.error(Lang.getString("TedLog.ShowListCorrupt"));
        }

    }

    /**
     * Open website where the latest translations can be donwloaded
     * @param name name of the show
     */
    public void openTranslationLink()
    {
        try
        {
            BrowserLauncher.openURL("http://www.ted.nu/wiki/index.php/Latest_translations"); //$NON-NLS-1$
        }
        catch (Exception ep)
        {
            // error launching ted website
            System.out.println("Error while opening language website"); //$NON-NLS-1$
        }

    }

    /** @return Whether the current OS has an active tray icon
      */
    public boolean osHasTray()
    {
        return this.osHasTray;
    }

    /**
     * @return The current tray icon
     */
    public TedTrayIcon getTrayIcon()
    {
        return this.tedTray;
    }

    public TedTable getSerieTable()
    {
        return this.serieTable;
    }

    public TedTablePopupMenu getPopupMenu()
    {
        return this.ttPopupMenu;
    }


    public void updateAllSeries()
    {
        // update status of all series if table is available
        if (this.getSerieTable() != null)
        {
            this.getSerieTable().updateAllSeries();
            this.updateGUI();
        }
    }


    /**
     * Method to forward the disabled status of the selected shows to the
     * main menu bar
     * @param firstShowDisabled
     * @param showBothOptions
     */
    public void checkDisabled(boolean firstShowDisabled, boolean showBothOptions)
    {
        this.tMenuBar.checkDisabled(firstShowDisabled, showBothOptions);
    }

    /**
     * Method to forward the automatic scheduler status of the selected shows to the
     * main menu bar
     * @param firstShowSchedulerEnabled
     * @param showBothOptions
     */
    public void checkAutoSchedule(boolean firstShowEnabled, boolean showBothOptions)
    {
        this.tMenuBar.checkAutoSchedule(firstShowEnabled, showBothOptions);
    }


    public void importShows()
    {
        JFileChooser chooser = new JFileChooser();
        TedFileFilter filter = new TedFileFilter();
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                String fileIn = chooser.getSelectedFile().getCanonicalPath();

                TedIO.importFromShowFile(fileIn);
            }
            catch (IOException e)
            {
                TedLog.error(e.toString());
            }
        }
    }

    public void exportShows()
    {
        JFileChooser chooser = new JFileChooser();
        TedFileFilter filter = new TedFileFilter();
        chooser.setFileFilter(filter);

        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                String fileOut = chooser.getSelectedFile().getCanonicalPath();

                TedIO.exportToShowFile(fileOut);
            }
            catch (IOException e)
            {
                TedLog.error(e.toString());
            }
        }
    }

    class TedFileFilter extends FileFilter
    {
        public boolean accept(File f)
        {
            return f.toString().toLowerCase().endsWith(".ted");
        }

        public String getDescription()
        {
            return "show definitions";
        }
    }
}
