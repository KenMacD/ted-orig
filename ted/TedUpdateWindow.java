package ted;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.Dimension;
import java.awt.Toolkit;
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
	
	private JScrollPane updateInfoScrollPane;
	private JTextPane updateInfoPane;
	private JButton okButton;
	private JButton cancelButton;
	private JButton donateButton;
	private String okActionCommand;
	private TedMainDialog mainDialog;
	String startHTML = "<html><font face=\"Arial, Helvetica, sans-serif\">";
	String endHTML = "</font></html>";

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
			this.getShowInfoPane().setPage(url);
		} 
		catch (IOException e) 
		{
			this.getShowInfoPane().setText(startHTML + Lang.getString("TedUpdateWindow.ErrorLoadingUpdateInfo") + endHTML);
		}
		this.setTitle(title);	
		this.setResizable(false);
	}
	
	private void initGUI(String message) 
	{
		try 
		{
			FormLayout thisLayout = new FormLayout(
					"max(p;5dlu), 15dlu:grow, max(p;15dlu), max(p;15dlu), max(p;15dlu), max(p;5dlu)", 
					"2dlu, max(p;15dlu), 5dlu, 30dlu:grow, 5dlu, max(p;5dlu), max(p;5dlu)");
			
			getContentPane().setLayout(thisLayout);
			this.setSize(500, 500);
			
			JLabel infoLabel = new JLabel(message);
			
			{
				getContentPane().add(getShowInfoScrollPane(), new CellConstraints("2, 4, 4, 1, fill, fill"));
				getContentPane().add(getDonateButton(), new CellConstraints("2, 6, 1, 1, left, default"));
				getContentPane().add(infoLabel, new CellConstraints("2, 2, 4, 1, fill, fill"));
				getContentPane().add(getCancelButton(), new CellConstraints("4, 6, 1, 1, default, default"));
				getContentPane().add(getOkButton(), new CellConstraints("5, 6, 1, 1, default, default"));
			}
			
			// Get the screen size
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Dimension screenSize = toolkit.getScreenSize();

		    //Calculate the frame location
		    int x = (screenSize.width - this.getWidth()) / 2;
		    int y = (screenSize.height - this.getHeight()) / 2;

		    //Set the new frame location
		    this.setLocation(x, y);
			
			this.setVisible(true);

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
		
	private JTextPane getShowInfoPane() 
	{
		if (updateInfoPane == null) 
		{
			updateInfoPane = new JTextPane();
			updateInfoPane.setContentType( "text/html" );
			updateInfoPane.setEditable( false );
			updateInfoPane.setText(startHTML + Lang.getString("TedUpdateWindow.LoadingUpdateInfo") + endHTML);
			
			//	Set up the JEditorPane to handle clicks on hyperlinks
		    updateInfoPane.addHyperlinkListener(new HyperlinkListener() 
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
		return updateInfoPane;
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
		if (updateInfoScrollPane == null) 
		{
			updateInfoScrollPane = new JScrollPane();
			updateInfoScrollPane.setViewportView(getShowInfoPane());
			updateInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return updateInfoScrollPane;
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
			donateButton.setText(Lang.getString("TedUpdateWindow.ButtonDonate"));
			donateButton.addActionListener(this);
			donateButton.setActionCommand("donate");
			
		}
		
		return donateButton;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		
		if (action.equals(okActionCommand))
		{
			mainDialog.actionPerformed(arg0);
			
			this.setVisible(false);
			this.dispose();
		}
		else if (action.equals("cancel"))
		{
			this.setVisible(false);
			this.dispose();
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
