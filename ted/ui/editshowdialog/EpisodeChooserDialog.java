package ted.ui.editshowdialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JPanel;

import ted.Lang;
import ted.TedSerie;
import ted.ui.addshowdialog.EpisodeChooserPanel;
import ted.ui.addshowdialog.EpisodeParserThread;


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
public class EpisodeChooserDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3196371120607378813L;
	private JButton cancelButton;
	private JButton okButton;
	private EpisodeChooserPanel episodeChooserPanel;

	public EpisodeChooserDialog(JDialog frame)
	{
		super(frame);
		this.setModal(true);
		this.setResizable(false);
		this.initGUI();
	}
	
	private void initGUI() {
		try {
			{
				this.setLayout(null);
				okButton = new JButton();
				this.add(okButton);
				okButton.setText(Lang.getString("TedGeneral.ButtonOk"));
				okButton.setBounds(280, 238, 98, 28);
			}
			{
				cancelButton = new JButton();
				this.add(cancelButton);
				cancelButton.setText(Lang.getString("TedEpisodeDialog.ButtonCancel"));
				cancelButton.setBounds(189, 238, 84, 28);
				
				episodeChooserPanel = new EpisodeChooserPanel();
				this.add(episodeChooserPanel);
				episodeChooserPanel.setBounds(0, 0, 385, 231);
				
				
			}
			{
				this.setSize(385, 295);
				
//				Get the screen size
			    Toolkit toolkit = Toolkit.getDefaultToolkit();
			    Dimension screenSize = toolkit.getScreenSize();

			    //Calculate the frame location
			    int x = (screenSize.width - this.getWidth()) / 2;
			    int y = (screenSize.height - this.getHeight()) / 2;

			    //Set the new frame location
			    this.setLocation(x, y);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadEpisodes(TedSerie show)
	{
		EpisodeParserThread ept = new EpisodeParserThread(this.episodeChooserPanel, show);
		ept.setPriority( Thread.NORM_PRIORITY - 1 ); 
		
		ept.start();		
	}

}
