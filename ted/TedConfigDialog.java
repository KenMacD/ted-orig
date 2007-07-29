package ted;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;

import ted.ui.configdialog.AdvancedPanel;
import ted.ui.configdialog.GeneralPanel;
import ted.ui.configdialog.LooknFeelPanel;
import ted.ui.configdialog.UpdatesPanel;

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
 * TED: Torrent Episode Downloader (2005 - 2006)
 * 
 * This is the dialog that a user can use to edit ted's configuration
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
public class TedConfigDialog extends javax.swing.JDialog
{

	/****************************************************
	 * GLOBAL VARIABLES
	 ****************************************************/
	private static final long serialVersionUID = 1L;
	//TedConfig config;
	private JButton jHelpButton;
	private JPanel jConfigTabs;
	private JButton Save_Button;
	private JButton Annuleer_Button;
	private TedMainDialog main;
	private boolean show_cancel_btn;
	TedConfigListener TCListener = new TedConfigListener(this);
	JToggleButton generalButton;
    JToggleButton looknfeelButton;
    JToggleButton advancedButton;
    JToggleButton updatesButton;
	private GeneralPanel generalPanel;
	private LooknFeelPanel looknfeelPanel;
	private AdvancedPanel advancedPanel;
	private UpdatesPanel updatesPanel;
	private int width = 400;
	private int height = 450;
	
	/****************************************************
	 * CONSTRUCTOR
	 ****************************************************/
	/**
	 * Create a new config dialog
	 * @param frame Main window of ted
	 * @param tc Current TedConfig
	 * @param showcancelbutton Show a cancel button in the config dialog?
	 */
	public TedConfigDialog(TedMainDialog frame, boolean showcancelbutton) 
	{
		super(frame);
		this.setModal(true);
		this.setResizable(false);
		this.main = frame;
		//config = tc;
		this.show_cancel_btn = showcancelbutton;
		initGUI();
	}
	
	/****************************************************
	 * LOCAL METHODS
	 ****************************************************/
	private void initGUI() 
	{
		try 
		{

			this.setSize(this.width, this.height);


		    //Get the screen size
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Dimension screenSize = toolkit.getScreenSize();

		    //Calculate the frame location
		    int x = (screenSize.width - this.getWidth()) / 2;
		    int y = (screenSize.height - this.getHeight()) / 2;

		    //Set the new frame location
		    this.setLocation(x, y);

			// prevent the dialog from being closed other then the cancel/save buttons
			if (!this.show_cancel_btn)
			{
				this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			}

			loadConfig();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the values of the config into the dialog
	 */
	private void loadConfig()
	{
		this.getContentPane().setLayout(null);
		this.setTitle(Lang.getString("TedConfigDialog.WindowTitle")); //$NON-NLS-1$

		{
			if (this.show_cancel_btn)
			{
				this.Annuleer_Button = new JButton();
				this.getContentPane().add(Annuleer_Button);
				this.Annuleer_Button.setText(Lang.getString("TedConfigDialog.ButtonCancel")); //$NON-NLS-1$
				this.Annuleer_Button.setBounds(175, 378, 98, 28);
				this.Annuleer_Button.addActionListener(TCListener);
				this.Annuleer_Button.setActionCommand("Cancel");
			}
		}
		{
			this.Save_Button = new JButton();
			this.getContentPane().add(Save_Button);
			this.Save_Button.setText(Lang.getString("TedConfigDialog.ButtonSave")); //$NON-NLS-1$
			this.Save_Button.setBounds(287, 378, 98, 28);
			this.Save_Button.addActionListener(TCListener);
			this.Save_Button.setActionCommand("Save");
		}

		this.jConfigTabs = new JPanel(new CardLayout());
		getContentPane().add(this.jConfigTabs);
		this.jConfigTabs.setBounds(0, 75, 400, 300);
		
		TedConfigDialogToolBar toolBarPanel = new TedConfigDialogToolBar(this);
		getContentPane().add(toolBarPanel);
		toolBarPanel.setBounds(0, 0, 400, 70);
		
		//jConfigTabs.setModel(toolBarPanel);
		
		toolBarPanel.setBackground(Color.WHITE);

		this.jHelpButton = new JButton();
		getContentPane().add(this.jHelpButton);
		this.jHelpButton.setIcon(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/help.png")));
		this.jHelpButton.setActionCommand("Help");
		this.jHelpButton.setBounds(11, 380, 28, 28);
		this.jHelpButton.addActionListener(this.TCListener);
		
		this.generalPanel = new GeneralPanel();
		this.jConfigTabs.add("general", this.generalPanel);
		this.generalPanel.setValues();
		
		this.looknfeelPanel = new LooknFeelPanel(this.main);
		this.jConfigTabs.add("looknfeel", this.looknfeelPanel);
		this.looknfeelPanel.setValues();
		
		this.advancedPanel = new AdvancedPanel();
		this.jConfigTabs.add("advanced", this.advancedPanel);
		this.advancedPanel.setValues();
		
		this.updatesPanel = new UpdatesPanel(this.main);
		this.jConfigTabs.add("updates", this.updatesPanel);
		this.updatesPanel.setValues();
	}

	

	/****************************************************
	 * PUBLIC METHODS
	 ****************************************************/
	/**
	 * Save the values from the dialog into the TedConfig
	 */
	public void saveConfig()
	{
		int currentTime = TedConfig.getTimeOutInSecs();
		boolean resetTime = false;
		
		if (this.generalPanel.checkValues() && this.looknfeelPanel.checkValues() && this.advancedPanel.checkValues() && this.updatesPanel.checkValues())
		{		
			this.generalPanel.saveValues();
			this.looknfeelPanel.saveValues();
			this.advancedPanel.saveValues();
			this.updatesPanel.saveValues();
			
			
			int newTime = TedConfig.getTimeOutInSecs();
			
			if (currentTime != newTime)
			{
				resetTime = true;
			}
			
			this.main.saveConfig(resetTime);
			this.setVisible(false);
		}
	}

	/**
	 * Return int value from textfield. If no int, display error
	 * @param field
	 * @return
	 */
	private int getIntValue(JTextField field, String fieldName)
	{
		int value = -1;
		try
		{
			value = Integer.parseInt(field.getText());
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, field.getText() + " " + Lang.getString("TedConfigDialog.DialogNotANumber1") 
											+ " " + fieldName + ". " + Lang.getString("TedConfigDialog.DialogNotANumber2"));
			return -1;
		}
		
		if (value < 0)
		{
			JOptionPane.showMessageDialog(this, field.getText() + " " + Lang.getString("TedConfigDialog.DialogNotANumber1") 
					+ " " + fieldName + ". " + Lang.getString("TedConfigDialog.DialogNotANumber2"));
			return -1;
		}
		
		return value;
	}

	
	
	
	

	public void showPanel(String command)
	{
		CardLayout cl = (CardLayout)(this.jConfigTabs.getLayout());
	    cl.show(this.jConfigTabs, command);
		
	}

}
