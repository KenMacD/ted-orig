package ted.ui.addshowdialog;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


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
public class SubscribeOptionsPanel extends JPanel
								   implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static final int DOWNLOAD_LATEST = 0;
	public static final int ONLY_SUBSCRIBE  = 1;
	public static final int SELECT_EPISODE  = 2;
	
	private ButtonGroup subscribeOptions;
	private int selectedOption = 0;
	
	public SubscribeOptionsPanel()
	{
		init();
	}
	
	private void init()
	{
		String buttonString = "Subscribe to this show and download latest episode";
		JRadioButton downloadLatestAndSubscribe = new JRadioButton(buttonString);
		downloadLatestAndSubscribe.setActionCommand("latest");
		downloadLatestAndSubscribe.addActionListener(this);
				
		buttonString = "Only subscribe to this show";
		JRadioButton OnlySubscribe = new JRadioButton(buttonString);
		OnlySubscribe.setActionCommand("only");
		OnlySubscribe.addActionListener(this);
		
		buttonString = "Select episode from which subscription should start";
		JRadioButton SubscribeAndSelectEpisode = new JRadioButton(buttonString);
		SubscribeAndSelectEpisode.setActionCommand("select");
		SubscribeAndSelectEpisode.addActionListener(this);
		
		subscribeOptions = new ButtonGroup();
		subscribeOptions.add(downloadLatestAndSubscribe);
		subscribeOptions.add(OnlySubscribe);
		subscribeOptions.add(SubscribeAndSelectEpisode);
		
		downloadLatestAndSubscribe.setSelected(true);
		
		BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
		this.setLayout(thisLayout);
		
		this.add(downloadLatestAndSubscribe);
		this.add(OnlySubscribe);
		this.add(SubscribeAndSelectEpisode);
	}
	
	public int getSelectedOption()
	{
		return selectedOption;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		String action = arg0.getActionCommand();
		
		if (action.equals("latest"))
		{
			selectedOption = DOWNLOAD_LATEST;
		}
		else if (action.equals("only"))
		{
			selectedOption = ONLY_SUBSCRIBE;
		}
		else if  (action.equals("select"))
		{
			selectedOption = SELECT_EPISODE;
		}
	}
}
