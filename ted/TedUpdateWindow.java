package ted;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


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
public class TedUpdateWindow extends JDialog implements ActionListener
{
	private static final long serialVersionUID = -8032924816220581954L;
	
	private JScrollPane showInfoScrollPane;
	private JTextPane showInfoPane;
	private JButton okButton;
	private JButton cancelButton;
	private JButton donateButton;
	private JPanel mainPanel;
	private JLabel donateLabel;
	private String okActionCommand;
	private TedMainDialog mainDialog;

	public TedUpdateWindow(String title,
						   String message,
						   String url,
						   String actionCommand,
						   TedMainDialog mainDialog)
	{
		this.mainDialog = mainDialog;
		this.okActionCommand = actionCommand;
		
		this.initGUI(message);
		
		try 
		{
			showInfoPane.setPage(url);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle(title);		
		this.setAlwaysOnTop(true);
	}
	
	private void initGUI(String message) 
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 15dlu:grow, max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;5dlu)", 
					"max(p;5dlu), max(p;15dlu), 5dlu, max(p;5dlu), max(p;5dlu)");
			
			getContentPane().setLayout(thisLayout);
			this.setSize(500, 335);
			
			JLabel infoLabel = new JLabel(message);
			
			{
				getContentPane().add(getMainPanel(), new CellConstraints("2, 1, 4, 1, default, default"));
				getContentPane().add(getDonateLabel(), new CellConstraints("4, 2, 1, 1, default, default"));
				getContentPane().add(getDonateButton(), new CellConstraints("5, 2, 1, 1, default, default"));
				getContentPane().add(infoLabel, new CellConstraints("2, 4, 2, 1, default, default"));
				getContentPane().add(getCancelButton(), new CellConstraints("4, 4, 1, 1, default, default"));
				getContentPane().add(getOkButton(), new CellConstraints("5, 4, 1, 1, default, default"));
			}
			
			this.setVisible(true);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	private JTextPane getShowInfoPane() 
	{
		if (showInfoPane == null) 
		{
			showInfoPane = new JTextPane();
			showInfoPane.setContentType( "text/html" );
			showInfoPane.setEditable( false );

			showInfoPane.setPreferredSize(new java.awt.Dimension(475, 228));
			
			//	Set up the JEditorPane to handle clicks on hyperlinks
		    showInfoPane.addHyperlinkListener(new HyperlinkListener() 
		    {
		      public void hyperlinkUpdate(HyperlinkEvent e) 
		      {
				// Handle clicks; ignore mouseovers and other link-related events
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
				{
				  // Get the HREF of the link and display it.
					try {
						BrowserLauncher.openURL(e.getDescription());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				}
		      }
		    });
			
		}
		return showInfoPane;
	}
	
	private JPanel getMainPanel() {
		if(mainPanel == null) 
		{
			mainPanel = new JPanel();
			mainPanel.add(getShowInfoScrollPane());

			// create a new infoPane to (correctly) show the information
			showInfoPane = null;
			showInfoScrollPane.setViewportView(this.getShowInfoPane());
			
			mainPanel.add(showInfoScrollPane);
		}
		return mainPanel;
	}
	
	private JButton getOkButton() {
		if(okButton == null) {
			okButton = new JButton();
			okButton.setText(Lang.getString("TedGeneral.ButtonOk"));
			okButton.addActionListener(this);
			okButton.setActionCommand(okActionCommand);
		}
		return okButton;
	}
	
	private JScrollPane getShowInfoScrollPane() 
	{
		if (showInfoScrollPane == null) 
		{
			showInfoScrollPane = new JScrollPane();
			showInfoScrollPane.setViewportView(getShowInfoPane());
			showInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return showInfoScrollPane;
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Lang.getString("TedGeneral.ButtonCancel"));
			cancelButton.addActionListener(this);
			cancelButton.setActionCommand("cancel");
		}
			
		return cancelButton;
	}

	public JButton getDonateButton() {
		if (donateButton == null) {
			donateButton = new JButton();
			donateButton.setText(Lang.getString("TedGeneral.Donate"));
			donateButton.addActionListener(this);
			donateButton.setActionCommand("donate");
			
		}
		
		return donateButton;
	}
	
	public JLabel getDonateLabel() {
		if (donateLabel == null) {
			donateLabel = new JLabel(Lang.getString("TedMainMenuBar.Support"));
		}
		
		return donateLabel;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		
		if (action.equals(okActionCommand))
		{
			mainDialog.actionPerformed(arg0);
			
			this.setVisible(false);
			
			// TODO: Delete this window.
		}
		else if (action.equals("cancel"))
		{
			this.setVisible(false);
			
			// TODO: Delete this window.
		}
		else if (action.equals("donate"))
		{
			try 
			{
				BrowserLauncher.openURL("http://www.ted.nu/donate.php"); //$NON-NLS-1$
			} 
			catch (IOException e) 
			{
				// error launching ted website
			}
		}
	}
}
